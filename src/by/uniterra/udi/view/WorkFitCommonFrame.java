package by.uniterra.udi.view;

import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import by.uniterra.dai.eao.HolidayEAO;
import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.eao.YearEAO;
import by.uniterra.udi.model.HolidayTableModel;
import by.uniterra.udi.model.MonthTableModel;
import by.uniterra.udi.model.WorkerTableModel;
import by.uniterra.udi.model.YearTableModel;

public class WorkFitCommonFrame
{

    public static void main(String[] args)
    {
        Locale.setDefault(new Locale("ru"));
        CommonDataTablePanel panelYear = new CommonDataTablePanel(new YearTableModel(), new YearOptionPanel(), new YearEAO(ServiceBaseEAO.getDefaultEM()));
        CommonDataTablePanel panelMonth = new CommonDataTablePanel(new MonthTableModel(), new MonthOptionPanel(), new MonthEAO(ServiceBaseEAO.getDefaultEM()));
        CommonDataTablePanel panelWorker = new CommonDataTablePanel(new WorkerTableModel(), new WorkerOptionPanel(), new WorkerEAO(
                ServiceBaseEAO.getDefaultEM()));
        CommonDataTablePanel panelHoliday = new CommonDataTablePanel(new HolidayTableModel(), new HolidayOptionPanel(), new HolidayEAO(ServiceBaseEAO.getDefaultEM()));
        JPanel panelCommon = new JPanel();
        panelCommon.add(panelMonth);
        panelCommon.add(panelYear);
        panelCommon.add(panelWorker);
        panelCommon.add(panelHoliday);
        // show editor
        JOptionPane.showMessageDialog(null, panelCommon, "Main Frame", JOptionPane.PLAIN_MESSAGE);
        // save to DB
        panelMonth.writeValues();
        panelYear.writeValues();
        panelWorker.writeValues();
        panelHoliday.writeValues();

        ServiceBaseEAO.disconnectFromDb();
    }

}
