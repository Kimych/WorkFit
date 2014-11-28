package by.uniterra.dai.eao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import by.uniterra.dai.entity.CalendarSpecialDay;
import by.uniterra.udi.util.Log;

public class CalendarSpecialDayEAO extends ServiceBaseEAO<CalendarSpecialDay>
{

    public CalendarSpecialDayEAO(EntityManager em)
    {
        super(em, CalendarSpecialDay.class);
    }

    /**
     * Returns List<CalendarSpecialDay> are between two dates
     * 
     * @param dateStart - start Date
     * @param dateFinish - finish date
     * @return -List<CalendarSpecialDay> 
     *
     * @author Sergio Alecky
     * @date 27 нояб. 2014 г.
     */
    public List<CalendarSpecialDay> getCSpecialDayBetweenTwoDate(Date dateStart, Date dateFinish)
    {
        List<CalendarSpecialDay> lstResult = null;
        
        try
        {
            Query queryDeleteByDSId = getNamedQuery(CalendarSpecialDay.NQ_FIND_ALL_BETWEEN_TWO_DATE);
            queryDeleteByDSId.setParameter(CalendarSpecialDay.PARAMETER_START_DATE, dateStart);
            queryDeleteByDSId.setParameter(CalendarSpecialDay.PARAMETER_FINISH_DATE, dateFinish);
            
            lstResult = (List<CalendarSpecialDay>)queryDeleteByDSId.getResultList();
            
        }
        catch (Exception e)
        {
           Log.error(CalendarSpecialDayEAO.class, e, " getCSpecialDayBetweenDate namedQuery error");
        }
        
        return lstResult;
        
    }
}
