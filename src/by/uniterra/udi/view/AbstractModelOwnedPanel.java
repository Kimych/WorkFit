/**
 * Filename  : AbstractModelOwnedPanel.java
 *
 * ***************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ***************************************************************************
 *
 * Author     : Anton Nedbailo
 *
 * last change by : $Author:$ 
 * last check in  : $Date: $
 */

package by.uniterra.udi.view;

import java.awt.GridBagLayout;

import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXPanel;

import by.uniterra.dai.model.EModelState;
import by.uniterra.udi.iface.IUIModelOwner;

public abstract class AbstractModelOwnedPanel extends JXPanel implements IUIModelOwner
{
    private static final long serialVersionUID = -8356506250141168750L;
    //members
    protected Object mIntModel;

    public AbstractModelOwnedPanel()
    {
        super(new GridBagLayout());
        //init members
        init();
        //init ui
        jbInit();
        //init members data
        loadData();
    }

    /**
     * 
     * Initialosation method (called right after constructor)
     *
     * @author Anton Nedbailo
     * @date 15 ���. 2013 �.
     */
    protected abstract void init();
    
    /**
     * Init UDI
     * 
     * @author Anton Nedbailo
     * @date Sep 21, 2013
     */
    protected abstract void jbInit();

    /**
     * @see by.uniterra.armparis.udi.iface.IUIModelOwner#setModel(java.lang.Object)
     */
    @Override
    public void setModel(Object objModel)
    {
        mIntModel = objModel;
    }

    /** 
     * @see by.uniterra.armparis.udi.iface.IUIModelOwner#getModel()
     */
    @Override
    public Object getModel()
    {
        return mIntModel;
    }

    /** 
     * @see by.uniterra.armparis.udi.iface.IUIModelOwner#readValues()
     */
    @Override
    final public void readValues()
    {
        if (mIntModel != null)
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    readIntValues();
                }
            });
        }
    }
    
    /**
     * Internal method to bring data from model to view
     * 
     *
     * @author Anton Nedbailo
     * @date Sep 21, 2013
     */
    protected abstract void readIntValues();

    /** 
     * @see by.uniterra.armparis.udi.iface.IUIModelOwner#writeValues()
     */
    @Override
    public abstract EModelState writeValues();
    
    /**
     * Call to load some time consuming UI data (in secondary thread) a show it in event-dispatching thread
     * 
     *
     * @author Anton Nedbailo
     * @date Sep 28, 2013
     */
    private final void loadData()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //load time consuming data
               loadIntData();
               //show this data safely in UI
               SwingUtilities.invokeLater(new Runnable()
               {
                   @Override
                   public void run()
                   {
                       postShowInitData();
                   }
               });
            }
        }).start();
    }
    
    /**
     * Implement time consuming UI data loading (will be called in secondary thread)
     * 
     *
     * @author Anton Nedbailo
     * @date Sep 28, 2013
     */
    protected abstract void loadIntData();
    
    /**
     * Show just loaded time consuming UI data (in event-dispatching thread)
     * 
     * @author Anton Nedbailo
     * @date Sep 28, 2013
     */
    protected abstract void postShowInitData();
}