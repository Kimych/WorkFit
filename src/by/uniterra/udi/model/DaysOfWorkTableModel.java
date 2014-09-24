package by.uniterra.udi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sun.jmx.snmp.Timestamp;

import by.uniterra.dai.entity.DaysOfWork;

public class DaysOfWorkTableModel extends AbstractFlexTableModel
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = -5815300635044440198L;

    private final static int COL_WORKLOG = 0;
    private final static int COL_TIMESTAMP = 1;
    private final static int COL_BONUS_TIME = 2;
    private final static int COL_BONUS_TIME_DESC = 3;
    private final static int COL_AKTUAL_WORKED_DAYS = 4;
    private final static int COL_DAYS_OF_WORK_ID = 5;
    private final static int COL_MONTH_ID = 6;
    private final static int COL_WORKERE_ID = 7;

    private List<DaysOfWork> dataArrayList;
    public List<String> arrColumnNames;

    public DaysOfWorkTableModel()
    {
        addColumn(COL_WORKLOG, "From Log");
        addColumn(COL_TIMESTAMP, "Timestamp", Date.class);
        addColumn(COL_BONUS_TIME, "Bonus time");
        addColumn(COL_BONUS_TIME_DESC, "Bonus time desc");
        addColumn(COL_AKTUAL_WORKED_DAYS, "Aktual worked days");
        addColumn(COL_DAYS_OF_WORK_ID, "Days of work");
        addColumn(COL_MONTH_ID, "Mont ID");
        addColumn(COL_WORKERE_ID, "Worker ID");
    }
    
    public void addData(List<DaysOfWork> arrData)
    {
        this.dataArrayList = new ArrayList<DaysOfWork>(arrData);
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }
    public List<DaysOfWork> setData()
    {
        return dataArrayList;
    }
    

    @Override
    public Object getValueById(int rowIndex, int columnId)
    {
        Object objResult = null;
        DaysOfWork idData = (DaysOfWork) getRowData(rowIndex);
        switch (columnId)
        {
        case COL_WORKLOG:
            objResult = idData.getWorklog();
            break;
        case COL_TIMESTAMP:
            objResult = idData.getTimestamp();
            break;
        case COL_BONUS_TIME:
            objResult = idData.getBonusTime();
            break;
        case COL_BONUS_TIME_DESC:
            objResult = idData.getBonusTimeDescription();
            break;
        case COL_AKTUAL_WORKED_DAYS:
            objResult = idData.getAktualWorkedDays();
            break;
        case COL_DAYS_OF_WORK_ID:
            objResult = idData.getDaysOfWorkId();
            break;
        case COL_MONTH_ID:
            objResult = idData.getMonth().getNameMonth().getName();
            break;
        case COL_WORKERE_ID:
            objResult = idData.getWorker().toString();
            break;
        default:
            break;
        }
        return objResult;
        
    }

}
