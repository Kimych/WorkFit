/**
 * Filename  : ILogLevels.java
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

package by.uniterra.system.iface;

/**
 * The <code>ILogLevels</code> is used to declare a log levels
 * 
 * @author Anton Nedbailo
 * @since Aug 30, 2013
 */
public interface ILogLevels
{
    // constants
    short LOGLEVEL_INFORMATION = 101;
    short LOGLEVEL_ERROR = 103;
    short LOGLEVEL_DEBUG = 100;
    short LOGLEVEL_WARNING = 102;
    short LOGLEVEL_ALARM = 104;
}
