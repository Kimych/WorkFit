package by.uniterra.dai;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import by.uniterra.dai.eao.AuthorizationEAO;
import by.uniterra.dai.eao.RoleEAO;
import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.entity.Role;
import by.uniterra.system.model.SystemModel;

public class AuthorizationToRoleEntitiesTest
{

    private static final String TEST_LOGIN_1 = "user1";
    private static final String TEST_LOGIN_2 = "user2";
    private static final String TEST_LOGIN_3 = "user3";
    private static final String TEST_PASSWORD = "password";
    private static final String TEST_ROLE_1 = "role1";
    private static final String TEST_ROLE_2 = "role2";
    private static List<Role> lstRolesToDelete;
    private static List<Authorization> lstUsersToDelete;

    @Before
    public void setUp() throws Exception
    {
	SystemModel.initJPA();
    }
    
    @After
    public void tearDown() throws Exception
    {
	SystemModel.disposeJPA();
    }

    @BeforeClass
    public static void init()
    {
	lstRolesToDelete = new ArrayList<Role>();
	lstUsersToDelete = new ArrayList<Authorization>();
    }
    
    @AfterClass
    public static  void clean()
    {
	SystemModel.initJPA();
	// delete Roles
	RoleEAO eaoRole = new RoleEAO(SystemModel.getDefaultEM());
	for (Role role : lstRolesToDelete)
	{
	    Role rFoundRole = eaoRole.find(role.getRoleId());
	    if (rFoundRole != null)
	    {
		eaoRole.delete(rFoundRole);
	    }
	}
	
	// delete Authorization
	AuthorizationEAO eaoAuth = new AuthorizationEAO(SystemModel.getDefaultEM());
	for (Authorization auth : lstUsersToDelete)
	{
	    Authorization authCurAuth = eaoAuth.find(auth.getAuthorizationId());
	    if (authCurAuth != null)
	    {
		eaoAuth.delete(authCurAuth);
	    }
	}
	SystemModel.disposeJPA();
    }
    
    @Test
    public void AuttorisationTest()
    {

        // create two roles
        Role role1 = new Role();
        role1.setName(TEST_ROLE_1);

        Role role2 = new Role();
        role2.setName(TEST_ROLE_2);

        RoleEAO eaoRole = new RoleEAO(SystemModel.getDefaultEM());
        try
        {
            role1 = eaoRole.save(role1);
            lstRolesToDelete.add(role1);
            role2 = eaoRole.save(role2);
            lstRolesToDelete.add(role2);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }

        List<Role> lstRole1 = new ArrayList<Role>();
        lstRole1.add(role1);

        List<Role> lstRole2 = new ArrayList<Role>();
        lstRole2.add(role2);

        List<Role> lstAllRole = new ArrayList<Role>();
        lstAllRole.add(role1);
        lstAllRole.add(role2);

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

        AuthorizationEAO eaoAuth = new AuthorizationEAO(SystemModel.getDefaultEM());
        try
        {
            user1 = eaoAuth.save(user1);
            lstUsersToDelete.add(user1);
            user2 = eaoAuth.save(user2);
            lstUsersToDelete.add(user2);
            user3 = eaoAuth.save(user3);
            lstUsersToDelete.add(user3);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
        
        // disconnect to flash caching
        SystemModel.disposeJPA();
        
        List<Authorization> lstAut1 = eaoAuth.getAuthorizationsByLogin(TEST_LOGIN_1);
        List<Authorization> lstAut2 = eaoAuth.getAuthorizationsByLogin(TEST_LOGIN_2);
        List<Authorization> lstAut3 = eaoAuth.getAuthorizationsByLogin(TEST_LOGIN_3);

        assertTrue(lstAut1.contains(user1) && lstAut2.contains(user2) && lstAut3.contains(user3));

    }
    
    @Test
    public void listTest()
    {
	// create lists
	List<Integer> lstSourceList = new ArrayList<Integer>();
	List<Integer> lstDestinationList = new ArrayList<Integer>();
	
	// add data
	lstSourceList.add(3);
	lstSourceList.add(1);
	lstSourceList.add(2);
	lstSourceList.add(3);
	
	lstDestinationList.add(3);
	lstDestinationList.add(2);
	lstDestinationList.add(1);
	lstDestinationList.add(1);
	
	// compare
	//assertTrue(lstSourceList.equals(lstDestinationList));
	
	// extendex check
	assertTrue(lstSourceList.size() == lstDestinationList.size() && lstSourceList.containsAll(lstDestinationList));
    }
}
