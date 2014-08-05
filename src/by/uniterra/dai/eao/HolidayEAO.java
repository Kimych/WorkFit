package by.uniterra.dai.eao;

import javax.persistence.EntityManager;

import by.uniterra.dai.entity.Holiday;

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

}
