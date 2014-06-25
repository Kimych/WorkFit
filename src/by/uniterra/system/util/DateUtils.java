/**
 * Filename  : DateUtils.java
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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * The <code>DateUtils</code> is used for some Date formatting and converting
 *
 * @author Anton Nedbailo
 * @since Sep 11, 2013
 */
public class DateUtils
{
    //constants
    public static final String EUROP_FULL_DATETIMEFORMAT = "dd.MM.yyyy HH:mm:ss";
    public static final String EUROP_SHORT_TIMEFORMAT = "HH:mm";
    public static final String EUROP_FULL_DATEFORMAT = "dd.MM.yyyy";
    public static final String STANDARD_DATETIMEFORMAT = "dd.MM.yyyy HH:mm";
    public static final String STRAVITA_CSV_FILENAME_DATEFORMAT = "yyyyMMdd";
    public static final String FULL_MONTH_NAME_DATEFORMAT = "dd MMMM yyyy";
    
    public static final String TZ_GMTplus3 = "GMT+3";
    public static final String TZ_UTC = "UTC";
    
    /** a hour in ms . */
    public static final long ONE_HOUR = 3600000;
    /** count of hours oer day */
    private static final short HOUR_PERDAY = 24;
    /** a day in ms . */
    public static final long ONE_DAY = ONE_HOUR * HOUR_PERDAY;
    /** a minute in ms. */
    public static final long ONE_MIN = 60000;
    /** a second in ms . */
    public static final int ONE_SECOND = 1000;
    /** a year in ms . */
    public static final long ONE_YEAR = ONE_DAY * 365;
    /** a week in ms . */
    public static final long ONE_WEEK = 7 * ONE_DAY;
    /** a month in ms . */
    public static final long ONE_MONTH = 30 * ONE_DAY;
    
    public static final long ONE_MINUTE = 60 * ONE_SECOND;
    
   
    /**
     * Get current time
     * 
     * @return Date object with curren time
     *
     * @author Anton Nedbailo
     * @date Sep 22, 2013
     */
    public static Date getTimestamp()
    {
        return new Date(System.currentTimeMillis());
    }
    /**
     * @param ts time
     * @return Wandelt einen Timestamp in eine String bezogen auf GMT um.
     * 
     */
    public static String toGMT(final long ts)
    {
        return toGMT(new Timestamp(ts));
    }

    /**
     * @param ts time
     * @return Wandelt einen Timestamp in eine String bezogen auf GMT um.
     * 
     */
    public static String toGMT(final Timestamp ts)
    {
        return timestampToString(ts, STANDARD_DATETIMEFORMAT, TZ_UTC);
    }
    
    /**
     * 
     * @param ts
     * @param format
     * @param tzId
     * @return
     *
     * @author Anton Nedbailo
     * @date Sep 29, 2013
     */
    public static String timestampToString(final Timestamp ts, final String format, final String tzId)
    {
        Timestamp ret = ts;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone(tzId));
        if (ret == null)
        {
            ret = new Timestamp(new Date().getTime());
        }
        return formatter.format(ret);
    }
}
