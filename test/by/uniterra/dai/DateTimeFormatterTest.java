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
import java.util.List;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Test;

import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.entity.DaysOfWork;

/**
 * The <code>DateTimeFormatterTest</code> is used to test SimpleDateFormat and DB driver (for time convertion)
 *
 * @author Anton Nedbailo
 * @since Aug 26, 2014
 */
public class DateTimeFormatterTest
{
    private static final int TEST_NAMED_MONTH_ID = 8;
    private static final int TEST_WORKER_ID = 2;
    
    @Test
    public void testDBdriverTimeConvertion()
    {
	 // create a set of EAOs
        WorkerEAO eaoWorker = new WorkerEAO(ServiceBaseEAO.getDefaultEM());
        DaysOfWorkEAO eaoDaysOfWork = new DaysOfWorkEAO(ServiceBaseEAO.getDefaultEM());

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
    
    @After
    public void disconnectFromDB()
    {
	// disconnect from DB
        ServiceBaseEAO.disconnectFromDb();
    }
}
