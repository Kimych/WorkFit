/**
 * Filename  : SpentHolidayNamedQueryTest.java
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

package by.uniterra.dai;

import static org.junit.Assert.assertTrue;
import jdk.nashorn.internal.ir.annotations.Ignore;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import by.uniterra.dai.eao.SpentHolidayEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.system.model.SystemModel;

/**
 * The <code>SpentHolidayNamedQueryTest</code> is used for ...
 *
 * @author Sergio Alecky
 * @since 25 авг. 2014 г.
 */
public class SpentHolidayNamedQueryTest
{

    /**
     * @throws java.lang.Exception
     *
     * @author Sergio Alecky
     * @date 25 авг. 2014 г.
     */
    private static final int TEST_NUMBER_YEAR = 2014;
    private static final int TEST_WORKER_ID = 2;
    private static final int TEST_MONTH_NUMBER = 11;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
      SystemModel.initJPA();  
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
      SystemModel.disposeJPA();
    }
    
    
/*    @Test
    @Ignore
    public void getHoliday()
    {
        // create a set of EAOs
        MonthEAO eaoMonth = new MonthEAO(ServiceBaseEAO.getDefaultEM());
        YearEAO eaoYear = new YearEAO(ServiceBaseEAO.getDefaultEM());
        WorkerEAO eaoWorker = new WorkerEAO(SystemModel.getDefaultEM());
        SpentHolidayEAO eaoSpentHoliday = new SpentHolidayEAO(SystemModel.getDefaultEM());
        System.out.println(eaoSpentHoliday.getSpentHolidayWorkerAndYear(eaoWorker.find(TEST_WORKER_ID), TEST_NUMBER_YEAR));

        assertTrue(eaoSpentHoliday.getSpentHolidayWorkerAndYear(eaoWorker.find(TEST_WORKER_ID), TEST_NUMBER_YEAR) != 0);

        // disconnect from DB
        SystemModel.disposeJPA();
    }
    */
    @Test
    public void getSppentHolidayByMonthYearAndWorker()
    {
    
        WorkerEAO eaoWorker = new WorkerEAO(SystemModel.getDefaultEM());
        SpentHolidayEAO eaoSpentHoliday = new SpentHolidayEAO(SystemModel.getDefaultEM());
        double days = eaoSpentHoliday.getSpentHolidayByWorkerAndMonth(eaoWorker.find(TEST_WORKER_ID), TEST_MONTH_NUMBER, TEST_NUMBER_YEAR);
        System.out.println(days);
        assertTrue(days !=0);
    }

}
