package KNN;

import java.io.IOException;

public class Intermediary extends Parameters
{
	public static int chooseMethod[];

	public static String filePath;
	public static String fileParent;	public static String fileParent_temp;
	public static String fileName;

	public static String stringData[][];
	public static int dataNum;
	public static int dimNum;
	public static int classNum;
	public static int dataClass[];

	public static double data[][];
	public static String nameList[];

	// KNN
	public static int K_num;
	
	private Classify classify;
	private static double classification_result;
	
	private double classification_result_a[];
	
	public Intermediary()
	{
		
	}
	
	public void get_paramater(String fileParent, String fileName) throws IOException
	{
		Intermediary.chooseMethod = chooseMethod;
		
		Intermediary.fileParent = fileParent;	
		//Intermediary.fileParent_temp = fileParent;
		Intermediary.fileName = fileName;		
		Intermediary.K_num = K;
	
		if(Intermediary.fileName.endsWith(".arff")){
			filePath = "";
			Intermediary.fileParent_temp = fileParent;
			filePath = Intermediary.fileParent_temp.concat(Intermediary.fileName);
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

		classify = new Classify();
		
		// KNN classification
		Classify.calculate();
		classification_result = Classify.return_result();

		show_classification_result();
		classify = null;

	}
	
	private void read_file()
	{
		readfile rf = new readfile(filePath);
		stringData = rf.stringData;
		dataNum = rf.dataNum;
		dimNum = rf.dimNum;
		classNum = rf.classNum;
		dataClass = rf.dataClass;
		rf = null;
	}
	
	private void defined()
	{
		data = new double[dataNum][dimNum];
	}
	
	private void stringToDouble()
	{
		
		for(int i=0;i<dataNum;i++)
		{
			for(int j=0;j<dimNum;j++)
			{
				data[i][j] = Double.parseDouble(stringData[i][j]);
			}
		}
	}
	
	private void normalize()
	{
		double upper = 1.0;
		double lower = 0.0;
		double dataMax[] = new double[dimNum];
		double dataMin[] = new double[dimNum];
		
		for (int j=0;j<dimNum;j++) 
		{
			dataMax[j] = -100000.0;
			dataMin[j] = 100000.0;
			for (int i=0;i<dataNum;i++) 
			{
				if (data[i][j] > dataMax[j])
				{
					dataMax[j] = data[i][j];
				}
				if (data[i][j] < dataMin[j])
				{
					dataMin[j] = data[i][j];
				}
			}
		}
		
		for (int j=0;j<dimNum;j++) 
		{
			for (int i=0;i<dataNum;i++)
			{
				data[i][j] = lower + (upper - lower) * ((data[i][j] - dataMin[j]) / (dataMax[j] - dataMin[j]));
			}
		}
	}
	
	private static void show_classification_result()
	{
		System.out.println("Classification accuacy: "+Math.rint(classification_result*10000)/100+" %");
		System.out.println("===================================");
	}

}
