package by.uniterra.udi.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.eao.HolidayEAO;
import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.eao.SpentHolidayEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.dai.entity.Worker;
import by.uniterra.system.model.SystemModel;
import by.uniterra.system.util.DateUtils;
import by.uniterra.system.util.WorkLogUtils;
import by.uniterra.udi.util.Log;

public class WorkLogInfoHelper
{

    public static List<WorkLogInfoHolder> getLogListUpToDate(Date date)
    {
        List<WorkLogInfoHolder> lstResult = new ArrayList<WorkLogInfoHolder>();
        WorkerEAO eaoWorker = new WorkerEAO(SystemModel.getDefaultEM());
        for (Worker currentWorker : eaoWorker.loadAll())
        {
            WorkLogInfoHolder objResult = getLogListUpToDateAndWorker(currentWorker, date);
            if (objResult != null)
            {
                lstResult.add(objResult);
            }
        }
        return lstResult;
    }

    public static WorkLogInfoHolder getLogListUpToDateAndWorker(Worker curentWorker, Date date)
    {
        WorkLogInfoHolder objResult = null;

        // create set of EAOs
        DaysOfWorkEAO eaoDaysOfWork = new DaysOfWorkEAO(SystemModel.getDefaultEM());
        MonthEAO eaoMonth = new MonthEAO(SystemModel.getDefaultEM());
        SpentHolidayEAO eaoSpentHoliday = new SpentHolidayEAO(SystemModel.getDefaultEM());
        HolidayEAO eaoHoliday = new HolidayEAO(SystemModel.getDefaultEM());

        List<DaysOfWork> lstDaysOfWork = eaoDaysOfWork.getfindLastForWorkerAndTimestamp(curentWorker, DateUtils.upToEndDayDate(date));
        if (lstDaysOfWork.size() == 1)
        {
            DaysOfWork dfwLastDaysOfWorl = lstDaysOfWork.get(0);
            int calculatedMonth = DateUtils.getMonthNumber(dfwLastDaysOfWorl.getTimestamp());
            int calculatedYear = DateUtils.getYearNumber(dfwLastDaysOfWorl.getTimestamp());

            double spentHolidayDayInCurrentMont = eaoSpentHoliday.getSpentHolidayByWorkerAndMonthAndYear(curentWorker, calculatedMonth, calculatedYear);

            int dayPassed = dfwLastDaysOfWorl.getAktualWorkedDays();
            // get work log time
            double workLogTime = dfwLastDaysOfWorl.getWorklog();
            // get sum bonus time for the current worker
            double curentSumBonus = eaoDaysOfWork.getSumBonusTimeForWorkerAndMonthNum(curentWorker, calculatedMonth);
            // to plane
            double ToPlane = WorkLogUtils.getTimeRemainsToPlaneToDay(dayPassed, workLogTime, curentSumBonus, spentHolidayDayInCurrentMont);
            // get time to bonus
            double ToBonus = WorkLogUtils.getTimeRemainsToBonusToDay(dayPassed, workLogTime, curentSumBonus, spentHolidayDayInCurrentMont);
            // get rest of the holiday
            double holiday = eaoHoliday.getHolidayDaysCountForWorkerAndYear(curentWorker, calculatedYear);
            // get spend holiday
            double timeLeft = eaoSpentHoliday.getSpentHolidayWorkerAndYear(curentWorker, calculatedYear);
            int workingDaysInMonth = eaoMonth.getWorkDayDataForMonth(calculatedMonth);
            // get result of the work
            boolean beInPlane = WorkLogUtils.beInPlaneAtTime(dayPassed, workingDaysInMonth, ToPlane);
            // add to result
            objResult = new WorkLogInfoHolder(WorkLogUtils.roundToString(workLogTime, 2, BigDecimal.ROUND_HALF_UP), ToPlane, ToBonus, (holiday - timeLeft),
                    dfwLastDaysOfWorl.getTimestamp(), curentWorker.toString(), beInPlane);
        }
        else
        {
            Log.debug(WorkLogInfoHelper.class, "We found more than 1 record with last DaysOfWork for \"" + curentWorker + "\" and " + DateUtils.toGMT(date));
        }
        return objResult;

    }
}
