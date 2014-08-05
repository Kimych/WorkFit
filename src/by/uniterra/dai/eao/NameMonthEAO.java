package by.uniterra.dai.eao;

import javax.persistence.EntityManager;

import by.uniterra.dai.entity.NameMonth;

public class NameMonthEAO extends ServiceBaseEAO<NameMonth>
{
	/**
	 * 
	 * Constructor.
	 *
	 * @param em
	 *            - EntityManager to be used in the class
	 */
	public NameMonthEAO(EntityManager em)
	{
		super(em, NameMonth.class);
	}

}
