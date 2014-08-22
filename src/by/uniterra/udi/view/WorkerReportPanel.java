package by.uniterra.udi.view;

import java.awt.Component;
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
import by.uniterra.dai.entity.Month;
import by.uniterra.dai.entity.Worker;
import by.uniterra.udi.iface.IModelOwner;

public class WorkerReportPanel
{
    private static List<Worker> workerArrayList;;

    public static void main(String[] args)
    {
        jbInit();
        ServiceBaseEAO.disconnectFromDb();
    }

    public static void jbInit()
    {
        int curentMonth = (Calendar.getInstance().get(Calendar.MONTH) + 1);
        workerArrayList = new WorkerEAO(ServiceBaseEAO.getDefaultEM()).loadAll();
        JTabbedPane tabbedPane = new JTabbedPane();
        DaysOfWorkEAO eaoDaysOfWork = new DaysOfWorkEAO(ServiceBaseEAO.getDefaultEM());
        
        WorkerEAO eaoWorker = new WorkerEAO(ServiceBaseEAO.getDefaultEM());
        
        MonthEAO eaoMonth = new MonthEAO(ServiceBaseEAO.getDefaultEM()); 
        int workingDaysCount = eaoMonth.getWorkDayDataForMonth(curentMonth);
        
        for (Worker wrk : workerArrayList)
        {
            // load DaysOfWork
            List<DaysOfWork> lstData = eaoDaysOfWork.getLastDataForWorkerAndMonthNum(eaoWorker.find(wrk.getWorkerId()), curentMonth);
            Component wlop = null;
            if (lstData.size() == 1)
            {
                wlop = new WorkLogOptionPanel();
                ((IModelOwner) wlop).setModel(lstData.get(0));
            }
            else
            {
                wlop = new JLabel("No Data!!! Result lisr size=" + lstData.size());
            }
            tabbedPane.addTab((wrk.getFirstName() + " " + wrk.getSecondName()), wlop);
        }

        JFrame frame = new JFrame("Test Employe");
        frame.setVisible(true);
        frame.setSize(640, 480);
        frame.add(tabbedPane);
        frame.setVisible(true);

    }

}
