/**
 * Filename  : IPerson.java
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

package by.uniterra.dai.iface;

import java.util.Date;

public interface IPerson
{
    //constants
    public final static int FIND_BY_LIKE_UNIQUE_NUMBER = 0;
    public final static int FIND_BY_UNIQUE_NUMBER = 1;

    public abstract int getPersonId();
    public abstract void setPersonId(int personId);
    public abstract String getComment();
    public abstract void setComment(String comment);
    public abstract Date getDateOfBirth();
    public abstract void setDateOfBirth(Date dateOfBirth);
    public abstract String getFirstName();
    public abstract void setFirstName(String firstName);
    public abstract String getOwnerUniqueNumber();
    public abstract void setOwnerUniqueNumber(String ownerUniqueNumber);
    public abstract String getSecondName();
    public abstract void setSecondName(String secondName);
    public abstract String getThirdName();
    public abstract void setThirdName(String thirdName);
    public abstract int getSex();
    public abstract void setSex(int sex);
    public String getFullFIO();
}