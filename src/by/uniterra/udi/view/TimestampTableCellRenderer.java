/**
 * Filename  : TimestampTableCellRenderer.java
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

package by.uniterra.udi.view;

import java.awt.Component;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import by.uniterra.system.util.DateUtils;

/**
 * The <code>TimestampTableCellRenderer</code> is used for displaying formatter Timestamp value
 * @author Anton Nedbailo
 * @since Apr 3, 2013
 */
public class TimestampTableCellRenderer extends DefaultTableCellRenderer
{
    private static final long serialVersionUID = 1292776441167208214L;
    //members
    private String strFormat;
    private String strTimeZone;
    
    public TimestampTableCellRenderer(int iHorizontalAlignment, String strFormat, String strTimeZone)
    {
        super();
        this.strFormat = strFormat;
        this.strTimeZone = strTimeZone;
        setHorizontalAlignment(iHorizontalAlignment);
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value instanceof Date)
        {
            setText(DateUtils.timestampToString((Date)value, strFormat, strTimeZone));
        } else if (value instanceof Timestamp)
        {
            setText(DateUtils.timestampToString((Timestamp) value, strFormat, strTimeZone));
        } else if (value instanceof Long)
        {
            setText(DateUtils.timestampToString(new Timestamp((long)value), strFormat, strTimeZone));
        }
        return this;
    }
}
