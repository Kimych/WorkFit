package by.uniterra.udi.model;

import java.util.Date;

import javax.swing.SortOrder;

import by.uniterra.dai.entity.DaysOfWork;

public class DaysOfWorkTableModel extends AbstractFlexTableModel
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = -5815300635044440198L;

    private static final int COL_WORKLOG = 1;
    private static final int COL_TIMESTAMP = 0;
    private static final int COL_BONUS_TIME = 2;
    private static final int COL_BONUS_TIME_DESC = 7;
    private static final int COL_AKTUAL_WORKED_DAYS = 4;
    private static final int COL_DAYS_OF_WORK_ID = 5;
    private static final int COL_MONTH_ID = 6;
    private static final int COL_WORKER_ID = 3;

    public DaysOfWorkTableModel()
    {
        addColumn(COL_TIMESTAMP, UDIPropSingleton.getString(this, "timestamp.column"), Date.class);
        addColumn(COL_WORKLOG, UDIPropSingleton.getString(this, "fromLog.column"), Double.class);
        addColumn(COL_WORKER_ID, UDIPropSingleton.getString(this, "workerID.column"), Integer.class);
        addColumn(COL_BONUS_TIME, UDIPropSingleton.getString(this, "bonusTime.column"), Double.class);
        addColumn(COL_AKTUAL_WORKED_DAYS, UDIPropSingleton.getString(this, "actualWorkedDays.column"), Integer.class);
        addColumn(COL_BONUS_TIME_DESC, UDIPropSingleton.getString(this, "descBonusTime.column"), String.class);
        setSortColumn(getColIndex(COL_TIMESTAMP));
        setSortOrder(SortOrder.DESCENDING);
    }

    public DaysOfWorkTableModel(boolean flag)
    {
        addColumn(COL_TIMESTAMP, UDIPropSingleton.getString(this, "timestamp.column"), Date.class);
        addColumn(COL_WORKLOG, UDIPropSingleton.getString(this, "fromLog.column"), Double.class);
        addColumn(COL_BONUS_TIME, UDIPropSingleton.getString(this, "bonusTime.column"), Double.class);
        addColumn(COL_AKTUAL_WORKED_DAYS, UDIPropSingleton.getString(this, "actualWorkedDays.column"), String.class);
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
            objResult = idData.getWorker().getAlias().toString();
            break;
        default:
            break;
        }
        return objResult;

    }

}
