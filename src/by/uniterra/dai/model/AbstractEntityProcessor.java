/**
 * Filename  : AbstractEntityProcessor.java
 *
 * ***************************************************************************
 * Copyright (C) 2001-2014 by ENVINET GmbH
 * Hans-Pinsel-Str. 4 
 * 85540 Haar / Munich
 * info@envinet.com
 *                                                      
 * This material is a CONFIDENTIAL, unpublished work of authorship
 * created in 2001 and subsequently updated. It is a TRADE SECRET which
 * is property of ENVINET GmbH.
 *
 * All use, disclosure, and/or reproduction not specifically authorized
 * by ENVINET GmbH in writing is prohibited. All rights reserved.
 ***************************************************************************
 * Project    : amcCommon
 *
 * Author     : Anton Nedbailo
 *
 * last change by : $Author:$ 
 * last check in  : $Date: $
 */

package by.uniterra.dai.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * The <code>Processor</code> is used to execute some operation on given Entity in transaction/nontransaction mode

 * @author Anton Nedbailo
 * @since Apr 28, 2014
 */
public abstract class AbstractEntityProcessor<T>
{
    //members
    protected T tEntity;
    protected EntityManager emManager;
    
    /**
     * 
     * Constructor.

     * @param entity - entity to be processed
     * @param emManager - associated EntityManager 
     */
    public AbstractEntityProcessor(T entity, EntityManager emManager)
    {
        tEntity = entity;
        this.emManager = emManager;
    }
    
    /**
     * A method which will do main process operation
     * 
     * @param entity - entity to be processed
     * @param emManager - associated EntityManager
     * 
     * @return - processed entity
     */
    abstract protected T doProcess();
    
    /**
     * Execute process operation
     * 
     * @return - processed object
     */
    public T execute()
    {
        EntityTransaction etTransaction = emManager.getTransaction();
        // check transaction object
        if (etTransaction.isActive())
        {
            // expected that we use one transaction for several operation with a possibility to make one roll back
            tEntity = doProcess();
        }
        else
        {
            // we should do own transaction
            try
            {
                etTransaction.begin();
                tEntity = doProcess();
                etTransaction.commit();
            }
            catch(Exception e)
            {
                if(etTransaction.isActive())
                {
                    etTransaction.rollback();
                }
                throw e;
            }
        }
        return tEntity;
    }
}
