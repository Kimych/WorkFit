/**
 * Filename  : UserRoleTableMolel.java
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

package by.uniterra.udi.model;

import java.util.List;

import by.uniterra.dai.entity.Authorization;

/**
 * The <code>UserRoleTableMolel</code> is used for ...
 *
 * @author Sergio Alecky
 * @since 25 сент. 2014 г.
 */
public class UserRoleTableMolel extends AbstractFlexTableModel
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = -3926145395315621807L;

    private static final int COL_NAME = 0;
    private static final int COL_LOGIN = 1;
    private static final int COL_ROLE_NAME = 2;
    private static final int COL_DESCRIPTIONS = 3;
    private static final int COL_EMAIL = 4;

    public UserRoleTableMolel()
    {
        addColumn(COL_NAME, UDIPropSingleton.getString(this, "name.column"), String.class);
        addColumn(COL_LOGIN, UDIPropSingleton.getString(this, "login.column"), String.class);
        addColumn(COL_ROLE_NAME, UDIPropSingleton.getString(this, "roleName.column"), List.class);
        addColumn(COL_DESCRIPTIONS, UDIPropSingleton.getString(this, "description.column"), String.class);
        addColumn(COL_EMAIL, UDIPropSingleton.getString(this, "email.column"), String.class);
    }

    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }

    @Override
    public Object getValueById(int rowIndex, int columnId)
    {
        Object objResult = null;
        Authorization idData = (Authorization) getRowData(rowIndex);
        switch (columnId)
        {
        case COL_NAME:
            objResult = idData.getWorker().toString();
            break;
        case COL_LOGIN:
            objResult = idData.getLogin();
            break;
        case COL_ROLE_NAME:
            objResult = idData.getRoles().get(0);
            break;
        case COL_DESCRIPTIONS:
            objResult = idData.getRoles().get(0).getDescription();
            break;
        case COL_EMAIL:
            objResult = idData.getEmail();
            break;
        default:
            break;
        }
        return objResult;
    }

}
