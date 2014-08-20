package by.uniterra.udi.view;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.entity.Worker;

public class EmployeReportPanel
{
    private static List<Worker> workerArrayList;;

    public static void main(String[] args)
    {
        jbInit();
        ServiceBaseEAO.disconnectFromDb();
    }
    
    public static void jbInit()
    {
        workerArrayList = new WorkerEAO(ServiceBaseEAO.getDefaultEM()).loadAll();
        JTabbedPane tabbedPane = new JTabbedPane();
        for(Worker wrk : workerArrayList)
        {
            tabbedPane.addTab((wrk.getFirstName() + " " + wrk.getSecondName()), new EmployeReportTab().getTab(wrk.getWorkerId()));  
        }
        JFrame frame = new JFrame("Test Employe");
        frame.setVisible(true);
        frame.setSize(640, 480);
        frame.add(tabbedPane);
        frame.setVisible(true);

    }

}
