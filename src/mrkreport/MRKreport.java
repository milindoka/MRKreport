package mrkreport;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

public class MRKreport

{
	static String JarFilePath;
	static int PageTotal=0;
	static String strlog="";
	static String newLine = System.getProperty("line.separator");
	private static String Division,Examination, Subject, Examiner,Date,MaxMarks;
    private static int TotalMarklists;
	private static ArrayList<String> strArray = new ArrayList<String>(); //array for RollSubScheme
	private static ArrayList<String> pathArray = new ArrayList<String>(); //array containing full paths
	private static ArrayList<String> nameArray = new ArrayList<String>(); //array containing file names only
	 
	
	
	public static <LoadMarkListFileToSt> void main(String[] args) 
	{
	JFrame frame = new JFrame("Frame");
    frame.setSize(400, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    getJarPath();   
    int totalfiles=GetAllFiles();
    for(int curfile=0;curfile<totalfiles;curfile++)
     {  PageTotal=0;
    	LoadMarkListFileToStrArray(curfile);
    	ExtractAllHeaderFields(curfile);
     }
    SaveReport();
    
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
	  	         } 
	  	      }
	  	  TotalMarklists=pathArray.size();
	       System.out.println(TotalMarklists);
	  	  return TotalMarklists;
	    }
	

	    public static void ExtractAllHeaderFields(int currentindex)
	    {	String temp[],stemp;
		    stemp=strArray.get(7);
		    temp=stemp.split(":");
		   // Errors.removeAll(Errors);
		    int TotalSets=Integer.parseInt(temp[1].replaceAll("[^0-9.]",""));
	    
		    stemp=strArray.get(13+3*TotalSets); temp=stemp.split(":");
	    	Examiner=temp[1].trim();
	    	
	    	stemp=strArray.get(15+3*TotalSets); temp=stemp.split(":");
	    	Division=temp[1].trim();
	    	
	    	stemp=strArray.get(17+3*TotalSets); temp=stemp.split(":");
	    	Subject=temp[1].trim();
	   
	    	
	    	stemp=strArray.get(18+3*TotalSets); temp=stemp.split(":");
	    	Examination=temp[1].trim();
	    	
	    	stemp=strArray.get(19+3*TotalSets); temp=stemp.split(":");
	    	MaxMarks=temp[1].trim();
	    	stemp=strArray.get(20+3*TotalSets); temp=stemp.split(":");
	    	Date=temp[1].trim();

			for(int i=28+3*TotalSets;i<strArray.size();i++) 
			{
			stemp=strArray.get(i); temp=stemp.split(":");
			String strmarks=temp[1].trim();
			if(strmarks.length()==0) continue;
			if(strmarks.contains("AB")) continue;
			int intmarks=Integer.parseInt(strmarks);
			PageTotal+=intmarks;
			 
			}
			//System.out.println(nameArray.get(currentindex)+String.format(":  %d", PageTotal));
			strlog=strlog+nameArray.get(currentindex);
			strlog=strlog+String.format(" : %d", PageTotal);
	        strlog=strlog+newLine;
	    }
			
		
	    public static void LoadMarkListFileToStrArray(int currentindex)
	    {       strArray.removeAll(strArray);
	        	BufferedReader reader=null;
	    		try {
	    			reader = new BufferedReader(new FileReader(pathArray.get(currentindex)));
	    		} catch (FileNotFoundException e1) 
	    		{
	    		
	    			e1.printStackTrace();
	    		}
	     				
	    		String line = null;
	        	try { while ((line = reader.readLine()) != null) 
	    			{
	    			 
	    			 strArray.add(line);
	    			}
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	         }


    	public static void SaveReport()
{
	 //String newLine = System.getProperty("line.separator");

    	  FileWriter f0=null;
	    	try { 
	    		  f0 = new FileWriter(JarFilePath+"/MRKreport.txt");	
                  f0.write(strlog);
	    		  f0.close();
	    	
	    	     } 
	    	  catch (IOException e1)
	    	   {e1.printStackTrace();	}

    	
    
    
      }






}




	
	
	
	

