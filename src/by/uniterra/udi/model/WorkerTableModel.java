package by.uniterra.udi.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import by.uniterra.dai.entity.Worker;

public class WorkerTableModel extends AbstractFlexTableModel
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = -7240846608397488776L;

    private final static int COL_ID_WORKER = 0;
    private final static int COL_FIRST_NAME_WORKER = 1;
    private final static int COL_SECOND_NAME_WORKER = 2;
    private final static int COL_THIRD_NAME_WORKER = 3;
    private final static int COL_BUTTON_VIEW_HOLIDAY = 4;

    private List<Worker> dataArrayList;
    public List<String> arrColumnNames;
    JButton button = new JButton("Кнопка");

    public WorkerTableModel()
    {
        // addColumn(COL_ID_WORKER, "#ID", String.class);
        addColumn(COL_FIRST_NAME_WORKER, UDIPropSingleton.getString(this, "FirstName.column"), String.class);
        addColumn(COL_SECOND_NAME_WORKER, UDIPropSingleton.getString(this, "SecondName.column"), String.class);
        addColumn(COL_THIRD_NAME_WORKER, UDIPropSingleton.getString(this, "ThirdName.column"), String.class);
    }

    public void addData(List<Worker> arrData)
    {
        this.dataArrayList = new ArrayList<Worker>(arrData);
        // notify "view" about changed model data
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int row, int column)
    {

        return getColIndex(COL_BUTTON_VIEW_HOLIDAY) == column;

    }

    public List<Worker> setData()
    {
        return dataArrayList;
    }

    @Override
    public Object getValueById(int rowIndex, int columnId)
    {
        Object objResult = null;
        Worker idData = (Worker) getRowData(rowIndex);
        switch (columnId)
        {
        case COL_ID_WORKER:
            objResult = idData.getWorkerId();
            break;
        case COL_FIRST_NAME_WORKER:
            objResult = idData.getFirstName();
            break;
        case COL_SECOND_NAME_WORKER:
            objResult = idData.getSecondName();
            break;
        case COL_THIRD_NAME_WORKER:
            objResult = idData.getThirdName();
            break;
        default:
            break;
        }
        return objResult;
    }

}
