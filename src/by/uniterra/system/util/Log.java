/**
 * Filename  : Logger.java
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import by.uniterra.system.iface.ILogLevels;

/**
 * The <code>Logger</code> is used for store log messages
 * @author Anton Nedbailo
 * @since Aug 30, 2013
 */
public class Log
{
    //constants
    private static final int MAX_CLASSNAME_CHARS = 20;
    //members
    private static Logger m_logger;
    //static constructor
    static
    {
        initLogger();
    }
    
    /**
     * write a Debug Message to the looger<BR>
     * same as sendDebugMessage
     * 
     * @param source the Object that produce the message
     * @param message the Error Message
     */
    public static void debug(final Object source, final String message)
    {
        sendMessage(source, message, ILogLevels.LOGLEVEL_DEBUG);
    }

    public static void error(final Object source, Exception e)
    {
        error(source, e, "");
    }

    public static void error(final Object source, final Exception e, final String message)
    {
        StringBuffer msg = new StringBuffer(message);
        msg.append(e instanceof NullPointerException ? " NPE " : e.getMessage());
        StackTraceElement[] arStackTrace = e.getStackTrace();
        for (int i = 0; i < arStackTrace.length && i < 5; i++)
        {
            msg.append("\n at " + arStackTrace[i].getClassName() + " (:" + arStackTrace[i].getLineNumber() + ")");
        }
        sendMessage(source, msg.toString(), ILogLevels.LOGLEVEL_ERROR);
    }

    /**
     * writes an ErrorMessage to the Logger
     * 
     * @param source the Object that produce the message
     * @param message the Error Message
     */
    public static void error(final Object source, final String message)
    {
        sendMessage(source, message, ILogLevels.LOGLEVEL_ERROR);
    }

    /**
     * writes an InfoMessage to the Logger
     * 
     * @param source the Object that produce the message
     * @param message the Info Message
     */
    public static void info(final Object source, String message)
    {
        sendMessage(source, message, ILogLevels.LOGLEVEL_INFORMATION);
    }

    public static void printStackTrace(final Object source, final Exception e)
    {
        StackTraceElement[] elStack = e.getStackTrace();
        error(source, e.getLocalizedMessage());
        for (StackTraceElement element : elStack)
        {
            error(source, "line " + element.getLineNumber() + ":  " + element.toString());
        }
    }

    
    private static void sendMessage(final Object source, final String szParams, final short nLogLevel)
    {
        if (m_logger != null)
        {
            
            String strClassName = "unknown";
            if (source != null)
            {
                strClassName = (source instanceof Class) ? ((Class<?>)source).getName() : source.getClass().getName();
            }
            String strClassName4Log = strClassName.length() < MAX_CLASSNAME_CHARS 
        	    ? String.format("%" + MAX_CLASSNAME_CHARS + "s", strClassName)
        	    : (strClassName.substring(strClassName.length() - MAX_CLASSNAME_CHARS, strClassName.length()));
    	    //switch log level
            switch (nLogLevel)
            {
             case ILogLevels.LOGLEVEL_ERROR:
        	 m_logger.error(strClassName4Log + " " + szParams);
            	 break;
             case ILogLevels.LOGLEVEL_INFORMATION:
        	 m_logger.info(strClassName4Log + " " + szParams);
            	break;
             case ILogLevels.LOGLEVEL_WARNING:
        	 m_logger.warn(strClassName4Log + " " + szParams);
            	break;
             case ILogLevels.LOGLEVEL_ALARM:
        	 m_logger.fatal(strClassName4Log + " " + szParams);
        	 break;
             case ILogLevels.LOGLEVEL_DEBUG:
	    default:
		m_logger.debug(strClassName4Log + " " + szParams);
		break;
            }
        } else
        {
            System.out.println( "no logger available : " + szParams);
        }
    }
    
    private static void initLogger()
    {
	try
	{
	    // create logger
	    m_logger = Logger.getLogger(Log.class.getName());
	    // load configuration
	    Properties props = new Properties();
	    try
	    {
		//load configuration file
		InputStream configStream = Log.class.getClassLoader().getResourceAsStream("config/log4j.properties");
		props.load(configStream);
		configStream.close();
		//overwrite some properties
		 props.setProperty("log4j.appender.R.File", "log\\WorkFit_" + ApplicationUtils.getLocaleHostName() + ".log");
		//apply configuration
		PropertyConfigurator.configure(props);
	    } catch (IOException e)
	    {
		System.out.println("Error: Cannot configure Log4J");
	    }
	} catch (Exception e)
	{
	    System.out.println(e.getMessage() + " " + e.getCause() + "" + e.getClass().getName());
	}
    }

    public static void warning(final Object source, final String message)
    {
        sendMessage(source, message, ILogLevels.LOGLEVEL_WARNING);
    }
}
