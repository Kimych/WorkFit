package by.uniterra.dai.eao;

import javax.persistence.EntityManager;

import by.uniterra.dai.entity.DaysOfWork;

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

}
