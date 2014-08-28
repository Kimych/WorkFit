/**
 * Filename  : StringUtils.java
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

package by.uniterra.system.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * The <code>StringUtils</code> is used for some String class iterations
 * @author Anton Nedbailo
 * @since Aug 30, 2013
 */
public class StringUtils
{
    /**
     * Tokenize a string and give back a String-Array <br>
     * 
     * @param szInput the string which tokenize <br>
     * @return a String-Array <br>
     */
    public static String[] tokenize(String szInput, String szSep)
    {
        if (szInput == null)
        {
            return new String[] {};
        }
        // declaration
        List<String> v = new ArrayList<String>();
        StringTokenizer t = new StringTokenizer(szInput, szSep);
        // move strings to the vector
        while (t.hasMoreTokens())
        {
            v.add(t.nextToken());
        }
        // move vector to string-array
        String[] cmd = new String[v.size()];
        for (int i = 0; i < cmd.length; i++)
        {
            cmd[i] = v.get(i);
        }
        // get back the string-array
        return cmd;
    }

    /**
     * get back the classname without package extension from an object <br>
     * 
     * @param obj the object <br>
     * @return the class name without package extension <br>
     */
    public static String getClassName(final Object obj)
    {
        return getClassName(obj.getClass().getName());
    }

    /**
     * @param classname an String example : net.envinet.cpu.Start
     * @return the last part example Start
     */
    public static String getClassName(final String classname)
    {
        int pos = classname.lastIndexOf('.');
        return classname.substring(pos + 1);
    }

    /**
     * Get spezified parameters from a String
     * 
     * @param paramStr the String to tokenize
     * @param delimiter the used delimiter, i.e. " " or ";"
     * @param key the key of the demanded value(s)
     * @return a array of Values
     */
    public static String[] getStrParametersFromString(String paramStr, String delimiter, String key)
    {
        StringTokenizer params = new StringTokenizer(paramStr, delimiter);
        ArrayList<String> result = new ArrayList<String>();
        while (params.hasMoreTokens())
        {
            StringTokenizer param = new StringTokenizer(params.nextToken(), "=");
            if (param.countTokens() == 2 && param.nextToken().equals(key))
            {
                result.add(param.nextToken());
            }
        }
        return result.toArray(new String[0]);
    }

    /**
     * 
     * Get one parameter from string
     * 
     * @param paramStr - input string
     * @param delimiter - delimeter symbol
     * @param key - parameter name
     * @return - parameter value if success, empty string otherwise
     * 
     *         Author: Anton Nedbailo Create date: 14.07.2011 16:52:08
     */
    public static String getStrParamFromString(String paramStr, String delimiter, String key)
    {
        String[] arrParams = getStrParametersFromString(paramStr, delimiter, key);
        return arrParams.length > 0 ? arrParams[0] : "";
    }
    

    /**
     * This method looks if the txt match to one of the patterns
     * 
     * @param txt
     * @param pattern
     * @param ignorCase
     * @return true if pattern match txt
     */
    public static boolean matchFilter(String txt, String pattern, boolean ignorCase)
    {
        if ((txt == null || txt.equals("")) && (pattern == null || pattern.equals("")))
        {
            return false;
        }
        if (ignorCase)
        {
            txt = txt.toLowerCase();
            pattern = pattern.toLowerCase();
        }
        String[] patterns = pattern.split(",");
        for (String str : patterns)
        {
            if (str.startsWith("*") && str.endsWith("*"))
            {
                str = str.substring(1, str.length() - 1);
                if (txt.contains(str))
                {
                    return true;
                }
            } else if (str.startsWith("*"))
            {
                str = str.substring(1);
                if (txt.endsWith(str))
                {
                    return true;
                }
            } else if (str.endsWith("*"))
            {
                str = str.substring(0, str.length() - 1);
                if (txt.startsWith(str))
                {
                    return true;
                }
            } else if (txt.equals(str))
            {
                return true;
            }
        }
        return false;
    }
    

    /**
     * Get spezified parameters from a String
     * 
     * @param paramStr the String to tokenize
     * @param delimiter the used delimiter, i.e. " " or ";"
     * @param key the key of the demanded value(s)
     * @return a array of Values
     */
    public static Long[] getParametersFromString(final String paramStr, final String delimiter, final String key)
    {
        StringTokenizer params = new StringTokenizer(paramStr, delimiter);
        List<Long> result = new ArrayList<Long>();
        while (params.hasMoreTokens())
        {
            StringTokenizer param = new StringTokenizer(params.nextToken(), "=");
            if (param.countTokens() == 2 && param.nextToken().equals(key))
            {
                result.add(Long.valueOf(param.nextToken()));
            }
        }
        return result.toArray(new Long[0]);
    }
}
