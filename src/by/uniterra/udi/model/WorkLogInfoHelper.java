package by.uniterra.udi.model;

import java.awt.Component;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.eao.HolidayEAO;
import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.eao.SpentHolidayEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.dai.entity.Worker;
import by.uniterra.system.model.SystemModel;
import by.uniterra.system.util.WorkLogUtils;
import by.uniterra.udi.iface.IModelOwner;
import by.uniterra.udi.view.WorkLogOptionPanel;

public class WorkLogInfoHelper
{
    static int curentMonth = YearMonth.now(Clock.systemUTC()).getMonthValue();
    static int numberYear = YearMonth.now(Clock.systemUTC()).getYear();

    static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy (hh:mm)");

    public static WorkLogInfoHolder getLogInfoByWorker()
    {
        WorkLogInfoHolder objResult = null;
        Authorization objAuth = SystemModel.getAuthorization();
        // get worker
        Worker curentWorker = objAuth.getWorker();

        if (curentWorker != null)
        {
            DaysOfWorkEAO eaoDaysOfWork = new DaysOfWorkEAO(SystemModel.getDefaultEM());
            // get the number of working days in a month
            MonthEAO eaoMonth = new MonthEAO(SystemModel.getDefaultEM());
            int workingDaysInMonth = eaoMonth.getWorkDayDataForMonth(curentMonth);

            HolidayEAO eaoHoliday = new HolidayEAO(SystemModel.getDefaultEM());

            SpentHolidayEAO eaoSpentHoliday = new SpentHolidayEAO(SystemModel.getDefaultEM());
            List<DaysOfWork> lstDaysOfWork = eaoDaysOfWork.getLastDataForWorkerAndMonthNum(curentWorker, curentMonth);
            double workLogTime = lstDaysOfWork.get(0).getWorklog();

            String lastUpdateTime = DATE_FORMAT.format(lstDaysOfWork.get(0).getTimestamp());

            double curentSumBonus = eaoDaysOfWork.getSumBonusTimeForWorkerAndMonthNum(curentWorker, curentMonth);
            // to plane
            double ToPlane = WorkLogUtils.getTimeRemainsToPlane(workingDaysInMonth, workLogTime, curentSumBonus);
            // get time to bonus
            double ToBonus = WorkLogUtils.getTimeRemainsToBonus(workingDaysInMonth, workLogTime, curentSumBonus);
            // get rest of the holiday
            double holiday = eaoHoliday.getHolidayDaysCountForWorkerAndYear(curentWorker, numberYear);
            // get spend holiday
            double timeLeft = eaoSpentHoliday.getSpentHolidayWorkerAndYear(curentWorker, numberYear);
            // get result of the work
            boolean beInPlane = WorkLogUtils.beInPlaneAtTime(lstDaysOfWork.get(0).getAktualWorkedDays(), workingDaysInMonth, ToPlane);
            // get worklog time
            String roundWorkLogTime = WorkLogUtils.roundToString(workLogTime, 2, BigDecimal.ROUND_HALF_UP);
            objResult = new WorkLogInfoHolder(roundWorkLogTime, ToPlane, ToBonus, (holiday - timeLeft), lastUpdateTime, curentWorker.toString(), beInPlane);
        }

        return objResult;
    }

    public static List<WorkLogInfoHolder> getAllLogList()
    {

        List<WorkLogInfoHolder> lstResult = new ArrayList<WorkLogInfoHolder>();
        DaysOfWorkEAO eaoDaysOfWork = new DaysOfWorkEAO(SystemModel.getDefaultEM());
        WorkerEAO eaoWorker = new WorkerEAO(SystemModel.getDefaultEM());
        List<Worker> workerArrayList = eaoWorker.loadAll();

        // get the number of working days in a month
        MonthEAO eaoMonth = new MonthEAO(SystemModel.getDefaultEM());
        int workingDaysInMonth = eaoMonth.getWorkDayDataForMonth(curentMonth);

        SpentHolidayEAO eaoSpentHoliday = new SpentHolidayEAO(SystemModel.getDefaultEM());

        HolidayEAO eaoHoliday = new HolidayEAO(SystemModel.getDefaultEM());

        for (Worker curentWorker : workerArrayList)
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
                // String lastUpdateTime =
                // String.valueOf(lstDaysOfWork.get(0).getTimestamp());
                String lastUpdateTime = DATE_FORMAT.format(lstDaysOfWork.get(0).getTimestamp());
                // get sum bonus time for the current worker
                double curentSumBonus = eaoDaysOfWork.getSumBonusTimeForWorkerAndMonthNum(curentWorker, curentMonth);
                // to plane
                double ToPlane = WorkLogUtils.getTimeRemainsToPlane(workingDaysInMonth, workLogTime, curentSumBonus);
                // get time to bonus
                double ToBonus = WorkLogUtils.getTimeRemainsToBonus(workingDaysInMonth, workLogTime, curentSumBonus);
                // get rest of the holiday
                double holiday = eaoHoliday.getHolidayDaysCountForWorkerAndYear(curentWorker, numberYear);
                // get spend holiday
                double timeLeft = eaoSpentHoliday.getSpentHolidayWorkerAndYear(curentWorker, numberYear);
                // get result of the work
                boolean beInPlane = WorkLogUtils.beInPlaneAtTime(lstDaysOfWork.get(0).getAktualWorkedDays(), workingDaysInMonth, ToPlane);
                // get worklog time
                String roundWorkLogTime = WorkLogUtils.roundToString(workLogTime, 2, BigDecimal.ROUND_HALF_UP);

                WorkLogInfoHolder wlih = new WorkLogInfoHolder(roundWorkLogTime, ToPlane, ToBonus, (holiday - timeLeft), lastUpdateTime,
                        curentWorker.toString(), beInPlane);
                lstResult.add(wlih);
            }
            else
            {
                wlop = new JLabel("No Data!!! Result lisr size=" + lstDaysOfWork.size());
            }
        }
        return lstResult;
    }
}
