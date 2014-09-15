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

import javax.persistence.EntityManager;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.kohsuke.rngom.parse.Parseable;

import by.uniterra.dai.eao.AuthorizationEAO;
import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.entity.Role;
import by.uniterra.system.iface.IRole;
import by.uniterra.system.model.SystemModel;
import by.uniterra.udi.model.WorkLogInfoHelper;
import by.uniterra.udi.util.Log;
import by.uniterra.udi.view.LoginPanel;
import by.uniterra.udi.view.WorkLogOptionPanel;
import by.uniterra.udi.view.WorkLogReportPanel;

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
        wfFrame.setSize(600, 400);
        wfFrame.setLocationRelativeTo(null);
        wfFrame.setVisible(true);
        
        //args==[login=admin ,password=admin]
        wfFrame.doLogin((args[0].split("="))[1], (args[1].split("="))[1]);
        
        /*SystemModel.initJPA();
        WorkLogReportPanel logPanel = new WorkLogReportPanel();
        wfFrame.add(logPanel);*/
        
        // JFrame workfit = new WorkFitFrame();
        // System.out.println(sm.getBool("57.srt", false));
        // Log.info(WorkFitFrame.class, "Start project");

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

    public void doLogin(String strLogin, String strPassword)
    {

        // check db connection
        // SystemModel.initJPA();
        try
        {
            SystemModel.initJPA();
            EntityManager em = SystemModel.getDefaultEM();
            AuthorizationEAO autEAO = new AuthorizationEAO(em);
            // lstUser = autEAO.loadAll();
            Authorization auth = checkLogin(strLogin, strPassword, autEAO, 1);
            
            if (auth != null)
            {
                SystemModel.setAuthorization(auth);
                //System.out.println(SystemModel.getAuthorization().getRoles().get(0).getRoleId());;
                // alyze Role 
                // create UI for Admin or User
                if (isContainsRole(auth, IRole.ADMIN))
                {
                    createAdminUI();
                }
                else
                {
                    System.out.println("User");
                    createUserUI();
                }
            }
            
        }
        catch (Exception e)
        {
            Log.error(this, "not visible DB");
            Log.info(this, "application close.");
            disposeMainFrame();
            throw e;
        }
    }

    private void createUserUI()
    {
        
       WorkLogOptionPanel wlop = new WorkLogOptionPanel();
       wlop.setModel(WorkLogInfoHelper.getLogInfoByWorker());
       super.add(wlop);
       super.setVisible(true);
        
    }

    private void createAdminUI()
    {
        WorkLogReportPanel logPanel = new WorkLogReportPanel();
        super.add(logPanel);
        super.setVisible(true);
    }

    private boolean isContainsRole(Authorization auth, int iRileToSearch)
    {
        boolean bReasult = false;
        for (Role curRole : auth.getRoles())
        {
            if (curRole.getRoleId() == iRileToSearch)
            {
                bReasult = true;
                break;
            }
        }
        return bReasult;
    }

    private Authorization checkLogin(String strUserName, String strPassword, AuthorizationEAO autEAO, int retryСounter)
    {
        if (strUserName != null && !strUserName.isEmpty()
                && strPassword != null && !strPassword.isEmpty())
        {
            Authorization authUser = autEAO.getAuthorizationByLoginAndPassword(strUserName, strPassword);
            if (authUser != null)
            {
                return authUser;
            }
            else
            {
                retryСounter++;
                checkLogin(strUserName, "", autEAO, retryСounter);
            }
        }
        if (retryСounter <= 3)
        {
            // create login dialog
            LoginPanel panelLogin = new LoginPanel(strUserName);
            // show login dialog
            int input = JOptionPane.showConfirmDialog(this, panelLogin, "Авторизация:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            // check result
            if (input == JOptionPane.OK_OPTION)
            {
                String userName = panelLogin.getUserName();
                String currentPass = panelLogin.getPassword();
                try
                {
                    Authorization authUser = autEAO.getAuthorizationByLoginAndPassword(userName, currentPass);
                    if (authUser != null)
                    {
                        return authUser;
                    }
                    else
                    {
                        retryСounter++;
                        checkLogin(userName, "", autEAO, retryСounter);
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
        return null;
    }

}
