package by.uniterra.udi.view;

import java.awt.Component;
import java.time.Clock;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.dai.entity.Worker;
import by.uniterra.system.util.WorkLogUtils;
import by.uniterra.udi.iface.IModelOwner;
import by.uniterra.udi.model.WorkLogInfoHolder;

public class WorkLogReportPanel
{
    private static List<Worker> workerArrayList;

    public static void main(String[] args)
    {
        jbInit();
        ServiceBaseEAO.disconnectFromDb();
    }

    public static void jbInit()
    {
        int curentMonth = YearMonth.now(Clock.systemUTC()).getMonthValue();;

        WorkerEAO eaoWorker = new WorkerEAO(ServiceBaseEAO.getDefaultEM());
        workerArrayList = eaoWorker.loadAll();
        JTabbedPane tabbedPane = new JTabbedPane();
        DaysOfWorkEAO eaoDaysOfWork = new DaysOfWorkEAO(ServiceBaseEAO.getDefaultEM());

        // get the number of working days in a month
        MonthEAO eaoMonth = new MonthEAO(ServiceBaseEAO.getDefaultEM());
        int workingDaysInMonth = eaoMonth.getWorkDayDataForMonth(curentMonth);

        for (Worker curentWorker : workerArrayList)
        {

            // Worker curentWorker = eaoWorker.find(wrk.getWorkerId());

            // load DaysOfWork
            List<DaysOfWork> lstDaysOfWork = eaoDaysOfWork.getLastDataForWorkerAndMonthNum(curentWorker, curentMonth);
            Component wlop = null;
            if (lstDaysOfWork.size() == 1)
            {
                wlop = new WorkLogOptionPanel();
                // get work log time
                double workLogTime = lstDaysOfWork.get(0).getWorklog();
                // get last update time
                String lastUpdateTime = String.valueOf(lstDaysOfWork.get(0).getTimestamp());
                // get sum bonus time for the current worker
                double curentSumBonus = eaoDaysOfWork.getSumBonusTimeForWorkerAndMonthNum(curentWorker, curentMonth);
                // to plane
                double toPlane = WorkLogUtils.getTimeRemainsToPlane(workingDaysInMonth, workLogTime, curentSumBonus);
                // get time to bonus
                double toBonus = WorkLogUtils.getTimeRemainsToBonus(workingDaysInMonth, workLogTime, curentSumBonus);
                // ((IModelOwner) wlop).setModel(lstData.get(0));
                ((IModelOwner) wlop).setModel(new WorkLogInfoHolder(String.valueOf(workLogTime), toPlane, toBonus, 25, lastUpdateTime, curentWorker.toString()));
            }
            else
            {
                wlop = new JLabel("No Data!!! Result lisr size=" + lstDaysOfWork.size());
            }

            tabbedPane.addTab((curentWorker.getFirstName() + " " + curentWorker.getSecondName()), wlop);

        }

        JFrame frame = new JFrame("Test Employe");
        frame.setVisible(true);
        frame.setSize(640, 480);
        frame.add(tabbedPane);
        frame.setVisible(true);

    }

}
