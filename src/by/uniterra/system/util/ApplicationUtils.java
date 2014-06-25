/**
 * Filename  : ApplicationUtils.java
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
