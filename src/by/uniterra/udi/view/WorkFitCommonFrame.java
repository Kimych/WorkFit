package by.uniterra.udi.view;

import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.eao.YearEAO;
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
        CommonDataTablePanelEx panelWorker = new CommonDataTablePanelEx(new WorkerTableModel(), new WorkerOptionPanel(), new WorkerEAO(
                ServiceBaseEAO.getDefaultEM()), new HolidayOptionPanel());
        JPanel panelCommon = new JPanel();
        panelCommon.add(panelMonth);
        panelCommon.add(panelYear);
        panelCommon.add(panelWorker);
        // show editor
        JOptionPane.showMessageDialog(null, panelCommon, "Main Frame", JOptionPane.PLAIN_MESSAGE);
        // save to DB
        panelMonth.writeValues();
        panelYear.writeValues();
        panelWorker.writeValues();

        ServiceBaseEAO.disconnectFromDb();
    }

}
