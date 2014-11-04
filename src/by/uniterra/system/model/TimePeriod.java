/**
 * Filename  : TimePeriod.java
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

package by.uniterra.system.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import by.uniterra.system.util.DateUtils;

public final class TimePeriod implements Serializable
{
    private static final long serialVersionUID = -64210768599878314L;
    //members
    private String timePeriod = "000000000000";
    private int year;
    private int m_month;
    private int m_week;
    private int m_day;
    private int m_hour;
    private int m_minute;
    
    //constants
    private static final String SZ_YEAR = "���";
    private static final String SZ_MONTH = "�����";
    private static final String SZ_WEEK = "������";
    private static final String SZ_DAY = "����";
    private static final String SZ_HOUR = "���";
    private static final String SZ_MINUTE = "������";

    /**
     * 
     * @param referDate
     * @param time
     * @return
     *
     * @author Anton Nedbailo
     * @date Sep 29, 2013
     */
    public static long getTime(final Date referDate, final String time)
    {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeZone(TimeZone.getTimeZone(DateUtils.TZ_UTC));
        cal.setTime(referDate);
        long beforeTime = cal.getTime().getTime();
        cal.add(Calendar.YEAR, Integer.valueOf(time.substring(0, 2)).intValue());
        cal.add(Calendar.MONTH, Integer.valueOf(time.substring(2, 4)).intValue());
        cal.add(Calendar.WEEK_OF_MONTH, Integer.valueOf(time.substring(4, 6)).intValue());
        cal.add(Calendar.DAY_OF_MONTH, Integer.valueOf(time.substring(6, 8)).intValue());
        cal.add(Calendar.HOUR_OF_DAY, Integer.valueOf(time.substring(8, 10)).intValue());
        cal.add(Calendar.MINUTE, Integer.valueOf(time.substring(10, 12)).intValue());
        return cal.getTime().getTime() - beforeTime;
    }
    
    /** empty standard constructor */
    public TimePeriod()
    {
        super();
    }

   /**
    * 
    * Constructor.
    *
    * @param szTimePeriode
    */
    public TimePeriod(String szTimePeriode)
    {
        timePeriod = szTimePeriode;
        init();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        TimePeriod other = (TimePeriod) obj;
        if (timePeriod == null)
        {
            if (other.timePeriod != null)
            {
                return false;
            }
        } else if (!timePeriod.equals(other.timePeriod))
        {
            return false;
        }
        return true;
    }

    /**
     * 
     * @return
     *
     * @author Anton Nedbailo
     * @date Sep 29, 2013
     */
    public int getDays()
    {
        return m_day;
    }

   /**
    * 
    * @return
    *
    * @author Anton Nedbailo
    * @date Sep 29, 2013
    */
    public String getFormattedString()
    {
        String strResultString = "";
        // get years
        strResultString += getYears() != 0 ? getYears() + SZ_YEAR : "";
        // get months
        strResultString += getMonths() != 0 ? (!strResultString.isEmpty() ? " " : "") + getMonths() + SZ_MONTH : "";
        // get weeks
        strResultString += getWeeks() != 0 ? (!strResultString.isEmpty() ? " " : "") + getWeeks() + SZ_WEEK : "";
        // get days
        strResultString += getDays() != 0 ? (!strResultString.isEmpty() ? " " : "") + getDays() + SZ_DAY : "";
        // get hours
        strResultString += getHours() != 0 ? (!strResultString.isEmpty() ? " " : "") + getHours() + SZ_HOUR : "";
        // get minuts
        strResultString += getMinutes() != 0 ? (!strResultString.isEmpty() ? " " : "") + getMinutes() + SZ_MINUTE : "";
        return strResultString;
    }

   /**
    * 
    * @return
    *
    * @author Anton Nedbailo
    * @date Sep 29, 2013
    */
    public int getHours()
    {
        return m_hour;
    }

    /**
     * 
     * @return
     *
     * @author Anton Nedbailo
     * @date Sep 29, 2013
     */
    public int getMinutes()
    {
        return m_minute;
    }

    /**
     * 
     * @return
     *
     * @author Anton Nedbailo
     * @date Sep 29, 2013
     */
    public int getMonths()
    {
        return m_month;
    }

   /**
    * 
    * @param date
    * @param roundup
    * @return
    *
    * @author Anton Nedbailo
    * @date Sep 29, 2013
    */
    public long getReferenceDate(Date date, boolean roundup)
    {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeZone(TimeZone.getTimeZone(DateUtils.TZ_UTC));
        cal.setTime(date);
        int actYear = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        // start with 0 = January
        int week = cal.get(Calendar.DAY_OF_WEEK);
        // start with 1 = Sunday
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        // test minutes
        if (m_minute != 0)
        {
            int diff = minute / m_minute;
            Calendar ref = Calendar.getInstance(Locale.getDefault());
            ref.setTimeZone(TimeZone.getTimeZone(DateUtils.TZ_UTC));
            ref.setTime(date);
            ref.set(Calendar.SECOND, 00);
            ref.set(Calendar.MINUTE, diff * m_minute);
            if (roundup && ref.getTime().getTime() < cal.getTime().getTime())
            {
                ref.set(Calendar.MINUTE, (diff + 1) * m_minute);
            }
            return ref.getTime().getTime();
        }
        if (m_hour != 0)
        {
            int diff = hour / m_hour;
            Calendar ref = Calendar.getInstance(Locale.getDefault());
            ref.setTimeZone(TimeZone.getTimeZone(DateUtils.TZ_UTC));
            ref.setTime(date);
            ref.set(Calendar.SECOND, 00);
            ref.set(Calendar.MINUTE, 00);
            ref.set(Calendar.HOUR_OF_DAY, diff * m_hour);
            if (roundup && ref.getTime().getTime() < cal.getTime().getTime())
            {
                ref.set(Calendar.HOUR_OF_DAY, (diff + 1) * m_hour);
            }
            return ref.getTime().getTime();
        }
        if (m_day != 0)
        {
            int diff = day / m_day;
            Calendar ref = Calendar.getInstance(Locale.getDefault());
            ref.setTimeZone(TimeZone.getTimeZone(DateUtils.TZ_UTC));
            ref.setTime(date);
            ref.set(Calendar.SECOND, 00);
            ref.set(Calendar.MINUTE, 00);
            ref.set(Calendar.HOUR_OF_DAY, 00);
            ref.set(Calendar.DAY_OF_MONTH, diff * m_day);
            if (roundup && ref.getTime().getTime() < cal.getTime().getTime())
            {
                ref.set(Calendar.DAY_OF_MONTH, (diff + 1) * m_day);
            }
            return ref.getTime().getTime();
        }
        if (m_week != 0)
        {
            int diff = week - 2;
            // 2 is Monday
            Calendar ref = Calendar.getInstance(Locale.getDefault());
            ref.setTimeZone(TimeZone.getTimeZone(DateUtils.TZ_UTC));
            ref.setTime(date);
            ref.set(Calendar.SECOND, 00);
            ref.set(Calendar.MINUTE, 00);
            ref.set(Calendar.HOUR_OF_DAY, 00);
            ref.set(Calendar.DAY_OF_MONTH, day - diff);
            if (roundup && ref.getTime().getTime() < cal.getTime().getTime())
            {
                ref.set(Calendar.DAY_OF_MONTH, day - diff + 7);
                // add one week
            }
            return ref.getTime().getTime();
        }
        if (m_month != 0)
        {
            int diff = month / m_month;
            Calendar ref = Calendar.getInstance(Locale.getDefault());
            ref.setTimeZone(TimeZone.getTimeZone(DateUtils.TZ_UTC));
            ref.setTime(date);
            ref.set(Calendar.SECOND, 00);
            ref.set(Calendar.MINUTE, 00);
            ref.set(Calendar.HOUR_OF_DAY, 00);
            ref.set(Calendar.DAY_OF_MONTH, 01);
            ref.set(Calendar.MONTH, diff * m_month);
            if (roundup && ref.getTime().getTime() < cal.getTime().getTime())
            {
                ref.set(Calendar.MONTH, (diff + 1) * m_month);
            }
            return ref.getTime().getTime();
        }
        if (actYear != 0)
        {
            int diff = actYear / actYear;
            Calendar ref = Calendar.getInstance(Locale.getDefault());
            ref.setTimeZone(TimeZone.getTimeZone(DateUtils.TZ_UTC));
            ref.setTime(date);
            ref.set(Calendar.SECOND, 00);
            ref.set(Calendar.MINUTE, 00);
            ref.set(Calendar.HOUR_OF_DAY, 00);
            ref.set(Calendar.DAY_OF_MONTH, 01);
            ref.set(Calendar.MONTH, 00);
            ref.set(Calendar.YEAR, diff * actYear);
            if (roundup && ref.getTime().getTime() < cal.getTime().getTime())
            {
                ref.set(Calendar.YEAR, (diff + 1) * actYear);
            }
            return ref.getTime().getTime();
        }
        return cal.getTime().getTime();
    }

    /**
     * 
     * @return
     *
     * @author Anton Nedbailo
     * @date Sep 29, 2013
     */
    public long getTime()
    {
        return getYears() * DateUtils.ONE_YEAR + getMonths() * DateUtils.ONE_MONTH + getWeeks() * DateUtils.ONE_WEEK + getDays() * DateUtils.ONE_DAY + getHours() * DateUtils.ONE_HOUR
                + getMinutes() * DateUtils.ONE_MIN;
    }

    /**
     * 
     * @param referDate
     * @return
     *
     * @author Anton Nedbailo
     * @date Sep 29, 2013
     */
    public long getTime(Date referDate)
    {
        return getTime(referDate, false);
    }

   /**
    * 
    * @param referDate
    * @param regress
    * @return
    *
    * @author Anton Nedbailo
    * @date Sep 29, 2013
    */
    public long getTime(Date referDate, boolean regress)
    {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeZone(TimeZone.getTimeZone(DateUtils.TZ_UTC));
        cal.setTime(referDate);
        long beforeTime = cal.getTime().getTime();
        cal.add(Calendar.YEAR, (regress ? -year : year));
        if (m_month > 0)
        {
            int delta = cal.get(Calendar.DAY_OF_MONTH) - cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            cal.add(Calendar.MONTH, (regress ? -m_month : m_month));
            if (delta == 0)
            {
                cal.add(Calendar.DAY_OF_MONTH, (cal.getActualMaximum(Calendar.DAY_OF_MONTH) - cal.get(Calendar.DAY_OF_MONTH)));
            }
        }
        cal.add(Calendar.WEEK_OF_MONTH, (regress ? -m_week : m_week));
        cal.add(Calendar.DAY_OF_MONTH, (regress ? -m_day : m_day));
        cal.add(Calendar.HOUR_OF_DAY, (regress ? -m_hour : m_hour));
        cal.add(Calendar.MINUTE, (regress ? -m_minute : m_minute));
        long currentTime = cal.getTime().getTime();
        return (regress ? beforeTime - currentTime : currentTime - beforeTime);
    }

    /**
     * 
     * @return
     *
     * @author Anton Nedbailo
     * @date Sep 29, 2013
     */
    public String getTimePeriode()
    {
        return timePeriod;
    }

    /**
     * 
     * @return
     *
     * @author Anton Nedbailo
     * @date Sep 29, 2013
     */
    public int getWeeks()
    {
        return m_week;
    }

    /**
     * 
     * @return
     *
     * @author Anton Nedbailo
     * @date Sep 29, 2013
     */
    public int getYears()
    {
        return year;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((timePeriod == null) ? 0 : timePeriod.hashCode());
        return result;
    }

   /**
    * 
    * 
    *
    * @author Anton Nedbailo
    * @date Sep 29, 2013
    */
    private void init()
    {
        if (timePeriod != null && !timePeriod.isEmpty())
        {
            year = Integer.valueOf(timePeriod.substring(0, 2)).intValue();
            m_month = Integer.valueOf(timePeriod.substring(2, 4)).intValue();
            m_week = Integer.valueOf(timePeriod.substring(4, 6)).intValue();
            m_day = Integer.valueOf(timePeriod.substring(6, 8)).intValue();
            m_hour = Integer.valueOf(timePeriod.substring(8, 10)).intValue();
            m_minute = Integer.valueOf(timePeriod.substring(10, 12)).intValue();
        }
    }

   /**
    * 
    * @return
    *
    * @author Anton Nedbailo
    * @date Sep 29, 2013
    */
    public boolean isEmpty()
    {
        if (timePeriod.equals("000000000000"))
        {
            return true;
        }
        return false;
    }

    /**
     * 
     * @param time
     *
     * @author Anton Nedbailo
     * @date Sep 29, 2013
     */
    public void setTimePeriode(long time)
    {
        long milliseconds = time;
        String[] szdate = new String[6];
        szdate[0] = String.valueOf((int) (milliseconds / DateUtils.ONE_YEAR));
        milliseconds = milliseconds % DateUtils.ONE_YEAR;
        szdate[1] = String.valueOf((int) (milliseconds / DateUtils.ONE_MONTH));
        milliseconds = milliseconds % DateUtils.ONE_MONTH;
        szdate[2] = String.valueOf((int) (milliseconds / DateUtils.ONE_WEEK));
        milliseconds = milliseconds % DateUtils.ONE_WEEK;
        szdate[3] = String.valueOf((int) (milliseconds / DateUtils.ONE_DAY));
        milliseconds = milliseconds % DateUtils.ONE_DAY;
        szdate[4] = String.valueOf((int) (milliseconds / DateUtils.ONE_HOUR));
        milliseconds = milliseconds % DateUtils.ONE_HOUR;
        szdate[5] = String.valueOf((int) (milliseconds / DateUtils.ONE_MIN));
        milliseconds = milliseconds % DateUtils.ONE_MIN;
        timePeriod = "";
        for (String element : szdate)
        {
            if (element.length() < 2)
            {
                timePeriod = timePeriod + "0" + element;
            } else
            {
                timePeriod = timePeriod + element;
            }
        }
        init();
    }

    /**
     * 
     * @param szTimePeriode
     *
     * @author Anton Nedbailo
     * @date Sep 29, 2013
     */
    public void setTimePeriode(String szTimePeriode)
    {
        timePeriod = szTimePeriode;
        init();
    }
}
