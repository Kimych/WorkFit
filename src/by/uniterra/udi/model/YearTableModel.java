package by.uniterra.udi.model;


import java.util.*;

import javax.swing.table.AbstractTableModel;

import org.eclipse.persistence.sdo.types.SDOWrapperType.YearWrapperImpl;

import by.uniterra.dai.eao.YearService;
import by.uniterra.dai.entity.Year;


public class YearTableModel extends AbstractTableModel
{

	/** TODO document <code>serialVersionUID</code> */
	private static final long serialVersionUID = -5232340455887109379L;

	private int columpCount = 3;
	private List<Year> dataArrayList;
	
	public YearTableModel()
	{
		dataArrayList = new ArrayList<Year>();
		for (int i = 0; i < dataArrayList.size(); i++)
		{
			//YearTableModel.YearTableModel.dataArrayList.addAll(new String[getColumnCount()]);
		}
	}
	
/*	public void addDate(String[] row)
	{
		String[] rowTable = new String[getColumnCount()];
		rowTable = row;
		dataArrayList.add(rowTable);

	}*/
	public void addDate(java.util.List<Year> arrData)
	{
		this.dataArrayList = new ArrayList<Year>(arrData);
		
	}
	//-----------------------------------------


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
