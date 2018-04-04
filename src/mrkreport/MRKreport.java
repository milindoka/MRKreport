package mrkreport;


import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;    

public class MRKreport extends JFrame{    
   JLabel jb;
	 int PageTotal=0;
	 String strlog="";
	 String newLine = System.getProperty("line.separator");
	  private String JarFilePath;
	    public int TotalMarklists;
	    private ArrayList<String> strArray = new ArrayList<String>(); //array for RollSubScheme
		private ArrayList<String> pathArray = new ArrayList<String>(); //array containing full paths
		private ArrayList<String> nameArray = new ArrayList<String>(); //array containing file names only
		private String Division,Examination, Subject, Examiner,Date,MaxMarks;	    
	    public String getJarPath()
	    {
	    	File f = new File(System.getProperty("java.class.path"));
	     	File dir = f.getAbsoluteFile().getParentFile();
	        JarFilePath=dir.toString();
	     	return  JarFilePath;
	    }
	
MRKreport()
{
 Font tr = new Font("courier", Font.PLAIN,20);
jb=new JLabel(" ");    
jb.setForeground(Color.BLUE);

add(jb);    
setSize(550,60);
setBackground(Color.yellow);
getContentPane().setBackground(Color.yellow);

setLocationRelativeTo(null);
setUndecorated(true);    
jb.setFont(tr);
}    
public void iterate()
{
int curfile=0;
while(curfile<TotalMarklists)
{    
	PageTotal=0;
 	LoadMarkListFileToStrArray(curfile);
 	ExtractAllHeaderFields(curfile);
    String str=String.format(" %4d/%d :  %s",curfile+1,TotalMarklists,nameArray.get(curfile));
    jb.setText(str); 
    curfile=curfile+1;    
  try{Thread.sleep(300);}catch(Exception e){}    
} 
try{Thread.sleep(1000);}catch(Exception e){}
SaveReport();
System.exit(0);
}    


public int GetAllFiles()
{ 
// getJarPath();
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

public void LoadMarkListFileToStrArray(int currentindex)
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


public void ExtractAllHeaderFields(int currentindex)
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
    strlog=strlog+Pad(nameArray.get(currentindex),40,"-");
	strlog=strlog+String.format(" : %d",PageTotal);
	strlog=strlog+newLine;

}

public void SaveReport()
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


	public static String Pad(String str, int size, String string)
	{
	  StringBuffer padded = new StringBuffer(str);
	  while (padded.length() < size)
	  {
	    padded.append(string);
	  }
	  return padded.toString();
	}






}    
