package by.uniterra.udi.view;

import java.awt.Component;
import java.math.BigDecimal;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


import by.uniterra.system.util.WorkLogUtils;

public class DoubleTableCellRenderer extends DefaultTableCellRenderer
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 3238900968505557515L;

    private int iDecimalsCount;

    public DoubleTableCellRenderer(int iHorizontalAlignment, int iDecimalsCount)
    {
        super();
        this.iDecimalsCount = iDecimalsCount;
        setHorizontalAlignment(iHorizontalAlignment);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value instanceof Double)
        {
            setText(WorkLogUtils.roundToString((Double) value, iDecimalsCount, BigDecimal.ROUND_HALF_UP));

        }
        return this;
    }

}
