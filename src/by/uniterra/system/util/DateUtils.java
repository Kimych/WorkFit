/**
 * Filename  : DateUtils.java
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
 * Project    : ARMParis
 *
 * Author     : Anton Nedbailo
 *
 * last change by : $Author:$ 
 * last check in  : $Date: $
 */

package by.uniterra.system.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import by.uniterra.system.model.TimePeriodEx;

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
     * @param ts a Timestamp
     * @param timeZoneId Id of TimeZone
     * @return a String formated with SimpleDateFormat
     */
    public static String toFormatedString(final Timestamp ts, final String timeZoneId)
    {
        SimpleDateFormat df = new SimpleDateFormat(STANDARD_DATETIMEFORMAT);
        df.setTimeZone(TimeZone.getTimeZone(timeZoneId));
        return df.format(ts.getTime());
    }
    
   /**
    * 
    * @param s
    * @param format
    * @return
    *
    * @author Anton Nedbailo
    * @date Sep 29, 2013
    */
    public static Timestamp stringToTimestamp(final String s, final String format)
    {
        return stringToTimestamp(s, format, TZ_UTC, false);
    }

    /**
     * 
     * @param s
     * @param format
     * @param tzId
     * @return
     *
     * @author Anton Nedbailo
     * @date Sep 29, 2013
     */
    public static Timestamp stringToTimestamp(final String s, final String format, final String tzId)
    {
        return stringToTimestamp(s, format, tzId, false);
    }

    /**
     * 
     * @param s
     * @param format
     * @param tzId
     * @param checkIfDateIsValid
     * @return
     *
     * @author Anton Nedbailo
     * @date Sep 29, 2013
     */
    private static Timestamp stringToTimestamp(final String s, final String format, final String tzId, final boolean checkIfDateIsValid)
    {
        Date date = null;
        if (s != null && !s.equals("") && !s.equals("0"))
        {
            SimpleDateFormat df = new SimpleDateFormat(format);
            df.setLenient(!checkIfDateIsValid);
            df.setTimeZone(TimeZone.getTimeZone(tzId));
            try
            {
                date = df.parse(s);
            } catch (ParseException e)
            {
                //Log.error(DateUtils.class, e, "stringToTimestamp error ");
            }
        } else
        {
            date = new java.util.Date();
        }
        Timestamp ts = new Timestamp(date.getTime());
        return ts;
    }
    
    /**
     * 
     * @param tsStart
     * @param tsEnd
     * @return
     *
     * @author Anton Nedbailo
     * @date Sep 29, 2013
     */
    public static String to2GMTString(final Timestamp tsStart, final Timestamp tsEnd)
    {
        return timestampToString(tsStart, STANDARD_DATETIMEFORMAT, TZ_UTC) + " - " + timestampToString(tsEnd, STANDARD_DATETIMEFORMAT, TZ_UTC) + " GMT";
    }
    
    public static String to2GMTString(final Date tsStart, final Date tsEnd)
    {
        return timestampToString(new Timestamp(tsStart.getTime()), STANDARD_DATETIMEFORMAT, TZ_UTC) + " - " + timestampToString(new Timestamp(tsEnd.getTime()), STANDARD_DATETIMEFORMAT, TZ_UTC) + " GMT";
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
    

    /**
     * @return Wandelt einen Timestamp in eine String bezogen auf UTC um.
     * 
     */
    public static String toUTC(final long ts)
    {
        return toUTCString(ts, STANDARD_DATETIMEFORMAT);
    }

    /**
     * @param ts timestamp
     * @return in US Format with UTC as postfix
     */
    public static String toUTCString(final long ts)
    {
        return toUTCString(ts, STANDARD_DATETIMEFORMAT) + TZ_UTC;
    }

    /**
     * @param ts time
     * @param format the dateformat, for example "dd.MM.yyyy HH:mm"
     * @return formatted String
     */
    public static String toUTCString(final long ts, final String format)
    {
        return timestampToString(new Timestamp(ts), format, TZ_UTC);
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
     * Calculate and return start time by pointed parameters
     * 
     * @return - generated start time
     * 
     *         Author: Anton Create date: 20.01.2009 14:01:30
     */
    public static Timestamp makeNewBeginTime(TimePeriodEx tsPeriod, TimePeriodEx tsOffset, boolean bRoundTo, final TimeZone tzTimeZone)
    {
        final Timestamp tsNewBeginTime = new Timestamp(new Date().getTime());
        // check RoundTo flag
        if (bRoundTo)
        {
            tsNewBeginTime.setTime(getRoundToBeginTime(tsPeriod, tsOffset, tzTimeZone).getTime());
        } else
        { // BeginTime = (Now + Offset) + ( - Period)
          // make ( - Period)
            final TimePeriodEx extpNegPeriod = new TimePeriodEx(tsPeriod.getTime());
            extpNegPeriod.setNegative(!extpNegPeriod.isNegative());
            tsNewBeginTime.setTime(TimePeriodEx.getTimeWithOffset(getNowPlusOffset(tsOffset), extpNegPeriod).getTime());
        }
        return tsNewBeginTime;
    }
    
    /**
     * 
     * Get Now + Offset time
     * 
     * @return - Timestamp object = Now + Offset
     * 
     *         Author: Anton Nedbailo Create date: Sep 23, 2008 11:01:44 AM
     */
    private static Timestamp getNowPlusOffset(final TimePeriodEx tsOffset)
    {
        // create Calendar with Now time in UTC time zone
        final Calendar calNow = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        return TimePeriodEx.getTimeWithOffset(new Timestamp(calNow.getTimeInMillis()), tsOffset);
    }

    /**
     * 
     * Calculate and return end time by pointed parameters
     * 
     * @return - generated start time
     * 
     *         Author: Anton Create date: 20.01.2009 14:01:30
     */
    public static Timestamp makeNewEndTime(final TimePeriodEx tsPeriod, final TimePeriodEx tsOffset, final boolean bRoundTo,
            final TimeZone tzTimeZone)
    {
        final Timestamp tsNewEndTime = new Timestamp(new Date().getTime());
        // check RoundTo flag
        if (bRoundTo)
        { // EndTime = RoundToBeginTime + till end of unit (years, months...)
            tsNewEndTime.setTime(getRoundToEndTime(tsPeriod, tsOffset, tzTimeZone).getTime());
        } else
        { // EndTime = (Now + Offset)
            tsNewEndTime.setTime(getNowPlusOffset(tsOffset).getTime());
        }
        return tsNewEndTime;
    }
    

    /**
     * 
     * Calculate rounded end time by pointed parameters
     * 
     * @return - generated end time
     * 
     *         Author: Anton Create date: 20.01.2009 14:01:30
     */
    public static Timestamp getRoundToEndTime(TimePeriodEx tsPeriod, TimePeriodEx tsOffset, final TimeZone tzTimeZone)
    {
        // create Calendar with Now time in UTC time zone
        final Calendar calNow = Calendar.getInstance(tzTimeZone);
        // set RoundToBeginTime as Calendar time
        calNow.setTime(getRoundToBeginTime(tsPeriod, tsOffset, tzTimeZone));
        if (tsPeriod.getYears() != 0)
        {
            // set beginning of the year
            calNow.set(Calendar.DAY_OF_YEAR, 1);
            calNow.set(Calendar.HOUR_OF_DAY, 0);
            calNow.set(Calendar.MINUTE, 0);
            calNow.set(Calendar.SECOND, 0);
            calNow.set(Calendar.MILLISECOND, 0);
            // startTime + Years
            calNow.add(Calendar.YEAR, tsPeriod.getYears());
        } else if (tsPeriod.getMonths() != 0)
        {
            // set beginning of the month
            calNow.set(Calendar.DAY_OF_MONTH, 1);
            calNow.set(Calendar.HOUR_OF_DAY, 0);
            calNow.set(Calendar.MINUTE, 0);
            calNow.set(Calendar.SECOND, 0);
            calNow.set(Calendar.MILLISECOND, 0);
            // startTime + Month
            calNow.add(Calendar.MONTH, tsPeriod.getMonths());
        } else if (tsPeriod.getWeeks() != 0)
        {
            // set beginning of the week
            calNow.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            calNow.set(Calendar.HOUR_OF_DAY, 0);
            calNow.set(Calendar.MINUTE, 0);
            calNow.set(Calendar.SECOND, 0);
            calNow.set(Calendar.MILLISECOND, 0);
            // startTime + Weeks
            calNow.add(Calendar.WEEK_OF_YEAR, tsPeriod.getWeeks());
        } else if (tsPeriod.getDays() != 0)
        {
            // set beginning of the day
            calNow.set(Calendar.HOUR_OF_DAY, 0);
            calNow.set(Calendar.MINUTE, 0);
            calNow.set(Calendar.SECOND, 0);
            calNow.set(Calendar.MILLISECOND, 0);
            // startTime + Days
            calNow.add(Calendar.DAY_OF_YEAR, tsPeriod.getDays());
        } else if (tsPeriod.getHours() != 0)
        {
            // set beginning of the hour
            calNow.set(Calendar.MINUTE, 0);
            calNow.set(Calendar.SECOND, 0);
            calNow.set(Calendar.MILLISECOND, 0);
            // startTime + Hours
            calNow.add(Calendar.HOUR, tsPeriod.getHours());
        }
        return new Timestamp(calNow.getTimeInMillis());
    }


    /**
     * 
     * Calculate rounded start time by pointed parameters
     * 
     * @return - generated start time
     * 
     *         Author: Anton Create date: 20.01.2009 14:01:30
     */
    public static Timestamp getRoundToBeginTime(final TimePeriodEx tsPeriod, final TimePeriodEx tsOffset, final TimeZone tzTimeZone)
    {
        // create Calendar with Now time in UTC time zone
        final Calendar calNow = Calendar.getInstance(tzTimeZone);
        // set Now+Offset as Calendar time
        calNow.setTime(getNowPlusOffset(tsOffset));
        if (tsPeriod.getYears() != 0)
        {
            // startTime - (Years - 1)
            calNow.add(Calendar.YEAR, -(tsPeriod.getYears() - 1));
            // set beginning of the year
            calNow.set(Calendar.DAY_OF_YEAR, 1);
            calNow.set(Calendar.HOUR_OF_DAY, 0);
            calNow.set(Calendar.MINUTE, 0);
            calNow.set(Calendar.SECOND, 0);
            calNow.set(Calendar.MILLISECOND, 0);
        } else if (tsPeriod.getMonths() != 0)
        {
            // startTime - (Month - 1)
            calNow.add(Calendar.MONTH, -(tsPeriod.getMonths() - 1));
            // set beginning of the month
            calNow.set(Calendar.DAY_OF_MONTH, 1);
            calNow.set(Calendar.HOUR_OF_DAY, 0);
            calNow.set(Calendar.MINUTE, 0);
            calNow.set(Calendar.SECOND, 0);
            calNow.set(Calendar.MILLISECOND, 0);
        } else if (tsPeriod.getWeeks() != 0)
        {
            // startTime - (Weeks - 1)
            calNow.add(Calendar.WEEK_OF_YEAR, -(tsPeriod.getWeeks() - 1));
            // set beginning of the week
            calNow.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            calNow.set(Calendar.HOUR_OF_DAY, 0);
            calNow.set(Calendar.MINUTE, 0);
            calNow.set(Calendar.SECOND, 0);
            calNow.set(Calendar.MILLISECOND, 0);
        } else if (tsPeriod.getDays() != 0)
        {
            // startTime - (Days - 1)
            calNow.add(Calendar.DAY_OF_YEAR, -(tsPeriod.getDays() - 1));
            // set beginning of the day
            calNow.set(Calendar.HOUR_OF_DAY, 0);
            calNow.set(Calendar.MINUTE, 0);
            calNow.set(Calendar.SECOND, 0);
            calNow.set(Calendar.MILLISECOND, 0);
        } else if (tsPeriod.getHours() != 0)
        {
            // startTime - (Hours - 1)
            calNow.add(Calendar.HOUR, -(tsPeriod.getHours() - 1));
            // set beginning of the hour
            calNow.set(Calendar.MINUTE, 0);
            calNow.set(Calendar.SECOND, 0);
            calNow.set(Calendar.MILLISECOND, 0);
        }
        return new Timestamp(calNow.getTimeInMillis());
    }
    
}
