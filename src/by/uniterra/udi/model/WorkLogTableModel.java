package by.uniterra.udi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkLogTableModel extends AbstractFlexTableModel
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = -8488583506352518426L;

    private final static int COL_NAME = 0;
    private final static int COL_CURENT = 1;
    private final static int COL_TO_PLANE = 2;
    private final static int COL_TO_BONUS = 3;
    private final static int COL_REST_HOLIDAY = 4;
    private final static int COL_UPDATE_TIME = 8;

    private List<WorkLogInfoHolder> dataArrayList;
    public List<String> arrColumnNames;

    public WorkLogTableModel()
    {
        addColumn(COL_NAME, "Name", String.class);
        addColumn(COL_CURENT, "Curent", String.class);
        addColumn(COL_TO_PLANE, "To plane", String.class);
        addColumn(COL_TO_BONUS, "To bonus", String.class);
        addColumn(COL_REST_HOLIDAY, "To holiday", String.class);
        addColumn(COL_UPDATE_TIME, "Last Update", Date.class);
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
        case COL_CURENT:
            objResult = idData.getCurentTime();
            break;
        case COL_TO_PLANE:
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
