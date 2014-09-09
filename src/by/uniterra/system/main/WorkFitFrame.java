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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;

import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.system.model.SystemModel;
import by.uniterra.udi.util.Log;

/**
 * The <code>WorkFitFrame</code> is used for ...
 *
 * @author Sergio Alecky
 * @since 08 сент. 2014 г.
 */
public class WorkFitFrame extends JFrame
{

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
        
        System.out.println(SystemModel.getInstance());
       // System.out.println(sm.getBool("57.srt", false));
        Log.info("Main", "Start project");
    }

    public WorkFitFrame()
    {

        super("Main frame");

    }

}
