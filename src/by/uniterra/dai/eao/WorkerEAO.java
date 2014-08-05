package by.uniterra.dai.eao;

import javax.persistence.EntityManager;

import by.uniterra.dai.entity.Worker;

public class WorkerEAO extends ServiceBaseEAO<Worker>
{
	/**
	 * 
	 * Constructor.
	 *
	 * @param em
	 *            - EntityManager to be used in the class
	 */
	public WorkerEAO(EntityManager em)
	{
		super(em, Worker.class);
	}

}
