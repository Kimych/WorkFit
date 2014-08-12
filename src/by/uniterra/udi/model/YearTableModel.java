package by.uniterra.udi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import by.uniterra.dai.entity.Year;

public class YearTableModel extends AbstractFlexTableModel
{

	/** TODO document <code>serialVersionUID</code> */
	private static final long serialVersionUID = -5232340455887109379L;
	
	private final static int COL_ID_YEAR = 0;
    private final static int COL_NUMBER_YEAR = 1;
    private final static int COL__DESC_YEAR = 2;
    
	private List<Year> dataArrayList;
	public List<String> arrColumnNames;

	public YearTableModel()
	{
		/*dataArrayList = new ArrayList<Year>();
		arrColumnNames = Arrays.asList(new String[] { "ID", "Number",
				"Description" });*/
		addColumn(COL_ID_YEAR, "#ID", String.class);
        addColumn(COL_NUMBER_YEAR, "Номер", String.class);
        addColumn(COL__DESC_YEAR , "Примечание", String.class);
	}

	public void addData(List<Year> arrData)
	{
		this.dataArrayList = new ArrayList<Year>(arrData);
		// notify "view" about changed model data
		fireTableDataChanged();
	}

	//@Override
	/*public int getColumnCount()
	{
		return arrColumnNames.size();
	}

	@Override
	public int getRowCount()
	{
		return dataArrayList.size();
	}*/

/*	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Year year = dataArrayList.get(rowIndex);
		switch (columnIndex)
		{
		case 0:
			return year.getYearId();
		case 1:
			return year.getNumber();
		case 2:
			return year.getDeskription();
		}
		return null;
	}*/
	

	/*@Override
	public void setValueAt(Object editNewData, int rowIndex, int columnIndex)
	{
		Year year = dataArrayList.get(rowIndex);
		switch (columnIndex)
		{
		case 0:
			year.setYearId(Integer.valueOf((String) editNewData));
			break;
		case 1:
			year.setNumber(Integer.valueOf((String) editNewData));
			break;
		case 2:
			year.setDeskription((String) editNewData);
			break;
		}

		fireTableDataChanged();

	}*/

/*	public String getColumnName(int columnIndex) // возвращает название колонок
	{
		return arrColumnNames.get(columnIndex);
	}*/

	@Override
	public boolean isCellEditable(int row, int column)
	{
		return true;
	}

	public List<Year> setData()
	{
		return dataArrayList;
	}

	@Override
	public Object getValueById(int rowIndex, int columnId)
	{
		Object objResult = null;
        Year idData = (Year)getRowData(rowIndex);
        switch( columnId )
        {
            case COL_ID_YEAR:
                objResult = idData.getYearId();
                break;
            case COL_NUMBER_YEAR:
                objResult = idData.getNumber();
                break;
            case COL__DESC_YEAR:
                objResult = idData.getDeskription();
                break;
            default:
                break;
        }
        return objResult;
	}

}
