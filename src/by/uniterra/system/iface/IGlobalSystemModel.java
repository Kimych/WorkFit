/**
 * Filename  : IGlobalSystemModel.java
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

package by.uniterra.system.iface;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.TimeZone;

import javax.persistence.EntityManager;

/**
 * @author Anton Nedbailo
 * 
 */
public interface IGlobalSystemModel
{
    //constants
    String PARAM_DB_CRYPTEDPASSWORD = "DBCRYPTEDPASSWORD";
    String PARAM_DB_DRIVER = "DRIVER";
    String PARAM_DB_PASSWORD = "DBPASSWORD";
    String PARAM_DB_URL = "URL";
    String PARAM_DB_USER = "DBUSER";
    String PARAM_DEBUGMODE = "DEBUGMODE";
    String PARAM_USERNAME = "USERNAME";
    String PARAM_PASSWORD = "PASSWORD";
    String HOSTNAME = "HOSTNAME";
    String PARAM_DB_DATE_FORMAT = "DB_DATE_FORMAT";
    String TIMEZONE = "TIMEZONE";
    
    String PARAM_LOG_DIRECTORY = "LOG_DIRECTORY";
    String LOG_FILE_APPENDER = "LOG_FILE_APPENDER";

    boolean isDebugMode();
    
    TimeZone getTimezone();

    Properties getProperties();

    int getSysInt(String key, int defaultValue);

    String getSysString(String key);

    Object getSystemProperty(Object key);

    void loadProperties(String name) throws IOException;

    void setSystemProperty(Object key, Object aObject);
    
    String getGUID();
    
    /**
     * Get EntityManager to manage JPZ business objects
     * @return threadsave EntityManager object
     *
     * @author Anton Nedbailo
     * @date Sep 1, 2013
     */
    EntityManager getEM();
    
    /**
     * Get formatted for date/timestamp objects with given format and application timezone (GMT+3 by default)
     * @param strFormat - format definition string
     * @return SimpleDateFormat object
     *
     * @author Anton Nedbailo
     * @date Sep 11, 2013
     */
    SimpleDateFormat getDateFormatter(String strFormat);
}
