/**
 * Filename  : IdDocInfoPanel.java
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
 *
 * Author     : Anton Nedbailo
 *
 * last change by : $Author:$ 
 * last check in  : $Date: $
 */

package by.uniterra.udi.view;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.DefaultComboBoxModel;
import javax.swing.InputVerifier;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXComboBox;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.error.ErrorInfo;

import by.uniterra.dai.entity.Person;
import by.uniterra.dai.iface.IPerson;
import by.uniterra.dai.model.EModelState;
import by.uniterra.dai.model.ESex;
import by.uniterra.system.iface.IGlobalActionCommand;
import by.uniterra.system.main.SystemFactory;
import by.uniterra.system.util.DateUtils;
import by.uniterra.system.util.Log;
import by.uniterra.udi.util.UIUtils;

import com.toedter.calendar.JDateChooser;

/**
 * The <code>IdDocInfoPanel</code> is used for identification document info presentation
 *
 * @author Anton Nedbailo
 * @since Sep 21, 2013
 */
public class PersonPanel extends AbstractModelOwnedPanel implements ActionListener
{
    private static final long serialVersionUID = 7008265520024392329L;
    
    //members
    private JXTextField tfSecondName;
    private JXTextField tfFirstName;
    private JXTextField tfThirdName;
    private JDateChooser dpDateOfBirth;
    private JXComboBox cbSex;
    
    public class MyInputVerifier extends InputVerifier
    {
        @Override
        public boolean verify(JComponent input)
        {
            String text = ((JTextField) input).getText();
            try
            {
                BigDecimal value = new BigDecimal(text);
                return (value.scale() <= Math.abs(4));
            } catch (NumberFormatException e)
            {
                return false;
            }
        }
    }

    public PersonPanel()
    {
        super();
    }
    
    @Override
    protected void jbInit()
    {  
        Log.info(this, "jbInit() called");
        //second name
        tfSecondName = UIUtils.createTextField("", UIUtils.LARGE_FIELD_WIDTH_PREFFERED);
        //first name
        tfFirstName = UIUtils.createTextField("", UIUtils.LARGE_FIELD_WIDTH_PREFFERED);
        //third name
        tfThirdName = UIUtils.createTextField("", UIUtils.LARGE_FIELD_WIDTH_PREFFERED);
        //date of birth
        //dpDateOfBirth = UIUtils.createDatePicker(new SimpleDateFormat(DateUtils.EUROP_FULL_DATEFORMAT));
        dpDateOfBirth = UIUtils.createDatePicker(new Date(0), DateUtils.EUROP_FULL_DATEFORMAT, UIUtils.LARGE_FIELD_WIDTH_PREFFERED, true);
        dpDateOfBirth.addPropertyChangeListener(new PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent arg0)
            {
                if ("date".equals(arg0.getPropertyName())) 
                {
                    //notify owner
                }
            }
        });
        //sex
        cbSex = UIUtils.createXComboBox(IGlobalActionCommand.PERSON_SEX_ACTION, PersonPanel.this, UIUtils.SMALL_FIELD_WIDTH_PREFFERED + 30);
        cbSex.setModel(new DefaultComboBoxModel<String>(new String[]{ESex.FEMALE.toString() , ESex.MALE.toString()}));
        
        int iXpos = 0;
        int iYpos = 0;
        final Insets insCommonInsets = new Insets(0, 2, 0, 0);
        final Insets insLabelInsets = new Insets(0, 5, 0, 0);
        
        //first line
        add(new JXLabel("Фамилия:"), new GridBagConstraints(iXpos = 0, ++iYpos, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, insLabelInsets, 0, 0));
        add(tfSecondName, new GridBagConstraints(++iXpos, iYpos, 2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insCommonInsets, 0, 0));
        add(new JXLabel("Имя:"), new GridBagConstraints(iXpos = 0, ++iYpos, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, insLabelInsets, 0, 0));
        add(tfFirstName, new GridBagConstraints(++iXpos, iYpos, 1, 1, 0.5, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insCommonInsets, 0, 0));
        add(new JXLabel("Отчество"), new GridBagConstraints(iXpos = 0, ++iYpos, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, insLabelInsets, 0, 0));
        add(tfThirdName, new GridBagConstraints(++iXpos, iYpos, 1, 1, 0.5, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insCommonInsets, 0, 0));
        add(new JXLabel("Дата рождения:"), new GridBagConstraints(iXpos = 0, ++iYpos, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, insLabelInsets, 0, 0));
        add(dpDateOfBirth, new GridBagConstraints(++iXpos, iYpos, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insCommonInsets, 0, 0));
        //second line
        add(new JXLabel("Пол:"), new GridBagConstraints(iXpos = 0, ++iYpos, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, insLabelInsets, 0, 0));
        add(cbSex, new GridBagConstraints(++iXpos, iYpos, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insCommonInsets, 0, 0));
    }

    @Override
    protected void readIntValues()
    {
        Log.info(this, "readIntValues() called");
        if (mIntModel instanceof IPerson)
        {
            IPerson mModel = (IPerson) mIntModel;
            tfSecondName.setText(mModel.getSecondName());
            tfFirstName.setText(mModel.getFirstName());
            tfThirdName.setText(mModel.getThirdName());
            dpDateOfBirth.setDate(mModel.getDateOfBirth());
            cbSex.setSelectedIndex(mModel.getSex());
        }
    }


    @Override
    public EModelState writeValues()
    {
        EModelState msResult = EModelState.UNMODIFYED;
        // check for null
        if (mIntModel == null)
        {
            // create a new model
            mIntModel = new Person();
            msResult = EModelState.NEW;
        }
        IPerson mModel = (IPerson) mIntModel;
        // check other parametrs
        if (!mModel.getSecondName().equals(tfSecondName.getText()))
        {
            mModel.setSecondName(tfSecondName.getText());
            msResult = msResult != EModelState.NEW ? EModelState.MODIFYED : EModelState.NEW;
        }
        if (!mModel.getFirstName().equals(tfFirstName.getText()))
        {
            mModel.setFirstName(tfFirstName.getText());
            msResult = msResult != EModelState.NEW ? EModelState.MODIFYED : EModelState.NEW;
        }
        if (!mModel.getThirdName().equals(tfThirdName.getText()))
        {
            mModel.setThirdName(tfThirdName.getText());
            msResult = msResult != EModelState.NEW ? EModelState.MODIFYED : EModelState.NEW;
        }
        if (mModel.getSex() != cbSex.getSelectedIndex())
        {
            mModel.setSex(cbSex.getSelectedIndex());
            msResult = msResult != EModelState.NEW ? EModelState.MODIFYED : EModelState.NEW;
        }
        if (mModel.getDateOfBirth().getTime() != dpDateOfBirth.getDate().getTime())
        {
            mModel.setDateOfBirth(dpDateOfBirth.getDate());
            msResult = msResult != EModelState.NEW ? EModelState.MODIFYED : EModelState.NEW;
        }
        // set back to internal model
        mIntModel = mModel;
        return msResult;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch (e.getActionCommand())
        {
            case IGlobalActionCommand.ID_DOC_LIST_ACTION:
            default:
                Log.info(this, "actionPerformed for " + e.getActionCommand());
                break;
        }
    }
    
    /**
     * Get currently selected ESex
     * 
     * @return selected ESex value
     *
     * @author Anton Nedbailo
     * @date Sep 24, 2013
     */
    public ESex getCurrentSex()
    {
        ESex sResult = ESex.MALE;
        if (cbSex != null)
        {
            sResult = ESex.fromInteger(cbSex.getSelectedIndex());
        }
        return sResult;
    }
    
    /**
     * Get currently selected date of birth
     * 
     * @return Date with seleted date of birth 
     *
     * @author Anton Nedbailo
     * @date Sep 24, 2013
     */
    public Date getCurrentDateOfBirth()
    {
        Date dResult = new Date(0);
        if (dpDateOfBirth != null)
        {
            dResult.setTime(dpDateOfBirth.getDate().getTime());
        }
        return dResult;
    }

    @Override
    protected void loadIntData()
    {
        Log.info(this, "loadIntData() called");
    }

    @Override
    protected void postShowInitData()
    {
        Log.info(this, "postShowInitData() called");
    }

    @Override
    protected void init()
    {
        Log.info(this, "init() called");
    }
}
