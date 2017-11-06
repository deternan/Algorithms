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
	private int rightNum=0;									//�������T����Ƽƶq
	
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
		countClass = new int[this.classNum];				//K�ӾF�~���O�έp
		
		double minTemp;										//�̵u�Z���Ȧs
		double min;
		double max;
		int minPosTemp;										//�̪񪺸���I��m�Ȧs
		int maxClassTemp;									//�̤j�ƶq���O�έp�Ȧs
		
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
							minPosTemp = k;					//�O���̪񪺸���I�O�ĴX��
						}
					}
				}
				countClass[this.dataClass[minPosTemp] - 1]++;	//�O��K�ӳ̪����I�����O�ƶq
				minTemp = min;
				min = 99999;
				distance[minPosTemp] = 99999;				//�N�̤p�Z���M��, �H�Q�M��U�@�ӾF�~
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