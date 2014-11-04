package by.uniterra.udi.model;

import java.util.ArrayList;
import java.util.List;

import by.uniterra.dai.entity.Holiday;

public class HolidayTableModel extends AbstractFlexTableModel
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1263328037014654761L;

    private final static int COL_COUNT_DAYS = 0;
    private final static int COL_ID_YEAR = 1;
    private final static int COL_ID_WORKER = 2;

    private List<Holiday> dataArrayList;
    public List<String> arrColumnNames;
    
    public HolidayTableModel()
    {
        addColumn(COL_COUNT_DAYS, UDIPropSingleton.getString(this, "ColCountDaysHoliday.column"), Integer.class);
        addColumn(COL_ID_YEAR, UDIPropSingleton.getString(this, "ColIdYearHoliday.column"), String.class);
        addColumn(COL_ID_WORKER, UDIPropSingleton.getString(this, "ColIdWorkerHoliday.column"), String.class);
    }

    public void addData(List<Holiday> arrData)
    {
        this.dataArrayList = new ArrayList<Holiday>(arrData);
        // notify "view" about changed model data
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }

    public List<Holiday> setData()
    {
        return dataArrayList;
    }
    @Override
    public Object getValueById(int rowIndex, int columnId)
    {
        Object objResult = null;
        Holiday idData = (Holiday) getRowData(rowIndex);
        switch (columnId)
        {
        case COL_COUNT_DAYS:
            objResult = idData.getCountDays();
            break;
        case COL_ID_YEAR:
            objResult = idData.getYear();
            break;
        case COL_ID_WORKER:
            objResult = idData.getWorker();
            break;
        default:
            break;
        }
        return objResult;
    }

}
