package by.uniterra.udi.util;

import java.util.ArrayList;
import java.util.List;

import by.uniterra.dai.entity.DaysOfWork;

public class LogParser
{
    private final static String SEPARATOR_TO_ALIAS = "Hours of ";
    private final static String SEPARATOR_TO_HOURS = " = ";
    private final static int ALIAS_LOG_START_POSITION = 9;
    private final static int HOURS_LOG__POSITION = 3;

    public static List<DaysOfWork> getListFromLog(List<String> arrayListFromLog)
    {
        for (int i = 0; i < arrayListFromLog.size(); i++)
        {
            String parseString = arrayListFromLog.get(i);

            int aliasStartPosition = parseString.indexOf(SEPARATOR_TO_ALIAS);
            if (aliasStartPosition != -1)
            {
                int aliasLogEndPosition = parseString.indexOf(SEPARATOR_TO_HOURS);
                // get alias
                String aliasName = parseString.substring(ALIAS_LOG_START_POSITION, aliasLogEndPosition);
                // get hour
                double hours = Double.valueOf(parseString.substring(aliasLogEndPosition + HOURS_LOG__POSITION, parseString.length()));

                System.out.println(aliasName);
                System.out.println(hours);

            }

        }

        return null;
    }

}
