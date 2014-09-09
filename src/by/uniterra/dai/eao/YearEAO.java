package by.uniterra.dai.eao;

import javax.persistence.EntityManager;

import by.uniterra.dai.entity.Year;

public class YearEAO extends ServiceBaseEAO<Year>
{
    /**
     * 
     * Constructor.
     *
     * @param em
     *            - EntityManager to be used in the class
     */
    public YearEAO(EntityManager em)
    {
        super(em, Year.class);
    }

}
