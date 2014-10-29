/**
 * Filename  : MailChecker.java
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

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;







/**
 * The <code>MailChecker</code> is used for ...
 *
 * @author Sergio Alecky
 * @since 28 окт. 2014 г.
 */
public class MailChecker
{

    /**
     * @param args
     *
     * @author Sergio Alecky
     * @date 28 окт. 2014 г.
     */
    public static void main(String[] args)
    {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "pop3");
        // props.setProperty("mail.protocol.port", "995");
        props.put("mail.pop3.port", "110");

        try
        {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("mail.uniterra.by", "worklog@uniterra.by", "Ljm6StWhLLy3HBQlixdl");
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            // int iMessageCount = folder.getMessageCount();
              // get 
            Message message[] = folder.getMessages();
            for (int i = 0, n = message.length; i < n; i++)
            {

                System.out.println(i + ": " + message[i].getContentType() );
                Multipart mp = (Multipart) message[i].getContent();
                BodyPart bp = mp.getBodyPart(0);
                System.out.println("SENT DATE:" + message[i].getSentDate());
                System.out.println("SUBJECT:" + message[i].getSubject());
                System.out.println("CONTENT:" + bp.getContent());
            }

            // close
            folder.close(false);
            store.close();
            System.exit(0);

        }
        catch (Exception mex)
        {
            mex.printStackTrace();
        }
    }
}
