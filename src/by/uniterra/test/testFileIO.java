package by.uniterra.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class testFileIO
{
    //constants
    private static final String DEFAULT_TEMP_FOLDER = "TEMP123";
    public static final String DEFAULT_FILE_EXTENSION = ".tmp";
    public static final int FOLDERS_COUNT = 3;

    public static void main(String[] args)
    {
        System.out.println("Start!");
        //get default user directory
        String strUserHome = System.getProperty("user.home");
        try (Scanner scScanner = new Scanner(System.in)) //feature of Java 8, called "try with resources" (http://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html)
        {
            while(true)
            {
                System.out.println("Select:\n" + "1 - Create.\n" + "2 - Delete.\n" + "3 - Exit.");
                switch(scScanner.nextInt())
                {
                    case 1:
                        System.out.println("Creating...");
                        // create first directory
                        System.out.println("user.home - " + strUserHome);
                        File f = new File(strUserHome, DEFAULT_TEMP_FOLDER);
                        f.mkdir();
                        subDirCreate(f.getPath(), FOLDERS_COUNT);
                        break;
                    case 2:
                        File f2 = new File(strUserHome, DEFAULT_TEMP_FOLDER);
                        if(f2.exists())
                        {
                            delDir(f2);
                            System.out.println(f2.getName() + " successfully removed.");
                        }
                        else
                        {
                            System.out.println(f2.getName() + " not found.");
                        }
                        break;
                    case 3:
                        System.out.println("Exit OK!");
                        System.exit(0);
                    default:
                        System.out.println("Not...");
                        break;
                }
            }
        }
    }
    
    public static void subDirCreate(String parentPath, int count)
    {
        for(int i = 0; i < count; i++)
        {
            String nameNewDir = Integer.toString(i);
            File f2 = new File(parentPath, nameNewDir);
            f2.mkdir();
            String path2 = f2.getPath();

            try
            {
                new FileWriter(path2 + DEFAULT_FILE_EXTENSION).close(); // create
                                                                        // *.tmp
                                                                        // files
                                                                        // in
                                                                        // TEMP123
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

            for(int i2 = count * (i + 1); i2 < (count * (i + 1) + count); i2++)
            {
                // String nameNewDir2 = Integer.toString(i2);
                File f3 = new File(path2, Integer.toString(i2));
                f3.mkdir();
                String path3 = f3.getPath();

                try
                {
                    new FileWriter(path3 + DEFAULT_FILE_EXTENSION).close(); // create
                                                                            // a
                                                                            // *.tmp
                                                                            // files
                                                                            // in
                    // the sub-directories
                    for(int i3 = count * (i2 + 1); i3 < (count * (i2 + 1) + count); i3++)
                    {
                        new FileWriter(path3 + "/" + i3 + DEFAULT_FILE_EXTENSION).close();
                    }

                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void delDir(File dir)
    {
        if(dir.isDirectory())
        {
            String[] children = dir.list();
            for(int i = 0; i < children.length; i++)
            {
                delDir(new File(dir, children[ i ]));
            }
        }
        //delete himself
        dir.delete();
    }
}
