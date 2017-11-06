package Swing.API.KNN;

import java.util.Arrays;

public class KNN_fitness
{
	private int K;
	private double data[][];
	private int dataClass[];
	private int classNum;
	
	private int dataNum;
	private int dimNum;
	// KNN
//	private int vote;
//	private double fitness[];
	private float KNN_fitness;
	private double distance[];
	private int countClass[];
	private int rightNum=0;									//分類正確的資料數量
	
	public KNN_fitness(int K,double[][] data,int[] dataClass,int dimNum,int classNum)
	{
		this.K = K;
		this.data = data;
		this.dataClass = dataClass;
//		this.dimNum = dimNum;
		this.dimNum = this.data[0].length;
			//System.out.println("dimNum: "+this.dimNum);
		this.classNum = classNum;
		dataNum = this.dataClass.length;
		distance = new double[dataNum];
		countClass = new int[this.classNum];				//K個鄰居類別統計
		
		double minTemp;										//最短距離暫存
		double min;
		double max;
		int minPosTemp;										//最近的資料點位置暫存
		int maxClassTemp;									//最大數量類別統計暫存
		
		rightNum = 0;	
		for(int i=0;i<dataNum;i++)
		{
			Arrays.fill(distance, 0);
			for(int j=0;j<dataNum;j++)
			{
				for(int k=0; k<this.dimNum; k++)
				{
					distance[j] += Math.pow((this.data[i][k] - this.data[j][k]), 2);
				}
			}
			distance[i] = Math.pow(distance[i], 0.5);
			
			
			minTemp = 0.0;
			min = 99999;
			minPosTemp = 0;
			Arrays.fill(countClass, 0);
			for(int j=0;j<this.K;j++)
			{
				for(int k=0;k<dataNum;k++)
				{
					if(k != i){
						if(distance[k] >= minTemp && distance[k] < min){
							min = distance[k];
							minPosTemp = k;					//記錄最近的資料點是第幾筆
						}
					}
				}
				countClass[this.dataClass[minPosTemp] - 1]++;	//記錄K個最近資料點的類別數量
				minTemp = min;
				min = 99999;
				distance[minPosTemp] = 99999;				//將最小距離清除, 以利尋找下一個鄰居
				minPosTemp = 0;
			}
			max = -99999;
			maxClassTemp = 0;
			for(int j=0; j<this.classNum; j++)
			{
				if(countClass[j] > max) {
					max = countClass[j];
					maxClassTemp = (j + 1);
				}
			}
			if(maxClassTemp == this.dataClass[i]) 
			{
				rightNum++;
			}
		}
		KNN_fitness = (float) rightNum / (float) dataNum;
//		System.out.println("right: "+rightNum+"		dataNum: "+dataNum);
	}
		
	public float r_KNN_fitness()
	{
		return KNN_fitness;
	}
	
}