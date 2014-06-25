/**
 * Filename  : PersonEAO.java
 *
 *  * ***************************************************************************
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
 *
 */

package by.uniterra.dai.eao;

import javax.persistence.EntityManager;

import by.uniterra.dai.entity.Person;

/**
 * The <code>PersonEAO</code> is used to access Person data in DB
 *
 * @author Anton Nedbailo
 * @since Jun 25, 2014
 */
public class PersonEAO extends AbstractEAO<Person>
{
    /**
     * Default constructor
     */
    public PersonEAO(EntityManager emEntityManager) 
    {
        super(Person.class,  emEntityManager);
    }
}
