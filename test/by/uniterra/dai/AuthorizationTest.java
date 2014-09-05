package by.uniterra.dai;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import by.uniterra.dai.eao.AuthorizationEAO;
import by.uniterra.dai.eao.RoleEAO;
import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.entity.Role;

public class AuthorizationTest
{
    public static Role role1;
    public static Role role2;
    
    public static List<Role> lstRoles1;
    public static List<Role> lstRoles2;
    public static List<Role> lstAllRoles;
    
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        //create two roles
        role1 = new Role();
        role1.setRoleId(1);
        role1.setName("1 test role name");
        
        role2 = new Role();
        role1.setRoleId(2);
        role2.setName("2 test role name");
        
        //role 1
        lstRoles1 = new ArrayList<Role>();
        lstRoles1.add(role1);
        
        // role 2 
        lstRoles2 = new ArrayList<Role>();
        lstRoles2.add(role2);
        
        // all roles
        lstAllRoles = new ArrayList<Role>();
        lstAllRoles.add(role1);
        lstAllRoles.add(role2);
        
        
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
        
    }

    @Before
    public void setUp() throws Exception
    {
        
    }

    @After
    public void tearDown() throws Exception
    {
        ServiceBaseEAO.disconnectFromDb();
    }

    @Test
    public void roleEntitiesTest()
    {
        Role role = new Role();
        role.setRoleId(1);
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
        assertTrue(lstEntitiesBefore.size()-lstEntitiesAfter.size() == 0);
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
    
    @Test
    public void autToRole()
    {
        /*Role role = new Role();
        role.setName("");*/
    }
    
    

}
