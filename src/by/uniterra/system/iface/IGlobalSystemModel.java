/**
 * Filename  : IGlobalSystemModel.java
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
     * Get formatted for date/timestamp objects with given format and application timezone (GMT+3 by default)
     * @param strFormat - format definition string
     * @return SimpleDateFormat object
     *
     * @author Anton Nedbailo
     * @date Sep 11, 2013
     */
    SimpleDateFormat getDateFormatter(String strFormat);
}
