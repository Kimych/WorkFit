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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import by.uniterra.dai.eao.AuthorizationEAO;
import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.eao.HolidayEAO;
import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.eao.YearEAO;
import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.entity.Role;
import by.uniterra.system.iface.IRole;
import by.uniterra.system.model.SystemModel;
import by.uniterra.udi.model.DaysOfWorkTableModel;
import by.uniterra.udi.model.HolidayTableModel;
import by.uniterra.udi.model.MonthTableModel;
import by.uniterra.udi.model.UDIPropSingleton;
import by.uniterra.udi.model.UserRoleTableMolel;
import by.uniterra.udi.model.WorkLogInfoHelper;
import by.uniterra.udi.model.WorkerTableModel;
import by.uniterra.udi.model.YearTableModel;
import by.uniterra.udi.util.Log;
import by.uniterra.udi.util.LogParser;
import by.uniterra.udi.view.AdminPanel;
import by.uniterra.udi.view.CommonDataTablePanel;
import by.uniterra.udi.view.DaysOfWorkOptionPanel;
import by.uniterra.udi.view.HolidayOptionPanel;
import by.uniterra.udi.view.LoginPanel;
import by.uniterra.udi.view.MonthOptionPanel;
import by.uniterra.udi.view.UserRoleOptionPanel;
import by.uniterra.udi.view.WorkLogOptionPanel;
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

    AdminPanel adp;

    private static final String ACTION_ADD_LOG = "Add Log";
    private static final String ACTION_EXIT = "Exit";
    private static final String EDIT_WORKER = "Edit worker";
    private static final String EDIT_HOLIDAY = "Edit holiday";
    private static final String EDIT_DAYS_OF_WORK = "Edit days of work";
    private static final String EDIT_MONTH = "Edit month";
    private static final String EDIT_YEAR = "Edit year";
    private static final String EDIT_USER_ROLE = "Edit user role";
    private static final String UPDATE_LOG = "Update log";

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
        wfFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        wfFrame.setSize(800, 400);
        wfFrame.setLocationRelativeTo(null);
        wfFrame.setVisible(true);

        // args==[login=admin ,password=admin]
        if (args.length != 0)
        {
            wfFrame.doLogin((args[0].split("="))[1], (args[1].split("="))[1]);
        }
        else
        {
            wfFrame.doLogin("", "");
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
                // create UI for Admin or User
                if (isContainsRole(auth, IRole.ADMIN))
                {
                    adp = new AdminPanel();
                    getContentPane().add(adp);
                    adp.loadDataInUI();
                    createAdminMenu();

                    setVisible(true);

                }
                else
                {
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
        getContentPane().add(wlop);
        createUserMenu();
        super.setVisible(true);
        super.pack();

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
        if (strUserName != null && !strUserName.isEmpty() && strPassword != null && !strPassword.isEmpty())
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
            int input = JOptionPane.showConfirmDialog(this, panelLogin, UDIPropSingleton.getString(this, "Authotization.frame"), JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
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

    private void createAdminMenu()
    {
        // create Menu

        JMenuBar jmBar = new JMenuBar();

        JMenu jmMenuFit = new JMenu(UDIPropSingleton.getString(this, "Fit.menu"));

        JMenuItem itemAddLog = new JMenuItem(UDIPropSingleton.getString(this, "AddLog.menu"));
        jmMenuFit.add(itemAddLog);
        itemAddLog.setActionCommand(ACTION_ADD_LOG);
        itemAddLog.addActionListener(this);

        jmMenuFit.addSeparator();

        JMenuItem itemExit = new JMenuItem(UDIPropSingleton.getString(this, "Exit.menu"));
        itemExit.setActionCommand(ACTION_EXIT);
        itemExit.addActionListener(this);
        jmMenuFit.add(itemExit);

        // add menu Edit
        JMenu jmMenuEdit = new JMenu(UDIPropSingleton.getString(this, "Edit.menu"));

        JMenuItem itemEditWorker = new JMenuItem(UDIPropSingleton.getString(this, "EditWorker.menu"));
        itemEditWorker.setActionCommand(EDIT_WORKER);
        itemEditWorker.addActionListener(this);
        jmMenuEdit.add(itemEditWorker);

        JMenuItem itemEditHoliday = new JMenuItem(UDIPropSingleton.getString(this, "EditHoliday.menu"));
        itemEditHoliday.setActionCommand(EDIT_HOLIDAY);
        itemEditHoliday.addActionListener(this);
        jmMenuEdit.add(itemEditHoliday);

        JMenuItem itemEditDaysOfWork = new JMenuItem(UDIPropSingleton.getString(this, "EditDofW.menu"));
        itemEditDaysOfWork.setActionCommand(EDIT_DAYS_OF_WORK);
        itemEditDaysOfWork.addActionListener(this);
        jmMenuEdit.add(itemEditDaysOfWork);

        jmMenuEdit.addSeparator();

        JMenuItem itemEditMOnth = new JMenuItem(UDIPropSingleton.getString(this, "EditMonth.menu"));
        itemEditMOnth.setActionCommand(EDIT_MONTH);
        itemEditMOnth.addActionListener(this);
        jmMenuEdit.add(itemEditMOnth);

        JMenuItem itemEditYear = new JMenuItem(UDIPropSingleton.getString(this, "EditYear.menu"));
        itemEditYear.setActionCommand(EDIT_YEAR);
        itemEditYear.addActionListener(this);
        jmMenuEdit.add(itemEditYear);

        jmMenuEdit.addSeparator();

        JMenuItem itemEditUserRole = new JMenuItem(UDIPropSingleton.getString(this, "EditUserRole.menu"));
        itemEditUserRole.setActionCommand(EDIT_USER_ROLE);
        itemEditUserRole.addActionListener(this);
        jmMenuEdit.add(itemEditUserRole);

        jmBar.add(jmMenuFit);
        jmBar.add(jmMenuEdit);

        setJMenuBar(jmBar);

    }

    private void createUserMenu()
    {
        JMenuBar jmBar = new JMenuBar();

        JMenu jmMenuFit = new JMenu(UDIPropSingleton.getString(this, "Fit.menu"));

        JMenuItem itemAddLog = new JMenuItem(UDIPropSingleton.getString(this, "Update.menu"));
        jmMenuFit.add(itemAddLog);
        itemAddLog.setActionCommand(UPDATE_LOG);
        itemAddLog.addActionListener(this);

        jmMenuFit.addSeparator();

        JMenuItem itemExit = new JMenuItem(UDIPropSingleton.getString(this, "Exit.menu"));
        itemExit.setActionCommand(ACTION_EXIT);
        itemExit.addActionListener(this);
        jmMenuFit.add(itemExit);

        jmBar.add(jmMenuFit);

        setJMenuBar(jmBar);
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        try
        {
            switch (arg0.getActionCommand())
            {
            case ACTION_ADD_LOG:
                createFileChooser(this);
                adp.loadDataInUI();
                break;
            case UPDATE_LOG:
                createUserUI();
                break;
            case ACTION_EXIT:
                disposeMainFrame();
                break;
            case EDIT_WORKER:
                CommonDataTablePanel panelWorker = new CommonDataTablePanel(new WorkerTableModel(), new WorkerOptionPanel(), new WorkerEAO(
                        SystemModel.getDefaultEM()));
                showEditPanel(panelWorker, UDIPropSingleton.getString(this, "EditWorkerTable.frame"));
                panelWorker.writeValues();
                break;
            case EDIT_YEAR:
                CommonDataTablePanel panelYear = new CommonDataTablePanel(new YearTableModel(), new YearOptionPanel(), new YearEAO(SystemModel.getDefaultEM()));
                showEditPanel(panelYear, UDIPropSingleton.getString(this, "EditYearTable.frame"));
                panelYear.writeValues();
                break;
            case EDIT_DAYS_OF_WORK:
                CommonDataTablePanel panelDaysOfWork = new CommonDataTablePanel(new DaysOfWorkTableModel(), new DaysOfWorkOptionPanel(), new DaysOfWorkEAO(
                        SystemModel.getDefaultEM()));
                showEditPanel(panelDaysOfWork, UDIPropSingleton.getString(this, "EditDofWTable.frame"));
                panelDaysOfWork.writeValues();
                break;
            case EDIT_MONTH:
                CommonDataTablePanel panelMonth = new CommonDataTablePanel(new MonthTableModel(), new MonthOptionPanel(), new MonthEAO(
                        SystemModel.getDefaultEM()));
                showEditPanel(panelMonth, UDIPropSingleton.getString(this, "EditMonth.frame"));
                panelMonth.writeValues();
                break;
            case EDIT_HOLIDAY:
                CommonDataTablePanel panelHoliday = new CommonDataTablePanel(new HolidayTableModel(), new HolidayOptionPanel(), new HolidayEAO(
                        SystemModel.getDefaultEM()));
                showEditPanel(panelHoliday, UDIPropSingleton.getString(this, "EditHolidayTable.frame"));
                panelHoliday.writeValues();
                break;
            case EDIT_USER_ROLE:
                CommonDataTablePanel panelUserRole = new CommonDataTablePanel(new UserRoleTableMolel(), new UserRoleOptionPanel(), new AuthorizationEAO(
                        SystemModel.getDefaultEM()));
                showEditPanel(panelUserRole, UDIPropSingleton.getString(this, "EditUserRole.frame"));
                panelUserRole.writeValues();
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
        // JOptionPane.showMessageDialog(this, panelCommon, frameTitle,
        // JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showConfirmDialog(this, panelCommon, frameTitle, JOptionPane.OK_CANCEL_OPTION);
    }

    private static void createFileChooser(final JFrame frame)
    {
        String filename = File.separator + "tmp";
        JFileChooser fileChooser = new JFileChooser(new File(filename));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("txt file (*.txt)", "txt"));
        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.showOpenDialog(frame);

        Log.info(WorkFitFrame.class, "Load log from file: " + fileChooser.getSelectedFile());

        LogParser.saveLogInfoToDB(LogParser.getListFromLog(fileChooser.getSelectedFile().toPath()));
        // return fileChooser.getSelectedFile().toPath();

    }
}
