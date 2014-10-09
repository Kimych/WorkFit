package by.uniterra.dai;

import static org.junit.Assert.*;

import org.junit.Test;

import by.uniterra.udi.util.Cryptor;

public class CryptorTest
{

    private static String SECURE_PASSW = "securePassword";


    @Test
    public void test()
    {
        assertTrue(Cryptor.getSecurePassword(SECURE_PASSW).equals(Cryptor.getSecurePassword(SECURE_PASSW)));
    }

}
