/**
 * Filename  : SystemModelTest.java
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

package by.uniterra.system;

import static org.junit.Assert.*;

import org.junit.Test;

import by.uniterra.system.model.SystemModel;

/**
 * The <code>SystemModelTest</code> is used to test some methonds of SystemModel
 * class
 *
 * @author Anton Nedbailo
 * @since Sep 8, 2014
 */
public class SystemModelTest
{

    private static final String INVALID_KEY_NAME = "invalid.key";
    private static final Object INVALID_VALUE = true;
    
    private static final String DEFAULT_STRING_VALUE = "dfgdhdt";
    private static final Boolean DEFAULT_BOOL_VALUE = true;
    private static final Integer DEFAULT_INT_VALUE = 34235;
    private static final Double DEFAULT_DOUBLE_VALUE = 345.34;
    @Test
    public void test()
    {
        try
        {
            // add according property
            SystemModel.setProperty(INVALID_KEY_NAME, INVALID_VALUE);
            // check properties methods set
            String strResult = SystemModel.getString(INVALID_KEY_NAME, DEFAULT_STRING_VALUE);
            Boolean bResult = SystemModel.getBool(INVALID_KEY_NAME, DEFAULT_BOOL_VALUE);
            Integer iResult = SystemModel.getInt(INVALID_KEY_NAME, DEFAULT_INT_VALUE);
            Double dResult = SystemModel.getDouble(INVALID_KEY_NAME, DEFAULT_DOUBLE_VALUE);
            System.out.println("Test value is \"" + INVALID_VALUE + "\"");
            System.out.println("getString returns \"" + strResult + "\"");
            System.out.println("getBool returns \"" + bResult + "\"");
            System.out.println("getInt returns \"" + iResult + "\"");
            System.out.println("getDouble returns \"" + dResult + "\"");
            
            assertTrue((strResult.equals(DEFAULT_STRING_VALUE) || strResult.equals(INVALID_VALUE.toString()))
                    && (bResult == DEFAULT_BOOL_VALUE || bResult.equals(DEFAULT_BOOL_VALUE))
                    && (iResult == DEFAULT_INT_VALUE || iResult.equals(DEFAULT_INT_VALUE))
                    && (dResult == DEFAULT_DOUBLE_VALUE || dResult.equals(DEFAULT_DOUBLE_VALUE)));
        } catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

}
