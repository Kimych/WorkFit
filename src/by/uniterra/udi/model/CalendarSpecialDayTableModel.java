package by.uniterra.udi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.uniterra.dai.entity.CalendarSpecialDay;

public class CalendarSpecialDayTableModel extends AbstractFlexTableModel
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 3411488034611667141L;

    private final static int COL_CS_ID = 0;
    private final static int COL_DATE = 1;
    private final static int COL_TYPE_DAY = 2;
    private final static int COL_DESC = 3;

    private List<CalendarSpecialDay> dataArrayList;
    public List<String> arrColumnNames;

    public CalendarSpecialDayTableModel()
    {
        // TODO UDIPropSingleton.getString(this, "name.column")
        addColumn(COL_DATE, "Date", Date.class);
        addColumn(COL_TYPE_DAY, "Day Type", String.class);
        addColumn(COL_DESC, "Description", String.class);
    }

    public void addData(List<CalendarSpecialDay> arrData)
    {
        this.dataArrayList = new ArrayList<CalendarSpecialDay>(arrData);
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }

    public List<CalendarSpecialDay> setData()
    {
        return dataArrayList;
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
        case COL_DATE:
            objResult = idData.getDateDay();
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
