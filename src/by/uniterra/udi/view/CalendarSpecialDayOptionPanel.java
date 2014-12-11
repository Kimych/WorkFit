package by.uniterra.udi.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import by.uniterra.system.util.DateUtils;
import by.uniterra.udi.util.EDayType;
import by.uniterra.udi.util.UTCheckBoxList;

public class CalendarSpecialDayOptionPanel extends JPanel
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 8380269231834025937L;

    static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    
    private UTCheckBoxList chblDayType;
    
 /*   public static void main(String[] args)
    {
    
        JFrame fFrame = new JFrame();
        JPanel panelCommon = new JPanel();
        panelCommon.add(new CalendarSpecialDayOptionPanel());
        panelCommon.setVisible(true);
        
        fFrame.add(panelCommon);
        fFrame.setVisible(true);
    }*/
    
    public CalendarSpecialDayOptionPanel(Date date)
    {
        super(new GridBagLayout());
        jbInit(date);
    }

    private void jbInit(Date date)
    {
        chblDayType = new UTCheckBoxList();
        EDayType[] arrDayType = EDayType.values();
        List<EDayType> dayTypeList = new ArrayList<EDayType>(Arrays.asList(arrDayType));
        if(DateUtils.isDayOff(date))
        {
           dayTypeList.remove(1);
           chblDayType.setListData(dayTypeList.stream().toArray(EDayType[]::new));
        }
        else
        {
            dayTypeList.remove(0);
            chblDayType.setListData(dayTypeList.stream().toArray(EDayType[]::new));
        }
        
        add(chblDayType, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 5), 0, 0));
    }

    public void setModel(List<EDayType> lstDayType)
    {
        chblDayType.setSelectedItems(lstDayType);
    }

    public int getModel()
    {
        int iResult = 0;
        List<EDayType> lstReturn = new ArrayList<EDayType>();
        lstReturn = (List<EDayType>) chblDayType.getSelectedItems();
        for (EDayType eDayType : lstReturn)
        {
            iResult |=  (int) Math.pow(2, eDayType.ordinal());
        }
        return iResult;
    }
    
    
 

}
