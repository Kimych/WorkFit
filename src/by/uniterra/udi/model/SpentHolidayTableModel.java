/**
 * Filename  : SpentHolidayTableModel.java
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

import java.util.ArrayList;
import java.util.List;

import by.uniterra.dai.entity.SpentHoliday;

/**
 * The <code>SpentHolidayTableModel</code> is used for ...
 *
 * @author Sergio Alecky
 * @since 02 окт. 2014 г.
 */
public class SpentHolidayTableModel extends AbstractFlexTableModel
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 862518620915258199L;

    private final static int COL_DAYS_COUNT = 0;
    private final static int COL_WORKER = 1;
    private final static int COL_DATE = 2;
    private final static int COL_DESC = 3;

    private List<SpentHoliday> dataArrayList;
    public List<String> arrColumnNames;

    public SpentHolidayTableModel()
    {
        addColumn(COL_DAYS_COUNT, UDIPropSingleton.getString(this, "DaysCount.column"), Integer.class);
        addColumn(COL_WORKER, UDIPropSingleton.getString(this, "Worker.column"), String.class);
        addColumn(COL_DATE, UDIPropSingleton.getString(this, "Date.column"), String.class);
        addColumn(COL_DESC, UDIPropSingleton.getString(this, "Desc.column"), String.class);

    }

    public void addData(List<SpentHoliday> arrData)
    {
        this.dataArrayList = new ArrayList<SpentHoliday>(arrData);
        fireTableDataChanged();
    }

    public boolean isCellEditable(int row, int column)
    {
        return false;
    }

    public List<SpentHoliday> setData()
    {
        return dataArrayList;
    }

    @Override
    public Object getValueById(int rowIndex, int columnId)
    {
        Object objResult = null;
        SpentHoliday idData = (SpentHoliday) getRowData(rowIndex);
        switch (columnId)
        {
        case COL_DAYS_COUNT:
            objResult = idData.getCountDays();
            break;
        case COL_WORKER:
            objResult = idData.getWorker();
            break;
        case COL_DATE:
            objResult = idData.getMonth();
            break;
        case COL_DESC:
            objResult = idData.getDescription();
            break;
        default:
            break;
        }
        return objResult;
    }
}
