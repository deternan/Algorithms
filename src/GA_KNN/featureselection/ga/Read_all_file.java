package GA_KNN.featureselection.ga;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import readwrite.read.ReadVarible;

public class Read_all_file extends ReadVarible
{
	private int file_num;
	private String lineStr;
	
	public Read_all_file() throws IOException
	{
		read_file_num();
		read_file_name();		
	}
	
	private void read_file_num() throws IOException
	{
		FileReader fr = new FileReader(readPath + fileName);
		BufferedReader br = new BufferedReader(fr);
		while((lineStr = br.readLine()) != null)
		{
			file_num++;	
		}
		files_name_array = new String[file_num];
	}
	
	private void read_file_name() throws IOException
	{
		int file_index=0;
		FileReader fr = new FileReader(readPath + fileName);
		BufferedReader br = new BufferedReader(fr);
		while((lineStr = br.readLine()) != null)
		{
			files_name_array[file_index++] = lineStr;
		}
	}
	
}
