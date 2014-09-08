/**
 * Filename  : HolidayNamedQuery.java
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

import org.junit.Test;

import by.uniterra.dai.eao.HolidayEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.eao.YearEAO;
import by.uniterra.system.model.SystemModel;

/**
 * The <code>HolidayNamedQuery</code> is used for is used to test named queries
 * usage for Holiday
 * 
 * @author Sergio Alecky
 * @since 25 авг. 2014 г.
 */
public class HolidayNamedQueryTest
{

    /**
     * @throws java.lang.Exception
     *
     * @author Sergio Alecky
     * @date 25 авг. 2014 г.
     */
    private static final int TEST_NUMBER_YEAR = 2014;
    private static final int TEST_WORKER_ID = 1;

    @Test
    public void getHoliday()
    {
	// create a set of EAOs
	HolidayEAO eaoHoliday = new HolidayEAO(SystemModel.getDefaultEM());
	YearEAO eaoYear = new YearEAO(SystemModel.getDefaultEM());
	WorkerEAO eaoWorker = new WorkerEAO(SystemModel.getDefaultEM());

	assertTrue(eaoHoliday.getHolidayDaysCountForWorkerAndYear(eaoWorker.find(TEST_WORKER_ID), TEST_NUMBER_YEAR) != 0);

	// disconnect from DB
	SystemModel.disposeJPA();
    }

}
