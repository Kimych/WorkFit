package by.uniterra.dai.eao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import by.uniterra.dai.entity.Holiday;
import by.uniterra.dai.entity.Worker;
import by.uniterra.udi.util.Log;

public class HolidayEAO extends ServiceBaseEAO<Holiday>
{
    /**
     * 
     * Constructor.
     *
     * @param em
     *            - EntityManager to be used in the class
     */
    public HolidayEAO(EntityManager em)
    {
        super(em, Holiday.class);
    }

    /**
     * Get the number of holiday days
     * 
     * @param wWorker
     *            - Worker to search data for
     * @param mYear
     *            - Year to search data for
     * @return - the number of holiday days
     *
     * @author Sergio Alecky
     * @date Aug 22, 2014
     */
    public int getHolidayDaysCountForWorkerAndYear(Worker wWorker, int year)
    {
        int holiday = 0;
        try
        {
            Query queryDeleteByDSId = getNamedQuery(Holiday.NQ_FIND_HOLIDAY_BY_WORKER_AND_YEAR);
            // set Worker
            queryDeleteByDSId.setParameter(Holiday.PARAMETER_WORKER, wWorker);
            // set Month
            queryDeleteByDSId.setParameter(Holiday.PARAMETER_YEAR, year);
            // execute and return result
            Object objResult = queryDeleteByDSId.getSingleResult();
            if (objResult != null)
            {
                holiday = (int) objResult;
            }

        }
        catch (Exception e)
        {
            Log.error(this, e, "namedQuery getHolidayDaysCountForWorkerAndYear error");
        }

        return holiday;
    }

}
