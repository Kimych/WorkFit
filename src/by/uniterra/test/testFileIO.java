package by.uniterra.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class testFileIO
{
    public static void main(String[] args)
    {
	System.out.println("Start!");

	int n = 10;
	// create first directory
	File f = new File("TEMP123");
	f.mkdir();
	String path = f.getAbsolutePath();
	// create sub-directory
	subDirCreate(path, n);

	System.out.println("Finish!");
    }

    public static void subDirCreate(String parentPath, int count)
    {

	for (int i = 0; i < count; i++)
	{
	    String nameNewDir = Integer.toString(i);
	    File f2 = new File(parentPath, nameNewDir);
	    f2.mkdirs();
	    String path2 = f2.getAbsolutePath();
	    // try
	    // {
	    // File.createTempFile(nameNewDir, ".tmp", f2);
	    // }
	    // catch (IOException e)
	    // {
	    // e.printStackTrace();
	    // }

	    try
	    {
		new FileWriter(path2 + ".tmp");
	    } catch (IOException e)
	    {
		e.printStackTrace();
	    }
	}
    }
}
