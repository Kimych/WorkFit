/**
 * Filename  : WorkFitFrame.java
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

package by.uniterra.system.main;

import java.awt.GridBagLayout;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


import by.uniterra.dai.eao.AuthorizationEAO;
import by.uniterra.dai.entity.Authorization;
import by.uniterra.system.model.SystemModel;
import by.uniterra.udi.util.Log;
import by.uniterra.udi.view.LoginPanel;

/**
 * The <code>WorkFitFrame</code> is used for ...
 *
 * @author Sergio Alecky
 * @since 08 сент. 2014 г.
 */
public class WorkFitFrame extends JFrame
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 165708470997304032L;

    

    /**
     * @param args
     *
     * @author Sergio Alecky
     * @date 08 сент. 2014 г.
     */
    public static void main(String[] args)
    {
        Log.info(WorkFitFrame.class, "\n\n\n");
        Log.info(WorkFitFrame.class, "Starting the app...");

        WorkFitFrame wfFrame = new WorkFitFrame();
        wfFrame.setTitle("Work Fit Test Frame");
        wfFrame.setLayout(new GridBagLayout());
        wfFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        wfFrame.setLocationRelativeTo(null);
        wfFrame.setSize(600, 400);
        wfFrame.setVisible(true);
        
        wfFrame.doLogin();
        //JFrame workfit = new WorkFitFrame();
        // System.out.println(sm.getBool("57.srt", false));
        //Log.info(WorkFitFrame.class, "Start project");
    }

    public WorkFitFrame()
    {
        super();
        jbUnit();

    }

    private void disposeMainFrame()
    {
        SystemModel.disposeJPA();
        dispose();
        Log.info(WorkFitFrame.class, "application close.");

    }

    private void jbUnit()
    {


    }
    
    public void doLogin()
    {

        // check db connection
        // SystemModel.initJPA();
        try
        {
            SystemModel.initJPA();
            EntityManager em = SystemModel.getDefaultEM();
            AuthorizationEAO autEAO = new AuthorizationEAO(em);
            //lstUser = autEAO.loadAll();
            checkLogin("",autEAO.loadAll(), 1);
        }
        catch (Exception e)
        {
            Log.error(this, "not visible DB");
            Log.info(this, "application close.");
            disposeMainFrame();
            throw e;
        }
    }

    private void checkLogin(String strUserName, List<Authorization> lstUser, int retryСounter)
    {
        if (retryСounter <= 3)
        {
            // create login dialog
            LoginPanel panelLogin = new LoginPanel(strUserName);
            // show login dialog
            int input = JOptionPane.showConfirmDialog(null, panelLogin, "Авторизация:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            // check result
            if (input == JOptionPane.OK_OPTION)
            {
                String userName = panelLogin.getUserName();
                //List<Authorization> lstUser = new ArrayList<Authorization>();
                try
                {
                    //AuthorizationEAO autEAO = new AuthorizationEAO(em);
                    //stUser = autEAO.loadAll();
                    for (Authorization aut : lstUser)
                    {
                        if (aut.getLogin().equals(userName))
                        {
                            // if a match is found -> get password
                            // TODO encryption passwords
                            String currentPass = panelLogin.getPassword();
                            if (aut.getPassword().equals(currentPass))
                            {
                                Log.info(WorkFitFrame.class, "Login SUCCESS!!!");
                                // get role for current user
                                
                                // 3) Set Authorization object to SystemModel
                                // (SystemModel.setAuthorization(Authorization
                                // authCurUser)).
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "incorect password");
                                retryСounter++;
                                checkLogin(userName, lstUser, retryСounter);
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "User " + userName + "not found!!!");
                            retryСounter++;
                            checkLogin(userName, lstUser, retryСounter);
                        }
                    }
                }
                catch (Exception e)
                {
                    Log.error(WorkFitFrame.class, "authorization problem...");
                }
            }
        }
        else
        {
            Log.info(WorkFitFrame.class, "3 abortive attempts of authorization");
            disposeMainFrame();
            JOptionPane.showMessageDialog(null, "Sorry I can not continue...");

        }
    }
    
}
