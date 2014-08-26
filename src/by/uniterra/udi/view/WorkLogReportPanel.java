package by.uniterra.udi.view;

import java.awt.Component;
import java.time.Clock;
import java.time.YearMonth;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.eao.HolidayEAO;
import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.dai.eao.SpentHolidayEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.dai.entity.Worker;
import by.uniterra.system.util.WorkLogUtils;
import by.uniterra.udi.iface.IModelOwner;
import by.uniterra.udi.model.WorkLogInfoHolder;

public class WorkLogReportPanel extends JTabbedPane
{
    private static final long serialVersionUID = -7127157043829355694L;

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Test Employe");
        frame.setVisible(true);
        frame.setSize(640, 480);
        frame.add(new WorkLogReportPanel());
        frame.setVisible(true);
    }

    /**
     * 
     * Constructor.
     *
     */
    public WorkLogReportPanel()
    {
	jbInit();
        ServiceBaseEAO.disconnectFromDb();
    }
    
    /**
     * Create UI     * 
     *
     * @author Anton Nedbailo
     * @date Aug 26, 2014
     */
    private void jbInit()
    {
	YearMonth ymCurMonth = YearMonth.now(Clock.systemUTC());
        int curentMonth = ymCurMonth.getMonthValue();
        
        // create EAO set
        WorkerEAO eaoWorker = new WorkerEAO(ServiceBaseEAO.getDefaultEM());
        DaysOfWorkEAO eaoDaysOfWork = new DaysOfWorkEAO(ServiceBaseEAO.getDefaultEM());
        MonthEAO eaoMonth = new MonthEAO(ServiceBaseEAO.getDefaultEM());
        SpentHolidayEAO eaoSpentHoliday = new SpentHolidayEAO(ServiceBaseEAO.getDefaultEM());
        HolidayEAO eaoHoliday = new HolidayEAO(ServiceBaseEAO.getDefaultEM());
        
        // for all workers
        for (Worker curentWorker : eaoWorker.loadAll())
        {
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
                // get the number of working days in a month
                int workingDaysInMonth = eaoMonth.getWorkDayDataForMonth(curentMonth);
                // to plane
                double toPlane = WorkLogUtils.getTimeRemainsToPlane(workingDaysInMonth, workLogTime, curentSumBonus);
                // get time to bonus
                double toBonus = WorkLogUtils.getTimeRemainsToBonus(workingDaysInMonth, workLogTime, curentSumBonus);
                
                // get  rest of the holiday
                int numberYear = ymCurMonth.getYear();
                double holiday = eaoHoliday.getHolidayDaysCountForWorkerAndYear(curentWorker, numberYear);
                double timeLeft = eaoSpentHoliday.getSpentHolidayWorkerAndYear(curentWorker, numberYear);
                
                ((IModelOwner) wlop).setModel(new WorkLogInfoHolder(String.valueOf(workLogTime), toPlane, toBonus, (holiday-timeLeft), lastUpdateTime, curentWorker.toString()));
            }
            else
            {
                wlop = new JLabel("No Data!!! Result lisr size=" + lstDaysOfWork.size());
            }
            // add a new tab
            addTab((curentWorker.getFirstName() + " " + curentWorker.getSecondName()), wlop);
        }
    }

}
