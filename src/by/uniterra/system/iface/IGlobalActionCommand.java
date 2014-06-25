/**
 * Filename  : IGlobalActionCommand.java
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
 *
 * Author     : Anton Nedbailo
 *
 * last change by : $Author:$ 
 * last check in  : $Date: $
 */

package by.uniterra.system.iface;

/**
 * The <code>IGlobalActionCommand</code> is used to declare a global action commands
 *
 * @author Anton Nedbailo
 * @since Sep 21, 2013
 */
public interface IGlobalActionCommand
{
    //constants
    public static final String UPDATE_FEE_ACTION = "UpdateFee";
    public static final String CLOSE_ACTION = "Close";
    public static final String INSURANCE_SUM_CURRENCY_ACTION = "InsurSumCurrency";
    public static final String PERSON_DATE_OF_BITH_ACTION = "PersonDateOfBith";
    public static final String PERSON_SEX_ACTION = "PersonSex";
    public  final static String ID_DOC_LIST_ACTION = "IdDocumentList";
}
