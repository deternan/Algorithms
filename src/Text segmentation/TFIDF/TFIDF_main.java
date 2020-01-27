package TFIDF;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.TimeUnit;


public class TFIDF_main extends Parameters
{
	// TFIDF output
	private BufferedWriter writer;
	private Vector TFIDF_all = new Vector();	
	private Vector TFIDF_ = new Vector();
	
	public TFIDF_main() throws Exception
	{
		Source_clean();
		
		CKIP();
		Segment();		
		
		TFIDF tfidf_ = new TFIDF();					
		Map<String,List<String>> result= tfidf_.getKeywords(TFIDF_ckipoutfolder);
		//System.out.println(result);
//
//		for (Object key : result.keySet()) 
//		{
//            System.out.println(key + "	" + result.get(key)+"	"+result.get(key).size());
//            
//            for(int j=0;j<result.get(key).size();j++)
//            {
//            	//System.out.println(result.get(key).get(j).toString().trim());
//            	TFIDF_all.add(result.get(key).get(j).toString().trim());
//            }
//        }
//		
//		System.out.println(TFIDF_all.size());
//		//RemoveDuplicates_vector();
//		//Calculate_TFIDF(result);
	}
	
	private void Source_clean() throws Exception
	{
		// Read 
		String Line = "";
		
		String temp;
		File file = new File(CKIP_folder + CKIP_in + CKIP_file);
		FileReader fr = new FileReader(CKIP_folder + CKIP_in + CKIP_file);
		BufferedReader br = new BufferedReader(fr);
		while ((temp = br.readLine()) != null) {			
			Line+= temp;
		}
		//System.out.println(Line);
		fr.close();
		br.close();		
		
		// Clean
		Line = Line.replace("(", "");		Line = Line.replace("（", "");	
		Line = Line.replace(")", "");		Line = Line.replace("）", "");
		//Line = Line.replace("「", "");		Line = Line.replace("」", "");
		//System.out.println(Line);
		// Output (replace)
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(CKIP_folder + CKIP_in + CKIP_file), "utf-8"));
		writer.write(Line);
		writer.close();
	}
	
	private void CKIP() throws Exception
	{
		File f = new File(CKIP_path);
		if(f.exists() == false){
			Call_CKIP();
			// delay
			TimeUnit.SECONDS.sleep(5);			
		}
	}
	
	private void Call_CKIP()
	{
		String Jar_str = CKIP_folder + JAR_file;
		String prop_str = CKIP_folder + CKIP_prop;
		String input_str = CKIP_folder + CKIP_in;
		String output_str = CKIP_folder + CKIP_out;
		
		String CKIP_exe_path = Jar_str+" "+prop_str+" "+input_str+" "+output_str;		
		//System.out.println("Java -jar "+CKIP_exe_path);		
		
		String command = "Java -jar " + CKIP_exe_path;
		
		Process ee;
		try {
			ee = Runtime.getRuntime().exec(command, null);
			BufferedReader reader = new BufferedReader(new InputStreamReader(ee.getInputStream()));
//		    String line;
//		    while ((line = reader.readLine()) != null) 
//		    {
//		    	System.out.println(line);
//		    }
		    //System.out.println("CKIP segmentation Finished");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	private void Segment() throws Exception
	{		
		BufferedReader br = new BufferedReader(new FileReader(CKIP_path));
		StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            //System.out.println(line);
        	sb.append(line);
        }
        //System.out.println(sb.toString());
        
		Vector sentence = new Vector();
		String sentence_array[];
		
		sentence_array = sb.toString().split("(PERIODCATEGORY)");		
		for(int i=0;i<sentence_array.length;i++)
		{
			//System.out.println(sentence_array[i]);
			if(sentence_array[i].substring(0, 1).equalsIgnoreCase(")") && (sentence_array[i].length()>1)){
				sentence.add(sentence_array[i].substring(1, sentence_array[i].length())+"PERIODCATEGORY)");				
			}else{
				if(sentence_array[i].length()>1){
					sentence.add(sentence_array[i]+"PERIODCATEGORY)");
				}
				
			}
			//System.out.println(sentence_array[i]);			
		}
		
		// output
		for(int i=0;i<sentence.size();i++)
		{
			//System.out.println(sentence.get(i));
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(TFIDF_ckipoutfolder +i+".txt"), "utf-8"));
			writer.write(sentence.get(i).toString());
			writer.close();
		}		
		
	}
	
	private void RemoveDuplicates_vector()
	{
		Vector newVect = new Vector(new LinkedHashSet(TFIDF_all));
		System.out.println(TFIDF_all.size());
	}
	
	private void Calculate_TFIDF()
	{
		
	}
	
	public static void main(String[] args)
	{
		try {
			TFIDF_main tfidf = new TFIDF_main();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
