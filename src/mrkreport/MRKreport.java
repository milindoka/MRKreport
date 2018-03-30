package mrkreport;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import javax.swing.JFrame;

public class MRKreport

{
	static String JarFilePath;
    private static int TotalMarklists;
	private ArrayList<String> strArray = new ArrayList<String>(); //array for RollSubScheme
	private static ArrayList<String> pathArray = new ArrayList<String>(); //array containing full paths
	private static ArrayList<String> nameArray = new ArrayList<String>(); //array containing file names only
	 
	
	
	public static void main(String[] args) 
	{
		
		
	JFrame frame = new JFrame("Frame");
    frame.setSize(400, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    getJarPath();   
    GetAllFiles();
    	
	}

	
	
	 public static String getJarPath()
	    {
	    	File f = new File(System.getProperty("java.class.path"));
	     	File dir = f.getAbsoluteFile().getParentFile();
	        JarFilePath=dir.toString();
	        System.out.println(JarFilePath); 
	     	return  JarFilePath;
	     }
	 
	 public static int GetAllFiles()
	    { 
//	     getJarPath();
	  	  FilenameFilter mrkFilter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					String lowercaseName = name.toLowerCase();
					if (lowercaseName.endsWith(".mrk")) {
						return true;
					} else {
						return false;
					}
				}
			};
	  	  
	  	  pathArray.removeAll(pathArray);
	  	  nameArray.removeAll(nameArray);
	  	  File folder = new File(JarFilePath);
	  	  File[] listOfFiles = folder.listFiles(mrkFilter);
	  	      for (int i = 0; i < listOfFiles.length; i++) {
	  	        if (listOfFiles[i].isFile()) 
	  	        {  
	  	           pathArray.add(listOfFiles[i].getAbsolutePath());
	  	           nameArray.add(listOfFiles[i].getName());
	  	           System.out.println(listOfFiles[i].getName());
	  	           
	  	         } 
	  	      }
	  	  TotalMarklists=pathArray.size();
	       System.out.println(TotalMarklists);
	  	  return TotalMarklists;
	    }

	 
	
	

	
	
	
	
}
