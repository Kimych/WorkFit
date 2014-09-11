/**
 * Filename  : AuthorizationQueryTest.java
 *
 * ***************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ***************************************************************************
 * Project    : WorkFit
 *
 * Author     : Sergio Alecky
 *
 * last change by : $Author:$ 
 * last check in  : $Date: $
 */

package by.uniterra.dai;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import by.uniterra.dai.eao.AuthorizationEAO;
import by.uniterra.dai.eao.RoleEAO;
import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.entity.Role;
import by.uniterra.system.model.SystemModel;

/**
 * The <code>AuthorizationQueryTest</code> is used for test Named Query
 *
 * @author Sergio Alecky
 * @since 05 сент. 2014 г.
 */
public class AuthorizationQueryTest
{

    private static final String TEST_LOGIN = "test login";
    private static final String TEST_PAS = "test pass";
    private static final String TEST_ROLE = "role1";

    /**
     * @throws java.lang.Exception
     *
     * @author Sergio Alecky
     * @date 05 сент. 2014 г.
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
        SystemModel.disposeJPA();
    }

    @Before
    public void setUp() throws Exception
    {
        SystemModel.initJPA();
    }

    @Test
    public void getAuthorizationsByLoginTest()
    {
        Authorization user = new Authorization();
        user.setLogin(TEST_LOGIN);
        user.setPassword(TEST_PAS);

        AuthorizationEAO eaoAut = new AuthorizationEAO(SystemModel.getDefaultEM());
        try
        {
            user = eaoAut.save(user);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
        List<Authorization> lstAut = eaoAut.getAuthorizationsByLogin(TEST_LOGIN);

        assertTrue(lstAut.get(0).getPassword().equals(TEST_PAS));

        try
        {
            eaoAut.delete(user);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void getRoleByLoginAndPasswordTest()
    {
        // create role
        Role role = new Role();
        role.setName(TEST_ROLE);
        RoleEAO eaoRole = new RoleEAO(SystemModel.getDefaultEM());
        try
        {
            role = eaoRole.save(role);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
        // create List<Role>
        List<Role> lstRole = new ArrayList<Role>();
        lstRole.add(role);

        Authorization user = new Authorization();
        user.setLogin(TEST_LOGIN);
        user.setPassword(TEST_PAS);
        user.setRoles(lstRole);

        AuthorizationEAO eaoAuth = new AuthorizationEAO(SystemModel.getDefaultEM());
        try
        {
            user = eaoAuth.save(user);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }

        List<Role> lstResultRole = eaoAuth.getRoleByLoginAndPassword(TEST_LOGIN, TEST_PAS);
        for (Role rl : lstResultRole)
        {
            assertTrue(rl.getName().equals(TEST_ROLE));
        }

        // delete data from db
        eaoRole.delete(role);
        eaoAuth.delete(user);

    }

    @Test
    public void getAuthorizationByLoginAndPasswordTest()
    {
        // create role
        Role role = new Role();
        role.setName(TEST_ROLE);
        RoleEAO eaoRole = new RoleEAO(SystemModel.getDefaultEM());
        try
        {
            role = eaoRole.save(role);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
        // create List<Role>
        List<Role> lstRole = new ArrayList<Role>();
        lstRole.add(role);

        Authorization user = new Authorization();
        user.setLogin(TEST_LOGIN);
        user.setPassword(TEST_PAS);
        user.setRoles(lstRole);

        AuthorizationEAO eaoAuth = new AuthorizationEAO(SystemModel.getDefaultEM());
        try
        {
            user = eaoAuth.save(user);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }

        List<Authorization> lstResultRole = eaoAuth.getAuthorizationByLoginAndPassword(TEST_LOGIN, TEST_PAS);
        for (Authorization rl : lstResultRole)
        {
            assertTrue(rl.getLogin().equals(TEST_LOGIN));
        }

        // delete data from db
        eaoRole.delete(role);
        eaoAuth.delete(user);

    }

}
