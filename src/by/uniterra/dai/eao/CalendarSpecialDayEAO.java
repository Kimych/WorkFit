package by.uniterra.dai.eao;


import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import by.uniterra.dai.entity.CalendarSpecialDay;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.udi.util.EDayType;
import by.uniterra.udi.util.Log;

public class CalendarSpecialDayEAO extends ServiceBaseEAO<CalendarSpecialDay>
{

    public CalendarSpecialDayEAO(EntityManager em)
    {
        super(em, CalendarSpecialDay.class);
    }
    
    public List<CalendarSpecialDay> getSpecialDayByYear(int yearNumber)
    {
        List<CalendarSpecialDay> lstResult = null;
        try
        {
            Query queryDeleteByDSId = getNamedQuery(CalendarSpecialDay.NQ_FIND_SPECIAL_DAYS_BY_YEAR);
            // set Worker
            queryDeleteByDSId.setParameter(CalendarSpecialDay.PARAMETER_YEAR_NUMBER, yearNumber);
            // set Month
            // execute and return result
            lstResult = (List<CalendarSpecialDay>) queryDeleteByDSId.getResultList();
        }
        catch (Exception e)
        {
            Log.error(this, e, "NamedQuery getSpecialDayByYear error");
        }
        return (List<CalendarSpecialDay>) (lstResult != null ? lstResult : Collections.emptyList());
    }

}
