package SpeechToText.iflytek;

/*
 * 
 * version: January 09, 2019 05:19 PM
 * Last revision: January 25, 2019 02:11 PM
 * 
 * Author : Chao-Hsuan Ke
 * Institute: Delta Research Center
 * Company : Delta Electronics Inc. (Taiwan)
 * 
 * JAR
 * java-json.jar
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonParser 
{
	// Read file
	private String read_file_path = "";
	private String read_file_name = "";
	private BufferedReader bfr;
	
	private String Tag_onebest = "onebest";
	
	// Write output
	private String output_folder = read_file_path;
	private String output_file = "";
	BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output_folder + output_file), "utf-8"));
	
	public JsonParser() throws Exception
	{
		String Line = "";
		FileReader fr = new FileReader(read_file_path + read_file_name);
		BufferedReader bfr = new BufferedReader(fr);
		
		
		Line = bfr.readLine();
		//System.out.println(Line);		
		JSONArray objArray = new JSONArray(Line);
		System.out.println(objArray.get(0));
		
		for(int i=0; i<objArray.length(); i++)
		{
			//System.out.println(objArray.get(i));
			JSONObject obj = new JSONObject(objArray.get(i).toString());
			
			System.out.println(obj.get(Tag_onebest));
			
			writer.write(obj.get(Tag_onebest)+"\n");
		}
		
		bfr.close();
		
		writer.close();
	}	
	
	public static void main(String args[])
	{
		try {
			JsonParser jp = new JsonParser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
