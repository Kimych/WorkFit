/**
 * Filename  : IGlobalProperties.java
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

package by.uniterra.system.iface;


/**
 * The <code>IGlobalProperties</code> is used to hold all global.parameters
 * properties names
 *
 * @author Anton Nedbailo
 * @since Sep 8, 2014
 */
public interface IGlobalProperties
{
    // postfixes
    String CRYPT_POSTFIX = ".crypted";
    
    // DB connection settings
    String DB_URL = "db.url";
    String DB_USER = "db.user" + CRYPT_POSTFIX;
    String DB_PWD = "db.pwd" + CRYPT_POSTFIX;
    String DB_DRIVER = "db.driver";
    
    //mail connection settings
    String ML_SERVER = "ml.mailserver"+CRYPT_POSTFIX;
    String ML_EMAIL = "ml.email"+CRYPT_POSTFIX;
    String ML_PWD = "ml.password"+CRYPT_POSTFIX;
    
    //mail checker
    String MC_HOUR = "mc.hour";
    String MC_MINUTE = "mc.minute";
    String MC_SEKOND = "mc.sekond";
}
