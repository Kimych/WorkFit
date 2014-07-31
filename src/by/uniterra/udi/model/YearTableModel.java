package by.uniterra.udi.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import by.uniterra.dai.entity.Year;


public class YearTableModel extends AbstractTableModel
{

	/** TODO document <code>serialVersionUID</code> */
	private static final long serialVersionUID = -5232340455887109379L;

	private List<Year> dataArrayList;
	private List<String> arrColumnNames ;
	
	public YearTableModel()
	{
		dataArrayList = new ArrayList<Year>();
		arrColumnNames = Arrays.asList(new String[]{"ID", "Number", "Description"});
	}
	
	public void addData(List<Year> arrData)
	{
		this.dataArrayList = new ArrayList<Year>(arrData);
	}

	@Override
	public int getColumnCount()
	{
		return arrColumnNames.size();
	}

	@Override
	public int getRowCount()
	{
		return dataArrayList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Year year = dataArrayList.get(rowIndex);
		switch(columnIndex)
		{
		case 0:
			return year.getYearId();
		case 1:
			return year.getNumber();
		case 2:
			return year.getDeskription();			
		}
	    return null;
	}

	public String getColumnName(int columnIndex) // возвращает название колонок
	{
	    return arrColumnNames.get(columnIndex);
	}
}
