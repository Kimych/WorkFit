package by.uniterra.dai.eao;

import javax.persistence.EntityManager;
import javax.persistence.Query;


import by.uniterra.dai.entity.SpentHoliday;
import by.uniterra.dai.entity.Worker;

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
            System.out.println("ggetSpentHolidayWorkerAndYear ERROR!!!");
            e.printStackTrace();
        }

        return spHoliday;
    }

}
