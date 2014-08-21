package by.uniterra.dai.eao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.dai.entity.Month;
import by.uniterra.dai.entity.Worker;

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
     * @param wWorker - Worker to search data for
     * @param mMonth - Month to search data for
     * @return - latest known timestamp if succeed, null otherwise
     *
     * @author Anton Nedbailo
     * @date Aug 20, 2014
     */
    public List<DaysOfWork> getLastDataForWorkerAndMonth(Worker wWorker, int namedMonthId)
    {
	List<DaysOfWork> lstResult = null;
	try
	{
	 // get according query
	    Query queryDeleteByDSId = getNamedQuery(DaysOfWork.NQ_FINDLAST_BY_WORKER_AND_MONTH);
	    // set Worker
	    queryDeleteByDSId.setParameter(DaysOfWork.PARAMETER_WORKER, wWorker);
	    // set Month
	    queryDeleteByDSId.setParameter(DaysOfWork.PARAMETER_MONTH, namedMonthId);
	    // execute and return result
	    lstResult = (List<DaysOfWork>) queryDeleteByDSId.getResultList();
	} catch (Exception e)
	{
	    System.out.println("getLastDataForWorkerAndMonth error ");
	    e.printStackTrace();
	}
	// check for null result
	return (List<DaysOfWork>) (lstResult != null ? lstResult : Collections.emptyList());
    }
}
