package by.uniterra.udi.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class YearTableModel extends AbstractTableModel
{

	/** TODO document <code>serialVersionUID</code> */
	private static final long serialVersionUID = -5232340455887109379L;

	private int columpCount = 3;
	private ArrayList<String[]> dataArrayList;
	
	public YearTableModel()
	{
		dataArrayList = new ArrayList<String[]>();
		for (int i = 0; i < dataArrayList.size(); i++)
		{
			dataArrayList.add(new String[getColumnCount()]);
		}
	}
	
	public void addDate(String[] row)
	{
		String[] rowTable = new String[getColumnCount()];
		rowTable = row;
		dataArrayList.add(rowTable);

	}

	@Override
	public int getColumnCount()
	{
		return columpCount;
	}

	@Override
	public int getRowCount()
	{
		return dataArrayList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		String[] rows = dataArrayList.get(rowIndex);
		return rows[columnIndex];
	}

	public String getColumnName(int columnIndex) // возвращает название колонок
	{
		switch (columnIndex)
		{
		case 0:
			return "#id";
		case 1:
			return "year";
		case 2:
			return "description";
		}
		return "";

	}

}
