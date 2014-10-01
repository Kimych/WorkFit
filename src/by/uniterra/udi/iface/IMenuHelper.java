/**
 * Filename  : IMenuHelper.java
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

package by.uniterra.udi.iface;

/**
 * The <code>IMenuHelper</code> is used for define some menu bar commands and
 * constants
 *
 * @author Sergio Alecky
 * @since 30 сент. 2014 г.
 */
public interface IMenuHelper
{

    //menuNoUser
     static final String MCOMMAND_LOGIN = "Login";
     static final String MCOMMAND_LOGOUT = "Logout";
     static final String MCOMMAND_EXIT = "Exit";
     
     //menuAmin
     static final String MCOMMAND_EDIT_WORKER = "Edit worker";
     static final String MCOMMAND_EDIT_HOLIDAY = "Edit holiday";
     static final String MCOMMAND_EDIT_DAYS_OF_WORK = "Edit DofW";
     static final String MCOMMAND_EDIT_MONTH = "Edit month";
     static final String MCOMMAND_EDIT_YEAR = "Edit year";
     static final String MCOMMAND_EDIT_USER_ROLE = "Edit user role";
     static final String MCOMMAND_ADD_FROM_LOG = "Add from Log";
     
     //menuUser
     static final String MCOMMAND_REFRESH = "Refresh";
     static final String MCOMMAND_VIEW_HISTORY = "View History";
    
     //menuHelp
     static final String MCOMMAND_WELCOME = "Welcome";
     static final String MCOMMAND_ABOUT = "About";
     

}
