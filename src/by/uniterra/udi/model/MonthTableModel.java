package by.uniterra.udi.model;

import java.util.ArrayList;
import java.util.List;

import by.uniterra.dai.entity.Month;

public class MonthTableModel extends AbstractFlexTableModel
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = -711301084307271936L;

    private final static int COL_ID_MONTHS = 0;
    private final static int COL_WORKING_DAYS_COUNT_MONTHS = 1;
    private final static int COL_DESC_MONTHS = 2;
    private final static int COL_NAME_MONTH_ID_MONTHS = 3;
    private final static int COL_YEAR_ID_MONTHS = 4;

    private List<Month> dataArrayList;
    public List<String> arrColumnNames;

    public MonthTableModel()
    {
        // addColumn(COL_ID_MONTHS,"#ID" , String.class);
        addColumn(COL_NAME_MONTH_ID_MONTHS, "Месяц", String.class);
        addColumn(COL_YEAR_ID_MONTHS, "Год", String.class);
        addColumn(COL_WORKING_DAYS_COUNT_MONTHS, "Рабочих дней", String.class);
        addColumn(COL_DESC_MONTHS, "Примечание", String.class);

    }

    public void addData(List<Month> arrData)
    {
        this.dataArrayList = new ArrayList<Month>(arrData);
        // notify "view" about changed model data
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }

    public List<Month> setData()
    {
        return dataArrayList;
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
        case COL_WORKING_DAYS_COUNT_MONTHS:
            objResult = idData.getWorkingDaysCount();
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
