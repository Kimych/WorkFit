package by.uniterra.dai.eao;

import javax.persistence.EntityManager;

import by.uniterra.dai.entity.Month;

public class MonthEAO extends ServiceBaseEAO<Month>
{
	/**
	 * 
	 * Constructor.
	 *
	 * @param em
	 *            - EntityManager to be used in the class
	 */
	public MonthEAO(EntityManager em)
	{
		super(em, Month.class);
	}

}
