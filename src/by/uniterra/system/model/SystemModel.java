package by.uniterra.system.model;

import java.io.IOException;
import java.util.Properties;

public class SystemModel
{
    private static final String DEFAULT_PROP_FILE = "config/global.properties";

    private static SystemModel instance = new SystemModel(DEFAULT_PROP_FILE);
    private static Properties property;

    private SystemModel(String strURLtoProperties)
    {
        property = new Properties();
        try
        {
            ClassLoader classThis = this.getClass().getClassLoader();
            if (classThis.getResource(strURLtoProperties) != null)
            {
                property.load(classThis.getResourceAsStream(strURLtoProperties));
                System.out.println("Load OK!");
                System.out.println(property.getProperty("URL"));
            }
            else
            {
                System.out.println("Can not fing resources for \"" + strURLtoProperties + "\"");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public static SystemModel getInstance()
    {
        return instance;
    }

    public static boolean getBool(String strKey, boolean bDefValue)
    {
        return Boolean.valueOf(getString(strKey, String.valueOf(bDefValue)));
    }

    public static String getString(String strKey, String sDefValue)
    {
        String sResult = sDefValue;
        if (property.containsKey(strKey))
        {
            sResult = property.getProperty(strKey);
        }
        return sResult;
    }

    public static int getInt(String strKey, int iDefValue)
    {
        return Integer.valueOf(getString(strKey, String.valueOf(iDefValue)));
    }
    
    public static double getDouble(String strKey, double dDefValue)
    {
        return Double.valueOf(getString(strKey, String.valueOf(dDefValue)));
    }

}
