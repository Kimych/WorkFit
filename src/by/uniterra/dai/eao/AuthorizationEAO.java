package by.uniterra.dai.eao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.udi.util.Log;

public class AuthorizationEAO extends ServiceBaseEAO<Authorization>
{

    public AuthorizationEAO(EntityManager em)
    {
        super(em, Authorization.class);
    }

    public List<Authorization> getAuthorizationsByLogin(String login)
    {
        List<Authorization> lstResult = null;
        try
        {
            // get according query
            Query queryDeleteByDSId = getNamedQuery(Authorization.NQ_FIND_AUTHORIZATION_BY_LOGIN);
            // set login
            queryDeleteByDSId.setParameter(Authorization.PARAMETER_LOGIN, login);
            // execute and return result
            lstResult = (List<Authorization>) queryDeleteByDSId.getResultList();
        }
        catch (Exception e)
        {
            Log.error(this, e, "namedQuery getAuthorizationsByLogin error");
        }
        return (List<Authorization>) (lstResult != null ? lstResult : Collections.emptyList());

    }

}
