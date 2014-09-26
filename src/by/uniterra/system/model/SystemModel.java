package by.uniterra.system.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.model.JPASessionCustomizer;
import by.uniterra.system.iface.IGlobalProperties;
import by.uniterra.system.main.WorkFitFrame;
import by.uniterra.udi.util.Log;

public class SystemModel
{
    // constants
    private static final String PERSISTENCE_UNIT_NAME = "WorkFit";
    private static final String DEFAULT_PROP_FILE = "config/global.properties";
    private static final String CUSTOMER_PROPERTIES = "custom.properties";

    // members
    private static SystemModel instance = new SystemModel(DEFAULT_PROP_FILE);
    private static Properties property;
    private static EntityManagerFactory emfFactory;
    private static EntityManager emDefaultManager;
    private static Authorization authorization;

    private SystemModel(String strURLtoProperties)
    {
        // properties holder
        property = new Properties();
        // load properties from DEFAULT_PROP_FILE
        property.putAll(loadPropFromRes(DEFAULT_PROP_FILE));
        // load properties from CUSTOMER_PROPERTIES
        property.putAll(loadPropFromRes(CUSTOMER_PROPERTIES));
    }

    public static SystemModel getInstance()
    {
        return instance;
    }

    /**
     * Initialize database connection with default system settings
     * (global.properties)
     * 
     *
     * @author Anton Nedbailo
     * @date Sep 8, 2014
     */
    public static void initJPA()
    {
        // prepare setting map for JPA
        Map<String, String> mapCustomProp = new HashMap<String, String>();
        // load JPA parameters (use global.properties)
        mapCustomProp.put(PersistenceUnitProperties.JDBC_URL, getString(IGlobalProperties.DB_URL, "jdbc:mysql://192.168.186.128:3306/Workfit"));
        mapCustomProp.put(PersistenceUnitProperties.JDBC_USER, getString(IGlobalProperties.DB_USER, "testdb"));
        mapCustomProp.put(PersistenceUnitProperties.JDBC_PASSWORD, getString(IGlobalProperties.DB_USER, "testdb"));
        mapCustomProp.put(PersistenceUnitProperties.JDBC_DRIVER, getString(IGlobalProperties.DB_DRIVER, "com.mysql.jdbc.Driver"));
        // the correct way to disable the shared cache (L2 cache)
        mapCustomProp.put("eclipselink.cache.shared.default", "false");
        // enable SQL lqueries logging
        //mapCustomProp.put("eclipselink.logging.level","FINE");
        // Specify an EclipseLink session customizer class: a Java
        mapCustomProp.put(PersistenceUnitProperties.SESSION_CUSTOMIZER, JPASessionCustomizer.class.getName());
        // a new connection (factory) to target DB
        emfFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, mapCustomProp);
        emDefaultManager = emfFactory.createEntityManager();
        Log.info(SystemModel.class, "init JPA");
    }

    /**
     * Get default EntityManager
     * 
     * @return EntityManager if succeed
     * 
     * @throws RuntimeException
     *             if JPA connection wasn't initialized
     *
     * @author Anton Nedbailo
     * @date Sep 8, 2014
     */
    public static EntityManager getDefaultEM()
    {
        // check JPA initialization
        if (emfFactory == null || !emfFactory.isOpen())
        {
            Log.error(SystemModel.class, "JPA entity manager factory was not properly initialized.");
            throw new RuntimeException("JPA entity manager factory was not properly initialized.");
        }
        return emDefaultManager;
    }

    /**
     * Try to load properties from given resource
     * 
     * @param strResourceName
     *            - resource name
     */
    private static Map<String, String> loadPropFromRes(String strResourceName)
    {
        Map<String, String> mapCustomProp = new HashMap<String, String>();
        try
        {
            ClassLoader classThis = SystemModel.class.getClassLoader();
            // check if resource exists
            if (classThis.getResource(strResourceName) != null)
            {
                // load properties
                property.load(classThis.getResourceAsStream(strResourceName));
                Log.info(SystemModel.class, "Properties successfully loaded from \"" + strResourceName + "\".");
            }
        }
        catch (IOException e)
        {
            Log.error(SystemModel.class, e, "Failed to load properties from \"" + strResourceName + "\".");
        }
        return mapCustomProp;
    }

    /**
     * Close all database connections
     * 
     *
     * @author Anton Nedbailo
     * @date Sep 8, 2014
     */
    public static void disposeJPA()
    {
        // close EntityManager
        if (emDefaultManager != null && emDefaultManager.isOpen())
        {
            emDefaultManager.close();
        }
        // close EntityManagerFactory
        if (emfFactory != null && emfFactory.isOpen())
        {
            emfFactory.close();
        }
        Log.info(SystemModel.class, "dispose JPA");
    }

    /**
     * Get system property value
     * 
     * @param strKey
     *            - property key
     * @param bDefValue
     *            - default value
     * 
     * @return boolean value of required system property if succeed; otherwise -
     *         default value
     *
     * @author Anton Nedbailo
     * @date Sep 8, 2014
     */
    public static Boolean getBool(String strKey, Boolean bDefValue)
    {
        Boolean bResult = bDefValue;
        // try to get string value
        String strFoundValue = getString(strKey, null);
        // check found value
        if (strFoundValue != null && !strFoundValue.isEmpty())
        {
            // Boolean.valueOf returns "false" even if the value isn't equals to
            // "false" string
            // so, it's not a case for us
            // check for "true"
            if (strFoundValue.equalsIgnoreCase("true") && !strFoundValue.equalsIgnoreCase("false"))
            {
                bResult = true;
            }
            else if (!strFoundValue.equalsIgnoreCase("true") && strFoundValue.equalsIgnoreCase("false"))
            {
                bResult = false;
            }
        }
        return bResult;
    }

    /**
     * Set new property (or overwrite existing) value
     * 
     * @param strKey
     *            - property key
     * @param objValue
     *            - property value
     * 
     * @throws IllegalArgumentException
     *             if: 1) key is null; 2) key is empty 3) value is null
     *
     * @author Anton Nedbailo
     * @date Sep 8, 2014
     */
    public static void setProperty(String strKey, Object objValue)
    {
        // check for valid key
        if (strKey == null)
        {
            Log.error(SystemModel.class, "Key value shouldn't be null!");
            throw new IllegalArgumentException("Key value shouldn't be null!");
        }
        if (strKey.isEmpty())
        {
            Log.error(SystemModel.class, "Key value shouldn't be empty!");
            throw new IllegalArgumentException("Key value shouldn't be empty!");
        }
        // check for valid value
        if (objValue == null)
        {
            Log.error(SystemModel.class, "Value shouldn't be null!");
            throw new IllegalArgumentException("Value shouldn't be null!");
        }
        // put into properties map
        property.put(strKey, objValue.toString());
    }

    /**
     * 
     * Get system property value
     * 
     * @param strKey
     *            - property key
     * @param bDefValue
     *            - default value
     * 
     * @return String value of required system property if succeed; otherwise -
     *         default value
     *
     * @author Anton Nedbailo
     * @date Sep 8, 2014
     */
    public static String getString(String strKey, String sDefValue)
    {
        String sResult = sDefValue;
        if (property.containsKey(strKey))
        {
            sResult = property.getProperty(strKey);
        }
        return sResult;
    }

    /**
     * Get system property value
     * 
     * @param strKey
     *            - property key
     * @param bDefValue
     *            - default value
     * 
     * @return integer value of required system property if succeed; otherwise -
     *         default value
     *
     * @author Anton Nedbailo
     * @date Sep 8, 2014
     */
    public static Integer getInt(String strKey, Integer iDefValue)
    {
        Integer iResult = iDefValue;
        // try to get string value
        String strFoundValue = getString(strKey, null);
        // check found value
        if (strFoundValue != null && !strFoundValue.isEmpty())
        {
            try
            {
                // parse result
                iResult = Integer.valueOf(strFoundValue);
            }
            catch (NumberFormatException e)
            {
                /* NOP */
            }
        }
        return iResult;
    }

    /**
     * Get system property value
     * 
     * @param strKey
     *            - property key
     * @param bDefValue
     *            - default value
     * 
     * @return double value of required system property if succeed; otherwise -
     *         default value
     *
     * @author Anton Nedbailo
     * @date Sep 8, 2014
     */
    public static Double getDouble(String strKey, Double dDefValue)
    {
        Double dResult = dDefValue;
        // try to get string value
        String strFoundValue = getString(strKey, null);
        // check found value
        if (strFoundValue != null && !strFoundValue.isEmpty())
        {
            try
            {
                // parse result
                dResult = Double.valueOf(strFoundValue);
            }
            catch (NumberFormatException e)
            {
                /* NOP */
            }
        }
        return dResult;
    }


    public static Authorization getAuthorization()
    {
        return authorization;
    }

    public static void setAuthorization(Authorization authorization)
    {
        SystemModel.authorization = authorization;
    }

}
