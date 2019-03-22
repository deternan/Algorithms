package CoreNLP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;

/*
 * Simplified Chinese and Traditional Chinese
 * 
 * version: March 21, 2019 01:26 PM
 * Last revision: March 22, 2019 08:54 AM
 * 
 * Author : Chao-Hsuan Ke
 * Institute: Delta Research Center
 * Company : Delta Electronics Inc. (Taiwan)
 * 
 */

/*
 * JAR
 * ZHConverter.jar
 * 
 */

import com.spreada.utils.chinese.ZHConverter;

public class CoreNLP_Translation 
{	
	// Input
	private String folder_source = "";
	private String filename = "";
	private BufferedReader bfr;	
	
	String text = "那除了会可能之外恶意遗弃一起死还有一个非常大的这个影响的程度他甚至于有可能超越ＷＰＳ呢它是一个什么样的一个别提拿到这个包这个路爆燃共同";
	
	// output
	private String output_folder = "";
	private String output_file = "";
	private BufferedWriter writer;
	
	
	
	public CoreNLP_Translation() throws Exception
	{		
		String Line = "";
		String result_str;
		
		File folder = new File(folder_source);
		File[] listOfFiles = folder.listFiles();	
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        System.out.println(file.getName());
		        
		        output_file = file.getName();
		        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output_folder + output_file), "utf-8"));
		        result_str = "";
		        
		    	FileReader fr = new FileReader(file);
		    	bfr = new BufferedReader(fr);
		    	while((Line = bfr.readLine())!=null) 
		    	{
		    		//System.out.println(Line);
		    		result_str = Sim2Tra(Line);
		    		
		    		writer.write(result_str);					
		    	}
		    	writer.close();
		    }
		}
		
		//Sim2Tra(text);
		
		//Tra2Sim(text);
	}
	
	private void files() {
		
	}
	
	private String Sim2Tra(String input)
	{		
		ZHConverter converter = ZHConverter.getInstance(ZHConverter.TRADITIONAL);
		
		String simplifiedStr = converter.convert(input);
        System.out.println(simplifiedStr);
        
        return simplifiedStr;
	}
	
	private void Tra2Sim(String input)
	{		
		ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);

        String traditionalStr = converter.convert(input);
        System.out.println(traditionalStr);		
	}
	
	public static void main(String[] a)
	{
		try {
			CoreNLP_Translation tra = new CoreNLP_Translation();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
