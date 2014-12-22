package by.uniterra.dai.eao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import by.uniterra.dai.entity.Month;
import by.uniterra.udi.util.Log;

public class MonthEAO extends ServiceBaseEAO<Month>
{
    /**
     * 
     * Constructor.
     *
     * @param em
     *            - EntityManager to be used in the class
     */
    public MonthEAO(EntityManager em)
    {
        super(em, Month.class);
    }

    
    public Month getMonthByMonthNumberAndYearNumber(int monthNumber, int yearNumber)
    {
        Month objResult = new Month();
        
        try
        {
            Query queryDeleteByDSId = getNamedQuery(Month.NQ_FIND_MONTH_BY_MONTH_NUMBER_AND_YEAR_NUMBER);
            queryDeleteByDSId.setParameter(Month.PARAMETER_MONTH_NUMBER, monthNumber);
            queryDeleteByDSId.setParameter(Month.PARAMETER_YEAR_NUMBER,yearNumber);

            objResult = (Month) queryDeleteByDSId.getSingleResult();
        }
        catch (Exception e)
        {
            Log.error(this, e, "namedQuery getMonthByMonthNumberAndYearNumber error");
        }
        return (objResult != null ? objResult : new Month());
        
    }

}
