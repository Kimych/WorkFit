package by.uniterra.dai.eao;

import javax.persistence.EntityManager;

import by.uniterra.dai.entity.Month;


public class MonthService extends ServiceBase<Month>
{
	/**
	 * 
	 * Constructor.
	 *
	 * @param em
	 *            - EntityManager to be used in the class
	 */
	public MonthService(EntityManager em)
	{
		super(em, Month.class);
	}

}
