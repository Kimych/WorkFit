package by.uniterra.udi.model;

import java.util.Date;

import by.uniterra.dai.entity.CalendarSpecialDay;

public class CalendarSpecialDayTableModel extends AbstractFlexTableModel
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 3411488034611667141L;

    private static final int COL_CS_ID = 0;
    private static final int COL_DATE = 1;
    private static final int COL_TYPE_DAY = 2;
    private static final int COL_DESC = 3;

    public CalendarSpecialDayTableModel()
    {
        // TODO UDIPropSingleton.getString(this, "name.column")
        addColumn(COL_DATE, "Date", Date.class);
        addColumn(COL_TYPE_DAY, "Day Type", String.class);
        addColumn(COL_DESC, "Description", String.class);
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
        CalendarSpecialDay idData = (CalendarSpecialDay) getRowData(rowIndex);
        switch (columnId)
        {
        case COL_CS_ID:
            objResult = idData.getDayTypeId();
            break;
        case COL_TYPE_DAY:
            objResult = idData.getTypeDay();
            break;
        case COL_DESC:
            objResult = idData.getDescrition();
            break;
        default:
            break;
        }
        return objResult;
    }
}
