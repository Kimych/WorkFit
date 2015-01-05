/**
 * Filename  : JobsModel.java
 *
 * ***************************************************************************
 * Copyright (C) 2001-2013 by ENVINET GmbH
 * Hans-Pinsel-Str. 4 
 * 85540 Haar / Munich
 * info@envinet.com
 *                                                      
 * This material is a CONFIDENTIAL, unpublished work of authorship
 * created in 2001 and subsequently updated. It is a TRADE SECRET which
 * is property of ENVINET GmbH.
 *
 * All use, disclosure, and/or reproduction not specifically authorized
 * by ENVINET GmbH in writing is prohibited. All rights reserved.
 ***************************************************************************
 * Project    : amcLuft
 *
 * Author     : Anton Nedbailo
 *
 * last change by : $Author:$ 
 * last check in  : $Date: $
 */

package by.uniterra.udi.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.SortOrder;
import javax.swing.table.AbstractTableModel;

/**
 * 
 * The <code>FlexColumnsTableModel</code> is used as abstract model for table models with flexible count of columns
 * @author Anton Nedbailo
 * @since Feb 8, 2013
 */
public abstract class AbstractFlexTableModel extends AbstractTableModel
{
    private static final long serialVersionUID = -1171102376299957729L;
    //members
    private final Map<Integer, String> mColumnNames; // column index/name pairs
    protected final Map<Integer, Integer> mColumnIndexId;// column index/Id pairs
    protected final Map<Integer, Class<?>> mColumnIdClass;// column Id/Class pairs
    private List<?> lstData;
    private SortOrder soSortOrder;
    private int iSortColumn;

    public AbstractFlexTableModel()
    {
        mColumnNames = new HashMap<Integer, String>();
        mColumnIndexId = new HashMap<Integer, Integer>();
        mColumnIdClass = new HashMap<Integer, Class<?>>();
        lstData = new LinkedList<Object>();
        soSortOrder = SortOrder.DESCENDING;
    }
    
    /**
     * @see javax.swing.table.TableModel#getRowCount()
     */
    @Override
    public int getRowCount()
    {
        return lstData.size();
    }
    
    /**
     * Get column index by Id
     * 
     * @param iColId - column Id
     * @return integer value with column index, -1 otherwise
     */
    public int getColIndex(int iColId)
    {
        int iResult = -1;
        for (Integer iColIndex : mColumnIndexId.keySet())
        {
            if (mColumnIndexId.get(iColIndex) == iColId)
            {
                iResult = iColIndex;
                break;
            }
        }
        return iResult;
    }
    
    /**
     * Add column to table
     * 
     * @param iColId - column Id
     * @param strColName - column name
     */
    protected void addColumn(int iColId, String strColName)
    {
        addColumn(iColId, strColName, Object.class);
    }
    
    /**
     * Add column with class type to table
     * 
     * @param iColId - column Id
     * @param strColName - column name
     */
    protected void addColumn(int iColId, String strColName, Class<?> classType)
    {
        mColumnNames.put(iColId, strColName);
        mColumnIndexId.put(mColumnNames.size() - 1, iColId);
        mColumnIdClass.put(iColId, classType);
    }

    /**
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount()
    {
        return mColumnNames.size();
    }

    @Override
    public final Object getValueAt( int rowIndex, int columnIndex )
    {
        return getValueById(rowIndex, mColumnIndexId.get(columnIndex));
    } 
    
    /**
     * Get value for a row by column Id
     * 
     * @param rowIndex - row index
     * @param columnId - columns Id
     * @return result value
     */
    public abstract Object getValueById( int rowIndex, int columnId );    
 
    /**
     * Set new data for the model
     *
     * @param newData list with a new data
     */
    public void setTableData( List<?> newData )
    {
        if (newData == null)
        {
            //remember the size
            int iSize =  lstData.size();
            //clear data
            lstData.clear();
            fireTableRowsDeleted(0, iSize);
        } else if (!newData.equals(lstData))
        {
            int i = 0;
            for (; i < newData.size(); i++)
            {
                //check destination size
                if (i >= lstData.size())
                {
                    ((List<Object>)lstData).add(newData.get(i));
                    fireTableRowsInserted(lstData.size() - 1, lstData.size() - 1);
                } else if (!newData.get(i).equals(lstData.get(i))) //compare
                {
                    ((List<Object>)lstData).set(i, newData.get(i));
                    fireTableRowsUpdated(i, i);
                }
            }
            //check destination size
            while (lstData.size() > i)
            {
                int iSize = lstData.size() - 1;
                lstData.remove(iSize);
                fireTableRowsDeleted(iSize, iSize);
            }
        }
    }
    
    /**
     * Add new data for the model
     *
     * @param newData - a data item to add
     */
    public void addTableData(Object newData )
    {
        // add new record
        ((List<Object>)lstData).add(newData);
        // fire data update event
        fireTableRowsInserted(lstData.size() - 1, lstData.size() - 1);
    }
    
    /**
     * Set given data object into given position
     * 
     * @param newData - data to set (replace)
     * @param iModelIndex - model owned index
     *
     * @author Anton Niadbaila
     * @date Aug 13, 2014
     */
    public void setTableData(Object newData, int iModelIndex)
    {
        ((List<Object>)lstData).set(iModelIndex, newData);
        fireTableRowsUpdated(iModelIndex, iModelIndex);
    }
    
    /**
     * Return data by given row index 
     * 
     * @return Qbject with row data
     */
    public Object getRowData(int iRowIndex)
    {
        return iRowIndex < lstData.size() ? lstData.get(iRowIndex) : null;
    }
    
    /**
     * Remove data from table by row index
     * 
     * @param iRowIndex - row index of data to remove
     *
     * @author Anton Nedbailo
     * @date 22 ���. 2013 �.
     */
    public void removeTableData(int iRowIndex)
    {
        if (iRowIndex < lstData.size())
        {
            lstData.remove(iRowIndex);
        }
        fireTableRowsDeleted(iRowIndex, iRowIndex);
    }

    /**
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName( int column )
    {
        return mColumnNames.get(mColumnIndexId.get(column));
    }
    
    /**
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    @Override
    public Class<?> getColumnClass( int columnIndex )
    {
        return mColumnIdClass.get(mColumnIndexId.get(columnIndex));
    }

    public int getSortColumn()
    {
        return iSortColumn;
    }

    public SortOrder getSortOrder()
    {
        return soSortOrder;
    }

    /**
     * @param soSortOrder the soSortOrder to set
     *
     * @author Sergio Alecky
     * @date 23 дек. 2014 г.
     */
    public void setSortOrder(SortOrder soSortOrder)
    {
        this.soSortOrder = soSortOrder;
    }

    /**
     * @param iSortColumn the iSortColumn to set
     *
     * @author Sergio Alecky
     * @date 23 дек. 2014 г.
     */
    public void setSortColumn(int iSortColumn)
    {
        this.iSortColumn = iSortColumn;
    }
}