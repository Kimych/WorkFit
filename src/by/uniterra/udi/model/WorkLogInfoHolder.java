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

import by.uniterra.dai.entity.Worker;

/**
 * The <code>WorklogInfoHolder</code> is used to hold all necessary worklog
 * statistics for one Worker
 *
 * @author Sergio Alecky
 * @since Aug 25, 2014
 */
public class WorkLogInfoHolder
{
    private String сurentTime;
    private double toPlane;
    private double toBonus;
    private double timeLeft;
    private Date lastUpdateDate;
    private Worker objWorker;
    private double spentHollidayInMonth;

    // private boolean BeInPlane;

    public WorkLogInfoHolder(String curentTime, double toPlane, double toBonus, double timeLeft, Date lastUpdateDate, Worker objWorker, double spentHollidayInMonth)
    {
        this.сurentTime = curentTime;
        this.toPlane = toPlane;
        this.toBonus = toBonus;
        this.timeLeft = timeLeft;
        this.lastUpdateDate = lastUpdateDate;
        this.objWorker = objWorker;
        this.spentHollidayInMonth = spentHollidayInMonth;
    }

    public String getCurentTime()
    {
        return сurentTime;
    }

    public void setCurentTime(String curentTime)
    {
        this.сurentTime = curentTime;
    }

    public double getToPlane()
    {
        return toPlane;
    }

    public void setToPlane(double toPlane)
    {
        this.toPlane = toPlane;
    }

    public double getToBonus()
    {
        return toBonus;
    }

    public void setToBonus(double toBonus)
    {
        this.toBonus = toBonus;
    }

    public double getTimeLeft()
    {
        return timeLeft;
    }

    public void setTimeLeft(double timeLeft)
    {
        this.timeLeft = timeLeft;
    }

    public Date getLastUpdateDate()
    {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate)
    {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Worker getWorker()
    {
        return objWorker;
    }

    public void setWorker(Worker objWorker)
    {
        this.objWorker = objWorker;
    }

    @Override
    public String toString()
    {
        return "WorkLogInfoHolder [CurentTime=" + сurentTime + ", ToPlane=" + toPlane + ", ToBonus=" + toBonus + ", TimeLeft=" + timeLeft + ", LastUpdateDate="
                + lastUpdateDate + ", NameWorker=" + objWorker.getAlias() + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((сurentTime == null) ? 0 : сurentTime.hashCode());
        result = prime * result + ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
        result = prime * result + ((objWorker == null) ? 0 : objWorker.hashCode());
        long temp;
        temp = Double.doubleToLongBits(timeLeft);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(toBonus);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(toPlane);
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
        if (сurentTime == null)
        {
            if (other.сurentTime != null)
                return false;
        }
        else if (!сurentTime.equals(other.сurentTime))
            return false;
        if (lastUpdateDate == null)
        {
            if (other.lastUpdateDate != null)
                return false;
        }
        else if (!lastUpdateDate.equals(other.lastUpdateDate))
            return false;
        if (objWorker == null)
        {
            if (other.objWorker != null)
                return false;
        }
        else if (!objWorker.equals(other.objWorker))
            return false;
        if (Double.doubleToLongBits(timeLeft) != Double.doubleToLongBits(other.timeLeft))
            return false;
        if (Double.doubleToLongBits(toBonus) != Double.doubleToLongBits(other.toBonus))
            return false;
        if (Double.doubleToLongBits(toPlane) != Double.doubleToLongBits(other.toPlane))
            return false;
        return true;
    }

    /**
     * @return the spentHollidayInMont
     *
     * @author Sergio Alecky
     * @date 23 дек. 2014 г.
     */
    public double getSpentHollidayInMonth()
    {
        return spentHollidayInMonth;
    }

    /**
     * @param spentHollidayInMont the spentHollidayInMont to set
     *
     * @author Sergio Alecky
     * @date 23 дек. 2014 г.
     */
    public void setSpentHollidayInMonth(double spentHollidayInMont)
    {
        this.spentHollidayInMonth = spentHollidayInMont;
    }
}
