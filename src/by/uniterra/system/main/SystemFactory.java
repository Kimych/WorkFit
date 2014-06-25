/**
 * Filename  : SystemFactory.java
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

package by.uniterra.system.main;

import by.uniterra.system.iface.IGlobalSystemModel;
import by.uniterra.system.util.StringUtils;

/**
 * 
 * The <code>SystemFactory</code> is used to give a fast access to some global objects
 *
 * @author Anton Nedbailo
 * @since Sep 1, 2013
 */
public abstract class SystemFactory
{
    private static SystemFactory currentFactory;

    protected String strProcessName = StringUtils.getClassName(this).toLowerCase();
    
    /**
     * @return the strProcessName
     *
     * @author Anton Nedbailo
     * @date 13 ���. 2013 �.
     */
    public String getProcessName()
    {
        return this.strProcessName;
    }

    /**
     * Get current factory 
     * 
     * @return system factory
     */
    public static SystemFactory getSystemFactory()
    {
        return currentFactory;
    }

    /**
     * Set current system factory
     * 
     * @param systemFactory - to be set
     */
    public static void setSystemFactory(SystemFactory systemFactory)
    {
        currentFactory = systemFactory;
    }

    /**
     * Standard constructor
     */
    public SystemFactory()
    {
        super();
    }
    
    public static boolean getSysBool(final String key)
    {
        try
        {
            return Boolean.valueOf(currentFactory.getSystemModel().getSysString(key).trim()).booleanValue();
        } catch (Exception e)
        {
            return false;
        }
    }

    public static int getSysInt(final String key, final int defaultValue)
    {
        if (currentFactory != null)
        {
            return (currentFactory.getSystemModel()).getSysInt(key, defaultValue);
        } else
        {
            return defaultValue;
        }
    }

    /**
     * @param key the key of the sysprop
     * @return the sysprop, a blank String if where are no match
     */
    public static String getSysProp(final String key)
    {
        return (currentFactory.getSystemModel()).getSysString(key);
    }

    /**
     * @param key the key of the sysprop
     * @return the sysprop, alternate String if where are no match
     */
    public static String getSysProp(String key, String alter)
    {
        String tmp = currentFactory.getSystemModel().getSysString(key);
        if (tmp.isEmpty())
        {
            return alter;
        } else
        {
            return tmp;
        }
    }
    
    public static boolean isDebugMode()
    {
        return getSysBool(IGlobalSystemModel.PARAM_DEBUGMODE);
    }
    
    public abstract IGlobalSystemModel getSystemModel();
}