package by.uniterra.udi.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.entity.DaysOfWork;

public class EmployeReportTab
{

    private JLabel jlNameWorker;
    private JLabel jlLastUpdateDate;
    private JLabel jlCurrentTime;
    private JLabel jlToPlane;
    private JLabel jlToBonus;
    private JLabel jlTimeLeft;
    private JTextField tfCurentTime;
    private JTextField tfToPlane;
    private JTextField tfToBonus;
    private JTextField tfTimeLeft;

    public EmployeReportTab()
    {

    }

    public JPanel getTab(int wrkId)
    {
        curentMonthNumber();
        MonthEAO eaoMonth = new MonthEAO(ServiceBaseEAO.getDefaultEM());
        WorkerEAO eaoWorker = new WorkerEAO(ServiceBaseEAO.getDefaultEM());
        DaysOfWorkEAO eaoDaysOfWork = new DaysOfWorkEAO(ServiceBaseEAO.getDefaultEM());
        List<DaysOfWork> lstData = eaoDaysOfWork.getLastDataForWorkerAndMonth(eaoWorker.find(wrkId), eaoMonth.find(curentMonthNumber()));

        // check result
        if (lstData.size() != 1)
        {
            System.out.println("We have problems with DaysOfWork Data (result list size=" + lstData.size() + ").");
            return new JPanel();
        }
        
        DaysOfWork selDow = lstData.get(0);
        System.out.println(selDow.getTimestamp());
        jlNameWorker = new JLabel(String.valueOf(selDow.getWorker()));
        jlLastUpdateDate = new JLabel("Текущая дата");

        jlCurrentTime = new JLabel("Текущее");
        jlToPlane = new JLabel("До плана");
        jlToBonus = new JLabel("До бонуса");
        jlTimeLeft = new JLabel("Осталось отработать");

        tfCurentTime = new JTextField(String.valueOf(selDow.getAktualWorkedDays()));
        tfToPlane = new JTextField();
        tfToBonus = new JTextField();
        tfTimeLeft = new JTextField();

        // tfCurentTime.setText(String.valueOf(selDow.getAktualWorkedDays()));

        JPanel panelTab = new JPanel(new GridBagLayout());

        panelTab.add(jlNameWorker, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0,
                0));
        panelTab.add(jlLastUpdateDate, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5),
                0, 0));
        panelTab.add(jlCurrentTime, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0,
                0));
        panelTab.add(jlToPlane, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        panelTab.add(jlToBonus, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        panelTab.add(jlTimeLeft, new GridBagConstraints(0, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));

        panelTab.add(tfCurentTime, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0,
                0));
        panelTab.add(tfToPlane, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        panelTab.add(tfToBonus, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        panelTab.add(tfTimeLeft, new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));

        panelTab.setVisible(true);

        return panelTab;

    }

    // get the number of the current month
    private int curentMonthNumber()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

}
