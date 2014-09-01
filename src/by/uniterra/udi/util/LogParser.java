package by.uniterra.udi.util;

import java.util.List;

import by.uniterra.dai.entity.DaysOfWork;

public class LogParser
{
    private final static String SEPARATOR_TO_ALIAS = "Hours of ";
    private final static String SEPARATOR_TO_HOURS = " = ";

    public static List<DaysOfWork> getListFromLog(List<String> arrayListFromLog)
    {
	for (String parseString : arrayListFromLog)
	{
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
            }
	}
        return null;
    }

}
