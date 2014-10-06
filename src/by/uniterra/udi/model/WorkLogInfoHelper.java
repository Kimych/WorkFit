package by.uniterra.udi.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.eao.HolidayEAO;
import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.eao.SpentHolidayEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.dai.entity.Month;
import by.uniterra.dai.entity.Worker;
import by.uniterra.system.model.SystemModel;
import by.uniterra.system.util.DateUtils;
import by.uniterra.system.util.WorkLogUtils;
import by.uniterra.udi.util.Log;

public class WorkLogInfoHelper
{
    static int curentMonth = YearMonth.now(Clock.systemUTC()).getMonthValue();
   
    static int curentYear = YearMonth.now(Clock.systemUTC()).getYear();


    
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
            
            //get month object
            Month objCurrentMonth = eaoMonth.getMonthByMonthNumberAndYearNumber(curentMonth, curentYear);
            //SpentHolidayEAO eaoSpentHoliday = new SpentHolidayEAO(SystemModel.getDefaultEM());

            SpentHolidayEAO eaoSpentHoliday = new SpentHolidayEAO(SystemModel.getDefaultEM());
            
            double spentHolidayDayInCurrentMont = eaoSpentHoliday.getSpentHolidayByWorkerAndMonthAndYear(curentWorker, curentMonth, curentYear);

            HolidayEAO eaoHoliday = new HolidayEAO(SystemModel.getDefaultEM());

            List<DaysOfWork> lstDaysOfWork = eaoDaysOfWork.getLastDataForWorkerAndMonthNum(curentWorker, curentMonth);
            if (!lstDaysOfWork.isEmpty())
            {
            int dayPassed = lstDaysOfWork.get(0).getAktualWorkedDays();
            double workLogTime = lstDaysOfWork.get(0).getWorklog();
            
            double curentSumBonus = eaoDaysOfWork.getSumBonusTimeForWorkerAndMonthNum(curentWorker, curentMonth);
            // to plane
            double ToPlane = WorkLogUtils.getTimeRemainsToPlaneToDay(dayPassed, workLogTime, curentSumBonus, spentHolidayDayInCurrentMont);
            // get time to bonus
            double ToBonus = WorkLogUtils.getTimeRemainsToBonusToDay(dayPassed, workLogTime, curentSumBonus, spentHolidayDayInCurrentMont);
            // get rest of the holiday
            double holiday = eaoHoliday.getHolidayDaysCountForWorkerAndYear(curentWorker, curentYear);
            // get spend holiday
            double timeLeft = eaoSpentHoliday.getSpentHolidayWorkerAndYear(curentWorker, curentYear);
            // get result of the work
            boolean beInPlane = WorkLogUtils.beInPlaneAtTime(dayPassed, workingDaysInMonth, ToPlane);
            // get worklog time
            String roundWorkLogTime = WorkLogUtils.roundToString(workLogTime, 2, BigDecimal.ROUND_HALF_UP);
            objResult = new WorkLogInfoHolder(roundWorkLogTime, ToPlane, ToBonus, (holiday - timeLeft), lstDaysOfWork.get(0).getTimestamp(), curentWorker.toString(), beInPlane);
            }
            else
            {
                Log.debug(WorkLogInfoHelper.class, "We have no DaysOfWork data for worker \"" +  curentWorker.getFirstName() + " " + curentWorker.getSecondName() 
                        + "\" and month #" + workingDaysInMonth + ".");
            }
        }

        return objResult;
    }

    public static List<WorkLogInfoHolder> getLogListUpToDate(Date date)
    {
        List<WorkLogInfoHolder> lstResult = new ArrayList<WorkLogInfoHolder>();
        // create set of EAOs
        DaysOfWorkEAO eaoDaysOfWork = new DaysOfWorkEAO(SystemModel.getDefaultEM());
        WorkerEAO eaoWorker = new WorkerEAO(SystemModel.getDefaultEM());
        MonthEAO eaoMonth = new MonthEAO(SystemModel.getDefaultEM());
        SpentHolidayEAO eaoSpentHoliday = new SpentHolidayEAO(SystemModel.getDefaultEM());
        HolidayEAO eaoHoliday = new HolidayEAO(SystemModel.getDefaultEM());

        // for all workers
        for (Worker curentWorker : eaoWorker.loadAll())
        {
            // tey to get last knows data
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
                lstResult.add(new WorkLogInfoHolder(WorkLogUtils.roundToString(workLogTime, 2, BigDecimal.ROUND_HALF_UP),
                        ToPlane, 
                        ToBonus, 
                        (holiday - timeLeft), dfwLastDaysOfWorl.getTimestamp(),
                        curentWorker.toString(), beInPlane));
            }
            else
            {
                Log.error(WorkLogInfoHelper.class, "We found more than 1 record with last DaysOfWork for \"" + curentWorker + "\" and " + DateUtils.toGMT(date) );
            }
        }
        return lstResult;

    }
}
    
