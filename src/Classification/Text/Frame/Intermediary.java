package Text.Frame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Text.API.readfile;

//import Swing.API.*;

public class Intermediary
{
	public static int chooseMethod[];
	// 參數設定
	public static String filePath;
	public static String fileParent;	public static String fileParent_temp;
	public static String fileName;
	// 讀檔部份
	public static String stringData[][];
	public static int dataNum;					//	全部資料數量(幾筆?)
	public static int dimNum;					//  資料特徵數
	public static int classNum;					//  資料類別數
	public static int dataClass[];				//  每筆資料的類別
	// 相關變數
	public static double data[][];
	public static String nameList[];
	// SVM
	public static double gammaList[];	public static double gamma;
	public static double costList[];	public static double cost;
	private String SVM_String;
	private String multi_string;
	// KNN
	public static int K_num;
	
	private Classify classify;
	private static double classification_result;
	private double best_result=-1000;
	private double best_gamma=-1000;
	private double best_cost=-1000;
	
	//  另寫入新檔
	private static BufferedWriter output;
	private static String ouput_file_temp="C:\\Users\\Barry\\Desktop\\";
	private static String ouput_file="";
	private int ga_m_co;
	private int gamma_num;
	private int cost_num;
	private double classification_result_a[];
	private int g_c_temp;

	
	public Intermediary()
	{
		
	}
	
	public void get_paramater(String fileParent, String fileName, int[] chooseMethod,
			double[] gammaList,double[] costList,int K) throws IOException
	{
		Intermediary.chooseMethod = chooseMethod;
		
		Intermediary.fileParent = fileParent;	Intermediary.fileParent_temp = fileParent;
		Intermediary.fileName = fileName;
		Intermediary.gammaList = gammaList;
		Intermediary.costList = costList;
		
		Intermediary.K_num = K;
	
//		if(Intermediary.fileName.endsWith(".txt")){
//			ReadFileList rfl = new ReadFileList(Intermediary.fileParent + Intermediary.fileName);
//			nameList = rfl.returnFileName();
//			rfl = null;
//			for(int i=0;i<nameList.length;i++)
//			{
//				best_result=-1000;
//				filePath = "";
//				Intermediary.fileName = "";
//				System.out.println(nameList[i]);
//				Intermediary.fileParent_temp = fileParent;
//				Intermediary.fileName = nameList[i];
//				filePath = Intermediary.fileParent_temp.concat(Intermediary.fileName);
//				
//				combine();
//			}
//		}else if(Intermediary.fileName.endsWith(".arff")){
//			filePath = "";
//			Intermediary.fileParent_temp = fileParent;
//			filePath = Intermediary.fileParent_temp.concat(Intermediary.fileName);
//			combine();
//		}
		
		filePath = "";
		Intermediary.fileParent_temp = fileParent;
		filePath = Intermediary.fileParent_temp.concat(Intermediary.fileName);
		combine();
	}
	
	public void combine() throws IOException
	{
		read_file();
		defined();
		
		stringToDouble();
		switch(chooseMethod[3])
		{
			case 0:
				break;
			case 1:
				normalize();
				break;
		}
		
		classify = new Classify();
		
		ouput_file = "";
		ouput_file = ouput_file_temp.concat(Intermediary.fileName+".txt");

		ga_m_co = gammaList.length * costList.length;		
		classification_result_a = new double[ga_m_co];
		gamma_num = gammaList.length;
		cost_num = costList.length;
		
		if(gamma_num>cost_num){
			g_c_temp = cost_num;
		}else if(gamma_num<cost_num){
			g_c_temp = gamma_num;
		}else if(gamma_num==cost_num){
			g_c_temp = 1;
		}
		
//		System.out.println(ga_m_co);
//		System.out.println(gamma_num);
//		System.out.println(cost_num);
		
		//chooseMethod[1] //multi_string
		SVM_String = "Classifier: SVM";
		
		switch(chooseMethod[1])
		{
		case 1:
			multi_string = " (OVO)";
			SVM_String = SVM_String.concat(multi_string);
			break;
		case 2:
			multi_string = " (OVA)";
			SVM_String = SVM_String.concat(multi_string);
			break;
		}
		
		switch(chooseMethod[4])
		{
		case 0:					// SVM
			System.out.println("-----------------------");
			System.out.println(SVM_String);
			check_paramater();			

			for(int j=0;j<gammaList.length;j++)
			{
				gamma = gammaList[j];
//				check_paramater();
				for(int k=0;k<costList.length;k++)
				{
					cost = costList[k];
					System.out.println("gamma: "+gamma+"	cost: "+cost);
					Classify.calculate();
					classification_result = Classify.return_result();
					
					classification_result_a[(j*g_c_temp)+k] = classification_result;
					
					if(classification_result>=best_result){
						best_result = classification_result;
						best_gamma = gamma;
						best_cost = cost;
					}
					show_classification_result();
				}
			}
			write_SVM();
			classify = null;
			break;
		case 1:					// K-NN
			System.out.println("-----------------------");
			System.out.println("Classifier: KNN");
			System.out.println("K: "+K_num);
			gamma = 0;
			cost = 0;
			Classify.calculate();
			classification_result = Classify.return_result();
			if(classification_result>=best_result){
				best_result = classification_result;
			}
			show_classification_result();
			classify = null;
			write_KNN();
			break;
		}
		show_best_result();
		back_to_zero();
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
		System.out.println("分類正確率: "+Math.rint(classification_result*10000)/100+" %");
		System.out.println("===================================");
	}
	
	private void show_best_result()
	{
		System.out.println("最好的分類結果: "+Math.rint(best_result*10000)/100+" %");
		switch(chooseMethod[4])
		{
		case 0:
			System.out.println("gamma: "+best_gamma+"	cost: "+best_cost);
			break;
		case 1:
			break;
		}
	}
	
	private void write_KNN() throws IOException
	{
		output = new BufferedWriter(new FileWriter(ouput_file));
		output.write(fileName+"\r\n");
		output.write("Classifier: KNN"+"\r\n");
		output.write("K: "+K_num+"\r\n");
		switch(chooseMethod[3])
		{
		case 0:
			output.write("No Normalize"+"\r\n");
			break;
		case 1:
			output.write("Normalize"+"\r\n");
			break;
		}
		output.write("==================================="+"\r\n");
//		output.write("最好的分類結果: "+Math.rint(best_result*10000)/100+" %"+"\r\n");
		output.write(Math.rint(classification_result*10000)/100+" %"+"\r\n");
		output.close();
	}
	
	private void write_SVM() throws IOException
	{
		output = new BufferedWriter(new FileWriter(ouput_file));
		output.write(fileName+"\r\n");
		output.write(SVM_String+"\r\n");
		switch(chooseMethod[3])
		{
		case 0:
			output.write("No Normalize"+"\r\n");
			break;
		case 1:
			output.write("Normalize"+"\r\n");
			break;
		}
		output.write("-----------------------"+"\r\n");
		output.write("gamma	cost"+"\r\n");
		
		for(int i=0;i<gamma_num;i++)
		{
			for(int j=0;j<cost_num;j++)
			{
				output.write(gammaList[i]+"	"+costList[j]+"	"+Math.rint(classification_result_a[((i*g_c_temp)+j)]*10000)/100+" %"+"\r\n");
			}
		}
		output.write("==================================="+"\r\n");
		output.write("最好的分類結果: "+Math.rint(best_result*10000)/100+" %	"+best_gamma+"	"+best_cost+"\r\n");
		output.close();
	}
	
	private static void check_paramater()
	{
		switch(chooseMethod[1])
		{
		case 0:
			if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
				gamma = 0.001953125;
			}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
				gamma = 0.001953125;
			}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
				gamma = 0.00390625;
			}else{
				gamma = 0.0009765625;
			}
			break;
		case 1:
			if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
				gamma = 0.00390625;
			}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
				gamma = 0.0625;
			}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
				gamma = 0.00390625;
			}else if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
				gamma = 0.03125;
			}else{
				gamma = 0.0009765625;
			}
			break;
		}
		
		
	}
	
	private void back_to_zero()
	{
		for(int i=0;i<ga_m_co;i++)
		{
			classification_result_a[i] = 0;
		}
		best_gamma = 0;
		best_cost = 0;
	}
	
	
}
