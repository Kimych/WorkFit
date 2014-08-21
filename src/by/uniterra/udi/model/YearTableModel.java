package by.uniterra.udi.model;

import java.util.ArrayList;
import java.util.List;

import by.uniterra.dai.entity.Year;

public class YearTableModel extends AbstractFlexTableModel
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = -5232340455887109379L;

    private final static int COL_ID_YEAR = 0;
    private final static int COL_NUMBER_YEAR = 1;
    private final static int COL_DESC_YEAR = 2;

    private List<Year> dataArrayList;
    public List<String> arrColumnNames;

    public YearTableModel()
    {
        // addColumn(COL_ID_YEAR, "#ID", String.class);
        addColumn(COL_NUMBER_YEAR, UDIPropSingleton.getString(this, "ColNumberYear.column"), Integer.class);
        addColumn(COL_DESC_YEAR, UDIPropSingleton.getString(this, "ColDescYear.column"), String.class);
    }

    public void addData(List<Year> arrData)
    {
        this.dataArrayList = new ArrayList<Year>(arrData);
        // notify "view" about changed model data
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }

    public List<Year> setData()
    {
        return dataArrayList;
    }

    @Override
    public Object getValueById(int rowIndex, int columnId)
    {
        Object objResult = null;
        Year idData = (Year) getRowData(rowIndex);
        switch (columnId)
        {
        case COL_ID_YEAR:
            objResult = idData.getYearId();
            break;
        case COL_NUMBER_YEAR:
            objResult = idData.getNumber();
            break;
        case COL_DESC_YEAR:
            objResult = idData.getDeskription();
            break;
        default:
            break;
        }
        return objResult;
    }

}
