/**
 * Filename  : SelectAllFocusAdapter.java
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

package by.uniterra.udi.util;

import java.awt.AWTKeyStroke;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.DateFormatter;
import javax.swing.text.JTextComponent;
import javax.swing.text.NumberFormatter;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXComboBox;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import by.uniterra.system.main.SystemFactory;
import by.uniterra.system.util.DateUtils;

import com.toedter.calendar.IDateEditor;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

/**
 * 
 * The <code>UIUtils</code> is used for UI components creation and initialisation
 *
 * @author Anton Nedbailo
 * @since Sep 19, 2013
 */
public class UIUtils
{
    //constants
    public static final int LARGE_FIELD_WIDTH_PREFFERED = 120;
    public static final int MEDIUM_FIELD_WIDTH_PREFFERED = 60;
    public static final int SMALL_FIELD_WIDTH_PREFFERED = 20;
    
    static
    {
        //check a flag for "Enter as Tab"
        if (SystemFactory.getSysBool("udi.view.tranferfocustonenter"))
        {
            // 1. Get default keys
            Set<AWTKeyStroke> ftk = new HashSet<AWTKeyStroke>(KeyboardFocusManager.getCurrentKeyboardFocusManager().getDefaultFocusTraversalKeys(
                    KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));

            // 2. Add our key
            ftk.add(KeyStroke.getKeyStroke("ENTER"));

            // 3. Set new keys
            KeyboardFocusManager.getCurrentKeyboardFocusManager().setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, ftk);
        }
    }
    /**
     * Create JXTextField
     * 
     * @param strName - initial text
     * @param iMaxWidth- max width size
     * 
     * @return initialized JXTextField
     *
     * @author Anton Nedbailo
     * @date Sep 19, 2013
     */
    public static JXTextField createTextField(String strText, int iMaxWidth)
    {
        return createTextField(strText, iMaxWidth, "");
    }
    
    /**
     * 
     * Create JXTextField
     * 
     * @param strName - initial text
     * @param iMaxWidth - max width size
     * @param strPrompt - prompt text
     * 
     * @return initialized JXTextField
     * @date 15 ���. 2013 �.
     */
    public static JXTextField createTextField(String strText, int iMaxWidth, String strPrompt)
    {
        JXTextField tfResult = new JXTextField();
        //set text
        if (strText != null && !strText.isEmpty())
        {
            tfResult.setText(strText);
        }
        //set prefeffed width
        if (iMaxWidth > 0)
        {
            tfResult.setPreferredSize(new Dimension(iMaxWidth, tfResult.getPreferredSize().height));
        }
        //set prompt text
        if (strPrompt != null && !strPrompt.isEmpty())
        {
            tfResult.setPrompt(strPrompt);
        }
        //add focus listener
        tfResult.addFocusListener(new SelectAllFocusAdapter(tfResult));
        return tfResult; 
    }
    
    /**
     * Create JXComboBox
     * 
     * @param strActionCommand - action command name
     * @param alListener - action listener
     * @param iPreferredWidth - max width size
     * 
     * @return initialized JXComboBox
     *
     * @author Anton Nedbailo
     * @date Sep 19, 2013
     */
    public static JXComboBox createXComboBox(String strActionCommand, ActionListener alListener, int iPreferredWidth)
    {
        return createXComboBox(strActionCommand, alListener, iPreferredWidth, false);
    }
    
    /**
     * Create editable JXComboBox
     * 
     * @param strActionCommand - action command name
     * @param alListener - action listener
     * @param iPreferredWidth - max width size
     * 
     * @return initialized JXComboBox
     *
     * @author Anton Nedbailo
     * @date Sep 21, 2013
     */
    public static JXComboBox createEditableXComboBox(String strActionCommand, ActionListener alListener, int iPreferredWidth)
    {
        return createXComboBox(strActionCommand, alListener, iPreferredWidth, true);
    }
    
    /**
     * Create editable JComboBox
     * 
     * @param strActionCommand - action command name
     * @param alListener - action listener
     * @param iPreferredWidth - max width size
     * 
     * @return initialized JComboBox
     *
     * @author Anton Nedbailo
     * @date 26 ���. 2013 �.
     */
    public static JComboBox<?> createEditableComboBox(String strActionCommand, ActionListener alListener, int iPreferredWidth)
    {
        JComboBox<?> cbResult = new JComboBox<Object>();
        //set preffered width
        if (iPreferredWidth != 0)
        {
            cbResult.setPreferredSize(new Dimension(iPreferredWidth, cbResult.getPreferredSize().height));
        }
        //set action command
        if (strActionCommand != null && !strActionCommand.isEmpty())
        {
            cbResult.setActionCommand(strActionCommand);
        }
        //check editable flag
        cbResult.setEditable(true);
        //add action listener
        if (alListener != null)
        {
            cbResult.addActionListener(alListener);
        }
        return cbResult;
    }
    
    /**
     * 
     * @param strActionCommand - action command name
     * @param alListener - action listener
     * @param iPreferredWidth - max width size
     * @param bEditable - if true - create with editable mode, false - use regular mode with autocomplete
     * 
     * @return initialized JXComboBox
     *
     * @author Anton Nedbailo
     * @date 15 ���. 2013 �.
     */
    private static JXComboBox createXComboBox(String strActionCommand, ActionListener alListener, int iPreferredWidth, boolean bEditable)
    {
        JXComboBox cbResult = new JXComboBox();
        //set preffered width
        if (iPreferredWidth != 0)
        {
            cbResult.setPreferredSize(new Dimension(iPreferredWidth, cbResult.getPreferredSize().height));
        }
        //set action command
        if (strActionCommand != null && !strActionCommand.isEmpty())
        {
            cbResult.setActionCommand(strActionCommand);
        }
        //check editable flag
        if (bEditable)
        {
            cbResult.setEditable(true);
        } else
        {
            AutoCompleteDecorator.decorate(cbResult);
        }
        //add action listener
        if (alListener != null)
        {
            cbResult.addActionListener(alListener);
        }
        return cbResult;
    }
    
    /**
     * 
     * Create JXComboBox
     * 
     * @param strActionCommand - action command name
     * @param alListener - action listener
     * 
     * @return initialized JXTextField
     *
     * @author Anton Nedbailo
     * @date Sep 20, 2013
     */
    public static JXComboBox createXComboBox(String strActionCommand, ActionListener alListener)
    {
        return  createXComboBox(strActionCommand, alListener, 0);
    }
    
    /**
     * Create JXComboBox
     * 
     * @param iPreferredWidth - preferred width size
     * 
     * @return initialized JXTextField
     *
     * @author Anton Nedbailo
     * @date Sep 20, 2013
     */
    public static JXComboBox createXComboBox(int iPreferredWidth)
    {
        return  createXComboBox("", null, iPreferredWidth);
    }
    
    /**
     * Create and return new JXButton
     * 
     * @param strText - title
     * @param strActionCommand - action command
     * @param alListener - ActionListener
     * 
     * @return a new instance of JXButton
     *
     * @author Anton Nedbailo
     * @date Sep 20, 2013
     */
    public static JXButton createButton(String strText, String strActionCommand, ActionListener alListener)
    {
        return createButton(null, strText, strActionCommand, alListener);
    }
    
    /**
     * Create and return new JXButton
     * 
     * @param icoCion icon for the button
     * @param strText - title
     * @param strActionCommand - action command
     * @param alListener - ActionListener
     * 
     * @return a new instance of JXButton
     *
     * @author Anton Nedbailo
     * @date Sep 20, 2013
     */
    public static JXButton createButton(ImageIcon icoImage, String strText, String strActionCommand, ActionListener alListener)
    {
        return createButton(icoImage, strText, strActionCommand, alListener, "");
    }
    
    /**
     * Create and return new JXButton
     * 
     * @param icoCion icon for the button
     * @param strText - title
     * @param strActionCommand - action command
     * @param alListener - ActionListener
     * @param strTooltip - tooltip for the button
     * 
     * @return a new instance of JXButton
     *
     * @author Anton Nedbailo
     * @date Sep 20, 2013
     */
    public static JXButton createButton(ImageIcon icoImage, String strText, String strActionCommand, ActionListener alListener, String strTooltip)
    {
        JXButton btnNewButton = new JXButton();
        //set icon
        if (icoImage != null)
        {
            btnNewButton.setIcon(icoImage);
        }
        //set text 
        if (strText != null && !strText.isEmpty())
        {
            btnNewButton.setText(strText);
        }
        //set action command
        if (strActionCommand != null && !strActionCommand.isEmpty())
        {
            btnNewButton.setActionCommand(strActionCommand);
        }
        //set action listener
        if (alListener != null)
        {
            btnNewButton.addActionListener(alListener);
        }
        //set tooltip
        if (strTooltip!= null && !strTooltip.isEmpty())
        {
            btnNewButton.setToolTipText(strTooltip);
        }
        return btnNewButton;
    }
    
    /**
     * Create a date selection component 
     * @param dfFormat - used format patter for the component
     * @param bEnableSelectOnFocus - if true - all contect will be selected on focus gained action
     * 
     * @return initialozed JDateChooser
     *
     * @author Anton Nedbailo
     * @date 29 ���. 2013 �.
     * 
     */
    public static JDateChooser createDatePicker(Date dateInitial, String dfFormat, int iPrefWidth,  boolean bEnableSelectOnFocus)
    {
        TimeZone tzSystemTimeZone = SystemFactory.getSystemFactory().getSystemModel().getTimezone();
        JDateChooser dChooser = new JDateChooser(new JCalendar(Calendar.getInstance(tzSystemTimeZone)), dateInitial, dfFormat, null);// new UTDateChooser(tzSystemTimeZone, dateInitial, dfFormat);
        dChooser.setMaxSelectableDate(new Date(System.currentTimeMillis() + DateUtils.ONE_YEAR * 20));
        if (iPrefWidth != 0)
        {
            dChooser.setPreferredSize(new Dimension(iPrefWidth, dChooser.getPreferredSize().height));
        }
        //check "select all on focus gained" flag
        if (bEnableSelectOnFocus)
        {
            IDateEditor teEditor = dChooser.getDateEditor();
            if (teEditor instanceof JTextComponent)
            {
                JTextComponent tcComponent = (JTextComponent) teEditor;
                tcComponent.addFocusListener(new SelectAllFocusAdapter(tcComponent));
            }
        }
        return dChooser;
    }
    
    /**
     * Create a date selection component 
     * @param dfFormat - used format patter for the component
     * 
     * @return initialozed JDateChooser
     *
     * @author Anton Nedbailo
     * @date 29 ���. 2013 �.
     * 
     */
    public static JDateChooser createDatePicker(Date dateInitial, String dfFormat, int iPrefWidth)
    {
        return createDatePicker(dateInitial, dfFormat, iPrefWidth, false);
    }
    
    /**
     * Create a date formatted text filed
     * @param dfFormat - DateFormatter for the field
     * @param  - enable or not transferring a focus on ENTER key events
     * 
     * @return created JFormattedTextField object
     *
     * @author Anton Nedbailo
     * @date 17.10.2013
     */
    public static JFormattedTextField createFormattedDateField(DateFormat dfFormat, boolean bOverwriteMode)
    {
        DateFormatter dfDateFormatter = new DateFormatter(dfFormat);
        dfDateFormatter.setValueClass(Date.class);
        dfDateFormatter.setOverwriteMode(bOverwriteMode);
        // If you want the value to be committed on each keystroke instead of focus lost
        dfDateFormatter.setCommitsOnValidEdit(true);
        JFormattedTextField ftfResult = new JFormattedTextField(dfDateFormatter);
        //contract from
        return ftfResult;
    }
    
    /**
     * Create formatted text field for some sum values
     * 
     * @param alActionListener - action listener
     * @param flFocusListener - focus listener
     * @param strActionCommand - action command
     * @param bUseGrouping - use grouping
     * 
     * @return initialized JFormattedTextField object
     *
     * @author Anton Nedbailo
     * @date 18.10.2013
     */
    public static JFormattedTextField createFormattedSumField(ActionListener alActionListener, PropertyChangeListener pclPropertyListener, String strActionCommand, boolean bUseGrouping) //SystemFactory.getSysBool("udi.view.usegroupingintextfields")
    {
        // create decimal format
        DecimalFormat format = new DecimalFormat();
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(0);
        // custom symbol
        DecimalFormatSymbols customSymbols = new DecimalFormatSymbols();
        customSymbols.setGroupingSeparator(' ');
        format.setDecimalFormatSymbols(customSymbols);
        format.setGroupingUsed(bUseGrouping);
        // If you want the value to be committed on each keystroke instead of focus lost
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Double.class);
        formatter.setAllowsInvalid(false);
        // If you want the value to be committed on each keystroke instead of focus lost
        formatter.setCommitsOnValidEdit(true);
        JFormattedTextField tfResult = new JFormattedTextField(formatter);
        tfResult.setHorizontalAlignment(SwingUtilities.RIGHT);
        if (strActionCommand != null && !strActionCommand.isEmpty())
        {
            tfResult.setActionCommand(strActionCommand);
        }
        //add action listener
        if (alActionListener != null)
        {
            tfResult.addActionListener(alActionListener);
        }
        // select all text on focus gained event
        tfResult.addFocusListener(new SelectAllFocusAdapter(tfResult));
        //add other focus listener (to react on each "value changed" event)
        if (pclPropertyListener != null)
        {
            tfResult.addPropertyChangeListener("value", pclPropertyListener);
        }
        return tfResult;
    }
    
    /**
     * Create a popup menu item
     * 
     * @param strTitle - title
     * @param strActionCommand - action command
     * @param alListener - action listener
     * @return JMenuItem
     *
     * @author Anton Nedbailo
     * @date 22 ���. 2013 �.
     */
    public static JMenuItem createMenuItem(String strTitle, String strActionCommand, ActionListener alListener)
    {
        JMenuItem deleteItem = new JMenuItem();
        //set title
        if (strTitle != null && !strTitle.isEmpty())
        {
            deleteItem.setText(strTitle); 
        }
        //set action command
        if (strActionCommand != null && !strActionCommand.isEmpty())
        {
            deleteItem.setActionCommand(strActionCommand);
        }
        //set action listener
        if (alListener != null)
        {
            deleteItem.addActionListener(alListener);
        }
        return deleteItem;
    }
}
