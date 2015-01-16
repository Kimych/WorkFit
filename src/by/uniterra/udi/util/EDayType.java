package by.uniterra.udi.util;

import by.uniterra.udi.model.UDIPropSingleton;
import by.uniterra.udi.view.MonthSpecialCalendar;

public enum EDayType
{
    //0
    WORKING_DAY,
    //1
    DAY_OFF,
    //2
    BIRTHDAY;

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
