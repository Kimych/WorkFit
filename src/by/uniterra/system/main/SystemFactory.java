/**
 * Filename  : SystemFactory.java
 *
 * ***************************************************************************
 * Copyright (C) 2013-2013 by UniTerra Ltd.
 * K. Chornogo 31, office 5, room 3
 * 220012 Minsk / Republic of Belarus
 * info@uniterra.by
 *                                                      
 * This material is a CONFIDENTIAL, unpublished work of authorship
 * created in 2013 and subsequently updated. It is a TRADE SECRET which
 * is property of UniTerra Ltd.
 *
 * All use, disclosure, and/or reproduction not specifically authorized
 * by UniTerra Ltd in writing is prohibited. All rights reserved.
 ***************************************************************************
 * Project    : Armparis
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