package by.uniterra.udi.model;

import java.util.Date;

public class WorkLogTableModel extends AbstractFlexTableModel
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = -8488583506352518426L;

    public static final int COL_NAME = 0;
    public static final int COL_CURRENT = 1;
    public static final int COL_TO_PLAN = 2;
    public static final int COL_TO_BONUS = 3;
    public static final int COL_SPENT_HOL_IN_MONT = 4;
    public static final int COL_REST_HOLIDAY = 5;
    public static final int COL_UPDATE_TIME = 6;

    public WorkLogTableModel()
    {
        addColumn(COL_NAME, UDIPropSingleton.getString(this, "name.column"), String.class);
        addColumn(COL_CURRENT, UDIPropSingleton.getString(this, "current.column"), String.class);
        addColumn(COL_TO_PLAN, UDIPropSingleton.getString(this, "toPlane.column"), Double.class);
        addColumn(COL_TO_BONUS, UDIPropSingleton.getString(this, "toBonus.column"), Double.class);
        addColumn(COL_SPENT_HOL_IN_MONT, UDIPropSingleton.getString(this, "spentHol.column"), Double.class);
        addColumn(COL_REST_HOLIDAY, UDIPropSingleton.getString(this, "restHoliday.column"), Double.class);
        addColumn(COL_UPDATE_TIME, UDIPropSingleton.getString(this, "updateTime.column"), Date.class);
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
        WorkLogInfoHolder idData = (WorkLogInfoHolder) getRowData(rowIndex);
        switch (columnId)
        {
        case COL_NAME:
            objResult = idData.getWorker().getAlias();
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
        case COL_SPENT_HOL_IN_MONT:
            objResult = idData.getSpentHollidayInMonth();
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
