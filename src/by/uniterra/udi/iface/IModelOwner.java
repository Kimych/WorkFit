/**
 * Filename  : IModelOwner.java
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

package by.uniterra.udi.iface;


public interface IModelOwner
{
    /**
     * Used to bring model into container and unitialize UI data 
     * @param mData - model data
     *
     * @author Anton Nedbailo
     * @date 15 авг. 2014 г.
     */
    void setModel(Object mData);

    
    /**
     * Bring data from UI into model and return it
     * @return model data
     *
     * @author Anton Nedbailo
     * @date Aug 15, 2014
     */
    Object getModel();

}