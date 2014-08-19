package by.uniterra.udi.view;

import java.util.List;

import javax.swing.JPanel;

import by.uniterra.dai.entity.Holiday;
import by.uniterra.udi.iface.IModelOwner;

public class HolidayOptionPanel extends JPanel implements IModelOwner
{
    private static final long serialVersionUID = -6282330382069748329L;
    
    @Override
    public void setModel(Object mData)
    {
        // here we will get a List<Holiday> to display and edit
	List<Holiday> objListToDisplay = (List<Holiday>)mData;
	for (Holiday holiday : objListToDisplay)
	{
	    System.out.println(holiday.toString());
	}
    }

    @Override
    public Object getModel()
    {
        // here we should return edited List<Holiday> 
        return null;
    }

}
