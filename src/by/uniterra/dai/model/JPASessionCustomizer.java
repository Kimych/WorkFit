/**
 * Filename  : JPASessionCustomizer.java
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

package by.uniterra.dai.model;

import java.sql.Connection;

import org.eclipse.persistence.dynamic.DynamicHelper.SessionCustomizer;
import org.eclipse.persistence.internal.databaseaccess.DatabaseAccessor;
import org.eclipse.persistence.sessions.Session;
import org.eclipse.persistence.sessions.SessionEvent;
import org.eclipse.persistence.sessions.SessionEventAdapter;

import com.mysql.jdbc.MySQLConnection;

/**
 * The <code>JPASessionCustomizer</code> is used to configure DataBase
 * connection
 *
 * @author Anton Nedbailo
 * @since Aug 26, 2014
 */
public class JPASessionCustomizer extends SessionCustomizer
{
    /**
     * 
     * The <code>AMCSessionAdapter</code> is used do some actions on session
     * events
     *
     * @author Anton Nedbailo
     * @since Aug 26, 2014
     */
    private class AMCSessionAdapter extends SessionEventAdapter
    {
	@Override
	public void postConnect(SessionEvent event)
	{
	    try
	    {
		// get connection
		Connection con = ((DatabaseAccessor) event.getResult()).getConnection();
		if (con instanceof MySQLConnection)
		{
		    // disable JDBC time zone conversion
		    ((MySQLConnection) con).setUseJDBCCompliantTimezoneShift(true);
		}
	    } catch (Exception e)
	    {
		System.out.println("Can't adjust driver timezone parameters.");
	    }
	}
    }

    @Override
    public void customize(Session s) throws Exception
    {
	// Add listener for EclipseLink events.
	s.getEventManager().addListener(new AMCSessionAdapter());
    }
}
