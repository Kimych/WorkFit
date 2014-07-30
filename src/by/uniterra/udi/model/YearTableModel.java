package by.uniterra.udi.model;

import javax.swing.table.AbstractTableModel;

public class YearTableModel extends AbstractTableModel
{

	/** TODO document <code>serialVersionUID</code> */
	private static final long serialVersionUID = -5232340455887109379L;

	private int columpCount = 3;

	@Override
	public int getColumnCount()
	{
		// TODO Auto-generated method stub
		return columpCount;
	}

	@Override
	public int getRowCount()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		// TODO Auto-generated method stub
		return null;
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
