/**
 * Filename  : UserRoleOptionPanel.java
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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import by.uniterra.dai.eao.RoleEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.entity.Role;
import by.uniterra.dai.entity.Worker;
import by.uniterra.system.model.SystemModel;
import by.uniterra.udi.iface.IModelOwner;

/**
 * The <code>UserRoleOptionPanel</code> is used for ...
 *
 * @author Sergio Alecky
 * @since 25 сент. 2014 г.
 */
public class UserRoleOptionPanel extends JPanel implements IModelOwner
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = -9107708424856787372L;

    private JComboBox<?> cbName;
    private JComboBox<?> cbRole;
    private JTextField tfEmail;
    private JTextField tfLogin;
    private JTextField tfPassword;
    private JTextArea taDesck;

    private List<Worker> workerArrayList;
    private List<Role> rolerArrayList;

    private Authorization authorization;

    public UserRoleOptionPanel()
    {
        super(new GridBagLayout());
        jbInit();
    }

    private void jbInit()
    {
        tfEmail = new JTextField();
        tfLogin = new JTextField();
        tfPassword = new JTextField();
        taDesck = new JTextArea();

        JLabel jlName = new JLabel("Name");
        JLabel jlEmail = new JLabel("Email");
        JLabel jlRole = new JLabel("Role");
        JLabel jlDescription = new JLabel("Description");
        JLabel jlLogin = new JLabel("Login");
        JLabel jlPassword = new JLabel("Password");

        workerArrayList = new WorkerEAO(SystemModel.getDefaultEM()).loadAll();
        cbName = new JComboBox(new DefaultComboBoxModel(workerArrayList.toArray()));

        rolerArrayList = new RoleEAO(SystemModel.getDefaultEM()).loadAll();
        cbRole = new JComboBox(new DefaultComboBoxModel(rolerArrayList.toArray()));

        add(jlName, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(cbName, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlEmail, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(tfEmail, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlRole, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(cbRole, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlDescription, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(taDesck, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlLogin, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(tfLogin, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlPassword, new GridBagConstraints(0, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(tfPassword, new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));

    }

    @Override
    public void setModel(Object objAutorization)
    {
        this.authorization = (Authorization) objAutorization;
        if (authorization.getAuthorizationId() == 0)
        {
            
            cbName.setSelectedIndex(0);
            cbRole.setSelectedIndex(0);
        }
        else
        {
            cbName.setSelectedItem(authorization.getWorker());
            cbRole.setSelectedItem(authorization.getRoles().get(0));
            
            //cbRole.setSelectedItem(authorization.getRoles());
        }
        tfEmail.setText(authorization.getEmail());
        taDesck.setText(authorization.getRoles().get(0).getDescription());
        tfPassword.setText(authorization.getLogin());
        tfLogin.setText(authorization.getPassword());
        
    }
    @Override
    public Object getModel()
    {
        Role objRole = new Role();
        List<Role> lstRole = new ArrayList<Role>();
        if(authorization == null)
        {
            authorization = new Authorization();
        }
        
        //get role name
        String roleName = cbRole.getSelectedItem().toString();
        
        //get RoleID
        lstRole = new RoleEAO(SystemModel.getDefaultEM()).loadAll();
        for(Role role : lstRole)
        {
            if(role.getName().equals(roleName))
            {
                objRole.setRoleId(role.getRoleId());
            }
            break;
        }
        objRole.setName(roleName);
        objRole.setDescription(taDesck.getText());
        lstRole.add(objRole);
        //lstRole = Arrays.asList(objRole);
        
        authorization.setWorker((Worker)cbName.getSelectedItem());
        authorization.setEmail(tfEmail.getText());
        authorization.setRoles(lstRole);
        authorization.setLogin(tfLogin.getText());
        authorization.setPassword(tfPassword.getText());
        
        return authorization;
    }
}
