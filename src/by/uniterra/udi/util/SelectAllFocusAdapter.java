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

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

/**
 * The <code>SelectAllFocusAdapter</code> is used like a tool to select all text in an JTextField when focus gained by the component
 *
 * @author Anton Nedbailo
 * @since Sep 15, 2013
 */
public class SelectAllFocusAdapter extends FocusAdapter
{
    //members 
    private JTextComponent tcOwner;
    /**
     * 
     * Constructor.
     *
     * @param tcOwner - owner of the adapter
     */
    public SelectAllFocusAdapter(JTextComponent tcOwner)
    {
        this.tcOwner = tcOwner;
    }
    
    @Override
    public void focusGained(FocusEvent evt)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                //Select all the text in the tcOwner component
                tcOwner.selectAll();
            }
        });
    }

}
