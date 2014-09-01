package by.uniterra.udi.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.dai.entity.Month;
import by.uniterra.dai.entity.Worker;
import by.uniterra.system.util.WorkLogUtils;

public class LogParser
{
    private final static String SEPARATOR_TO_ALIAS = "Hours of ";
    private final static String SEPARATOR_TO_HOURS = " = ";
    private final static String SEPARATOR_TO_DATE = "startdate = ";

    private static List<Worker> workerArrayList;
    private static List<Month> monthArrayList;

    public static void main(String[] args)
    {
        getListFromLog(Paths.get("C:\\Users\\Comp\\Documents\\worklog.txt"));

    }

    public static List<DaysOfWork> getListFromLog(Path path)
    {

        List<String> lstOriginalData = new ArrayList<String>();
        HashMap<String, Double> mapAliasHours = new HashMap<String, Double>();
        List<DaysOfWork> daysOfWorkList;
        Month month = null;
        Date date = null;

        try
        {
            lstOriginalData.addAll(Files.readAllLines(path, StandardCharsets.UTF_8));
        }
        catch (IOException e)
        {
            System.out.println("readAllLInes ERROR!");
            // Log.error(this, e, "createFileChooser error ");
        }

        for (String parseString : lstOriginalData)
        {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

            if (parseString.contains(SEPARATOR_TO_ALIAS))
            {
                int iAliasPos = parseString.indexOf(SEPARATOR_TO_ALIAS) + SEPARATOR_TO_ALIAS.length();
                int iSeparatorPos = parseString.indexOf(SEPARATOR_TO_HOURS);

                // get alias
                String aliasName = parseString.substring(iAliasPos, iSeparatorPos).trim();
                System.out.println("\"" + aliasName + "\"");

                // get hour
                double hours = Double.parseDouble(parseString.substring(iSeparatorPos + SEPARATOR_TO_HOURS.length()));
                System.out.println(hours);

                // add data in a Map
                mapAliasHours.put(aliasName, hours);

            }
            else if (parseString.contains(SEPARATOR_TO_DATE))
            {
                int iDatePos = parseString.indexOf(SEPARATOR_TO_DATE) + SEPARATOR_TO_DATE.length();
                String strDate = parseString.substring(iDatePos);

                System.out.println(strDate);

                try
                {
                    date = formatter.parse(strDate);
                }
                catch (ParseException e)
                {
                    System.out.println("Date parse ERROR!");
                    // Log.error(this, e, "getListFromLog error ");
                }
                break;
            }
        }
        // *******
        System.out.println("Map size: " + mapAliasHours.size());

        // get actual worked days
        int actualWorkedDays = WorkLogUtils.getWorkingDaysBetweenTwoDates(WorkLogUtils.getDateCurentMonthStart(), new Date());
        // ***********
        System.out.println(actualWorkedDays);

        // get month object
        int curentMonth = YearMonth.now(Clock.systemUTC()).getMonthValue();
        int numberYear = YearMonth.now(Clock.systemUTC()).getYear();
        monthArrayList = new MonthEAO(ServiceBaseEAO.getDefaultEM()).loadAll();
        for (Month selmonth : monthArrayList)
        {
            if (selmonth.getNameMonth().getNameMonthId() == curentMonth && selmonth.getYear().getNumber() == numberYear)
            {
                month = (Month) selmonth;
                // ***********
                System.out.println(month);
                break;
            }
        }

        // create empty List<DaysOfWork>
        daysOfWorkList = new ArrayList<DaysOfWork>();
        workerArrayList = new WorkerEAO(ServiceBaseEAO.getDefaultEM()).loadAll();
        
 
        
        for (Map.Entry hmAliasHours : mapAliasHours.entrySet())
        {

            for (Worker currentWorker : workerArrayList)
            {
                System.out.println("-------------------------------------");
                if (currentWorker.getAlias() == (String)hmAliasHours.getKey())
                {
                    //*********************************
                    System.out.println("+++++++++++++++++++++++++++++++++++++");
                    DaysOfWork dof = new DaysOfWork();

                    dof.setWorklog((double) hmAliasHours.getValue());
                    dof.setTimestamp(date);
                    dof.setBonusTime(0);
                    dof.setBonusTimeDescription(null);
                    dof.setAktualWorkedDays(actualWorkedDays);
                    dof.setMonth(month);
                    dof.setWorker(currentWorker);

                    System.out.println("Result" + dof);

                }
            }

        }

        return null;
    }

}
