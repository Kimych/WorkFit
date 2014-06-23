package by.uniterra.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class testFileIO
{
    public static void main(String[] args)
    {
	System.out.println("Start!");

	int n = 5; // number of folders
	
	// create first directory
	File f = new File("TEMP123");
	f.mkdir();

	String path3 = f.getPath();
	// create sub-directory
	subDirCreate(path3, n);
	
	System.out.println("Finish!");
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

	    for (int i2 = 0; i2 < count ; i2++)
	    {
		String nameNewDir2 = Integer.toString(i2);
		File f3 = new File(path2, nameNewDir2);
		f3.mkdir();
		String path3 = f3.getPath();
		try
		{
		    new FileWriter(path3 + ".tmp"); // create a *.tmp files in
						    // the sub-directories
		    for (int i3 = 0; i3 < count; i3++)
		    {
			new FileWriter(path3 + "/" + i3 + ".tmp"); // create a
								   // *.tmp
								   // files in
								   // the last
								   // folders
		    }

		} catch (IOException e)
		{
		    e.printStackTrace();
		}

	    }

	}
    }

}
