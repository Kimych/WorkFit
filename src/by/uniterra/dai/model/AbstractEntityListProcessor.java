/**
 * Filename  : AbstractEntityListProcessor.java
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
 * Author     : user
 *
 * last change by : $Author:$ 
 * last check in  : $Date: $
 */

package by.uniterra.dai.model;

import java.util.List;

import javax.persistence.EntityManager;

/**
 * The <code>AbstractEntityListProcessor</code> is used to execute some operation on given Entity in transaction/nontransaction mode
 * 
 * @author Anton Nedbailo
 * @since Apr 30, 2014
 */
public abstract class AbstractEntityListProcessor<T>
{
    //members
    protected List<T> lstEntities;
    protected EntityManager emManager;
    
    /**
     * 
     * Constructor.

     * @param entity - entities list to be processed
     * @param emManager - associated EntityManager 
     */
    public AbstractEntityListProcessor(List<T> entity, EntityManager emManager)
    {
        lstEntities = entity;
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
    abstract protected List<T> doProcess();
    
    /**
     * Execute process operation
     * 
     * @return - processed objects list
     */
    public List<T> execute()
    {
        // check transaction object
        if (emManager.getTransaction().isActive())
        {
            // expected that we use one transaction for several operation with a possibility to make one roll back
            lstEntities = doProcess();
        }
        else
        {
            //we should do own transaction
            try
            {
                emManager.getTransaction().begin();
                lstEntities = doProcess();
                emManager.getTransaction().commit();
            }
            catch( Exception e )
            {
                emManager.getTransaction().rollback();
                throw e;
            }
        }
        return lstEntities;
    }
}
