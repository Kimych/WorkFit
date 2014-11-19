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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.prefs.Preferences;

import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import by.uniterra.dai.eao.AuthorizationEAO;
import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.eao.HolidayEAO;
import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.eao.SpentHolidayEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.eao.YearEAO;
import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.entity.Role;
import by.uniterra.system.iface.IRole;
import by.uniterra.system.model.SystemModel;
import by.uniterra.udi.iface.IMenuHelper;
import by.uniterra.udi.model.DaysOfWorkTableModel;
import by.uniterra.udi.model.HolidayTableModel;
import by.uniterra.udi.model.MonthTableModel;
import by.uniterra.udi.model.SpentHolidayTableModel;
import by.uniterra.udi.model.UDIPropSingleton;
import by.uniterra.udi.model.UserRoleTableMolel;
import by.uniterra.udi.model.WorkerTableModel;
import by.uniterra.udi.model.YearTableModel;
import by.uniterra.udi.util.Cryptor;
import by.uniterra.udi.util.Log;
import by.uniterra.udi.util.LogParser;
import by.uniterra.udi.view.AboutPanel;
import by.uniterra.udi.view.AdminPanel;
import by.uniterra.udi.view.CommonDataTablePanel;
import by.uniterra.udi.view.DaysOfWorkOptionPanel;
import by.uniterra.udi.view.DynamicMenuBar;
import by.uniterra.udi.view.HolidayOptionPanel;
import by.uniterra.udi.view.LoginPanel;
import by.uniterra.udi.view.MonthOptionPanel;
import by.uniterra.udi.view.SpentHolidayOptionPanel;
import by.uniterra.udi.view.UserHistoryPanel;
import by.uniterra.udi.view.UserPanel;
import by.uniterra.udi.view.UserRoleOptionPanel;
import by.uniterra.udi.view.WorkerOptionPanel;
import by.uniterra.udi.view.YearOptionPanel;

/**
 * The <code>WorkFitFrame</code> is used for ...
 *
 * @author Sergio Alecky
 * @since 08 сент. 2014 г.
 */
public class WorkFitFrame extends JFrame implements ActionListener
{

    static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    JPanel panelToInsert;
    private Authorization auth;

    static Preferences userPrefs = Preferences.userNodeForPackage(WorkFitFrame.class);;

    // private static final String UPDATE_LOG = "Update log";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String FLAG_AUT = "flag";
    

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
        
        SystemModel.initJPA();
        WorkFitFrame wfFrame = new WorkFitFrame();
        wfFrame.setTitle("WorkFit");

        URL imageURL = WorkFitFrame.class.getClassLoader().getResource("images/MainFrameIcon.png");
        if (imageURL != null)
        {
            wfFrame.setIconImage(new ImageIcon(imageURL).getImage());
        }

        wfFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        wfFrame.setSize(800, 400);
        wfFrame.setLocationRelativeTo(null);
        wfFrame.setVisible(true);
        wfFrame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent event)
            {
                Object[] options = { UDIPropSingleton.getString(this, "Yes.btn"), UDIPropSingleton.getString(this, "No.btn") };
                int n = JOptionPane.showOptionDialog(event.getWindow(), UDIPropSingleton.getString(this, "CloseApp.label"), "", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (n == 0)
                {
                    wfFrame.disposeMainFrame();
                }
            }
        });

        // args==[login=admin ,password=admin]
        // Disable
        if (userPrefs.getBoolean(FLAG_AUT, false))
        {
            wfFrame.doLogin(userPrefs.get(LOGIN, ""), userPrefs.get(PASSWORD, ""));
        }
        else
        {
            if (args.length != 0)
            {
                wfFrame.doLogin((args[0].split("="))[1], Cryptor.getSecureString((args[1].split("="))[1]));
            }
            else
            {
                wfFrame.doLogin("", "");
            }
        }

    }

    public WorkFitFrame()
    {
        super();
        jbUnit();
    }

    private void disposeMainFrame()
    {
        SystemModel.disposeJPA();
        Log.info(WorkFitFrame.class, "application close.");
        dispose();
    }

    private void jbUnit()
    {
        DynamicMenuBar setBarListener = new DynamicMenuBar(this);
        SystemModel.addAuthListener(setBarListener);
        setJMenuBar(setBarListener);
        // set layout for a content pane
        getContentPane().setLayout(new GridBagLayout());
    }

    public void doLogin(String strLogin, String strPassword)
    {
        try
        {
            EntityManager em = SystemModel.getDefaultEM();
            AuthorizationEAO autEAO = new AuthorizationEAO(em);
            auth = checkLogin(strLogin, strPassword, autEAO);

            if (auth != null)
            {
                // JMenuBar panelMenu = null;
                SystemModel.setAuthorization(auth);
                // create UI for Admin or User
                if (isContainsRole(auth, IRole.ADMIN))
                {
                    panelToInsert = new AdminPanel();
                    ((AdminPanel) panelToInsert).loadDataInUI();
                }
                else
                {
                    panelToInsert = new UserPanel();
                    ((UserPanel) panelToInsert).loadDataInUI(auth.getWorker());
                }
                getContentPane().add(panelToInsert,
                        new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
                pack();
            }
            setVisible(true);
        }
        catch (Exception e)
        {
            Log.error(this, "not visible DB");
            Log.info(this, "application close.");
            disposeMainFrame();
        }
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

    private Authorization checkLogin(String strUserName, String strPassword, AuthorizationEAO autEAO)
    {

        Authorization authUser = autEAO.getAuthorizationByLoginAndPassword(strUserName, strPassword);
        if (authUser != null)
        {

            return authUser;
        }
        else
        {
            return showLoginPanel(strUserName, autEAO);
        }
    }

    private Authorization showLoginPanel(String strUserName, AuthorizationEAO autEAO)
    {
        Authorization authUser = null;
        // create login dialog
        while (authUser == null)
        {
            LoginPanel panelLogin = new LoginPanel(strUserName);
            // show login dialog
            int input = JOptionPane.showConfirmDialog(this, panelLogin, UDIPropSingleton.getString(this, "Authotization.frame"), JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (input == JOptionPane.OK_OPTION)
            {
                String userName = panelLogin.getUserName();
                String currentPass = panelLogin.getPassword();
                // save Login and Password
                authUser = autEAO.getAuthorizationByLoginAndPassword(userName, currentPass);
                if (authUser != null)
                {
                    if (panelLogin.getStatmentFlag() == true)
                    {
                        saveLoginAndPassword(userName, currentPass);
                    }
                    return authUser;
                }
            }
            else
            {
                break;
            }
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        try
        {
            switch (arg0.getActionCommand())
            {
            case IMenuHelper.MCOMMAND_ADD_FROM_LOG:
                createFileChooser(this);
                ((AdminPanel) panelToInsert).loadDataInUI();
                break;

            case IMenuHelper.MCOMMAND_LOGOUT:
                SystemModel.setAuthorization(null);
                getContentPane().removeAll();
                getContentPane().repaint();
                setEmtyLoginAndPassword();
                doLogin("", "");
                break;
            /*
             * case UPDATE_LOG: // createUserUI(); break;
             */
            case IMenuHelper.MCOMMAND_EXIT:
                disposeMainFrame();
                break;
            case IMenuHelper.MCOMMAND_EDIT_SPENT_HOLIDAY:
                CommonDataTablePanel panelSpentHoliday = new CommonDataTablePanel(new SpentHolidayTableModel(), new SpentHolidayOptionPanel(),
                        new SpentHolidayEAO(SystemModel.getDefaultEM()));
                showEditPanel(panelSpentHoliday, UDIPropSingleton.getString(this, "EditSpentHolidayTable.frame"));
                panelSpentHoliday.writeValues();
                break;
            case IMenuHelper.MCOMMAND_EDIT_WORKER:
                CommonDataTablePanel panelWorker = new CommonDataTablePanel(new WorkerTableModel(), new WorkerOptionPanel(), new WorkerEAO(
                        SystemModel.getDefaultEM()));
                showEditPanel(panelWorker, UDIPropSingleton.getString(this, "EditWorkerTable.frame"));
                panelWorker.writeValues();
                break;
            case IMenuHelper.MCOMMAND_EDIT_YEAR:
                CommonDataTablePanel panelYear = new CommonDataTablePanel(new YearTableModel(), new YearOptionPanel(), new YearEAO(SystemModel.getDefaultEM()));
                showEditPanel(panelYear, UDIPropSingleton.getString(this, "EditYearTable.frame"));
                panelYear.writeValues();
                break;
            case IMenuHelper.MCOMMAND_EDIT_DAYS_OF_WORK:
                CommonDataTablePanel panelDaysOfWork = new CommonDataTablePanel(new DaysOfWorkTableModel(), new DaysOfWorkOptionPanel(), new DaysOfWorkEAO(
                        SystemModel.getDefaultEM()));
                showEditPanel(panelDaysOfWork, UDIPropSingleton.getString(this, "EditDofWTable.frame"));
                panelDaysOfWork.writeValues();
                break;
            case IMenuHelper.MCOMMAND_EDIT_MONTH:
                CommonDataTablePanel panelMonth = new CommonDataTablePanel(new MonthTableModel(), new MonthOptionPanel(), new MonthEAO(
                        SystemModel.getDefaultEM()));
                showEditPanel(panelMonth, UDIPropSingleton.getString(this, "EditMonth.frame"));
                panelMonth.writeValues();
                break;
            case IMenuHelper.MCOMMAND_EDIT_HOLIDAY:
                CommonDataTablePanel panelHoliday = new CommonDataTablePanel(new HolidayTableModel(), new HolidayOptionPanel(), new HolidayEAO(
                        SystemModel.getDefaultEM()));
                showEditPanel(panelHoliday, UDIPropSingleton.getString(this, "EditHolidayTable.frame"));
                panelHoliday.writeValues();
                break;
            case IMenuHelper.MCOMMAND_EDIT_USER_ROLE:
                CommonDataTablePanel panelUserRole = new CommonDataTablePanel(new UserRoleTableMolel(), new UserRoleOptionPanel(), new AuthorizationEAO(
                        SystemModel.getDefaultEM()));
                showEditPanel(panelUserRole, UDIPropSingleton.getString(this, "EditUserRole.frame"));
                panelUserRole.writeValues();
                break;
            /*
             * case IMenuHelper.MCOMMAND_WELCOME: // TODO break;
             */
            case IMenuHelper.MCOMMAND_ABOUT:
                JOptionPane.showMessageDialog(this, new AboutPanel(), "About", JOptionPane.PLAIN_MESSAGE);
                break;
            case IMenuHelper.MCOMMAND_REFRESH:
                ((UserPanel) panelToInsert).loadDataInUI(auth.getWorker());
                break;
            
             case IMenuHelper.MCOMMAND_VIEW_HISTORY: 
                 UserHistoryPanel userHistoryPanel = new UserHistoryPanel(auth.getWorker());
                 JOptionPane.showMessageDialog(this, userHistoryPanel, UDIPropSingleton.getString(this, "History.frame") + auth.getWorker(), JOptionPane.NO_OPTION);
                 break;
             
                 
            default:
                break;
            }
        }
        catch (Exception e)
        {
            Log.error(WorkFitFrame.class, e, "actionPerformed expressions");
        }

    }

    private void showEditPanel(CommonDataTablePanel commonPanel, String frameTitle)
    {
        JPanel panelCommon = new JPanel();
        panelCommon.add(commonPanel);
        JOptionPane.showConfirmDialog(this, panelCommon, frameTitle, JOptionPane.OK_CANCEL_OPTION);
    }

    private static void createFileChooser(final JFrame frame) throws ParseException
    {
        String filename = File.separator + "tmp";
        JFileChooser fileChooser = new JFileChooser(new File(filename));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("txt file (*.txt)", "txt"));
        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.showOpenDialog(frame);

        Log.info(WorkFitFrame.class, "Load log from file: " + fileChooser.getSelectedFile());

        //LogParser.saveLogInfoToDB(LogParser.getListFromLog(fileChooser.getSelectedFile().toPath()));
        LogParser.processWorklogFile(fileChooser.getSelectedFile().toPath());
    }

    private static void saveLoginAndPassword(String userName, String userPassword)
    {
        userPrefs.put(LOGIN, userName);
        userPrefs.put(PASSWORD, userPassword);
        userPrefs.putBoolean(FLAG_AUT, true);
    }

    private static void setEmtyLoginAndPassword()
    {
        userPrefs.put(LOGIN, "");
        userPrefs.put(PASSWORD, "");
        userPrefs.putBoolean(FLAG_AUT, false);
    }
    
        
}
