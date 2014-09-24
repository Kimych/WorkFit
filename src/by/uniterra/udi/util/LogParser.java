package by.uniterra.udi.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.swing.JOptionPane;

import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.dai.entity.Month;
import by.uniterra.dai.entity.Worker;
import by.uniterra.system.model.SystemModel;
import by.uniterra.system.util.DateUtils;
import by.uniterra.system.util.WorkLogUtils;

public class LogParser
{
    private final static String SEPARATOR_TO_ALIAS = "Hours of ";
    private final static String SEPARATOR_TO_HOURS = " = ";
    private final static String SEPARATOR_TO_DATE = "startdate = ";
    private final static String DATE_FORMAT_FROM_LOG = "yyyy-MM-dd hh:mm:ss";
    private final static String DATE_FORMAT_SQL = "yyyy-MM-dd hh:mm:ss";

    public static void main(String[] args)
    {
        getListFromLog(Paths.get("C:\\Users\\Comp\\Documents\\worklog.txt"));
        // getListFromLog(Paths.get("e:\\Temp\\worklog.txt"));
    }

    public static List<DaysOfWork> getListFromLog(Path path)
    {
        List<DaysOfWork> daysOfWorkList = new ArrayList<DaysOfWork>();
        List<String> lstOriginalData = new ArrayList<String>();
        HashMap<String, Double> mapAliasHours = new HashMap<String, Double>();
        try
        {
            lstOriginalData.addAll(Files.readAllLines(path, StandardCharsets.UTF_8));
        }
        catch (IOException e)
        {
            Log.error(LogParser.class, e, "readAllLInes error");
        }
        Date date = null;
        for (String parseString : lstOriginalData)
        {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_FROM_LOG);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            if (parseString.contains(SEPARATOR_TO_ALIAS))
            {
                int iAliasPos = parseString.indexOf(SEPARATOR_TO_ALIAS) + SEPARATOR_TO_ALIAS.length();
                int iSeparatorPos = parseString.indexOf(SEPARATOR_TO_HOURS);
                // get alias
                String aliasName = parseString.substring(iAliasPos, iSeparatorPos).trim();
                // get hour
                double hours = Double.parseDouble(parseString.substring(iSeparatorPos + SEPARATOR_TO_HOURS.length()));
                // add data in a Map
                mapAliasHours.put(aliasName, hours);
            }
            else if (parseString.contains(SEPARATOR_TO_DATE))
            {
                int iDatePos = parseString.indexOf(SEPARATOR_TO_DATE) + SEPARATOR_TO_DATE.length();
                String strDate = parseString.substring(iDatePos);

                try
                {
                    date = formatter.parse(strDate);
                    Log.info(LogParser.class, "Parsing Log Date " + DateUtils.toGMT(date));
                }
                catch (ParseException e)
                {
                    Log.error(LogParser.class, e, "date parse error");
                }
                break;
            }
        }

        if (date != null && !mapAliasHours.isEmpty())
        {
            // get actual worked days
            int actualWorkedDays = WorkLogUtils.getWorkingDaysBetweenTwoDates(WorkLogUtils.getDateCurentMonthStart(), new Date());
            // get month object
            int curentMonth = YearMonth.now(Clock.systemUTC()).getMonthValue();
            int numberYear = YearMonth.now(Clock.systemUTC()).getYear();
            List<Month> monthArrayList;
            monthArrayList = new MonthEAO(SystemModel.getDefaultEM()).loadAll();
            Month month = null;
            for (Month selmonth : monthArrayList)
            {
                if (selmonth.getNameMonth().getNameMonthId() == curentMonth && selmonth.getYear().getNumber() == numberYear)
                {
                    month = (Month) selmonth;
                    break;
                }
            }
            // create empty List<DaysOfWork>
            daysOfWorkList = new ArrayList<DaysOfWork>();
            List<Worker> workerArrayList;
            workerArrayList = new WorkerEAO(SystemModel.getDefaultEM()).loadAll();

            // add data to workerArrayList
            for (Map.Entry<String, Double> hmAliasHours : mapAliasHours.entrySet())
            {
                for (Worker currentWorker : workerArrayList)
                {
                    if (currentWorker.getAlias().equals(hmAliasHours.getKey()))
                    {
                        DaysOfWork dof = new DaysOfWork();
                        dof.setWorklog((double) hmAliasHours.getValue());
                        dof.setTimestamp(date);
                        dof.setBonusTime(0);
                        dof.setBonusTimeDescription(null);
                        dof.setAktualWorkedDays(actualWorkedDays);
                        dof.setMonth(month);
                        dof.setWorker(currentWorker);
                        // add to List<>
                        daysOfWorkList.add(dof);
                        break;
                    }
                }
            }
        }
        return daysOfWorkList;
    }

    public static void saveLogInfoToDB(List<DaysOfWork> lstDoWfromLog)
    {
        Date dateFromLog = lstDoWfromLog.get(0).getTimestamp();

        // check whether there is a record with the Timestamp of the log
        DaysOfWorkEAO dofEAO = new DaysOfWorkEAO(SystemModel.getDefaultEM());
        long count = dofEAO.getCountForTimestamp(dateFromLog);

        if (count == 0)
        {
            try
            {
                for (DaysOfWork dow : lstDoWfromLog)
                {
                    dofEAO.save(dow);
                    Log.info(LogParser.class, "Data successfully added!");
                }
            }
            catch (Exception e)
            {

                Log.error(LogParser.class, e, "save info from log to DB problems");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"data from the log for: " + DateUtils.toGMT(dateFromLog.getTime()) + " already added!");
            Log.warning(LogParser.class, "attempted to add existing data");
        }

    }
}
