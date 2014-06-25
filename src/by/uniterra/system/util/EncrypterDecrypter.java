/**
 * Filename  : EncrypterDecrypter.java
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

package by.uniterra.system.util;

import java.security.Key;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.sun.crypto.provider.SunJCE;

/**
 * The <code>EncrypterDecrypter</code> is used for cryptind/decrypting of
 * passwords
 * 
 * @author Anton Nedbailo
 * @since Aug 30, 2013
 */
public class EncrypterDecrypter
{
    //constants
    private static final byte[] KEY_DATA = { (byte) 0x61, (byte) 0x26, (byte) 0x1C, (byte) 0x46, (byte) 0xF2, (byte) 0x53, (byte) 0x42,(byte) 0x47 };
    private static Key m_key = null;
    //static constructor
    static
    {
	try
	{
	    SunJCE jce = new SunJCE();
	    Security.addProvider(jce);
	    DESKeySpec desKeySpec = new DESKeySpec(KEY_DATA);
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	    m_key = keyFactory.generateSecret(desKeySpec);
	} catch (Exception e)
	{
	    Log.error(EncrypterDecrypter.class, e, " error ");
	}
    }

    private static String convertByteArray(byte[] block)
    {
	StringBuilder szTemp = new StringBuilder();
	for (byte element : block)
	{
	    int i = element & 0xFF;
	    if (Integer.toHexString(element).length() == 1)
	    {
		szTemp.append("0" + Integer.toHexString(i));
	    } else
	    {
		szTemp.append(Integer.toHexString(i));
	    }
	}
	return szTemp.toString().toUpperCase();
    }

    public static String decode(String szValue)
    {
	String ret = "";
	byte[] bValue = szValue.getBytes();
	try
	{
	    
	    if (bValue != null && !(bValue.length == 0))
	    {
		Cipher decodeCipher = Cipher.getInstance("DES");
		decodeCipher.init(Cipher.DECRYPT_MODE, m_key);
		byte[] aByte = toByteArray(bValue);
		if (aByte != null)
		{
		    ret = new String(decodeCipher.doFinal(aByte));
		} else
		{
		    ret = null;
		}
	    }
	} catch (Exception ex)
	{
	    Log.error(EncrypterDecrypter.class, "decoding " + Arrays.toString(bValue) + " fails " + ex.getMessage());
	}
	return ret;
    }

    public static String encode(String szValue)
	    throws javax.crypto.NoSuchPaddingException,
	    java.security.InvalidKeyException,
	    javax.crypto.BadPaddingException,
	    java.security.NoSuchAlgorithmException,
	    javax.crypto.IllegalBlockSizeException
    {
	Cipher encodeCipher = Cipher.getInstance("DES");
	encodeCipher.init(Cipher.ENCRYPT_MODE, m_key);
	byte[] encodeBytes = encodeCipher.doFinal(szValue.getBytes());
	return convertByteArray(encodeBytes);
    }

    private static byte[] toByteArray(byte[] bValue)
    {
	byte[] arrByte = new byte[0];
	try
	{
	    String szValue = new String(bValue);
	    if (szValue.indexOf('x') != -1) // found a valid looking password with "x"s
	    {
		String[] values = StringUtils.tokenize(szValue, "x");
		byte[] byteArray = new byte[values.length];
		for (int i = 0; i < byteArray.length; i++)
		{
		    Long l = Long.valueOf(values[i].replaceAll("m", "-"), 16);
		    Byte b = new Byte("" + l.shortValue());
		    byteArray[i] = b.byteValue();
		}
		arrByte = byteArray;
	    } else
	    { 
		String szTemp = new String(bValue);
		byte[] bPW = new byte[bValue.length / 2];
		for (int i = 0; i < szTemp.length(); i += 2)
		{
		    bPW[i / 2] = Integer.decode("0x" + szTemp.substring(i, i + 2)).byteValue();
		}
		arrByte = bPW;
	    }
	} catch (NumberFormatException e)
	{
	    Log.error(EncrypterDecrypter.class, e, "toByteArray error ");
	}
	return arrByte;
    }
}
