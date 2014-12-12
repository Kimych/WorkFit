/**
 * Filename  : DateTimeFormatterTest.java
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
 * Author     : Anton Nedbailo
 *
 * last change by : $Author:$ 
 * last check in  : $Date: $
 */

package by.uniterra.dai;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.system.model.SystemModel;

/**
 * The <code>DateTimeFormatterTest</code> is used to test SimpleDateFormat and DB driver (for time convertion)
 *
 * @author Anton Nedbailo
 * @since Aug 26, 2014
 */
public class DateTimeFormatterTest
{
    // members
    private static List<DaysOfWork> lstDaysOfWorkItemsToDelete;
    // constants
    private static final int TEST_NAMED_MONTH_ID = 8;
    private static final int TEST_WORKER_ID = 2;
    
    @BeforeClass
    public static void init()
    {
        lstDaysOfWorkItemsToDelete = new ArrayList<DaysOfWork>();
        // connect to db
        SystemModel.initJPA();
    }
    
    @AfterClass
    public static void clear()
    {
        DaysOfWorkEAO eaoDaysOfWork = new DaysOfWorkEAO(SystemModel.getDefaultEM());
        // clear temp data from DB 
        for (DaysOfWork daysOfWork : lstDaysOfWorkItemsToDelete)
        {
            // remove test data
            eaoDaysOfWork.delete(daysOfWork);
        }
        // disconnect from DB
        SystemModel.disposeJPA();
    }

    
    
    @Test
    public void testDBdriverLoadTimeConvertion()
    {
	 // create a set of EAOs
        WorkerEAO eaoWorker = new WorkerEAO(SystemModel.getDefaultEM());
        DaysOfWorkEAO eaoDaysOfWork = new DaysOfWorkEAO(SystemModel.getDefaultEM());

        List<DaysOfWork> lstDayOfWork = eaoDaysOfWork.getLastDataForWorkerAndMonthNum(eaoWorker.find(TEST_WORKER_ID), TEST_NAMED_MONTH_ID);
        if (lstDayOfWork.size() != 0)
        {
            DaysOfWork dfwData = lstDayOfWork.get(0);
            // print timestamp using default formatter
            System.out.println("Timestamp using default formatter=\"" + dfwData.getTimestamp() + "\"");
            // print timestamp using UTC timezone and custom formatter
            SimpleDateFormat sdfFormatter = new SimpleDateFormat("dd.MM.YYYY HH:mm:SS");
            sdfFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            System.out.println("Timestamp using UTC timezone and custom formatter=\"" + sdfFormatter.format(dfwData.getTimestamp()) + "\"");
        }
        // try to get last worklog data for given Worker and Month
        assertTrue(lstDayOfWork != null);
    }
    
    @Test
    public void testDBdriverSaveTimeConvertion()
    {
         // create a set of EAOs
        WorkerEAO eaoWorker = new WorkerEAO(SystemModel.getDefaultEM());
        MonthEAO eaoMonth = new MonthEAO(SystemModel.getDefaultEM());
        DaysOfWorkEAO eaoDaysOfWork = new DaysOfWorkEAO(SystemModel.getDefaultEM());

        // cerate new days of work
        DaysOfWork newData = new DaysOfWork(eaoMonth.find(TEST_NAMED_MONTH_ID),eaoWorker.find(TEST_WORKER_ID));
        // create ne date object
        Date dCurDate = new Date();
        newData.setTimestamp(dCurDate);
        // save new DaysOfWork
        newData = eaoDaysOfWork.save(newData);
        // add to delete list
        lstDaysOfWorkItemsToDelete.add(newData);
        
        // load save data by Id
        DaysOfWork dfwData = eaoDaysOfWork.find(newData.getDaysOfWorkId());
        if (dfwData != null)
        {
            // print timestamp using default formatter
            System.out.println("Timestamp using default formatter=\"" + dfwData.getTimestamp() + "\"");
            // print timestamp using UTC timezone and custom formatter
            SimpleDateFormat sdfFormatter = new SimpleDateFormat("dd.MM.YYYY HH:mm:SS");
            sdfFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            System.out.println("Timestamp using UTC timezone and custom formatter=\"" + sdfFormatter.format(dfwData.getTimestamp()) + "\"");
        }
        
        // try to get last worklog data for given Worker and Month
        assertTrue(dfwData != null);
    }
}
