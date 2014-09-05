package by.uniterra.dai;

import static org.junit.Assert.fail;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import by.uniterra.dai.eao.AuthorizationEAO;
import by.uniterra.dai.eao.RoleEAO;
import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.entity.Role;

public class AuthorizationToRoleEntitiesTest
{

    private static final String TEST_LOGIN_1 = "user1";
    private static final String TEST_LOGIN_2 = "user2";
    private static final String TEST_LOGIN_3 = "user3";
    private static final String TEST_PASSWORD = "password";
    private static final String TEST_ROLE_1 = "role1";
    private static final String TEST_ROLE_2 = "role2";

    @After
    public void tearDown() throws Exception
    {
        ServiceBaseEAO.disconnectFromDb();
    }

    @Test
    public void AuttorisationTest()
    {

        // create two roles
        Role role1 = new Role();
        role1.setRoleId(34);
        role1.setName(TEST_ROLE_1);

        Role role2 = new Role();
        role2.setRoleId(35);
        role2.setName(TEST_ROLE_2);

        List<Role> lstRole1 = new ArrayList<Role>();
        lstRole1.add(role1);

        List<Role> lstRole2 = new ArrayList<Role>();
        lstRole2.add(role2);

        List<Role> lstAllRole = new ArrayList<Role>();
        lstAllRole.add(role1);
        lstAllRole.add(role2);

        RoleEAO eaoRole = new RoleEAO(ServiceBaseEAO.getDefaultEM());
        try
        {
            role1 = eaoRole.save(role1);
            role2 = eaoRole.save(role2);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }

        // create user
        Authorization user1 = new Authorization();
        Authorization user2 = new Authorization();
        Authorization user3 = new Authorization();

        user1.setLogin(TEST_LOGIN_1);
        user1.setPassword(TEST_PASSWORD);
        // link first login with both roles
        user1.setRoles(lstAllRole);

        user2.setLogin(TEST_LOGIN_2);
        user2.setPassword(TEST_PASSWORD);
        // link second login with first role only;
        user2.setRoles(lstRole1);

        user3.setLogin(TEST_LOGIN_3);
        user3.setPassword(TEST_PASSWORD);
        // link third login with second role only;
        user3.setRoles(lstRole2);

        AuthorizationEAO eaoAuth = new AuthorizationEAO(ServiceBaseEAO.getDefaultEM());
        try
        {
            user1 = eaoAuth.save(user1);
            user2 = eaoAuth.save(user2);
            user3 = eaoAuth.save(user3);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }

        List<Authorization> lstAut1 = eaoAuth.getAllRoleByLogin(TEST_LOGIN_1);
        List<Authorization> lstAut2 = eaoAuth.getAllRoleByLogin(TEST_LOGIN_2);
        List<Authorization> lstAut3 = eaoAuth.getAllRoleByLogin(TEST_LOGIN_3);

        assertTrue((lstAut1.get(0).getRoles().get(0).getName() == TEST_ROLE_1) && (lstAut1.get(0).getRoles().get(1).getName() == TEST_ROLE_2)
                && (lstAut2.get(0).getRoles().get(0).getName() == TEST_ROLE_1) && (lstAut3.get(0).getRoles().get(0).getName() == TEST_ROLE_2));

        try
        {
            eaoAuth.delete(user1);
            eaoAuth.delete(user2);
            eaoAuth.delete(user3);

            eaoRole.delete(role1);
            eaoRole.delete(role2);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }
}
