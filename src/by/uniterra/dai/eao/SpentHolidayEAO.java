package by.uniterra.dai.eao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import by.uniterra.dai.entity.SpentHoliday;
import by.uniterra.dai.entity.Worker;
import by.uniterra.udi.util.Log;

public class SpentHolidayEAO extends ServiceBaseEAO<SpentHoliday>
{
    /**
     * 
     * Constructor.
     *
     * @param em
     *            - EntityManager to be used in the class
     */
    public SpentHolidayEAO(EntityManager em)
    {
        super(em, SpentHoliday.class);
    }

    public long getSpentHolidayWorkerAndYear(Worker wWorker, int numberYear)
    {
        long spHoliday = 0;
        try
        {
            Query queryDeleteByDSId = getNamedQuery(SpentHoliday.NQ_FIND_SPEND_HOLIDAY_BY_WORKER_AND_YEAR);
            // set Worker
            queryDeleteByDSId.setParameter(SpentHoliday.PARAMETER_WORKER, wWorker);
            // set Month
            queryDeleteByDSId.setParameter(SpentHoliday.PARAMETER_YEAR, numberYear);
            // execute and return result
            Object objResult = queryDeleteByDSId.getSingleResult();
            if (objResult != null)
            {
                spHoliday = (long) objResult;
            }

        }
        catch (Exception e)
        {
            Log.error(this, e, "namedQuery getSpentHolidayWorkerAndYear error");
        }

        return spHoliday;
    }

    public int getSpentHolidayByWorkerAndMonthAndYear(Worker wWorker, int nMonth, int nYear)
    {
        int dResult = 0;
        try
        {
            // get according query
            Query queryDeleteByDSId = getNamedQuery(SpentHoliday.NQ_FIND_SPEND_HOLIDAY_BY_WORKER_AND_CURRENT_MONTH_AND_EAR);
            // set Worker
            queryDeleteByDSId.setParameter(SpentHoliday.PARAMETER_WORKER, wWorker);
            // set Month
            queryDeleteByDSId.setParameter(SpentHoliday.PARAMETER_MONTH, nMonth);
            queryDeleteByDSId.setParameter(SpentHoliday.PARAMETER_YEAR, nYear);
            // execute and return result
            Object objResult = queryDeleteByDSId.getSingleResult();
            if (objResult != null)
            {
                dResult = (int) objResult ;
            }
        }
        catch(NoResultException ex)
        {
            // just ignore no result cases
            Log.debug(this, "We have not spend holiday for worker \"" + wWorker + "\" and nMonth=" + nMonth + " and nYear=" + nYear);
        }
        catch (Exception e)
        {
            Log.error(this, e, "namedQuery getSpentHolidayByWorkerAndMonth error");
        }
        return dResult;

    }

}
