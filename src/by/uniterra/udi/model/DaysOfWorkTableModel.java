package by.uniterra.udi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private final static int COL_WORKER_ID = 7;

    private List<DaysOfWork> dataArrayList;
    public List<String> arrColumnNames;

    public DaysOfWorkTableModel()
    {
        addColumn(COL_WORKLOG, UDIPropSingleton.getString(this, "fromLog.column"), String.class);
        addColumn(COL_TIMESTAMP,UDIPropSingleton.getString(this, "timestamp.column"), Date.class);
        addColumn(COL_BONUS_TIME, UDIPropSingleton.getString(this, "bonusTime.column"), String.class);
        addColumn(COL_AKTUAL_WORKED_DAYS, UDIPropSingleton.getString(this, "actualWorkedDays.column"), String.class);
        //addColumnNames.addColumn(COL_DAYS_OF_WORK_ID, UDIPropSingleton.getString(this, "daysOfWork.column"), String.class);
        addColumn(COL_MONTH_ID, UDIPropSingleton.getString(this, "montID.column"), String.class);
        addColumn(COL_WORKER_ID, UDIPropSingleton.getString(this, "workerID.column"), String.class);
        addColumn(COL_BONUS_TIME_DESC, UDIPropSingleton.getString(this, "descBonusTime.column"), String.class);
    }
    
    public DaysOfWorkTableModel(boolean flag)
    {
        addColumn(COL_TIMESTAMP,UDIPropSingleton.getString(this, "timestamp.column"), Date.class);
        addColumn(COL_WORKLOG, UDIPropSingleton.getString(this, "fromLog.column"), String.class);
        addColumn(COL_BONUS_TIME, UDIPropSingleton.getString(this, "bonusTime.column"), String.class);
        addColumn(COL_AKTUAL_WORKED_DAYS, UDIPropSingleton.getString(this, "actualWorkedDays.column"), String.class);
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
        case COL_WORKER_ID:
            objResult = idData.getWorker().toString();
            break;
        default:
            break;
        }
        return objResult;
        
    }

}
