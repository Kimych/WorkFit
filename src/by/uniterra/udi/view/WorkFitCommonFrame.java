package by.uniterra.udi.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.omg.CORBA.PERSIST_STORE;

import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.eao.HolidayEAO;
import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.eao.YearEAO;
import by.uniterra.udi.model.DaysOfWorkTableModel;
import by.uniterra.udi.model.HolidayTableModel;
import by.uniterra.udi.model.MonthTableModel;
import by.uniterra.udi.model.WorkerTableModel;
import by.uniterra.udi.model.YearTableModel;

public class WorkFitCommonFrame
{

    public static void main(String[] args)
    {
        // Locale.setDefault(new Locale("ru"));
        // CommonDataTablePanel panelYear = new CommonDataTablePanel(new
        // YearTableModel(), new YearOptionPanel(), new
        // YearEAO(ServiceBaseEAO.getDefaultEM()));
        // CommonDataTablePanel panelMonth = new CommonDataTablePanel(new
        // MonthTableModel(), new MonthOptionPanel(), new
        // MonthEAO(ServiceBaseEAO.getDefaultEM()));
        CommonDataTablePanel panelWorker = new CommonDataTablePanel(new WorkerTableModel(), new WorkerOptionPanel(), new WorkerEAO(
                ServiceBaseEAO.getDefaultEM()));
        // CommonDataTablePanel panelHoliday = new CommonDataTablePanel(new
        // HolidayTableModel(), new HolidayOptionPanel(), new
        // HolidayEAO(ServiceBaseEAO.getDefaultEM()));

        JPanel panelCommon = new JPanel();
        //panelCommon.add(panelMonth);
        //panelCommon.add(panelYear);
        panelCommon.add(panelWorker);
        //panelCommon.add(panelHoliday);
        //CommonDataTablePanel panelDoW = new CommonDataTablePanel(new DaysOfWorkTableModel(), new DaysOfWorkOptionPanel(), new DaysOfWorkEAO(
        //        ServiceBaseEAO.getDefaultEM()));

        //panelCommon.add(panelDoW);
        // show editor
        JOptionPane.showMessageDialog(null, panelCommon, "Main Frame", JOptionPane.PLAIN_MESSAGE);
        // save to DB
       // panelMonth.writeValues();
        //panelYear.writeValues();
        panelWorker.writeValues();
       //panelHoliday.writeValues();

        ServiceBaseEAO.disconnectFromDb();
        
        /*JFrame jfTestOpenFile = new JFrame();
        jfTestOpenFile.setSize(200, 200);

        JButton btnOpenFile = new JButton("Open file");

        JPanel jpParseFile = new JPanel();
        jpParseFile.add(btnOpenFile);
        jfTestOpenFile.add(jpParseFile);
        jfTestOpenFile.setVisible(true);

        // panelDoW.writeValues();
        ServiceBaseEAO.disconnectFromDb();

        JButton btnImport = new JButton("Import");
        btnImport.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                // TODO Auto-generated method stub
                // get selected file for import

                // parse selected file
            }
        });
        JOptionPane.showMessageDialog(null, btnImport, "Main Frame", JOptionPane.PLAIN_MESSAGE);
*/
    }

}
