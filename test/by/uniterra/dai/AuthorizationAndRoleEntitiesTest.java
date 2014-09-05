package by.uniterra.dai;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Test;

import by.uniterra.dai.eao.AuthorizationEAO;
import by.uniterra.dai.eao.RoleEAO;
import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.entity.Role;

public class AuthorizationAndRoleEntitiesTest
{
    @After
    public void tearDown() throws Exception
    {
        ServiceBaseEAO.disconnectFromDb();
    }

    @Test
    public void roleEntitiesTest()
    {
        Role role = new Role();
        role.setName("1 test role name");
        RoleEAO roleEAO = new RoleEAO(ServiceBaseEAO.getDefaultEM());

        List<Role> lstEntitiesBefore = roleEAO.loadAll();
        try
        {
            role = roleEAO.save(role);
            roleEAO.delete(role);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }

        List<Role> lstEntitiesAfter = roleEAO.loadAll();
        assertTrue(lstEntitiesBefore.size() - lstEntitiesAfter.size() == 0);
    }

    @Test
    public void authorizationTest()
    {
        Authorization aut = new Authorization();
        aut.setLogin("Test Login");
        aut.setPassword("Test Password");

        AuthorizationEAO autEAO = new AuthorizationEAO(ServiceBaseEAO.getDefaultEM());
        List<Authorization> lstEntitiesBefore = autEAO.loadAll();

        try
        {
            aut = autEAO.save(aut);
            autEAO.delete(aut);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }

        List<Authorization> lstEntitiesAfter = autEAO.loadAll();

        assertTrue(lstEntitiesBefore.size() - lstEntitiesAfter.size() == 0);
    }
}
