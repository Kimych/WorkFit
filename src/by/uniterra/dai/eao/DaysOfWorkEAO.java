package by.uniterra.dai.eao;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.dai.entity.Worker;
import by.uniterra.udi.util.Log;

public class DaysOfWorkEAO extends ServiceBaseEAO<DaysOfWork>
{
    /**
     * 
     * Constructor.
     *
     * @param em
     *            - EntityManager to be used in the class
     */
    public DaysOfWorkEAO(EntityManager em)
    {
        super(em, DaysOfWork.class);
    }

    /**
     * Get latest worklog data for given Worker and Month
     * 
     * @param wWorker
     *            - Worker to search data for
     * @param mMonth
     *            - Month to search data for
     * @return - latest known timestamp if succeed, null otherwise
     *
     * @author Anton Nedbailo
     * @date Aug 20, 2014
     */
    @SuppressWarnings("unchecked")
    public List<DaysOfWork> getLastDataForWorkerAndMonthNum(Worker wWorker, int namedMonthId)
    {
        List<DaysOfWork> lstResult = null;
        try
        {
            // get according query
            Query queryDeleteByDSId = getNamedQuery(DaysOfWork.NQ_FIND_LAST_BY_WORKER_AND_NUMBERMONTH);
            // set Worker
            queryDeleteByDSId.setParameter(DaysOfWork.PARAMETER_WORKER, wWorker);
            // set Month
            queryDeleteByDSId.setParameter(DaysOfWork.PARAMETER_MONTH, namedMonthId);
            // execute and return result
            lstResult = (List<DaysOfWork>) queryDeleteByDSId.getResultList();
        }
        catch (Exception e)
        {
            Log.error(this, e, "NamedQuery getLastDataForWorkerAndMonthNum error");
        }
        // check for null result
        return (List<DaysOfWork>) (lstResult != null ? lstResult : Collections.emptyList());
    }

    /**
     * Get all worklog data for given Worker and Month
     * 
     * @param wWorker
     *            - Worker to search data for
     * @param mMonth
     *            - Month to search data for
     * @return - double
     *
     * @author Sergio Alecky
     * @date Aug 22, 2014
     */
    public double getSumBonusTimeForWorkerAndMonthNum(Worker wWorker, int namedMonthId)
    {
        double lstResult = 0;
        try
        {
            // get according query
            Query queryDeleteByDSId = getNamedQuery(DaysOfWork.NQ_FIND_ALL_BY_WORKER_AND_NUMBERMONTH);
            // set Worker
            queryDeleteByDSId.setParameter(DaysOfWork.PARAMETER_WORKER, wWorker);
            // set Month
            queryDeleteByDSId.setParameter(DaysOfWork.PARAMETER_MONTH, namedMonthId);
            // execute and return result
            Object objResult = queryDeleteByDSId.getSingleResult();
            if (objResult != null)
            {
                lstResult = (double) objResult;
            }
        }
        catch (Exception e)
        {
            Log.error(this, e, "namedQuery getSumBonusTimeForWorkerAndMonthNum error");
        }
        return lstResult;
    }
    
    @SuppressWarnings("unchecked")
    public List<DaysOfWork> getLastForWorkerAndTimestamp(Worker wWorker, Timestamp timestamp)
    {
        List<DaysOfWork> lstResult = null;
        
        try
        {
         // get according query
            Query queryDeleteByDSId = getNamedQuery(DaysOfWork.NQ_FIND_LAST_BY_WORKER_AND_TIMESTAMP);
            // set Worker
            queryDeleteByDSId.setParameter(DaysOfWork.PARAMETER_WORKER, wWorker);
            // set Month
            queryDeleteByDSId.setParameter(DaysOfWork.PARAMETER_TIMESTAMP, timestamp);
            // execute and return result
            lstResult = (List<DaysOfWork>) queryDeleteByDSId.getResultList();
        }
        catch (Exception e)
        {
            Log.error(this, e, "namedQuery getfindLastForWorkerAndTimestamp error");
        }
        
        return lstResult;
    
    }
    
    public long getCountForTimestamp(Date timestamp)
    {
        long lstResult = 0;
        try
        {
            // get according query
            Query queryDeleteByDSId = getNamedQuery(DaysOfWork.NQ_GET_COUNT_BY_TIMESTAMP);
            // set Worker
            queryDeleteByDSId.setParameter(DaysOfWork.PARAMETER_TIMESTAMP, timestamp);
            // execute and return result
            Object objResult = queryDeleteByDSId.getSingleResult();
            if (objResult != null)
            {
                lstResult = (long) objResult;
            }
        }
        catch (Exception e)
        {
            Log.error(this, e, "namedQuery getCountForTimestamp error");
        }
        return lstResult;
    }
    
    @SuppressWarnings("unchecked")
    public List<DaysOfWork> finAllByWorker(Worker wWorker)
    {
        List<DaysOfWork> lstResult = null;
        
        try
        {
         // get according query
            Query queryDeleteByDSId = getNamedQuery(DaysOfWork.NQ_FIND_ALL_BY_WORKER_ID);
            // set Worker
            queryDeleteByDSId.setParameter(DaysOfWork.PARAMETER_WORKER, wWorker);
            // execute and return result
            lstResult = (List<DaysOfWork>) queryDeleteByDSId.getResultList();
        }
        catch (Exception e)
        {
            Log.error(this, e, "finAllByWorker error");
        }
        
        return lstResult;
    
    }

    public long getCountForTimestamp(Date timestamp, Worker worker)
    {
        long lstResult = 0;
        try
        {
            // get according query
            Query queryDeleteByDSId = getNamedQuery(DaysOfWork.NQ_GET_COUNT_BY_TIMESTAMP_AND_WORKER);
            // set Worker
            queryDeleteByDSId.setParameter(DaysOfWork.PARAMETER_TIMESTAMP, timestamp);
            queryDeleteByDSId.setParameter(DaysOfWork.PARAMETER_WORKER, worker);
            // execute and return result
            Object objResult = queryDeleteByDSId.getSingleResult();
            if (objResult != null)
            {
                lstResult = (long) objResult;
            }
        }
        catch (Exception e)
        {
            Log.error(this, e, "namedQuery getCountForTimestamp error");
        }
        return lstResult;
    }
}
