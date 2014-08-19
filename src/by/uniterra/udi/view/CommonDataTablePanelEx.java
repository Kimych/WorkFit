/**
 * Filename  : CommonDataTablePanelEx.java
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
 * Author     : Anton Nedbailo
 *
 * last change by : $Author:$ 
 * last check in  : $Date: $
 */

package by.uniterra.udi.view;

import javax.swing.JButton;

import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.udi.iface.IModelOwner;
import by.uniterra.udi.model.AbstractFlexTableModel;

/**
 * The <code>CommonDataTablePanelEx</code> is used to extend CommonDataTablePanel with button editor for special column
 *
 * @author Anton Nedbailo
 * @since Aug 19, 2014
 */
public class CommonDataTablePanelEx extends CommonDataTablePanel
{
    private static final long serialVersionUID = -1548181817789707172L;
    
    public CommonDataTablePanelEx(AbstractFlexTableModel atmModel, IModelOwner moPanel, ServiceBaseEAO<?> eaoCommon, IModelOwner moButtonEditor)
    {
	super(atmModel, moPanel, eaoCommon);
	// customize table
        CompCellRendered ccrRenderer = new CompCellRendered("...", moButtonEditor);
        tTable.setDefaultEditor(JButton.class, ccrRenderer);
        tTable.setDefaultRenderer(JButton.class, ccrRenderer);
    }
}
