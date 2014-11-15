/**
 * Filename  : Logger.java
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
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
                // load configuration file
                InputStream configStream = Log.class.getClassLoader().getResourceAsStream("config/log4j.properties");
                props.load(configStream);
                configStream.close();

                ThreadMXBean temp = ManagementFactory.getThreadMXBean();
                ThreadInfo t = temp.getThreadInfo(1, Integer.MAX_VALUE);
                StackTraceElement st[] = t.getStackTrace();
                // get full class name
                String strAppName = st[st.length - 1].getClassName();
                // convert to simple class name
                strAppName = strAppName.substring(strAppName.lastIndexOf(".") + 1);
                // overwrite some properties
                props.setProperty("log4j.appender.R.File", new File("").getAbsolutePath() + File.separatorChar + "logs" + File.separatorChar + strAppName + "_"
                        + ApplicationUtils.getLocaleHostName() + ".log");
                // Log.info(Log.class, new File("").getAbsolutePath());
                // props.setProperty("log4j.appender.R.File", new
                // java.io.File("").getAbsolutePath() + File.separatorChar +
                // "logs" + File.separatorChar + "WorkFit_" +
                // ApplicationUtils.getLocaleHostName() + ".log");
                // props.setProperty("log4j.appender.R.File","logs" +
                // java.io.File.separatorChar + "WorkFit_" +
                // ApplicationUtils.getLocaleHostName() + ".log");
                // props.setProperty("log4j.appender.R.File", "logs\\WorkFit_" +
                // ApplicationUtils.getLocaleHostName() + ".log");
                // apply configuration
                PropertyConfigurator.configure(props);
            } catch (IOException e)
            {
                e.printStackTrace();
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
