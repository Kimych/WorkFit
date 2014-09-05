package by.uniterra.dai;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;

import by.uniterra.dai.eao.AuthorizationEAO;
import by.uniterra.dai.eao.RoleEAO;
import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.entity.Role;

public class AuthorizationEntitiesTest
{

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
        
        
    }
    @Test
    @Ignore
    public void RoleTest()
    {
        
        //create three logins
        Authorization user1 = new Authorization();
        Authorization user2 = new Authorization();
        Authorization user3 = new Authorization();
        
        //link first login with both roles
        user1.setLogin("user_1");
        user1.setPassword("password_1");
        //user1.setRoles(lstAllRoles);
        
        //link second login with first role only
        user2.setLogin("user_2");
        user2.setPassword("password_2");
        //user2.setRoles(lstRoles1);
        
        //link third login with second role only
        user3.setLogin("user_3");
        user3.setPassword("password_3");
        //user3.setRoles(lstRoles2);
        
        
        List<Authorization> lstAut = new ArrayList<Authorization>();
        lstAut.add(user1);
        //lstAut.add(user2);
       // lstAut.add(user3);
        
        AuthorizationEAO autEAO = new AuthorizationEAO(ServiceBaseEAO.getDefaultEM());
        try
        {
            //save all data into DB
            autEAO.save(user1);
            autEAO.save(user2);
            autEAO.save(user3);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
        
        //create two roles
        Role role1 = new Role();
        role1.setRoleId(34);
        role1.setName("1 test role name");
        role1.setAuthorizations(lstAut);
        
        Role role2 = new Role();
        role2.setRoleId(35);
        role2.setName("2 test role name");
        role2.setAuthorizations(lstAut);
        
        try
        {
            RoleEAO roleEAO = new RoleEAO(ServiceBaseEAO.getDefaultEM());
            //autEAO.save(user1);
            roleEAO.save(role1);
            roleEAO.save(role2);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void AuttorisationTest()
    {

      //create two roles
        Role role1 = new Role();
        role1.setRoleId(34);
        role1.setName("1 test role name");
        
        Role role2 = new Role();
        role2.setRoleId(35);
        role2.setName("2 test role name");
        
        List<Role> lstRole1 = new ArrayList<Role>();
        lstRole1.add(role1);
        
        List<Role> lstRole2 = new ArrayList<Role>();
        lstRole2.add(role2);
        
        List<Role> lstAllRole = new ArrayList<Role>();
        lstAllRole.add(role1);
        lstAllRole.add(role2);
        
        
        try
        {
            RoleEAO roleEAO = new RoleEAO(ServiceBaseEAO.getDefaultEM());
            roleEAO.save(role1);
            roleEAO.save(role2);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
        
        //create user
        Authorization user1 = new Authorization();
        Authorization user2 = new Authorization();
        Authorization user3 = new Authorization();
        
        user1.setLogin("user_1");
        user1.setPassword("password_1");
        //link first login with both roles
        user1.setRoles(lstAllRole);
        
        user2.setLogin("user_2");
        user2.setPassword("password_2");
        //link second login with first role only;
        user2.setRoles(lstRole1);
        
        user3.setLogin("user_3");
        user3.setPassword("password_3");
        //link third login with second role only;
        user3.setRoles(lstRole2);
        
        AuthorizationEAO autEAO = new AuthorizationEAO(ServiceBaseEAO.getDefaultEM());
        try
        {
            //save all data into DB
            autEAO.save(user1);
            autEAO.save(user2);
            autEAO.save(user3);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }
    
    @After
    public void tearDown() throws Exception
    {
        ServiceBaseEAO.disconnectFromDb();
    }

}
