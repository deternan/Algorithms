package GA_KNN.featureselection.ga;

import java.util.*;

public class FitnessOfKNN extends GAsVarible 
{
	private final int K = 1;			// K-nearest neighbor
	private int rightNum;				// correct class numbers
	private int countClass[] = new int[classNum];
	private double distance[] = new double[dataNum];
	private double fitTemp;
	
	public void popuFitness() 
	{	
		for(int p=0; p<POPU_NUM; p++) 
		{
			loocvForPopu(p);
		}
	}
		
	public void poolFitness() 
	{	
		for(int i=0; i<POOL_NUM; i++) 
		{
			loocvForPool(i);
		}
	}
		
	public double signalFitness() 
	{
		loocvForSignal();
	
		return fitTemp;
	}

	public void loocvForSignal() 
	{
		double minTemp;	
		double min;
		double max;
		int minPosTemp;	
		int maxClassPosTemp;
		
		rightNum = 0;
		for(int i=0; i<dataNum; i++) {
			Arrays.fill(distance, 0);
			for(int j=0; j<dataNum; j++) {
				for(int k=0; k<dimNum; k++) {
					if(chrom[POPU_NUM - 1][k] == 1) {
						distance[j] += Math.pow((data[i][k] - data[j][k]), 2);
					}
				}
				distance[j] = Math.pow(distance[j], 0.5);
			}
			minTemp = 0.0;
			min = 99999;
			minPosTemp = 0;
			Arrays.fill(countClass, 0);
			for(int j=0; j<K; j++) {
				for(int k=0; k<dataNum; k++) {
					if(k != i) {
						if(distance[k] >= minTemp && distance[k] < min) {
							min = distance[k];
							minPosTemp = k;	
						}
					}
				}
				countClass[classValue[minPosTemp] - 1]++;
				minTemp = min;
				min = 99999;
				distance[minPosTemp] = 99999;
				minPosTemp = 0;
			}
			max = -99999;
			maxClassPosTemp = 0;
			for(int j=0; j<classNum; j++) {
				if(countClass[j] > max) {
					max = countClass[j];
					maxClassPosTemp = (j + 1);
				}
			}
			if(maxClassPosTemp == classValue[i]) {
				rightNum++;
			}
		}
		fitTemp = (double) rightNum / (double) dataNum;
	}
	
	public void loocvForPopu(int p) 
	{
		double minTemp;	
		double min;
		double max;
		int minPosTemp;	
		int maxClassPosTemp;
		
		rightNum = 0;	
		for(int i=0; i<dataNum; i++) {
			Arrays.fill(distance, 0);
			for(int j=0; j<dataNum; j++) {
				for(int k=0; k<dimNum; k++) {
					if(chrom[p][k] == 1) {	
						distance[j] += Math.pow((data[i][k] - data[j][k]), 2);
					}
				}
				distance[j] = Math.pow(distance[j], 0.5);
			}
			minTemp = 0.0;
			min = 99999;
			minPosTemp = 0;
			Arrays.fill(countClass, 0);
			for(int j=0; j<K; j++) {
				for(int k=0; k<dataNum; k++) {
					if(k != i) {
						if(distance[k] >= minTemp && distance[k] < min) {
							min = distance[k];
							minPosTemp = k;	
						}
					}
				}
				countClass[classValue[minPosTemp] - 1]++;	
				minTemp = min;
				min = 99999;
				distance[minPosTemp] = 99999;	
				minPosTemp = 0;
			}
			max = -99999;
			maxClassPosTemp = 0;
			for(int j=0; j<classNum; j++) {
				if(countClass[j] > max) {
					max = countClass[j];
					maxClassPosTemp = (j + 1);
				}
			}
			if(maxClassPosTemp == classValue[i]) {
				rightNum++;
			}
		}
		fit[p] = (double) rightNum / (double) dataNum;
	}
	
	public void loocvForPool(int p) 
	{
		double minTemp;	
		double min;
		double max;
		int minPosTemp;	
		int maxClassPosTemp;
		
		rightNum = 0;	
		for(int i=0; i<dataNum; i++) {
			Arrays.fill(distance, 0);
			for(int j=0; j<dataNum; j++) {
				for(int k=0; k<dimNum; k++) {
					if(genePool[p][k] == 1) {	
						distance[j] += Math.pow((data[i][k] - data[j][k]), 2);
					}
				}
				distance[j] = Math.pow(distance[j], 0.5);
			}
			minTemp = 0.0;
			min = 99999;
			minPosTemp = 0;
			Arrays.fill(countClass, 0);
			for(int j=0; j<K; j++) {
				for(int k=0; k<dataNum; k++) {
					if(distance[k] > minTemp && distance[k] < min) {
						min = distance[k];
						minPosTemp = k;	
					}
				}
				countClass[classValue[minPosTemp] - 1]++;	
				minTemp = min;
				min = 99999;
				minPosTemp = 0;
			}
			max = -99999;
			maxClassPosTemp = 0;
			for(int j=0; j<classNum; j++) {
				if(countClass[j] > max) {
					max = countClass[j];
					maxClassPosTemp = (j + 1);
				}
			}
			if(maxClassPosTemp == classValue[i]) {
				rightNum++;
			}
		}
		genePoolFit[p] = (double) rightNum / (double) dataNum;
	}
	
}
