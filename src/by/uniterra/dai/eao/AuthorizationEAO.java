package by.uniterra.dai.eao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.entity.Role;
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

    public List<Role> getRoleByLoginAndPassword(String login, String password)
    {
        List<Role> lstResult = null;
        try
        {
            Query queryDeleteByDSId = getNamedQuery(Authorization.NQ_FIND_ROLE_BY_LOGIN_AND_PASSWORD);
            queryDeleteByDSId.setParameter(Authorization.PARAMETER_LOGIN, login);
            queryDeleteByDSId.setParameter(Authorization.PARAMETER_PASSWORD, password);

            lstResult = (List<Role>) queryDeleteByDSId.getResultList();
        }
        catch (Exception e)
        {
            Log.error(this, e, "namedQuery getRoleByLoginAndPassword error");
        }

        return (List<Role>) (lstResult != null ? lstResult : Collections.emptyList());
    }

    public List<Authorization> getAuthorizationByLoginAndPassword(String login, String password)
    {
        List<Authorization> lstResult = null;
        try
        {
            Query queryDeleteByDSId = getNamedQuery(Authorization.NQ_FIND_AUTHORIZATION_BY_LOGIN_AND_PASSWORD);
            queryDeleteByDSId.setParameter(Authorization.PARAMETER_LOGIN, login);
            queryDeleteByDSId.setParameter(Authorization.PARAMETER_PASSWORD, password);

            lstResult = (List<Authorization>) queryDeleteByDSId.getResultList();
        }
        catch (Exception e)
        {
            Log.error(this, e, "namedQuery getRoleByLoginAndPassword error");
        }

        return (List<Authorization>) (lstResult != null ? lstResult : Collections.emptyList());
    }

}
