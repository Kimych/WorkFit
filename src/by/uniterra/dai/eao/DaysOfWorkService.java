package by.uniterra.dai.eao;

import javax.persistence.EntityManager;

import by.uniterra.dai.entity.DaysOfWork;

public class DaysOfWorkService extends ServiceBase<DaysOfWork>
{
	/**
	 * 
	 * Constructor.
	 *
	 * @param em
	 *            - EntityManager to be used in the class
	 */
	public DaysOfWorkService(EntityManager em)
	{
		super(em, DaysOfWork.class);
	}

}
