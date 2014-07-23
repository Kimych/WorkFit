package by.uniterra.dai.eao;

import javax.persistence.EntityManager;

import by.uniterra.dai.entity.Worker;

public class WorkerService extends ServiceBase<Worker>
{
	/**
	 * 
	 * Constructor.
	 *
	 * @param em
	 *            - EntityManager to be used in the class
	 */
	public WorkerService(EntityManager em)
	{
		super(em, Worker.class);
	}

}
