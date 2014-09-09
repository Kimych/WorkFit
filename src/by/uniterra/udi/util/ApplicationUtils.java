/**
 * Filename  : ApplicationUtils.java
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

package by.uniterra.udi.util;

import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * The <code>ApplicationUtils</code> is used for some application utils
 * @author Anton Nedbailo
 * @since Aug 30, 2013
 */
public class ApplicationUtils
{
    public static String getLocaleAddress()
    {
	String szAddress = "";
	try
	{
	    ServerSocket socket = new ServerSocket(0);
	    szAddress = InetAddress.getLocalHost().getHostAddress();
	    socket.close();
	} catch (Exception e)
	{
	    Log.error(ApplicationUtils.class, e, "getLocaleAddress error ");
	}
	return szAddress;
    }

    public static String getLocaleHostName()
    {
        String szAddress = "";
        try
        {
            ServerSocket socket = new ServerSocket(0);
            szAddress = InetAddress.getLocalHost().getHostName().toUpperCase();
            socket.close();
        } catch (Exception e)
	{
	    Log.error(ApplicationUtils.class, e, "getLocaleHostName error ");
	}
        return szAddress;
    }
}
