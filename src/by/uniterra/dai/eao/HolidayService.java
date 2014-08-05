package by.uniterra.dai.eao;

import javax.persistence.EntityManager;

import by.uniterra.dai.entity.Holiday;

public class HolidayService extends ServiceBaseEAO<Holiday>
{
	/**
	 * 
	 * Constructor.
	 *
	 * @param em
	 *            - EntityManager to be used in the class
	 */
	public HolidayService(EntityManager em)
	{
		super(em, Holiday.class);
	}

}
