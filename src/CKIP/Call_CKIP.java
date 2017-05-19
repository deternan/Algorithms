package CKIP;

/*
 * Unix version
 * Call CKIP jar
 * 
 * Created Date: January 04, 2017	02:22 PM
 * Last Updated Date: May 19, 2017 09:14 AM
 * 
 * Jar
 * CKIPClient.jar
 * https://github.com/fukuball/CKIPClient-PHP
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Call_CKIP 
{	
	// CKIP
	private String CKIP_folder = "";
	private String JAR_file = "CKIPClient.jar";
	private String CKIP_prop = "ckipsocket-utf-8.propeties";
	private String CKIP_in = "test-utf-8\\in\\";
	private String CKIP_out = "test-utf-8\\out\\";;
	
	public Call_CKIP() throws Exception
	{		
		String Jar_str = CKIP_folder + JAR_file;
		String prop_str = CKIP_folder + CKIP_prop;
		String input_str = CKIP_folder + CKIP_in;
		String output_str = CKIP_folder + CKIP_out;
		
		String CKIP_exe_path = Jar_str+" "+prop_str+" "+input_str+" "+output_str;		
		//System.out.println("Java -jar "+CKIP_exe_path);		
		
		cmd("Java -jar " + CKIP_exe_path);
	}
	
	private void cmd(String cmd) throws Exception
	{			   
		Process ee = Runtime.getRuntime().exec(cmd, null);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(ee.getInputStream()));
	    String line;
	    while ((line = reader.readLine()) != null) 
	    {
	    	System.out.println(line);
	    }
	    System.out.println("Finished");
	}
	
	public static void main(String args[])
	{
		try {
			Call_CKIP CK = new Call_CKIP();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
