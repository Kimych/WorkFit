/**
 * Filename  : TimePeriodEx.java
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
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


/**
 *  Extended TimePeriod class with negative period support
 */
public class TimePeriodEx implements Serializable
{
    private static final long serialVersionUID = 143715288332168854L;
    //members
    private boolean m_bIsNegative;
    private TimePeriod m_tpPeriod;
    
    /**
     * Default constructor
     */
    public TimePeriodEx()
    {
        m_tpPeriod = new TimePeriod();
    }
    
    /**
     * Constructor with parameters
     * 
     * @param strPeriod - TimePeriod formatted String object
     */
    public TimePeriodEx(String strPeriod)
    {
        //check for negative
        if (strPeriod.length() > 12 && strPeriod.charAt(12) == '-')
        {
            m_bIsNegative = true;
            strPeriod = strPeriod.substring(0, 12); 
        }
        else
            m_bIsNegative = false;
                 
        m_tpPeriod = new TimePeriod(strPeriod);
    }
    
    /**
     * Constructor with parameters
     * 
     * @param strPeriod - TimePeriod formatted String object
     */
    public TimePeriodEx(TimePeriod tpPeriod)
    {
       m_bIsNegative = false;
       m_tpPeriod = tpPeriod;
    }
    
    /**
     * Constructor with parameters
     * 
     * @param strPeriod - TimePeriod formatted String object
     */
    public TimePeriodEx(long lPeriodInMills)
    {
       m_bIsNegative = lPeriodInMills < 0;
      
       m_tpPeriod = new TimePeriod();
       m_tpPeriod.setTimePeriode(Math.abs(lPeriodInMills));
    }
    
    /**
     * 
     * Get time with pointed Offset from pointed Time
     *
     * @param tsTime - Timestamp start time
     * @param extpOffset - Offset value
     * @return - Time+Offset Timestamp object
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 20, 2008 3:38:28 PM
     */
    public static Timestamp getTimeWithOffset(Timestamp tsTime, TimePeriodEx extpOffset)
    {
        //set pointed date to Calendar
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTime(tsTime);

        //add Offset
        cal.add(Calendar.YEAR, extpOffset.getYears());
        cal.add(Calendar.MONTH, extpOffset.getMonths());
        cal.add(Calendar.WEEK_OF_MONTH, extpOffset.getWeeks());
        cal.add(Calendar.DAY_OF_MONTH, extpOffset.getDays());
        cal.add(Calendar.HOUR_OF_DAY, extpOffset.getHours());
        cal.add(Calendar.MINUTE, extpOffset.getMinutes());
                
        return new Timestamp(cal.getTimeInMillis());
    }
    
    /**
     * Description of this standard method
     * 
     * @param o TestObject
     * @return true, if o is equal
     */
    @Override
    public boolean equals(Object obj)
    {
        return (obj instanceof TimePeriodEx ? ((TimePeriodEx) obj).getTimePeriode().equals(getTimePeriode()) : false);
    }

    
    /**
     * 
     * Get days count
     *
     * @return - count of days
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 7:51:41 PM
     */
    public int getDays()
    {
        return m_tpPeriod.getDays() * (m_bIsNegative ? -1 : 1);
    }

    /**
     * 
     * Get count of hours
     *
     * @return - count of hours
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 7:52:20 PM
     */
    public int getHours()
    {
        return m_tpPeriod.getHours() * (m_bIsNegative ? -1 : 1);
    }

    /**
     * 
     * Get minutes count 
     *
     * @return - count of minutes
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 7:53:32 PM
     */
    public int getMinutes()
    {
        return m_tpPeriod.getMinutes() * (m_bIsNegative ? -1 : 1);
    }

    /**
     * 
     * Get count of months  
     *
     * @return - months count
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 7:54:17 PM
     */
    public int getMonths()
    {
        return m_tpPeriod.getMonths() * (m_bIsNegative ? -1 : 1);
    }
    
   /**
    * 
    * Get count of weeks 
    *
    * @return - weeks count
    *
    * Author:      Anton Nedbailo
    * Create date: Sep 18, 2008 8:00:09 PM
    */
    public int getWeeks()
    {
        return m_tpPeriod.getWeeks() * (m_bIsNegative ? -1 : 1);
    }

    /**
     * 
     * Get years count 
     *
     * @return - count of years
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 8:00:42 PM
     */
    public int getYears()
    {
        return m_tpPeriod.getYears() * (m_bIsNegative ? -1 : 1);
    }
    
    /**
     * 
     * Get get real count of calendar milliseconds from pointed Timestamp
     *
     * @param referDate - Timestamp object
     * @return - milliseconds count
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 7:55:44 PM
     */
    public long getTime(Timestamp referDate)
    {
        return m_tpPeriod.getTime(referDate, m_bIsNegative);
    }
    
    /**
     * 
     * Return time period
     *
     * @return - TimePeriod formatted String object
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 7:59:20 PM
     */
    public String getTimePeriode()
    {      
        return  m_tpPeriod.getTimePeriode() + (m_bIsNegative ? "-" : "");
    }
  
    /**
     * 
     * Return time period
     *
     * @return - TimePeriod object
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 7:59:20 PM
     */
    public TimePeriod getNativeTimePeriod()
    {      
        return  m_tpPeriod;
    }
    
    /**
     * 
     * Is period empty 
     *
     * @return - true if empty, false otherwise
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 8:03:15 PM
     */
    public boolean isEmpty()
    {
        return m_tpPeriod.isEmpty();
    }
    
    /**
     * 
     * Set time period by pointed milliseconds count  
     *
     * @param loTimePeriod - milliseconds count
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 8:04:22 PM
     */
    public void setTimePeriode(long loTimePeriod)
    {
        //cset negative flag
        m_bIsNegative = loTimePeriod < 0;
         
        m_tpPeriod.setTimePeriode(Math.abs(loTimePeriod));
    }
    
    /**
     * 
     * Set time period by pointed String object
     *
     * @param szTimePeriode - TimePeriod formatted String object
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 8:04:22 PM
     */
    public void setTimePeriode(String szTimePeriode)
    {
        //check for negative
        if (szTimePeriode.length() > 12 && szTimePeriode.charAt(12) == '-')
        {
            m_bIsNegative = true;
            szTimePeriode = szTimePeriode.substring(0, 12);           
        }
        else
            m_bIsNegative = false;
                       
        m_tpPeriod.setTimePeriode(szTimePeriode);
    }

    /**
     * 
     * Set time period by pointing start and end time 
     *
     * @param tsBegin - Timestamp start time
     * @param tsEnd - Timestamp end time
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 8:17:26 PM
     */
    public void setTimePeriode(Timestamp tsBegin, Timestamp tsEnd)
    {
        long loTimePeriod = tsEnd.getTime() - tsBegin.getTime();
        setTimePeriode(loTimePeriod);
    }
    
    /**
     * 
     * Set Negative flag 
     *
     * @param bSetNegative - boolean value
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 8:20:42 PM
     */
    public void setNegative(boolean bSetNegative)
    {
        m_bIsNegative = bSetNegative;
    }
    
    /**
     * 
     * Get period in milliseconds
     *
     * @return milliseconds count (not real Calendar time!)
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 8:20:42 PM
     */
    public long getTime()
    {
        return m_tpPeriod.getTime() + (m_bIsNegative ? -1 : 1) ;
    }
    
    /**
     * 
     * Return Negative flag 
     *
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 8:20:42 PM
     */
    public boolean isNegative()
    {
        return m_bIsNegative ;
    }
    
    /**
     * 
     * Set days count
     *
     * @param iDays - count of days
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 7:51:41 PM
     */
    public void setDays(int iDays)
    {       
        //get period string
        String strPeriod = m_tpPeriod.getTimePeriode();
        //change Days count
        strPeriod = strPeriod.subSequence(0, 6) + getTwoCharsValue(iDays) + strPeriod.subSequence(8, 12);
        //set new period
        m_tpPeriod.setTimePeriode(strPeriod);
    }
    
    /**
     * 
     * Convert value to two chars String object 
     *
     * @param iValue - integer value
     * @return - two chars String object
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 8:33:24 PM
     */
    private String getTwoCharsValue(int iValue)
    {
        String strValue = "";
        if (iValue <= 9 && iValue >= 0)
        {
            //if between 0 and 9
            strValue = "0" + iValue;
        }
        else
        {
            if (iValue <= 99 && iValue >= 10)
            {
              //if between 10 and 99
                strValue = "" + iValue;
            }
            else    //otherwise
                strValue = "00";
        }
        
        return strValue;        
    }

    /**
     * 
     * Set count of hours
     *
     * @param iHours - count of hours
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 7:52:20 PM
     */
    public void setHours(int iHours)
    {
        //get period string
        String strPeriod = m_tpPeriod.getTimePeriode();
        //change Days count
        strPeriod = strPeriod.subSequence(0, 8) + getTwoCharsValue(iHours) + strPeriod.subSequence(10, 12);
        //set new period
        m_tpPeriod.setTimePeriode(strPeriod);

    }

    /**
     * 
     * Set count of minutes
     *
     * @param iMinutes - count of minutes
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 7:52:20 PM
     */
    public void setMinutes(int iMinutes)
    {
        //get period string
        String strPeriod = m_tpPeriod.getTimePeriode();
        //change Days count
        strPeriod = strPeriod.subSequence(0, 10) + getTwoCharsValue(iMinutes);
        //set new period
        m_tpPeriod.setTimePeriode(strPeriod);
   }

    /**
     * 
     * Set count of months
     *
     * @param iMonths - count of months
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 7:52:20 PM
     */
    public void setMonths(int iMonths)
    {
        //get period string
        String strPeriod = m_tpPeriod.getTimePeriode();
        //change Days count
        strPeriod = strPeriod.subSequence(0, 2) + getTwoCharsValue(iMonths) + strPeriod.subSequence(4, 12);
        //set new period
        m_tpPeriod.setTimePeriode(strPeriod);
    }
    
    /**
     * 
     * Set count of weeks
     *
     * @param iWeeks - count of weeks
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 7:52:20 PM
     */
    public void setWeeks(int iWeeks)
    {
        //get period string
        String strPeriod = m_tpPeriod.getTimePeriode();
        //change Days count
        strPeriod = strPeriod.subSequence(0, 4) + getTwoCharsValue(iWeeks) + strPeriod.subSequence(6, 12);
        //set new period
        m_tpPeriod.setTimePeriode(strPeriod);
   }

    /**
     * 
     * Set count of years
     *
     * @param iYears - count of years
     *
     * Author:      Anton Nedbailo
     * Create date: Sep 18, 2008 7:52:20 PM
     */
    public void setYears(int iYears)
    {
        //get period string
        String strPeriod = m_tpPeriod.getTimePeriode();
        //change Days count
        strPeriod = getTwoCharsValue(iYears) + strPeriod.subSequence(2, 12);
        //set new period
        m_tpPeriod.setTimePeriode(strPeriod);
   }
    
    /**
     * 
     * Return human assosiated formatted string from this TimePeriod object
     *
     * @return String object
     *
     * Author:      Anton
     * Create date: 16.12.2008 11:37:15
     */
    public String getFormattedString()
    {
       return (m_bIsNegative ? "- " : "") + m_tpPeriod.getFormattedString();
    }
}
