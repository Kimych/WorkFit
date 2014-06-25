/**
 * Filename  : ESex.java
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

package by.uniterra.dai.model;

/**
 * The <code>ESex</code> is used to identify sex
 *
 * @author Anton Nedbailo
 * @since Sep 13, 2013
 */
public enum ESex
{
    FEMALE, 	//0 - means FEMALE
    MALE;	//1 - means MALE
    
    /**
     * Convert int value to enum
     * 
     * @param x - int representation of enum
     * @return enum value
     *
     * @author Anton Nedbailo
     * @date Sep 24, 2013
     */
    public static ESex fromInteger(int x)
    {
        switch (x)
        {
            case 0:
                return FEMALE;
            case 1:
                return MALE;
        }
        return null;
    }
    
    /**
     * Convert ESex to String representation
     * 
     * @param sValue - ESex value
     * @return "�" - for MALE, "�" - for FEMALE
     *
     * @author Anton Nedbailo
     * @date Sep 24, 2013
     */
    public static String toString(ESex sValue)
    {
        String strResult = "MALE";
        switch (sValue)
        {
            case FEMALE:
                strResult = "FEMALE";
            case MALE:
                strResult = "MALE";
        }
        return strResult;
    }
}
