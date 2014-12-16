package by.uniterra.dai.eao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.entity.Year;
import by.uniterra.udi.util.Log;

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

    public Year getYearByYearNum(int dayYearNum)
    {
        Year objResult = null;
        try
        {
            Query queryDeleteByDSId = getNamedQuery(Year.NQ_FIND_YEAR_BY_NUM);
            queryDeleteByDSId.setParameter(Year.PARAMETER_NUM_YEAR, dayYearNum);
            objResult = (Year) queryDeleteByDSId.getSingleResult();
        }
        catch(NoResultException e1)
        {
           //ignore this error
        }
        catch (Exception e)
        {
            Log.error(this, e, "namedQuery getYearByYearNum error");
        }

        return objResult;
    }

}
