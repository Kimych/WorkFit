/**
 * Filename : PersonPanelTest.java
 * 
 * * ***************************************************************************
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
 * Author : Anton Nedbailo
 * 
 * last change by : $Author:$ last check in : $Date: $
 * 
 */

package by.uniterra.test;

import javax.swing.JOptionPane;

import by.uniterra.dai.entity.Person;
import by.uniterra.system.main.SystemFactory;
import by.uniterra.system.main.UserSystemFactory;
import by.uniterra.udi.view.PersonPanel;

/**
 * The <code>PersonPanelTest</code> is used to test PersonPanel
 * 
 * @author Anton Nedbailo
 * @since Jun 25, 2014
 */
public class PersonPanelTest
{
    public static void main(String[] args)
    {
        //initialize system factory
        SystemFactory.setSystemFactory(new UserSystemFactory());
        //create a new Person model
        Person pPerson = new Person();
        pPerson.setFirstName("Антон");
        pPerson.setSecondName("Недбайло");
        pPerson.setThirdName("Кимович");
        //create Persin UDI panel
        PersonPanel objTestPanel = new PersonPanel();
        //set model
        objTestPanel.setModel(pPerson);
        //bring model data to UI components
        objTestPanel.readValues();
        //show the panel
        JOptionPane.showMessageDialog(null, objTestPanel, "Пример UDI основанного на Swing и SwingX", JOptionPane.PLAIN_MESSAGE);
    }
}
