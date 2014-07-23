package by.uniterra.dai.eao;

import javax.persistence.EntityManager;

import by.uniterra.dai.entity.Year;

public class YearService extends ServiceBase<Year>
{
	/**
	 * 
	 * Constructor.
	 *
	 * @param em
	 *            - EntityManager to be used in the class
	 */
	public YearService(EntityManager em)
	{
		super(em, Year.class);
	}

}
