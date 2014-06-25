/**
 * Filename  : BasicSystemModel.java
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

package by.uniterra.system.model;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import by.uniterra.dai.iface.IPersistentHelper;
import by.uniterra.dai.model.SimplePHelper;
import by.uniterra.system.iface.IGlobalSystemModel;
import by.uniterra.system.main.SystemFactory;
import by.uniterra.system.util.ApplicationUtils;
import by.uniterra.system.util.EncrypterDecrypter;
import by.uniterra.system.util.Log;
import by.uniterra.system.util.StringUtils;

/**
 * @author Anton Nedbailo
 *
 */
public class BasicSystemModel implements IGlobalSystemModel, IPersistentHelper
{
    // members
    private Properties propProperties = new Properties();
    private TimeZone tzDefault = TimeZone.getTimeZone("GMT+3");
    private String strGUID;
    // persistent manager helper
    private IPersistentHelper persistHelper;
    //constants
    private static final String GLOBAL_PROPERTIES = "config/global.properties";
    private static final String CUSTOMER_PROPERTIES = "custom.properties";

    public static String createGUID()
    {
        return ApplicationUtils.getLocaleAddress() + "(" + UUID.randomUUID() + ")";
    }

    public BasicSystemModel(String[] args, String szIniFile)
    {
        URL url;
        loadProperties(GLOBAL_PROPERTIES);
        propProperties.put(PARAM_USERNAME, System.getProperty("user.name"));
        propProperties.put(PARAM_PASSWORD, "");
	try
	{
	    propProperties.put(HOSTNAME, ApplicationUtils.getLocaleHostName());
	} catch (Exception e1)
	{
	    Log.error(this, e1, "BasicSystemModel error ");
	}

        if (szIniFile != null)
        {
            try
            {
                url = getClass().getClassLoader().getResource(szIniFile);
                propProperties.load(url.openStream());
            } catch (Exception e)
            {
                Log.debug(this, "File not found : " + szIniFile);
            }
        }
        readAdditionalProperties(CUSTOMER_PROPERTIES);
        if (args != null)
        {
            for (String arg : args)
            {
                String[] param = StringUtils.tokenize(arg, "=");
                if (param.length == 1)
                {
                    if (param[0].endsWith(".properties"))
                    {
                        readAdditionalProperties(param[0]);
                    } else
                    {
                        propProperties.put(param[0], "");
                    }
                } else
                {
                    propProperties.put(param[0], param[1]);
                }
            }
        }
        String szCryptedPassword = (String) getSystemProperty(PARAM_DB_CRYPTEDPASSWORD);
        String szPassword = (String) getSystemProperty(PARAM_DB_PASSWORD);
        if ((szCryptedPassword != null) && (szPassword == null))
        {
            szPassword = EncrypterDecrypter.decode(szCryptedPassword);
            if (szPassword != null)
            {
                propProperties.setProperty(PARAM_DB_PASSWORD, szPassword);
            }
        }
       
        /**
         * Area: API: Utilities
         * 
         * Synopsis: Updated sort behavior for Arrays and Collections may throw an IllegalArgumentException
         * 
         * Description: The sorting algorithm used by java.util.Arrays.sort and (indirectly) by java.util.Collections.sort has been
         * replaced. The new sort implementation may throw an IllegalArgumentException if it detects a Comparable that violates the
         * Comparable contract. The previous implementation silently ignored such a situation. If the previous behavior is desired, you can
         * use the new system property, java.util.Arrays.useLegacyMergeSort, to restore previous mergesort behavior.
         * 
         * Nature of Incompatibility: behavioral
         * 
         * RFE: 6804124
         */
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
    }

    @Override
    public TimeZone getTimezone()
    {
        return tzDefault;
    }


    @Override
    public String getGUID()
    {
        if (strGUID == null)
        {
            strGUID = createGUID();
        }
        return strGUID;
    }

  

    /**
     * Zugriff auf die Properties wird gew�hrt. <BR>
     * 
     * @return the PropertySet
     */
    public Properties getProperties()
    {
        return propProperties;
    }

    /**
     * Gives an int from a key. If Problems with parsing, the default Value is set.
     * 
     * @return int the returned Int
     */
    public int getSysInt(String name, int defaultInt)
    {
        // check for properties
        if (propProperties == null)
        {
            return defaultInt;
        }
        int value = defaultInt;
        try
        {
            String s = getSysString(name);
            if (!s.isEmpty())
            {
                try
                {
                    value = Integer.parseInt(s);
                } catch (Exception e)
                {
                    Log.debug(this, "Value " + s + " for " + name + " is no Integer. " + e.getMessage());
                }
            } else
            {
                if (isDebugMode())
                {
                    Log.debug(this, "No configured value for \"" + name + "\".");
                }
            }
        } catch (MissingResourceException e)
        {
            Log.error(this, "getSysInt error." + e.getMessage());
        }
        return value;
    }

    /**
     * (non-Javadoc)
     * 
     * @param key the key of the sysprop
     * @return the sysprop, a blank String if where are no match
     */
    public String getSysString(String key)
    {
        String res = null;
        try
        {
            res = (String) getSystemProperty(key);
        } catch (Exception e)
        {
            if (!key.equalsIgnoreCase(PARAM_DEBUGMODE) && isDebugMode())
            {
                Log.debug(this, "key not found " + key);
            }
        }
        if (res == null)
        {
            res = "";
        }
        return res;
    }

    /**
     * Gibt ein Propertywert von dem Systemproperties zur�ck
     * 
     * @param key key for an property
     * 
     * @return the value for this key
     */
    public Object getSystemProperty(Object key)
    {
        if (key == null)
        {
            return null;
        }
        if (!(key instanceof String))
        {
            return propProperties.get(key);
        }
        String szKey = (String) key;
        Object aObject = propProperties.get(szKey);
        if (aObject != null)
        {
            return aObject;
        }
        aObject = propProperties.get(szKey.toLowerCase());
        if (aObject != null)
        {
            return aObject;
        }
        aObject = propProperties.get(szKey.toUpperCase());
        return aObject;
    }


    public boolean isDebugMode()
    {
        if (getSystemProperty(PARAM_DEBUGMODE) == null)
        {
            return false;
        } else
        {
            return (getSysString(PARAM_DEBUGMODE)).equalsIgnoreCase("TRUE");
        }
    }

   

    public void loadProperties(String name)
    {
        URL url = getClass().getClassLoader().getResource(name);
        if (url != null)
        {
            try
	    {
		propProperties.load(url.openStream());
	    } catch (Exception e)
	    {
		Log.error(this, e, "loadProperties error ");
	    }
        }
    }

    private void readAdditionalProperties(String res)
    {
	try
	{
	    URL url = getClass().getClassLoader().getResource(res);
	    if (url != null)
	    {
		Properties overloadedProps = new Properties();
		overloadedProps.load(url.openStream());
		if (!overloadedProps.isEmpty())
		{
		    propProperties.putAll(overloadedProps);
		    Log.info(this,"read from " + res + " /" + overloadedProps.size());
		}
	    } else
	    {
		Log.warning(this, "no read from " + res);
	    }
	} catch (IOException e)
	{
	    Log.error(this, e, "readAdditionalProperties error ");
	}
    }

    public void setSystemProperty(Object key, Object aObject)
    {
        propProperties.put(key.toString(), aObject.toString());
    }
    
    /**
     * Get EntityManager for JPA business models
     * @return EntityManager object
     *
     * @author Anton Nedbailo
     * @date Sep 1, 2013
     */
    public EntityManager getEM()
    { 
        EntityManager emManager = null;
        synchronized (this)
        {
            // check for initialized JPA
            if (persistHelper == null)
            {
                initJPA();
            }  
            emManager = persistHelper != null ? persistHelper.getEM() : null;
        }
        return emManager;
    }

    @Override
    public SimpleDateFormat getDateFormatter(String strFormat)
    {
	// DateFormat
	SimpleDateFormat formatter = new SimpleDateFormat(strFormat);
	formatter.setTimeZone(tzDefault);
	return formatter;
    }
    
    private void initJPA()
    {
        // initialize JPA
        Log.debug(this, "Start JPA initialization...");
        try
        {
            Map<String, String> mapCustomProp = new HashMap<String, String>();
            // put system configuration properties
            mapCustomProp.put(PersistenceUnitProperties.JDBC_URL, SystemFactory.getSysProp(IGlobalSystemModel.PARAM_DB_URL));
            mapCustomProp.put(PersistenceUnitProperties.JDBC_USER, SystemFactory.getSysProp(IGlobalSystemModel.PARAM_DB_USER));
            mapCustomProp.put(PersistenceUnitProperties.JDBC_PASSWORD, SystemFactory.getSysProp(IGlobalSystemModel.PARAM_DB_PASSWORD));
            mapCustomProp.put(PersistenceUnitProperties.JDBC_DRIVER, SystemFactory.getSysProp(IGlobalSystemModel.PARAM_DB_DRIVER));
            /*// custom properties
            // eclipselink.jdbc.read-connections.min/max as of EclipseLink 2.2 replaced by CONNECTION_POOL_MIN
            // eclipselink.jdbc.write-connections.min/max as of EclipseLink 2.2 replaced by CONNECTION_POOL_MIN
            // Specifies the minimum number of connections in EclipseLink connection pool.
            defProperties.put(PersistenceUnitProperties.CONNECTION_POOL_MIN, "1");
            // Specifies the maximum number of connections in EclipseLink connection pool.
            defProperties.put(PersistenceUnitProperties.CONNECTION_POOL_MAX, "2");
            // Specify where EclipseLink should write generated DDL files
            defProperties.put(PersistenceUnitProperties.APP_LOCATION, "./logs");
            // Specify the file name of the DDL file that EclipseLink generates containing SQL statements to create tables for JPA entities.
            defProperties.put(PersistenceUnitProperties.CREATE_JDBC_DDL_FILE, "jpa_output.xml");*/
            // Specify an EclipseLink session customizer class: a Java
            //mapCustomProp.put(PersistenceUnitProperties.SESSION_CUSTOMIZER, JPASessionCustomizer.class.getName());
            /*// Ignore the cache, and build the object directly from the database result
            defProperties.put("javax.persistence.cache.retrieveMode", "BYPASS");
            // Do not cache the database results
            defProperties.put("javax.persistence.cache.storeMode", "BYPASS");*/
            //the correct way to disable the shared cache (L2 cache)
            mapCustomProp.put("eclipselink.cache.shared.default", "false");
            
            // create Persistence Manager helper
            persistHelper = new SimplePHelper(mapCustomProp);
            Log.debug(this, "JPA initialized.");
            
           /* //create factory
            emfFactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME, mapCustomProp);
            //create manager
            emManager = emfFactory.createEntityManager();
            //log connection info
            Log.info(this, "Connected to \"" + mapCustomProp.get(PersistenceUnitProperties.JDBC_URL) + "\" with \"" + 
                    mapCustomProp.get(PersistenceUnitProperties.JDBC_USER) + "/" +
                    mapCustomProp.get(PersistenceUnitProperties.JDBC_PASSWORD) + "\"");*/
            
          //log connection info
            Log.info(this, "Connection parameters are: \"" + mapCustomProp.get(PersistenceUnitProperties.JDBC_URL) + "\" with \"" + 
                    mapCustomProp.get(PersistenceUnitProperties.JDBC_USER) + "/" +
                    mapCustomProp.get(PersistenceUnitProperties.JDBC_PASSWORD) + "\"");
        } catch (Exception e)
        {
            Log.error(this, e, " JPA parameters initialization failed.");
        }
    }
}
