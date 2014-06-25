/**
 * Filename  : SimplePHelper.java
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

package by.uniterra.dai.model;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import by.uniterra.dai.iface.IPersistentHelper;
import by.uniterra.system.util.Log;

/**
 * The <code>SimplePHelper</code> is used to create EntityManagesa for JPA entities
 *
 * @author Anton Nedbailo
 * @since Sep 17, 2013
 */
public class SimplePHelper implements IPersistentHelper
{
    // members
    //current EM factory
    private EntityManagerFactory emFactory;

    /**
     * Constructor with parameters
     * 
     * @param mapParameters - Map with properties to override
     */
    public SimplePHelper(Map<String, String> mapParameters)
    {
        // get current factory
        emFactory = Persistence.createEntityManagerFactory(IPersistentHelper.DEFAULT_PU, mapParameters);
        // initialize shutdown hook to clean EM and stuff around
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                clear();
            }
        });
    }

    /**
     * 
     * Close all connection
     *
     * @author Anton Nedbailo
     * @date Sep 14, 2013
     */
    public void clear()
    {
        // close all factories
        if (emFactory.isOpen())
        {
            emFactory.close();
        }
        // clear storage
        Log.debug(this, "clear() method called: the factory closed.");
    }

    @Override
    public EntityManager getEM()
    {
        return emFactory.createEntityManager();
    }
}
