package Swing.Frame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import Swing.API.*;

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
	public static int classNum;				//  資料類別數
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
	private double best_result;
	private double best_gamma;
	private double best_cost;
	
	//  另寫入新檔
	private static BufferedWriter output;
	private static String ouput_file_temp="Swing\\output\\";
	private static String ouput_file="";
	private int ga_m_co;
	private int gamma_num;
	private int cost_num;
	private double classification_result_a[];
	private int g_c_temp;
	// Random
	public static Random rand;
	public static int SEED_num; 
	
	public Intermediary()
	{
		
	}
	
	public void get_paramater(String fileParent, String fileName, int[] chooseMethod,
			double[] gammaList,double[] costList,int K, int seed_num) throws IOException
	{
		Intermediary.chooseMethod = chooseMethod;
		
		Intermediary.fileParent = fileParent;	Intermediary.fileParent_temp = fileParent;
		Intermediary.fileName = fileName;
		Intermediary.gammaList = gammaList;
		Intermediary.costList = costList;
		
		Intermediary.K_num = K;
		
		Intermediary.SEED_num = seed_num;
		
		rand = new Random();
		rand.setSeed(SEED_num);
	
		if(Intermediary.fileName.endsWith(".txt")){
			ReadFileList rfl = new ReadFileList(Intermediary.fileParent + Intermediary.fileName);
			nameList = rfl.returnFileName();
			rfl = null;
			for(int i=0;i<nameList.length;i++)
			{
				best_result=-1000;
				filePath = "";
				Intermediary.fileName = "";
				System.out.println(nameList[i]);
				Intermediary.fileParent_temp = fileParent;
				Intermediary.fileName = nameList[i];
				filePath = Intermediary.fileParent_temp.concat(Intermediary.fileName);
				
				combine();
			}
		}else if(Intermediary.fileName.endsWith(".arff")){
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
			//g_c_temp = cost_num;
			g_c_temp = 1;
		}else if(gamma_num<cost_num){
			//g_c_temp = gamma_num;
			g_c_temp = 0;
		}else if((gamma_num==1) && (cost_num==1)){
			g_c_temp = 1;
		}else if((gamma_num == cost_num) && (cost_num!=1)){
			g_c_temp = gammaList.length;
		}
		
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
		
		System.out.println("SEED = "+SEED_num);
//		System.out.println("Chi-square");
		
		switch(chooseMethod[4])
		{
		case 0:					// SVM
			System.out.println("-----------------------");
			System.out.println(SVM_String);
			check_paramater();			

			for(int j=0;j<gammaList.length;j++)
			{
				gamma = gammaList[j];
				check_paramater();
				for(int k=0;k<costList.length;k++)
				{
					cost = costList[k];
					check_paramater();
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
			if(classification_result > best_result){
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
		output.write("dimNum: "+dimNum+"\r\n");
		output.write("Classifier: KNN"+"\r\n");
		output.write("K: "+K_num+"\r\n");
		output.write("SEED = "+SEED_num+"\r\n");
		
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
		output.write("dimNum: "+dimNum+"\r\n");
		output.write(SVM_String+"\r\n");
		output.write("SEED = "+SEED_num+"\r\n");
		output.write("Chi-square"+"\r\n");
		
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
		
		if((gammaList.length == 1)&&(costList.length == 1)){
			output.write("gamma: "+gamma+"	cost: "+cost+"\r\n");
		}else{
			for(int i=0;i<gamma_num;i++)
			{
				for(int j=0;j<cost_num;j++)
				{
					output.write(gammaList[i]+"	"+costList[j]+"	"+Math.rint(classification_result_a[((i*g_c_temp)+j)]*10000)/100+" %"+"\r\n");
				}
			}
		}

		
		output.write("==================================="+"\r\n");
		output.write("最好的分類結果: "+Math.rint(best_result*10000)/100+" %	"+best_gamma+"	"+best_cost+"\r\n");
		output.close();
	}
	
	private static void check_paramater()
	{
		
//		IG();
//		CFS();
//		Relief_f();
//		Gain_Ratio();
		Chi_Squire();
	}
	
	private static void IG()
	{
		/*
		// IG (OVA)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.03125;		
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 0.0625;				
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.0625;				
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 0.03125;				
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.0078125;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 0.0625;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		*/
		
		
		// IG (50)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.0625;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 0.0625;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 2.0;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.5;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 2.0;
		}else if(fileName.equalsIgnoreCase("Leukemia1_GEMS.arff")){
			gamma = 0.0625;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 0.0625;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 0.5;
			cost = 4.0;
		}else if(fileName.equalsIgnoreCase("SRBCT_GEMS.arff")){
			gamma = 0.001953125;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.03125;
			cost = 16.0;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 0.5;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		
		
		/*
		// IG (100)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.25;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 0.0625;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 2.0;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.015625;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 1.0;
		}else if(fileName.equalsIgnoreCase("Leukemia1_GEMS.arff")){
			gamma = 0.03125;
			cost = 4.0;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 0.001953125;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 0.25;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.0625;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 0.015625;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		*/
		
		/*
		// IG (150)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 0.03125;
			cost = 16.0;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 0.5;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 16.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.5;
		}else if(fileName.equalsIgnoreCase("Leukemia1_GEMS.arff")){
			gamma = 0.001953125;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 1.0;
			cost = 1.0;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.0625;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 0.0625;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		*/
		
		/*
		// IG (200)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.0625;
			cost = 4.0;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 0.0625;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 0.25;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 16.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("Leukemia1_GEMS.arff")){
			gamma = 0.25;
			cost = 0.25;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 0.0078125;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 0.125;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 0.015625;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		*/
		
		/*
		// IG (250)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.015625;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma =0.0078125;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 0.25;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.0625;
		}else if(fileName.equalsIgnoreCase("Leukemia1_GEMS.arff")){
			gamma = 0.25;
			cost = 0.5;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 0.5;
			cost = 2.0;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.001953125;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 0.015625;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		*/
	}
	
	private static void CFS()
	{
		
		/*
		// CFS (OVA)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 2.0;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 1.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.0625;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.0625;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 0.00390625;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.0078125;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 0.015625;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		*/
	}
	
	private static void Relief_f()
	{
		
		// Relief
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 0.0625;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.0625;
		}else if(fileName.equalsIgnoreCase("Leukemia1_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.0078125;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 0.0625;
			cost = 16.0;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		
		
		/*
		// Relief-F (50)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 1.0;
			cost = 1.0;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 0.25;
			cost = 16.0;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 0.125;
			cost = 16.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.0625;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.0078125;
			cost = 16.0;
		}else if(fileName.equalsIgnoreCase("Leukemia1_GEMS.arff")){
			gamma = 0.0078125;
			cost = 4.0;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 0.0078125;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 0.03125;
			cost = 16;
		}else if(fileName.equalsIgnoreCase("SRBCT_GEMS.arff")){
			gamma = 0.001953125;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 2.0;
			cost = 1.0;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 2.0;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		*/
		
		/*
		// Relief-F (100)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.0625;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 0.125;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 0.125;
			cost = 32.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.0625;
			cost = 4.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.03125;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Leukemia1_GEMS.arff")){
			gamma = 0.0078125;
			cost = 4.0;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 0.001953125;
			cost = 16.0;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 0.5;
			cost = 4.0;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.015625;
			cost = 16.0;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 2.0;
			cost = 0.25;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		*/
		
		/*
		// Relief-F (150)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.03125;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 0.0625;
			cost = 4.0;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 0.0625;
			cost = 32.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.0625;
			cost = 16.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.03125;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Leukemia1_GEMS.arff")){
			gamma = 0.001953125;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			cost = 16.0;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 0.0625;
			cost = 4.0;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.03125;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 1.0;
			cost = 1.0;
		}
		
		// Relief-F (200)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.03125;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 0.0625;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 0.0625;
			cost = 16.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.015625;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.015625;
			cost = 16.0;
		}else if(fileName.equalsIgnoreCase("Leukemia1_GEMS.arff")){
			gamma = 0.001953125;
			cost = 4.0;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 0.0625;
			cost = 0.5;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 0.015625;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.015625;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 0.03125;
			cost = 4.0;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		*/
		
		/*
		// Relief-F (250)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.015625;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 0.0625;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 0.03125;
			cost = 16.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.015625;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.25;
		}else if(fileName.equalsIgnoreCase("Leukemia1_GEMS.arff")){
			gamma = 0.00390625;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 0.0625;
			cost = 0.5;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.015625;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 0.0625;
			cost = 2.0;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		*/
	}
	
	private static void Gain_Ratio()
	{
		// Grain_Ration
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.03125;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 0.0625;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.00390625;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.0078125;
			cost = 1.0;
		}else if(fileName.equalsIgnoreCase("Leukemia1_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 0.00390625;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.001953125;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		
		/*
		// Grain_Ration (50)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.0625;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 2.0;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 2.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.125;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 1.0;
		}else if(fileName.equalsIgnoreCase("Leukemia1_GEMS.arff")){
			gamma = 0.015625;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 0.001953125;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 0.5;
		}else if(fileName.equalsIgnoreCase("SRBCT_GEMS.arff")){
			gamma = 0.03125;
			cost = 1024.0;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 4.0;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		*/
		
		
		// Grain_Ration (100)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 0.0625;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 0.5;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.125;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.5;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 2.0;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.0078125;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 0.03125;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		
		
		/*
		// Grain_Ration (150)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.03125;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 0.125;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 1.0;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.5;
			cost = 2.0;
		}else if(fileName.equalsIgnoreCase("Leukemia1_GEMS.arff")){
			gamma = 0.0078125;
			cost = 4.0;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 0.0078125;
			cost = 4.0;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 0.015625;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 0.0078125;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		/*
		
		/*
		// Grain_Ration (200)
		 if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.015625;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 0.0625;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 0.5;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.015625;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.25;
			cost = 2.0;
		}else if(fileName.equalsIgnoreCase("Leukemia1_GEMS.arff")){
			gamma = 0.00390625;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			cost = 0.0078125;
			cost = 4.0;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 0.125;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 0.0078125;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		 */
		 
		 /*
		// Grain_Ration (250)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 0.5;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.0078125;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.0625;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 1.0;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 0.125;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.0078125;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 0.00390625;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		*/
	}
	
	private static void Chi_Squire()
	{
		
		// Chi-Squire
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.03125;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.00390625;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.0078125;
			cost = 1.0;
		}else if(fileName.equalsIgnoreCase("Leukemia1_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 0.00390625;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.001953125;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		
		/*
		// Chi_Squire (50)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.0625;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 0.125;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 1.0;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.125;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.0625;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Leukemia1_GEMS.arff")){
			gamma = 0.03125;
			cost = 4.0;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 8.0;
		}else if(fileName.equalsIgnoreCase("SRBCT_GEMS.arff")){
			gamma = 2.0;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.25;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 0.125;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		*/
		
		/*
		// Chi_Squire (100)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.5;
			cost = 2.0;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 0.25;
			cost = 2.0;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 2.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.0625;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.03125;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Leukemia1_GEMS.arff")){
			gamma = 0.0078125;
			cost = 4.0;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 0.015625;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 0.0625;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 0.0625;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		*/
		/*
		// Chi_Squire (150)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 0.125;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 1.0;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.0625;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("Leukemia1_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 0.00390625;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 1.0;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.0078125;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 0.0625;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		*/
		/*
		// Chi_Squire (200)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.0625;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 0.015625;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 0.5;
			cost = 16.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.25;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("Leukemia2_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 0.0125;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 0.03125;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 0.0625;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		*/
		/*
		// Chi_Squire (250)
		if(fileName.equalsIgnoreCase("9_Tumors_GEMS.arff")){
			gamma = 0.015625;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("11_Tumors_GEMS.arff")){
			gamma = 0.015625;
		}else if(fileName.equalsIgnoreCase("14_Tumors_GEMS.arff")){
			gamma = 0.5;
			cost = 8.0;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor1_GEMS.arff")){
			gamma = 0.015625;
		}else if(fileName.equalsIgnoreCase("Brain_Tumor2_GEMS.arff")){
			gamma = 0.00390625;
		}else if(fileName.equalsIgnoreCase("Lung_Cancer_GEMS.arff")){
			gamma = 0.001953125;
		}else if(fileName.equalsIgnoreCase("Prostate_Tumor_GEMS.arff")){
			gamma = 16.0;
		}else if(fileName.equalsIgnoreCase("DLBCL_GEMS.arff")){
			gamma = 0.0625;
			cost = 1.0;
		}else{
			gamma = 0.0009765625;
			cost = 4096;
		}
		*/
	}
	
	
	private void back_to_zero()
	{
		for(int i=0;i<ga_m_co;i++)
		{
			classification_result_a[i] = 0;
		}
		best_result = Double.MIN_VALUE;
	}
	
	
}
