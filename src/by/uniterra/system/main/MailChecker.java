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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import by.uniterra.system.iface.IGlobalProperties;
import by.uniterra.system.model.SystemModel;
import by.uniterra.system.util.DateUtils;
import by.uniterra.udi.util.Log;
import by.uniterra.udi.util.LogParser;

/**
 * The <code>MailChecker</code> is used for read new mail
 *
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
    private static String SERVER = SystemModel.getString(IGlobalProperties.ML_SERVER, "");
    private static String EMAIL = SystemModel.getString(IGlobalProperties.ML_EMAIL, "");
    private static String PASSWORD = SystemModel.getString(IGlobalProperties.ML_PWD, "");
    private static int HOUR_PR = SystemModel.getInt(IGlobalProperties.MC_HOUR, 0);
    private static int MINUTE_PR = SystemModel.getInt(IGlobalProperties.MC_MINUTE, 0);
    private static int INTERVAL_PR = SystemModel.getInt(IGlobalProperties.MC_INTERVAL, 30);

    private static long INTERVAL = DateUtils.ONE_MINUTE * INTERVAL_PR;

    private static int sleepTime = 300000;

    public static void main(String[] args)
    {
        SystemModel.initJPA();
        
        Log.info(MailChecker.class,"Mail checker started");
        
        MailChecker mcEngine = new MailChecker();
        // initial check
        mcEngine.checkMail();

        Calendar calNow = Calendar.getInstance();
        calNow.set(Calendar.HOUR_OF_DAY, HOUR_PR);
        calNow.set(Calendar.MINUTE, MINUTE_PR);
        long lLastMailCheck = calNow.getTimeInMillis();

        // check in additional every CHACK_MAIL_INTERVAL
        while (!Thread.interrupted())
        {
            try
            {
                Log.info(MailChecker.class,"Mail cheker in process... " + DateUtils.toUTC(System.currentTimeMillis()));
                Log.info(MailChecker.class,"Last check " + DateUtils.toUTC(lLastMailCheck) + ", check interval = " + INTERVAL/60000 + " mins");
                
                if (System.currentTimeMillis() - lLastMailCheck > INTERVAL)
                {
                    lLastMailCheck = System.currentTimeMillis();
                    mcEngine.checkMail();
                    Log.info(MailChecker.class, "Mail checked:" + DateUtils.toUTC(lLastMailCheck));
                }
                Log.info(MailChecker.class,"Next check after minutes: " + (INTERVAL + lLastMailCheck - System.currentTimeMillis()) / 60000 + " minutes.");
                Thread.sleep(sleepTime);
            }
            catch (InterruptedException e)
            {
                Log.error(MailChecker.class, e);
                System.exit(1);
            }
        }

    }

    public void checkMail()
    {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imap");
        props.put("mail.imap.port", "143");

        try
        {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect(SERVER, EMAIL, PASSWORD);
            Folder inbox = store.getFolder("INBOX");
            // ---!!!!----
            inbox.open(Folder.READ_WRITE);
            // ----!!!!----
            // search for all "unseen" messages
            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
            Message message[] = inbox.search(unseenFlagTerm);
            for (int i = 0, n = message.length; i < n; i++)
            {
                Object objMp = message[i].getContent();
                if (objMp instanceof Multipart)
                {
                    findLogInMultipart((Multipart) objMp);
                }
                else if (objMp instanceof String)
                {
                    createFileFromMail(((String) objMp).getBytes());
                }
                Log.info(MailChecker.class, "SENT DATE:" + DateUtils.toGMT(message[i].getSentDate()));
            }
            // close
            inbox.close(false);
            store.close();
        }
        catch (Exception mex)
        {
            mex.printStackTrace();
        }
    }

    private boolean createFileFromMail(byte[] strToSave) throws IOException
    {
        boolean bResult = false;
        Path path = Paths.get(new File("").getAbsolutePath() + File.separatorChar + "temp.txt");
        // 1 save to temp file
        try
        {
            Files.write(path, strToSave, StandardOpenOption.CREATE);
        }
        catch (IOException e)
        {
            Log.error(MailChecker.class, e, "createFileFromMail error ");
        }
        // parse worklog, insert into DB and copy into archive folder
        LogParser.processWorklogFile(path);

        try
        {
            Files.delete(path);
        }
        catch (IOException e)
        {
            Log.error(MailChecker.class, e, "createFileFromMail error ");
        }
        return bResult;
    }

    private boolean findLogInMultipart(Multipart multiPart) throws MessagingException, IOException
    {
        int numberOfParts = multiPart.getCount();
        for (int partCount = 0; partCount < numberOfParts; partCount++)
        {
            BodyPart part = (BodyPart) multiPart.getBodyPart(partCount);
            Object objBodyPartContent = part.getContent();
            if (objBodyPartContent instanceof Multipart)
            {
                if (findLogInMultipart((Multipart) objBodyPartContent))
                {
                    return true;
                }
            }
            else
            {
                if (objBodyPartContent instanceof byte[])
                {
                    if (createFileFromMail((byte[]) objBodyPartContent))
                    {
                        return true;
                    }
                }
                if (objBodyPartContent instanceof String)
                {
                    if (createFileFromMail(((String) objBodyPartContent).getBytes()))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
