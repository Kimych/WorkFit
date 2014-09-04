package by.uniterra.dai;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;

import by.uniterra.dai.eao.AuthorizationEAO;
import by.uniterra.dai.eao.RoleEAO;
import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.entity.Role;

public class AuthorizationEntitiesTest
{
    public static List<Role> lstAllRoles;
    public static List<Role> lstRoles1;
    public static List<Role> lstRoles2;
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
        
        
    }
    @Test
    public void RoleTest()
    {
        //create two roles
        Role role1 = new Role();
        role1.setRoleId(1);
        role1.setName("1 test role name");
        
        Role role2 = new Role();
        role2.setRoleId(2);
        role2.setName("2 test role name");
        
        //role 1
        List<Role> lstRoles1 = new ArrayList<Role>();
        lstRoles1.add(role1);
        
        // role 2 
        List<Role> lstRoles2 = new ArrayList<Role>();
        lstRoles2.add(role2);
        
        // all roles
        List<Role> lstAllRoles = new ArrayList<Role>();
        lstAllRoles.add(role1);
        lstAllRoles.add(role2);
        
        
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
        //create three logins
        Authorization user1 = new Authorization();
        Authorization user2 = new Authorization();
        Authorization user3 = new Authorization();
        
        //link first login with both roles
        user1.setLogin("user_1");
        user1.setPassword("password_1");
        user1.setRoles(lstAllRoles);
        
        //link second login with first role only
        user2.setLogin("user_2");
        user2.setPassword("password_2");
        user2.setRoles(lstRoles1);
        
        //link third login with second role only
        user3.setLogin("user_3");
        user3.setPassword("password_3");
        user3.setRoles(lstRoles2);
        
        try
        {
            AuthorizationEAO autEAO = new AuthorizationEAO(ServiceBaseEAO.getDefaultEM());
            //save all data into DB
            autEAO.save(user1);
            autEAO.save(user2);
            autEAO.save(user3);
            
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
        
        // disconnect from DB
        ServiceBaseEAO.disconnectFromDb();
    }

}
