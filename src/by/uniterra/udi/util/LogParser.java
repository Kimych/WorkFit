package by.uniterra.udi.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.dai.entity.Month;
import by.uniterra.dai.entity.Worker;
import by.uniterra.system.main.MailChecker;
import by.uniterra.system.model.SystemModel;
import by.uniterra.system.util.DateUtils;
import by.uniterra.system.util.WorkLogUtils;

public class LogParser
{
    private final static String SEPARATOR_TO_ALIAS = "Hours of ";
    private final static String SEPARATOR_TO_HOURS = " = ";
    private final static String SEPARATOR_TO_DATE = "startdate = ";
    private final static String DATE_FORMAT_FROM_LOG = "yyyy-MM-dd HH:mm:ss";

    public static List<DaysOfWork> getListFromLog(Path path)
    {
        List<DaysOfWork> daysOfWorkList = new ArrayList<DaysOfWork>();
        List<String> lstOriginalData = new ArrayList<String>();
        Map<String, Double> mapAliasHours = new HashMap<String, Double>();
        try (FileInputStream input = new FileInputStream(path.toFile());)
        {
            CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
            decoder.onMalformedInput(CodingErrorAction.IGNORE);
            InputStreamReader reader = new InputStreamReader(input, decoder);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            while (line != null)
            {
                lstOriginalData.add(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception e)
        {
           Log.error(LogParser.class, e);
        }

        Date dParsedDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_FROM_LOG);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        for (String parseString : lstOriginalData)
        {
            if (parseString.contains(SEPARATOR_TO_ALIAS))
            {
                try
                {
                    int iAliasPos = parseString.indexOf(SEPARATOR_TO_ALIAS) + SEPARATOR_TO_ALIAS.length();
                    int iSeparatorPos = parseString.indexOf(SEPARATOR_TO_HOURS);
                    // get alias
                    String aliasName = parseString.substring(iAliasPos, iSeparatorPos).trim();
                    // get hours string
                    String strHours = parseString.substring(iSeparatorPos + SEPARATOR_TO_HOURS.length());
                    // get hour
                    double hours = strHours.indexOf("null") != -1 ? 0 : Double.parseDouble(strHours);
                    // add data in a Map
                    mapAliasHours.put(aliasName, hours);
                }
                catch(Exception e)
                {
                    Log.error(LogParser.class, e);
                }
            }
            else if (parseString.contains(SEPARATOR_TO_DATE))
            {
                try
                {
                    dParsedDate = formatter.parse(parseString.substring(parseString.indexOf(SEPARATOR_TO_DATE) + SEPARATOR_TO_DATE.length()));
                }
                catch (ParseException e)
                {
                    Log.error(LogParser.class, e, "date parse error");
                }
                break;
            }
        }

        if (dParsedDate != null && !mapAliasHours.isEmpty())
        {
            Date curentMonthStart = WorkLogUtils.getDateCurentMonthStart(dParsedDate);
            //Date curenDate = new Date();
            // get actual worked days
            int actualWorkedDays = WorkLogUtils.getWorkingDaysBetweenTwoDates(curentMonthStart, dParsedDate);
            
            Log.info(LogParser.class, "getListFromLog: Curent month start at: " + DateUtils.toGMT(curentMonthStart) 
                    + ", worklog date is " + DateUtils.toGMT(dParsedDate) + ", ActualWorkedDays: " +  actualWorkedDays);
            
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
                        dof.setTimestamp(dParsedDate);
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

    /**
     * 
     * FIXME we should return "true" only in case of all values in the list successfully inserted. If any item failed to save - we should rollback others. 
     * @param lstDoWfromLog
     * @return
     *
     * @author Sergio Alecky
     * @date 06 нояб. 2014 г.
     */
    public static boolean saveLogInfoToDB(List<DaysOfWork> lstDoWfromLog)
    {
        boolean bResult = false;
        // check whether there is a record with the Timestamp of the log
        DaysOfWorkEAO dofEAO = new DaysOfWorkEAO(SystemModel.getDefaultEM());
        try
        {
            for (DaysOfWork dow : lstDoWfromLog)
            {
                if (dofEAO.getCountForTimestamp(dow.getTimestamp(), dow.getWorker()) == 0)
                {
                    dofEAO.save(dow);
                    Log.info(LogParser.class,
                            "New worklog data inserted for \"" + dow.getWorker()+ "\" with timestamp " + DateUtils.toGMT(dow.getTimestamp()));
                } else
                {
                    Log.warning(LogParser.class, "Detected attempt to insert existing worklog data (for worker \"" + dow.getWorker() + "\" with date "
                            + DateUtils.toGMT(dow.getTimestamp()) + "). ");
                }
            }
            bResult = true;
        } catch (Exception e)
        {
            Log.error(LogParser.class, e, "save info from log to DB problems");
        }
        return bResult;

    }



    public static Path createDestinationPath(Path pathBaseDestDir, String strDestFolder, Date dDateFromFile)
    {
        // create dest directory name
        String strDestDir = pathBaseDestDir.toString() + File.separatorChar + strDestFolder + File.separatorChar + DateUtils.getYearNumber(dDateFromFile) + File.separatorChar +DateUtils.getMonthNumber(dDateFromFile) + File.separatorChar;
        File fileDestDir = new File(strDestDir);
        // check if the directory exists
        if(!(fileDestDir.exists()))
        {
            fileDestDir.mkdirs();
        }
        // add file name
        return Paths.get(strDestDir + DateUtils.toUTCString(dDateFromFile.getTime(), DateUtils.FILENAME_DATETIMEFORMAT) + ".txt");
    }
    
    public static boolean processWorklogFile(Path path)
    {
        boolean bResult = false;
        // 1 try to parse given file
        List<DaysOfWork> lstWorklogDtaa = LogParser.getListFromLog(path);
        if (!lstWorklogDtaa.isEmpty())
        {
            try
            {
                // 2 insert parsed data into DB
                if (LogParser.saveLogInfoToDB(lstWorklogDtaa))
                {
                    // 3 save into archive with renaming
                    Files.copy(path, 
                            LogParser.createDestinationPath(Paths.get(new File("").getAbsolutePath()), "WorkLogStorage", lstWorklogDtaa.get(0).getTimestamp()),
                            StandardCopyOption.REPLACE_EXISTING);
                }
                bResult = true;
            }
            catch ( Exception e)
            {
                Log.error(MailChecker.class, e, "createFileFromMail error ");
            }
        }
        return bResult;
    }
}
