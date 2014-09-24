/**
 * Filename  : WorklogInfoHolder.java
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
 * Project    : WorkFit
 *
 * Author     : Sergio Alecky
 *
 * last change by : $Author:$ 
 * last check in  : $Date: $
 */

package by.uniterra.udi.model;

import java.util.Date;



/**
 * The <code>WorklogInfoHolder</code> is used to hold all necessary worklog
 * statistics for one Worker
 *
 * @author Sergio Alecky
 * @since Aug 25, 2014
 */
public class WorkLogInfoHolder
{
    private String CurentTime;
    private double ToPlane;
    private double ToBonus;
    private double TimeLeft;
    private Date LastUpdateDate;
    private String NameWorker;
    private boolean BeInPlane;

    public WorkLogInfoHolder(String CurentTime, double ToPlane, double ToBonus, double TimeLeft, Date LastUpdateDate, String NameWorker, boolean BeInPlane)
    {
        this.CurentTime = CurentTime;
        this.ToPlane = ToPlane;
        this.ToBonus = ToBonus;
        this.TimeLeft = TimeLeft;
        this.LastUpdateDate = LastUpdateDate;
        this.NameWorker = NameWorker;
        this.BeInPlane = BeInPlane;
    }


    public String getCurentTime()
    {
        return CurentTime;
    }

    public void setCurentTime(String CurentTime)
    {
        this.CurentTime = CurentTime;
    }

    public double getToPlane()
    {
        return ToPlane;
    }

    public void setToPlane(double ToPlane)
    {
        this.ToPlane = ToPlane;
    }

    public double getToBonus()
    {
        return ToBonus;
    }

    public void setToBonus(double ToBonus)
    {
        this.ToBonus = ToBonus;
    }

    public double getTimeLeft()
    {
        return TimeLeft;
    }

    public void setTimeLeft(double TimeLeft)
    {
        this.TimeLeft = TimeLeft;
    }

    public Date getLastUpdateDate()
    {
        return LastUpdateDate;
    }

    public void setLastUpdateDate(Date LastUpdateDate)
    {
        this.LastUpdateDate = LastUpdateDate;
    }

    public String getNameWorker()
    {
        return NameWorker;
    }

    public void setNameWorker(String NameWorker)
    {
        this.NameWorker = NameWorker;
    }


    public boolean isBeInPlane()
    {
        return BeInPlane;
    }


    public void setBeInPlane(boolean BeInPlane)
    {
        this.BeInPlane = BeInPlane;
    }
    

    @Override
    public String toString()
    {
        return "WorkLogInfoHolder [CurentTime=" + CurentTime + ", ToPlane=" + ToPlane + ", ToBonus=" + ToBonus + ", TimeLeft=" + TimeLeft + ", LastUpdateDate="
                + LastUpdateDate + ", NameWorker=" + NameWorker + ", BeInPlane=" + BeInPlane + "]";
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (BeInPlane ? 1231 : 1237);
        result = prime * result + ((CurentTime == null) ? 0 : CurentTime.hashCode());
        result = prime * result + ((LastUpdateDate == null) ? 0 : LastUpdateDate.hashCode());
        result = prime * result + ((NameWorker == null) ? 0 : NameWorker.hashCode());
        long temp;
        temp = Double.doubleToLongBits(TimeLeft);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ToBonus);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ToPlane);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }


    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WorkLogInfoHolder other = (WorkLogInfoHolder) obj;
        if (BeInPlane != other.BeInPlane)
            return false;
        if (CurentTime == null)
        {
            if (other.CurentTime != null)
                return false;
        }
        else if (!CurentTime.equals(other.CurentTime))
            return false;
        if (LastUpdateDate == null)
        {
            if (other.LastUpdateDate != null)
                return false;
        }
        else if (!LastUpdateDate.equals(other.LastUpdateDate))
            return false;
        if (NameWorker == null)
        {
            if (other.NameWorker != null)
                return false;
        }
        else if (!NameWorker.equals(other.NameWorker))
            return false;
        if (Double.doubleToLongBits(TimeLeft) != Double.doubleToLongBits(other.TimeLeft))
            return false;
        if (Double.doubleToLongBits(ToBonus) != Double.doubleToLongBits(other.ToBonus))
            return false;
        if (Double.doubleToLongBits(ToPlane) != Double.doubleToLongBits(other.ToPlane))
            return false;
        return true;
    }


 
}
