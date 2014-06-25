/**
 * Filename  : ILogLevels.java
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
 * The <code>ILogLevels</code> is used to declare a log levels
 * @author Anton Nedbailo
 * @since Aug 30, 2013
 */
public interface ILogLevels
{
    //constants
    short LOGLEVEL_INFORMATION = 101;
    short LOGLEVEL_ERROR = 103;
    short LOGLEVEL_DEBUG = 100;
    short LOGLEVEL_WARNING = 102;
    short LOGLEVEL_ALARM = 104;
}
