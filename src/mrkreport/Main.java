package mrkreport;

public class Main 
{
	public static void main(String[] args) {    
	    MRKreport m=new MRKreport();    
	    m.getJarPath();
	    m.TotalMarklists=m.GetAllFiles();
	    m.setVisible(true);
	    m.Sort();
	    m.iterate();    
	}    


}

