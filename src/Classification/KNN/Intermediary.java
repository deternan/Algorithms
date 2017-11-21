package KNN;

import java.io.IOException;

public class Intermediary extends Parameters
{
	public static int chooseMethod[];

	public static String datamodel_filePath;
	public static String datamodel_fileParent;
	public static String datamodel_fileName;

	public static String stringData[][];
	public static int datamodel_dataNum;
	public static int datamodel_dimNum;
	public static int datamodel_classNum;
	public static int datamodel_dataClass[];

	public static double datamodel_data[][];
	public static String datamodel_nameList[];

	// KNN
	public static int K_num;
	
	private static double classification_result;
	
	public Intermediary()
	{
		
	}
	
	public void get_paramater(String datamodel_fileParent, String datamodel_fileName) throws IOException
	{		
		this.chooseMethod = chooseMethod;
		this.datamodel_fileParent = datamodel_fileParent;	
		this.datamodel_fileName = datamodel_fileName;
		this.K_num = K;
		
		if(datamodel_fileName.endsWith(".arff")){
			datamodel_filePath = "";			
			datamodel_filePath = this.datamodel_fileParent.concat(datamodel_fileName);
			combine();
		}
		
	}
	
	public void combine() throws IOException
	{
		read_file();
		defined();
		
		stringToDouble();
		
		if(preprocess_type == 0){
			
		}else if(preprocess_type == 1){
			normalize();
		}
		
		// KNN classification
		Fitness_Manager FM = new Fitness_Manager();		
		classification_result = FM.r_population_fitness_temp_single();
		
		show_classification_result();		
	}
	
	private void read_file()
	{
		readfile rf = new readfile(datamodel_filePath);
		stringData = rf.stringData;
		datamodel_dataNum = rf.dataNum;
		datamodel_dimNum = rf.dimNum;
		datamodel_classNum = rf.classNum;
		datamodel_dataClass = rf.dataClass;
		rf = null;
	}
	
	private void defined()
	{
		datamodel_data = new double[datamodel_dataNum][datamodel_dimNum];
	}
	
	private void stringToDouble()
	{
		
		for(int i=0;i<datamodel_dataNum;i++)
		{
			for(int j=0;j<datamodel_dimNum;j++)
			{
				datamodel_data[i][j] = Double.parseDouble(stringData[i][j]);
			}
		}
	}
	
	private void normalize()
	{
		double upper = 1.0;
		double lower = 0.0;
		double dataMax[] = new double[datamodel_dimNum];
		double dataMin[] = new double[datamodel_dimNum];
		
		for (int j=0; j<datamodel_dimNum; j++) 
		{
			dataMax[j] = -100000.0;
			dataMin[j] = 100000.0;
			for (int i=0; i<datamodel_dataNum; i++) 
			{
				if (datamodel_data[i][j] > dataMax[j])
				{
					dataMax[j] = datamodel_data[i][j];
				}
				if (datamodel_data[i][j] < dataMin[j])
				{
					dataMin[j] = datamodel_data[i][j];
				}
			}
		}
		
		for (int j=0; j<datamodel_dimNum; j++) 
		{
			for (int i=0;i<datamodel_dataNum;i++)
			{
				datamodel_data[i][j] = lower + (upper - lower) * ((datamodel_data[i][j] - dataMin[j]) / (dataMax[j] - dataMin[j]));
			}
		}
	}
	
	private static void show_classification_result()
	{
		System.out.println("Classification accuacy: "+Math.rint(classification_result*10000)/100+" %");
		System.out.println("===================================");
	}

}
