package by.uniterra.dai.eao;


import java.util.Collections;
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
     * Get list of DETACHED special days for given year
     *  
     * @param yearNumber - year number
     * 
     * @return list of detached special days for given year
     *
     * @author Anton Nedbailo
     * @date Dec 12, 2014
     */
    public List<CalendarSpecialDay> getSpecialDayByYear(int yearNumber)
    {
        return getSpecialDayByYear(yearNumber, true);
    }
    
    /**
     * Get list of special days for given year
     * 
     * @param yearNumber - year number
     * @param bReturnDetachedObjects - if true: result list will be detached from according EM
     * 
     * @return list of special days for given year
     *
     * @author Anton Nedbailo
     * @date Dec 12, 2014
     */
    public List<CalendarSpecialDay> getSpecialDayByYear(int yearNumber, boolean bReturnDetachedObjects)
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
            // check "detach" flag and detach objects form associated EM
            for (int i = 0; bReturnDetachedObjects && i < lstResult.size(); i++)
            {
                emEntityManager.detach(lstResult.get(i));
            }
        }
        catch (Exception e)
        {
            Log.error(this, e, "NamedQuery getSpecialDayByYear error");
        }
        return (List<CalendarSpecialDay>) (lstResult != null ? lstResult : Collections.emptyList());
    }

}
