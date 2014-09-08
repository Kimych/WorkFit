/**
 * Filename  : WorkerNamedQueryTest.java
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

import org.junit.Test;

import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.system.model.SystemModel;

/**
 * The <code>WorkerNamedQueryTest</code> is used to test named queries usage for
 * DaysOfWorkEAO
 *
 * @author Anton Nedbailo
 * @since Aug 20, 2014
 */
public class WorkerNamedQueryTest
{
    private static final int TEST_NAMED_MONTH_ID = 3;
    private static final int TEST_WORKER_ID = 2;

    @Test
    public void getLatestWorklog()
    {
        // create a set of EAOs
        WorkerEAO eaoWorker = new WorkerEAO(SystemModel.getDefaultEM());
        DaysOfWorkEAO eaoDaysOfWork = new DaysOfWorkEAO(SystemModel.getDefaultEM());

        // try to get last worklog data for given Worker and Month
        assertTrue(eaoDaysOfWork.getLastDataForWorkerAndMonthNum(eaoWorker.find(TEST_WORKER_ID), TEST_NAMED_MONTH_ID) != null);

        // disconnect from DB
        SystemModel.disposeJPA();
    }

    @Test
    public void getAllWorklog()
    {
        // create a set of EAOs
        WorkerEAO eaoWorker = new WorkerEAO(SystemModel.getDefaultEM());
        DaysOfWorkEAO eaoDaysOfWork = new DaysOfWorkEAO(SystemModel.getDefaultEM());
        // try to get last worklog data for given Worker and Month
        assertTrue(eaoDaysOfWork.getSumBonusTimeForWorkerAndMonthNum(eaoWorker.find(TEST_WORKER_ID), TEST_NAMED_MONTH_ID) != 0);
        // disconnect from DB
        SystemModel.disposeJPA();
    }
}
