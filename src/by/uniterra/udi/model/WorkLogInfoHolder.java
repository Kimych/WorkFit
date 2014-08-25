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
    private String LastUpdateDate;
    private String NameWorker;

    public WorkLogInfoHolder(String CurentTime, double ToPlane, double ToBonus, double TimeLeft, String LastUpdateDate, String NameWorker)
    {
        this.CurentTime = CurentTime;
        this.ToPlane = ToPlane;
        this.ToBonus = ToBonus;
        this.TimeLeft = TimeLeft;
        this.LastUpdateDate = LastUpdateDate;
        this.NameWorker = NameWorker;
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

    public String getLastUpdateDate()
    {
        return LastUpdateDate;
    }

    public void setLastUpdateDate(String LastUpdateDate)
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
}
