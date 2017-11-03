package GA_KNN.featureselection.ga;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

//import featureselection.gaibpso.bpsoknn.*;
import readwrite.read.ReadDatabase;
//import readwrite.write.hgabpso.*;

public class GA_KNN extends GAsVarible
{
	private int genTemp;
	private FitnessOfKNN fok;
	
	// 另寫入新檔
	private static BufferedWriter output;
	private static String ouput_file = readPath + "\\output\\";
	private double population_fitness_best_a[];
	private int chromosome_bit_num_best_a[];
	
	public GA_KNN() throws IOException 
	{
		ouput_file = ouput_file.concat(fileName+".txt");
		
		new ReadDatabase();

		chrom = new int[POPU_NUM][dimNum];
		fit = new double[POPU_NUM];
		parent = new int[CHOOSE_NUM][dimNum];
		parentPos = new int[CHOOSE_NUM];
		offspring = new int[dimNum];
		chromNum = new int[POPU_NUM];
		genePool = new int[POOL_NUM][dimNum];
		genePoolFit = new double[POOL_NUM];
		genePoolNum = new int[POOL_NUM];
		seedParent = new int[dimNum];
		rand = new Random();
		rand.setSeed(seednum);
		
		population_fitness_best_a = new double[GEN_NUM];
		chromosome_bit_num_best_a = new int[GEN_NUM];
		
		write();
				
		System.out.println("dimNum: "+dimNum);
		System.out.println("seednum: "+seednum);
		System.out.println("crossover rate: "+XOVER_RATE);
		System.out.println("mutation rate: "+MUTATE_RATE);

		// Initial population
		new InitialPopulation();
		// feature number
		new CalculateChromNum();
		// Object function)
		fok = new FitnessOfKNN();
		fok.popuFitness();
		// Sort
		new Sort();
		
		for(int i=0; i<POPU_NUM; i++) 
		{
			output.write("The" + (i + 1) + "chromose fitness:" + fit[i]+", "+"number of features:"+chromNum[i]+"\r\n");
			System.out.println("The" + (i + 1) + "chromose fitness:" + fit[i]+", "+"number of features:"+chromNum[i]);
		}
		
		while((genTemp < GEN_NUM)) 
		{			
			population_fitness_best_a[genTemp] = fit[0];
			chromosome_bit_num_best_a[genTemp] = chromNum[0];
			genTemp++;
						
			new RouletteWheelSelection();

			/*交配(Crossover)*/
			new Crossover();			
			fok.poolFitness();			
			new CalculateGenePoolNum();			
			
			/// Replacement
			new Replacement();
			// Sort
			new Sort();
			
			// Mutation)
			new Mutation();			
			fok.signalFitness();
			// feature number
			new CalculateChromNum();
			
			new Sort();
			System.out.print(genTemp+"	"+fit[0]+"	"+chromNum[0]+"	");			
			System.out.println();
		}		
		
		for(int j=0;j<genTemp;j++)
		{
			output.write(j+"	"+population_fitness_best_a[j]+"	"+chromosome_bit_num_best_a[j]+"\r\n");
		}
				
		new Sort();
		System.out.println("The best fitness value:" + fit[0] +", number of features:" + chromNum[0]);
		
		
		ouput_file="output\\";
	}
	
	private void write() throws IOException
	{
		
		output = new BufferedWriter(new FileWriter(ouput_file));
		output.write(fileName+"\r\n");
		
		output.write("Chromosome數量: "+POPU_NUM+"\r\n");
		output.write("dimNum: "+dimNum+"\r\n");
		output.write("Iteration: "+GEN_NUM+"\r\n");
		output.write("Cross over: "+XOVER_RATE+"\r\n");
		output.write("Mutation: "+MUTATE_RATE+"\r\n");
		output.write("GA_KNN (Gain ratio) 150"+"\r\n");
		output.write("SEED = "+seednum+"\r\n");
		
		if(normalize_check==true){
			output.write("Normalize"+"\r\n");
		}else if(normalize_check==false){
			output.write("No Normalize"+"\r\n");
		}
	}
	
	public static void main(String[] args) throws IOException 
	{		
		if(fileName.endsWith(".txt")){			
			new Read_all_file();			
		}else if(fileName.endsWith(".arff")){						
			files_name_array = new String[1];
			files_name_array[0] = fileName;
		}		
		
		for(int i=0;i<files_name_array.length;i++)
		{
			fileName = files_name_array[i];			
			
			startTime = System.currentTimeMillis();
			try {
				new GA_KNN();
			} catch (IOException e) {
				// TODO 自動產生 catch 區塊
				e.printStackTrace();
			}
			
			stopTime = System.currentTimeMillis();
			alltime = stopTime - startTime;
			
			allsec_d = alltime/1000;
			day = allsec_d/86400;		allsec_d = allsec_d%86400;
			hour = allsec_d/3600;		allsec_d = allsec_d%3600;
			min = allsec_d/60;			min = min%60;
			if(min>=60){
				min = min%60;
			}
			sec = allsec_d%60;
			
			System.out.println(day+"天"+hour+"時"+min+"分"+sec+"秒");
			
			output.write(day+"天"+hour+"時"+min+"分"+sec+"秒");
			output.close();
			output = null;
			
			dataNum = 0;
			dimNum = 0;
			classNum = 0;
		}
		
	}
}
