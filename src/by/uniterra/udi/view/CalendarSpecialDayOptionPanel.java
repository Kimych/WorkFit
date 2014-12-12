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
import javax.swing.JTextArea;

import by.uniterra.dai.entity.CalendarSpecialDay;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.system.util.DateUtils;
import by.uniterra.udi.util.EDayType;
import by.uniterra.udi.util.UTCheckBoxList;

public class CalendarSpecialDayOptionPanel extends JPanel
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 8380269231834025937L;

    static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private UTCheckBoxList chblDayType;
    private JTextArea taDescription;
    private CalendarSpecialDay objCSD;

    public CalendarSpecialDayOptionPanel()
    {
        super(new GridBagLayout());
        jbInit();
    }

    private void jbInit()
    {
        chblDayType = new UTCheckBoxList();
        taDescription = new JTextArea();
        taDescription.setColumns(20);
        taDescription.setRows(5);
        taDescription.setLineWrap(true);

        add(chblDayType, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 5), 0, 0));
        add(taDescription, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 5), 0, 0));
    }

    public void setModel(CalendarSpecialDay objCSD, boolean bIsWeekEnd)
    {
        this.objCSD = objCSD;

        EDayType[] arrDayType = EDayType.values();
        List<EDayType> dayTypeList = new ArrayList<EDayType>(Arrays.asList(arrDayType));
        dayTypeList.remove(bIsWeekEnd ? EDayType.DAY_OFF.ordinal() : EDayType.WORKING_DAY.ordinal());
        chblDayType.setListData(dayTypeList.stream().toArray(EDayType[]::new));

        chblDayType.setSelectedItems(objCSD.getTypeDay());

        taDescription.setText(objCSD.getDescrition());
    }

    @SuppressWarnings("unchecked")
    public CalendarSpecialDay getModel()
    {
        objCSD.setTypeDay((List<EDayType>) chblDayType.getSelectedItems());
        objCSD.setDescrition(taDescription.getText());

        return objCSD;
    }

}
