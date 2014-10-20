package by.uniterra.udi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkLogTableModel extends AbstractFlexTableModel
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = -8488583506352518426L;

    public final static int COL_NAME = 0;
    public final static int COL_CURRENT = 1;
    public final static int COL_TO_PLAN = 2;
    public final static int COL_TO_BONUS = 3;
    public final static int COL_REST_HOLIDAY = 4;
    public final static int COL_UPDATE_TIME = 5;

    private List<WorkLogInfoHolder> dataArrayList;
    public List<String> arrColumnNames;

    public WorkLogTableModel()
    {
        addColumn(COL_NAME, UDIPropSingleton.getString(this, "name.column"), String.class);
        addColumn(COL_CURRENT, UDIPropSingleton.getString(this, "current.column"), String.class);
        addColumn(COL_TO_PLAN, UDIPropSingleton.getString(this, "toPlane.column"), String.class);
        addColumn(COL_TO_BONUS, UDIPropSingleton.getString(this, "toBonus.column"), String.class);
        addColumn(COL_REST_HOLIDAY, UDIPropSingleton.getString(this, "restHoliday.column"), String.class);
        addColumn(COL_UPDATE_TIME, UDIPropSingleton.getString(this, "updateTime.column"), Date.class);
    }

    public void addData(List<WorkLogInfoHolder> arrData)
    {
        this.dataArrayList = new ArrayList<WorkLogInfoHolder>(arrData);
        // notify "view" about changed model data
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }

    public List<WorkLogInfoHolder> setData()
    {
        return dataArrayList;
    }

    @Override
    public Object getValueById(int rowIndex, int columnId)
    {
        Object objResult = null;
        WorkLogInfoHolder idData = (WorkLogInfoHolder) getRowData(rowIndex);
        switch (columnId)
        {
        case COL_NAME:
            objResult = idData.getNameWorker();
            break;
        case COL_CURRENT:
            objResult = idData.getCurentTime();
            break;
        case COL_TO_PLAN:
            objResult = idData.getToPlane();
            break;
        case COL_TO_BONUS:
            objResult = idData.getToBonus();
            break;
        case COL_REST_HOLIDAY:
            objResult = idData.getTimeLeft();
            break;
        case COL_UPDATE_TIME:
            objResult = idData.getLastUpdateDate();
            break;
        default:
            break;
        }
        return objResult;
    }

}
