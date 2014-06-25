/**
 * Filename  : UserSystemModel.java
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

package by.uniterra.system.model;

import by.uniterra.system.iface.IUserSystemModel;

/**
 * The <code>UserSystemModel</code> is used to combine basic system model functionality with logged user data
 *
 * @author Anton Nedbailo
 * @since Oct 8, 2013
 */
public class UserSystemModel extends BasicSystemModel implements IUserSystemModel
{
    public UserSystemModel(String[] args, String szIniFile)
    {
        super(args, szIniFile);
    }
}
