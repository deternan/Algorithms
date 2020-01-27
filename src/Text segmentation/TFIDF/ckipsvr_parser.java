package TFIDF;

/*
 *  Filter Chinese Words
 * 
 *	Version: January 12, 2017	03:30 PM
 * 	Last revision: January 12, 2017	03:30 PM
 * 
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import Hybrid.Parameters;

public class ckipsvr_parser extends Parameters
{
	private String CKIP_output;
	
	private BufferedReader bfr;
	private StringBuffer sb = new StringBuffer();
	//private String CKIP_path;
	
	private String tag_array[];
	private String str_array[];	
	private String bracket_array[];		// bracket		
	// Regression
//	private String regEx="///([a-zA-Z]+///)";
//	private Pattern pattern;
//	private Matcher match;
	// English combination
	private int eng_max_count = 3;
	private String eng_tag = "FW";
	private String num_tag = "DET";
	
	// Output
	public Vector words;
	
	public ckipsvr_parser(String filename) throws Exception
	{
		CKIP_output = filename;
		
		words = new Vector();
		
		Read_file();
		Split();
		Separate_bracket();
		Integration();		
	}
	
	private void Read_file() throws Exception
	{
		String Line = "";
		FileReader fr = new FileReader(TFIDF_ckipoutfolder + CKIP_output);
		
		bfr = new BufferedReader(fr);		
		while((Line = bfr.readLine())!=null)
		{
			sb.append(Line+"\n");
			//System.out.println(Line);
		}
		fr.close();
		bfr.close();
	}
	
	private void Split()
	{
		tag_array = sb.toString().replaceAll("\n"," ").split("　");		
		
		str_array = new String[tag_array.length];
		bracket_array = new String[tag_array.length];			
	}
	
	private void Separate_bracket()
	{
		String bracket_str;
		String str;
		//System.out.println(tag_array.length);
		for(int i=0;i<tag_array.length;i++)
		{			
			if((tag_array[i].length()>1) && (tag_array[i]!=null)){
				//System.out.println(tag_array[i]);
				
				str = tag_array[i].substring(0, tag_array[i].indexOf("("));
				bracket_str = tag_array[i].substring(tag_array[i].indexOf("(")+1, tag_array[i].indexOf(")"));
				
				str_array[i] = str;
				bracket_array[i] = bracket_str;				
				//System.out.println(str_array[i]+"	"+bracket_array[i]);
			}
		}
	}
	
	private void Integration()
	{
		// words
		String eng_term = "";
		boolean eng_check;
		for(int i=0; i<tag_array.length - eng_max_count; i++)
		{
			eng_check = false;
			if((tag_array[i].length()>1) && (tag_array[i]!=null)){
				//System.out.println(i+"	"+str_array[i]+"	"+bracket_array[i]);
				if((bracket_array[i].equalsIgnoreCase(eng_tag)) || (bracket_array[i].equalsIgnoreCase(num_tag)))
				{
					eng_term = str_array[i];
					for(int j=1; j<=eng_max_count; j++)
					{
						//if(bracket_array[i+j].equalsIgnoreCase(eng_tag))
						if(bracket_array[i+j].equalsIgnoreCase(eng_tag))
						{
							//eng_term += str_array[i+j];
							eng_term = eng_term + " "+ str_array[i+j];
							eng_check = true;
						}else if(bracket_array[i+j].equalsIgnoreCase(num_tag)){
							eng_term = eng_term + str_array[i+j];
						}
					}
					if(eng_check == true){
						words.add(eng_term);
					}
					eng_term = "";
				}
				if(eng_check == false){
					words.add(str_array[i]);
				}		
			}			
		}

	}
	
}
