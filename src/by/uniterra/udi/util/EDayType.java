package by.uniterra.udi.util;

import java.util.ArrayList;
import java.util.List;

import by.uniterra.udi.model.UDIPropSingleton;
import by.uniterra.udi.view.MonthSpecialCalendar;

public enum EDayType
{

    WORKING_DAY, // 0
    DAY_OFF, // 1
    BIRTHDAY; // 2

/*    public static List<EDayType> fromInteger(int intDayType)
    {
        List<EDayType> lstResult = new ArrayList<EDayType>();
        for (EDayType edt : EDayType.values())
        {
            if ((intDayType & (int) Math.pow(2, edt.ordinal())) > 0)
            {
                lstResult.add(edt);
            }
        }
        return lstResult;
    }*/
    

    public String toString()
    {
        String strResult = "default";
        switch (this)
        {
        case WORKING_DAY:
            strResult = UDIPropSingleton.getString(MonthSpecialCalendar.class, "WrkDay.label");
            break;
        case DAY_OFF:
            strResult = UDIPropSingleton.getString(MonthSpecialCalendar.class, "DayOff.label");
            break;
        case BIRTHDAY:
            strResult = UDIPropSingleton.getString(MonthSpecialCalendar.class, "Birthday.lqbel");
            break;
        }
        return strResult;
    }

}
