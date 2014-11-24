package by.uniterra.udi.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXMonthView;

import by.uniterra.dai.entity.CalendarSpecialDay;
import by.uniterra.udi.iface.IModelOwner;

public class CalendarSpecialDayOptionPanel extends JPanel implements IModelOwner
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 8380269231834025937L;

    
    private JXMonthView jxmvCalendar;
    private JTextField tfDayType;
    private JTextArea taDescription;
    private CalendarSpecialDay csDay;
    
    public CalendarSpecialDayOptionPanel()
    {
        super(new GridBagLayout());
        jbInit();
    }
    
    private void jbInit()
    {
        //TODO remade to comboBox including types of the day
        JLabel jlDayType = new JLabel("Day type");
        
        JLabel jlDescription = new JLabel("Desk");
        tfDayType = new JTextField();
        taDescription = new JTextArea();
        taDescription.setColumns(20);
        taDescription.setRows(7);
        taDescription.setLineWrap(true);
        
        jxmvCalendar = new JXMonthView();
        jxmvCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        jxmvCalendar.setDayForeground(Calendar.SUNDAY, Color.MAGENTA);
        jxmvCalendar.setDayForeground(Calendar.SATURDAY, Color.MAGENTA);
        jxmvCalendar.setZoomable(true);
        
        add(jxmvCalendar, new GridBagConstraints(0, 0, 1, 4, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlDayType, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(tfDayType, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 5), 0, 0));
        add(jlDescription, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 5), 0, 0));
        add(taDescription, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 5), 0, 0));
    }
    
    @Override
    public void setModel(Object objCalSpecDay)
    {
        this.csDay = (CalendarSpecialDay) objCalSpecDay;
        jxmvCalendar.setFlaggedDates(csDay.getDateDay());
        tfDayType.setText(String.valueOf(csDay.getTypeDay()));
        taDescription.setText(csDay.getDescrition()); 
    }

    @Override
    public Object getModel()
    {
        csDay.setDateDay(jxmvCalendar.getSelectionDate());
        csDay.setTypeDay(Integer.valueOf(tfDayType.getText()));
        csDay.setDescrition(taDescription.getText());
        return csDay;
    }
    

}
