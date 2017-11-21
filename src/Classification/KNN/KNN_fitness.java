package KNN;

import java.util.Arrays;

public class KNN_fitness extends Parameters
{	
	private int dataNum;
//	private int dimNum;
	// KNN
	private float KNN_fitness;
	private double distance[];
	private int countClass[];
	private int rightNum = 0;
	
	public KNN_fitness(double[][] data,int[] dataClass,int datamodel_dimNum,int datamodel_classNum)
	{				
		dataNum = dataClass.length;
		distance = new double[dataNum];				
		countClass = new int[datamodel_classNum];
		
		double minTemp;									
		double min;
		double max;
		int minPosTemp;									
		int maxClassTemp;								
		
		rightNum = 0;
				
		for(int i=0; i<dataNum; i++)
		{
			Arrays.fill(distance, 0);
			for(int j=0; j<dataNum; j++)
			{				
				for(int k=0; k<datamodel_dimNum; k++)
				{					
					distance[j] += Math.pow((data[i][k] - data[j][k]), 2);	
				}				
			}
			
			distance[i] = Math.pow(distance[i], 0.5);
			
			minTemp = 0.0;
			min = 99999;
			minPosTemp = 0;
			Arrays.fill(countClass, 0);
			for(int j=0; j<K; j++)
			{
				for(int k=0; k<dataNum; k++)
				{
					if(k != i){
						if(distance[k] >= minTemp && distance[k] < min){
							min = distance[k];
							minPosTemp = k;					
						}
					}
				}
				
				countClass[dataClass[minPosTemp] - 1]++;
				minTemp = min;
				min = 99999;
				distance[minPosTemp] = 99999;				
				minPosTemp = 0;
			}
			max = -99999;
			maxClassTemp = 0;
						
			for(int j=0; j<datamodel_classNum; j++)
			{
				if(countClass[j] > max) {
					max = countClass[j];
					maxClassTemp = (j + 1);
				}
			}
			System.out.println(i+"	Predicted: "+maxClassTemp+"	original: "+dataClass[i]);
			if(maxClassTemp == dataClass[i])
			{
				rightNum++;
			}
		}
		KNN_fitness = (float) rightNum / (float) dataNum;
		
		//System.out.println("right: "+rightNum+"		dataNum: "+dataNum);
	}
		
	public float r_KNN_fitness()
	{
		return KNN_fitness;
	}
	
}