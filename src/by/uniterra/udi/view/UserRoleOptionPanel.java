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
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import by.uniterra.dai.eao.RoleEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.entity.Role;
import by.uniterra.dai.entity.Worker;
import by.uniterra.system.model.SystemModel;
import by.uniterra.udi.iface.IModelOwner;
import by.uniterra.udi.model.UDIPropSingleton;

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

    private static String DEFAULT_PASSWORD = "!_!_!_!_!_!";

    private JComboBox<?> cbName;
    private JComboBox<?> cbRole;
    private JTextField tfEmail;
    private JTextField tfLogin;
    private JPasswordField tfPassword;
    private JPasswordField tfConfPassword;
    // private JTextArea taDesck;

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
        tfPassword = new JPasswordField();
        tfConfPassword = new JPasswordField();
        // taDesck = new JTextArea();

        JLabel jlName = new JLabel(UDIPropSingleton.getString(this, "name.label"));
        JLabel jlEmail = new JLabel(UDIPropSingleton.getString(this, "email.label"));
        JLabel jlRole = new JLabel(UDIPropSingleton.getString(this, "role.label"));
        // JLabel jlDescription = new JLabel("Description");
        JLabel jlLogin = new JLabel(UDIPropSingleton.getString(this, "login.label"));
        JLabel jlPassword = new JLabel(UDIPropSingleton.getString(this, "password.label"));
        JLabel jlConfPassword = new JLabel(UDIPropSingleton.getString(this, "password.label"));

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
        add(jlLogin, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(tfLogin, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlPassword, new GridBagConstraints(0, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(tfPassword, new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlConfPassword, new GridBagConstraints(0, 6, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(tfConfPassword, new GridBagConstraints(1, 6, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));

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

            // cbRole.setSelectedItem(authorization.getRoles());
        }
        tfEmail.setText(authorization.getEmail());
        // taDesck.setText(authorization.getRoles().get(0).getDescription());
        tfLogin.setText(authorization.getLogin());
        // tfPassword.setText(authorization.getPassword());

        tfPassword.setText(DEFAULT_PASSWORD);
        tfConfPassword.setText("");

    }

    @Override
    public Object getModel()
    {
        List<Role> lstRoleToSave = new ArrayList<Role>();

        if (authorization == null)
        {
            authorization = new Authorization();
        }

        // get role name
        String roleName = cbRole.getSelectedItem().toString();

        // get List<Role>
        RoleEAO roleEAO = new RoleEAO(SystemModel.getDefaultEM());
        lstRoleToSave = roleEAO.getRoleByName(roleName);

        // lstRoleToSave.add(objRole);
        authorization.setWorker((Worker) cbName.getSelectedItem());
        authorization.setEmail(tfEmail.getText());
        authorization.setRoles(lstRoleToSave);
        authorization.setLogin(tfLogin.getText());

        String passFromPassField = tfPassword.getText();
        String passFromConfPassField = tfConfPassword.getText();

        if (!passFromPassField.equals(DEFAULT_PASSWORD))
        {
            if (passFromPassField.equals(passFromConfPassField))
            {
                authorization.setPassword(passFromPassField);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "new password does not approved");
            }
        }
        
        return authorization;
    }
}
