/**
 * Filename  : DynamicMenuBar.java
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

package by.uniterra.udi.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.entity.Role;
import by.uniterra.system.iface.IRole;
import by.uniterra.system.model.SystemModel;
import by.uniterra.udi.iface.IMenuHelper;
import by.uniterra.udi.model.UDIPropSingleton;

/**
 * The <code>DynamicMenuBar</code> is used for ...
 *
 * @author Sergio Alecky
 * @since 30 сент. 2014 г.
 */
public class DynamicMenuBar extends JMenuBar implements ActionListener, IAuthListener
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;

    // members
    private JMenu menuNoUser;
    private JMenu menuAdmin;
    private JMenu menuUser;
    private JMenu menuHelp;
    private ActionListener objParent;

    
    public DynamicMenuBar(ActionListener workFitFrame)
    {
        super();
        this.objParent = workFitFrame;
        authUpdated(SystemModel.getAuthorization());
    }

    private  JMenu getMenuNoUser()
    {
        if (menuNoUser == null)
        {
            menuNoUser = new JMenu(UDIPropSingleton.getString(this, "Fit.menu"));

            // LogOUT
            JMenuItem itemLogout = new JMenuItem(UDIPropSingleton.getString(this, "ReLog.menu"));
            itemLogout.setActionCommand(IMenuHelper.MCOMMAND_LOGOUT);
            itemLogout.addActionListener(this);
            // Exit
            JMenuItem itemExit = new JMenuItem(UDIPropSingleton.getString(this, "Exit.menu"));
            itemExit.setActionCommand(IMenuHelper.MCOMMAND_EXIT);
            itemExit.addActionListener(this);
            // build menu
            menuNoUser.add(itemLogout);
            // separator ---------------------------
            menuNoUser.addSeparator();
            menuNoUser.add(itemExit);

        }
        return menuNoUser;

    }

    private JMenu getMenuAdmin()
    {

        if (menuAdmin == null)
        {
            menuAdmin = new JMenu(UDIPropSingleton.getString(this, "Administrationt.menu"));
            // sub menu Edit Table
            JMenu menuEditTable = new JMenu(UDIPropSingleton.getString(this, "EditTable.menu"));
            // sub element
            JMenuItem itemEditWorker = new JMenuItem(UDIPropSingleton.getString(this, "EditWorker.menu"));
            itemEditWorker.setActionCommand(IMenuHelper.MCOMMAND_EDIT_WORKER);
            itemEditWorker.addActionListener(this);
            // sub element
            JMenuItem itemEditHoliday = new JMenuItem(UDIPropSingleton.getString(this, "EditHoliday.menu"));
            itemEditHoliday.setActionCommand(IMenuHelper.MCOMMAND_EDIT_HOLIDAY);
            itemEditHoliday.addActionListener(this);
            // sub element
            JMenuItem itemEditDaysOfWork = new JMenuItem(UDIPropSingleton.getString(this, "EditDofW.menu"));
            itemEditDaysOfWork.setActionCommand(IMenuHelper.MCOMMAND_EDIT_DAYS_OF_WORK);
            itemEditDaysOfWork.addActionListener(this);
            // sub element
            JMenuItem itemEditMOnth = new JMenuItem(UDIPropSingleton.getString(this, "EditMonth.menu"));
            itemEditMOnth.setActionCommand(IMenuHelper.MCOMMAND_EDIT_MONTH);
            itemEditMOnth.addActionListener(this);
            // sub element
            JMenuItem itemEditYear = new JMenuItem(UDIPropSingleton.getString(this, "EditYear.menu"));
            itemEditYear.setActionCommand(IMenuHelper.MCOMMAND_EDIT_YEAR);
            itemEditYear.addActionListener(this);
            // sub element
            JMenuItem itemEditUserRole = new JMenuItem(UDIPropSingleton.getString(this, "EditUserRole.menu"));
            itemEditUserRole.setActionCommand(IMenuHelper.MCOMMAND_EDIT_USER_ROLE);
            itemEditUserRole.addActionListener(this);

            // build sub menu
            menuEditTable.add(itemEditWorker);
            menuEditTable.add(itemEditHoliday);
            menuEditTable.add(itemEditDaysOfWork);
            // separator ---------------------------
            menuEditTable.addSeparator();
            menuEditTable.add(itemEditMOnth);
            menuEditTable.add(itemEditYear);
            // separator ---------------------------
            menuEditTable.addSeparator();
            menuEditTable.add(itemEditUserRole);



            JMenuItem itemAddFromLOg = new JMenuItem(UDIPropSingleton.getString(this, "AddLog.menu"));
            itemAddFromLOg.setActionCommand(IMenuHelper.MCOMMAND_ADD_FROM_LOG);
            itemAddFromLOg.addActionListener(this);

            menuAdmin.add(menuEditTable);
            menuAdmin.add(itemAddFromLOg);

        }

        return menuAdmin;

    }

    private JMenu getMenuUser()
    {
        if (menuUser == null)
        {
            menuUser = new JMenu(UDIPropSingleton.getString(this, "User.menu"));

            // Refresh
            JMenuItem itemRefresh = new JMenuItem(UDIPropSingleton.getString(this, "Refresh.menu"));
            itemRefresh.setActionCommand(IMenuHelper.MCOMMAND_REFRESH);
            itemRefresh.addActionListener(this);
            // View History
            JMenuItem itemViewHistory = new JMenuItem(UDIPropSingleton.getString(this, "ViewHistory.menu"));
            itemViewHistory.setActionCommand(IMenuHelper.MCOMMAND_VIEW_HISTORY);
            itemViewHistory.addActionListener(this);

            menuUser.add(itemRefresh);
            menuUser.add(itemViewHistory);
        }
        return menuUser;

    }

    private JMenu getMenuHelp()
    {
        if (menuHelp == null)
        {
            menuHelp = new JMenu(UDIPropSingleton.getString(this, "Help.menu"));
            // Welcome
            JMenuItem itemWelcome = new JMenuItem(UDIPropSingleton.getString(this, "Welcome.menu"));
            itemWelcome.setActionCommand(IMenuHelper.MCOMMAND_WELCOME);
            itemWelcome.addActionListener(this);
            // About
            JMenuItem itemAbout = new JMenuItem(UDIPropSingleton.getString(this, "About.menu"));
            itemAbout.setActionCommand(IMenuHelper.MCOMMAND_ABOUT);
            itemAbout.addActionListener(this);

            menuHelp.add(itemWelcome);
            menuHelp.add(itemAbout);
        }
        return menuHelp;

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (objParent != null)
        {
            objParent.actionPerformed(e);
        }

    }
    
    public void ubdateMenuBarForCurrentUser(Authorization auth)
    {
        removeAll();
        add(getMenuNoUser());
        if (auth != null)
        {
            for (Role curRole : auth.getRoles())
            {
                if (curRole.getRoleId() == IRole.ADMIN)
                {
                    add(getMenuAdmin());
                }
                else
                {
                    add(getMenuUser());
                }

            }
            updateUI();
        }
        add(getMenuHelp());
        

    }

    @Override
    public void authUpdated(Authorization newAuth)
    {
        ubdateMenuBarForCurrentUser(newAuth);
    }
}
