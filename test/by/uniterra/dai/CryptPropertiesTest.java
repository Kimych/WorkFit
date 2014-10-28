package by.uniterra.dai;

import static org.junit.Assert.*;

import org.junit.Test;

import by.uniterra.system.iface.IGlobalProperties;
import by.uniterra.udi.util.EncryptedProperties;

public class CryptPropertiesTest
{

    private static final String APP_KEY = "3w45lk34h5k3p4h673";
    private static final String INPUT_STR = "workfit";

    @Test
    public void test() throws Exception
    {
                EncryptedProperties property = new EncryptedProperties(APP_KEY, IGlobalProperties.CRYPT_POSTFIX);
                String strIN = property.encrypt(INPUT_STR);
                String strOut = property.decrypt(strIN);
                System.out.println(strIN);
                System.out.println(strOut);
                assertTrue(INPUT_STR.equals(strOut));
    }

}
