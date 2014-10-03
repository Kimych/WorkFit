package by.uniterra.dai.eao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import by.uniterra.dai.entity.Month;
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

    public double getSpentHolidayByWorkerAndMonth(Worker wWorker, Month mMonth)
    {
        double lstResult = 0;
        try
        {
            // get according query
            Query queryDeleteByDSId = getNamedQuery(SpentHoliday.NQ_FIND_SPEND_HOLIDAY_BY_WORKER_AND_CURRENT_MONTH);
            // set Worker
            queryDeleteByDSId.setParameter(SpentHoliday.PARAMETER_WORKER, wWorker);
            // set Month
            queryDeleteByDSId.setParameter(SpentHoliday.PARAMETER_MONTH, mMonth);
            // execute and return result
            Object objResult = queryDeleteByDSId.getSingleResult();
            if (objResult != null)
            {
                lstResult = (Double)(objResult);
            }
        }
        catch (Exception e)
        {
            Log.error(this, e, "namedQuery getSpentHolidayByWorkerAndMonth error");
        }

        System.out.println(lstResult);
        return lstResult;

    }

}
