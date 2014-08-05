package by.uniterra.dai.eao;

import javax.persistence.EntityManager;

import by.uniterra.dai.entity.SpentHoliday;

public class SpentHolidayService extends ServiceBaseEAO<SpentHoliday>
{
	/**
	 * 
	 * Constructor.
	 *
	 * @param em
	 *            - EntityManager to be used in the class
	 */
	public SpentHolidayService(EntityManager em)
	{
		super(em, SpentHoliday.class);
	}

}
