package by.uniterra.udi.model;

import by.uniterra.dai.entity.Month;

public class MonthTableModel extends AbstractFlexTableModel
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = -711301084307271936L;

    private static final int COL_ID_MONTHS = 0;
    private static final int COL_DESC_MONTHS = 1;
    private static final int COL_NAME_MONTH_ID_MONTHS = 2;
    private static final int COL_YEAR_ID_MONTHS = 3;

    public MonthTableModel()
    {
        addColumn(COL_NAME_MONTH_ID_MONTHS, UDIPropSingleton.getString(this, "ColNameMounts.column"), Integer.class);
        addColumn(COL_YEAR_ID_MONTHS, UDIPropSingleton.getString(this, "ColYearMonth.column"), Integer.class);
        addColumn(COL_DESC_MONTHS, UDIPropSingleton.getString(this, "ColDescMonth.column"), String.class);
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
        Month idData = (Month) getRowData(rowIndex);
        switch (columnId)
        {

        case COL_ID_MONTHS:
            objResult = idData.getMonthId();
            break;
        case COL_DESC_MONTHS:
            objResult = idData.getDescription();
            break;
        case COL_NAME_MONTH_ID_MONTHS:
            objResult = idData.getNameMonth().getName();
            break;
        case COL_YEAR_ID_MONTHS:
            objResult = idData.getYear().getNumber();
            break;
        default:
            break;
        }
        return objResult;
    }

}
