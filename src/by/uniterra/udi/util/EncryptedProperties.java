/**
 * Filename  : EncryptedProperties.java
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
 * Project    : WorkFit
 *
 * Author     : Sergio Alecky
 *
 * last change by : $Author:$ 
 * last check in  : $Date: $
 */

package by.uniterra.udi.util;

import java.util.Map;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEParameterSpec;

/**
 * The <code>EncryptedProperties</code> is used for ...
 *
 * @author Sergio Alecky
 * @since 27 окт. 2014 г.
 */
public class EncryptedProperties extends Properties
{
    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 148177474462720964L;
    
    private static sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
    private static sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    private static Cipher encrypter;

    private static Cipher decrypter;
    private static byte[] salt = { (byte) 0x03, (byte) 0x33, (byte) 0x33, (byte) 0x33, (byte) 0x33, (byte) 0x33, (byte) 0x33, (byte) 0x33}; // make up your own
    private static final String CRYPT_FLAG = "_c";

    public EncryptedProperties(String password) throws Exception
    {
        PBEParameterSpec ps = new javax.crypto.spec.PBEParameterSpec(salt, 20);
        SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey k = kf.generateSecret(new javax.crypto.spec.PBEKeySpec(password.toCharArray()));
        encrypter = Cipher.getInstance("PBEWithMD5AndDES/CBC/PKCS5Padding");
        decrypter = Cipher.getInstance("PBEWithMD5AndDES/CBC/PKCS5Padding");
        encrypter.init(Cipher.ENCRYPT_MODE, k, ps);
        decrypter.init(Cipher.DECRYPT_MODE, k, ps);
    }

    public String getProperty(String key)
    {
        String strResult = null;
        try
        {
            strResult = key.endsWith(CRYPT_FLAG) ? decrypt(super.getProperty(key)) : super.getProperty(key);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Couldn't decrypt property:" + e.getMessage());
        }
        return strResult;
    }

    /**
     * @return the previous value of the specified key in this property list, or null if it did not have one. 
     */
    public synchronized Object setProperty(String key, String value)
    {
        try
        {
            return super.setProperty(key, key.endsWith(CRYPT_FLAG) ? encrypt(value) : value);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Couldn't encrypt property");
        }
    }

    public synchronized static String decrypt(String str) throws Exception
    {
        byte[] dec = decoder.decodeBuffer(str);
        byte[] utf8 = decrypter.doFinal(dec);
        return new String(utf8, "UTF-8");
    }

    public synchronized static String encrypt(String str) throws Exception
    {
        byte[] utf8 = str.getBytes("UTF-8");
        byte[] enc = encrypter.doFinal(utf8);
        return encoder.encode(enc);
    }

}
