package by.uniterra.dai.eao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import by.uniterra.dai.entity.Role;
import by.uniterra.udi.util.Log;

public class RoleEAO extends ServiceBaseEAO<Role>
{

    public RoleEAO(EntityManager em)
    {
        super(em, Role.class);
    }

    public List<Role> getRoleByName(String roleName)
    {
        List<Role> lstResult = null;
        try
        {
            // get according query
            Query queryDeleteByDSId = getNamedQuery(Role.NQ_FIND_ROLE_BY_NAME);
            // set login
            queryDeleteByDSId.setParameter(Role.PARAMETER_ROLE_NAME, roleName);
            // execute and return result
            lstResult = (List<Role>) queryDeleteByDSId.getResultList();
        }
        catch (Exception e)
        {
            Log.error(this, e, "getRoleByName");
        }
        return (List<Role>) (lstResult != null ? lstResult : Collections.emptyList());

    }

}
