package by.uniterra.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class testFileIO
{
    public static void main(String[] args)
    {

	System.out.println("Start!");

	while (true)
	{
	    System.out.println("Select:\n" + "1 - Create.\n" + "2 - Delete.\n"
		    + "3 - Exit.");
	    Reader r = new Reader();
	    r.Scan();
	    switch (r.s)
	    {
	    case 1:
		System.out.println("Creating...");
		// create first directory
		int n = 3; // number of folders
		System.out.println("user.home - "
			+ System.getProperty("user.home"));
		File f = new File(System.getProperty("user.home"), "TEMP123");
		f.mkdir();
		subDirCreate(f.getPath(), n);
		break;
	    case 2:
		File f2 = new File(System.getProperty("user.home"), "TEMP123");
		if (f2.exists())
		{
		    delDir(f2);
		    System.out.println("Successfully removed...");
		} else
		{
		    System.out.println("Not found...");
		}
		break;
	    case 3:
		System.out.println("Exit OK!");
		System.exit(0);
	    default:
		System.out.println("Not...");
		;
		break;
	    }
	}
    }

    public static void subDirCreate(String parentPath, int count)
    {
	for (int i = 0; i < count; i++)
	{
	    String nameNewDir = Integer.toString(i);
	    File f2 = new File(parentPath, nameNewDir);
	    f2.mkdir();
	    String path2 = f2.getPath();

	    try
	    {
		new FileWriter(path2 + ".tmp"); // create *.tmp files in TEMP123
	    } catch (IOException e)
	    {
		e.printStackTrace();
	    }

	    for (int i2 = count * (i + 1); i2 < (count * (i + 1) + count); i2++)
	    {
		// String nameNewDir2 = Integer.toString(i2);
		File f3 = new File(path2, Integer.toString(i2));
		f3.mkdir();
		String path3 = f3.getPath();

		try
		{
		    new FileWriter(path3 + ".tmp"); // create a *.tmp files in
						    // the sub-directories
		    for (int i3 = count * (i2 + 1); i3 < (count * (i2 + 1) + count); i3++)
		    {
			new FileWriter(path3 + "/" + i3 + ".tmp");
		    }

		} catch (IOException e)
		{
		    e.printStackTrace();
		}
	    }
	}
    }

    public static void delDir(File dir)
    {
	if (dir.isDirectory())
	{
	    String[] children = dir.list();
	    for (int i = 0; i < children.length; i++)
	    {
		File f = new File(dir, children[i]);
		delDir(f);
	    }
	    dir.delete();
	} else
	    dir.delete();
    }
}
