package Swing.API.SVM;

/**
 *@author Chung-Jui Tu, 1093320134@cc.kuas.edu.tw
 *@version 1.0, 08/13/2005
 */

import java.io.*;
import java.util.*;

/**
 *@param trainNum number of train data
 *@param testNum number of test data
 *@param dimNum number of dimension of data
 *@param testClass class of test data
 *@param alpha alpha is multiplier, i.e. support vector
 *@param deltaAlpha adjust alpha
 *@param z error of training
 *@param decisionValue calculate value of objective function
 *@param kernel kernel function
 *@param D power of polynomial kernel function
 *@param FREQUENCY run times of kernel adatron algorithms
 *@param ETA learning rate of kernel adatron algorithms
 */
public class KASVM
{
	private int trainNum;
	private int testNum;
	private int dimNum;
	private final int D = 3;
	private final int FREQUENCY_MAX = 100;
	private final double ETA = 0.1;
	private int testClass[];
	private double alpha[];
	private double deltaAlpha[];
	private double z[];
	private double decisionValue[];
	private double kernel[][];
	
	/**
	 *@param trainClass class of train data
	 *@param gamma value of gamma
	 *@param cost value of cost
	 *@param trainData value of train data
	 *@param testData value of test data
	 */
	public KASVM(int[] trainClass, double gamma, double cost, double[][] trainData, double[][] testData, String nowSVM)
	{
		trainNum = trainData.length;
		testNum = testData.length;
		dimNum = trainData[0].length;
		
		testClass = new int[testNum];
		alpha = new double[trainNum];
		deltaAlpha = new double[trainNum];
		z = new double[trainNum];
		decisionValue = new double[testNum];
		
		train(trainClass, gamma, cost, trainData, nowSVM);
		test(trainClass, gamma, trainData, testData);
	}
	
	/**
	 *@param trainClass class of train data
	 *@param gamma value of gamma
	 *@param cost value of cost
	 *@param trainData value of train data
	 *@param nowSVM which one SVM of category now
	 *@param frequency run times of algorithms
	 *@param margin maximize margin of SVM
	 *@param zMin minimize value of z
	 *@param zMax maximize value of z
	 */
	public void train(int[] trainClass, double gamma, double cost, double[][] trainData, String nowSVM)
	{
		int frequency = 1;
		double margin = 0.0;
		double zMin;
		double zMax;
		
		kernel = new double[trainNum][trainNum];

		Arrays.fill(alpha,0.0);
		Arrays.fill(deltaAlpha,0.0);
		
		/*
		 *kernel adatron algorithms
		 */
		
		RBFKernel(gamma, trainData, trainData);
		/*
		switch(chooseMethod[6])
		{
			case 0:
				RBFKernel(gamma, trainData, trainData);
				break;
			case 1:
				PolynomialKernel(gamma, trainData, trainData);
				break;
		}
		*/
		
		while(((margin > 1.005) || (margin < 0.995)) && (frequency <= FREQUENCY_MAX))
		{
			for(int i=0;i<trainNum;i++)
			{
				z[i] = 0.0;
				for(int j=0;j<trainNum;j++)
				{
					/*
					 *z(i) = Sigma(j=1~l)(alpha(j) * y(j) * k(x(i), x(j))
					 */
					z[i] += alpha[j] * (double)trainClass[j] * kernel[i][j];
				}
				
				/*
				 *deltaAlpha(i) = eta * (1 - (z(i) * y(i))
				 **/
				deltaAlpha[i] = ETA * (1.0 - (z[i] * (double)trainClass[i]));
				
				/*
				 *If (alpha(i) + deltaAlpha(i)) <= 0, then alpha(i) = 0
				 *If (alpha(i) + deltaAlpha(i)) > cost, then alpha(i) = cost
				 *If (alpha(i) + deltaAlpha(i)) > 0, then alpha(i) = alpha(i) + deltaAlpha(i)
				 */
				if((alpha[i] + deltaAlpha[i]) <= 0.0)
				{
					alpha[i] = 0.0;
				}
				else if((alpha[i] + deltaAlpha[i]) > cost)
				{
					alpha[i] = cost;
				}
				else if((alpha[i] + deltaAlpha[i]) > 0)
				{
					alpha[i] = alpha[i] + deltaAlpha[i];
				}
			}

			/*
			 *margin = 0.5 * (zMin(y = +1) - zMax(y = -1))
			 */
			zMax = -10000;
			zMin = 10000;
			for(int i=0;i<trainNum;i++)
			{
				if(trainClass[i] == -1)
				{
					if(z[i] > zMax)
					{
						zMax = z[i];
					}
				}
				else if(trainClass[i] == 1)
				{
					if(z[i] < zMin)
					{
						zMin = z[i];
					}
				}
			}
			margin = 0.5 * (zMin - zMax);
			
			frequency++;
		}
		
		//System.out.println("which one SVM:"+nowSVM);
		//System.out.println("maximize of margin:"+margin);

		//System.out.println("maximize of frequency:"+(frequency - 1));
		
	}
	
	/**
	 *@param trainClass class of train data
	 *@param gamma value of gamma
	 *@param trainData value of train data
	 *@param testData value of test data
	 */
	public void test(int[] trainClass, double gamma, double[][] trainData, double[][] testData)
	{
		kernel = new double[testNum][trainNum];
		
		RBFKernel(gamma, testData, trainData);
		/*
		switch(chooseMethod[6])
		{
			case 0:
				RBFKernel(gamma, testData, trainData);
				break;
			case 1:
				PolynomialKernel(gamma, testData, trainData);
				break;
		}
		*/
		
		for(int i=0;i<testNum;i++)
		{
			/*
			 *decisionValue(i) = Sigma(j=1~l)(alpha(j) * y(j) * k(x(i), x(j))
			 */
			decisionValue[i] = 0.0;
			for(int j=0;j<trainNum;j++)
			{
				decisionValue[i] += alpha[j] * (double)trainClass[j] * kernel[i][j];
			}
			
			if(decisionValue[i] > 0.0)
			{
				testClass[i] = 1;
			}
			else if(decisionValue[i] <= 0.0)
			{
				testClass[i] = -1;
			}
		}
	}
	
	/**
	 *@param gamma value of gamma
	 *@param data1 value of data i
	 *@param data2 value of data j
	 *@param xSquare quadratic of x
	 */
	public void RBFKernel(double gamma, double[][] data1, double[][] data2)
	{
		double xSquare;
		
		for(int i=0;i<data1.length;i++)
		{
			for(int j=0;j<data2.length;j++)
			{
				xSquare = 0.0;
				for(int k=0;k<dimNum;k++)
				{
					xSquare += Math.pow((data1[i][k] - data2[j][k]), 2);
				}
				kernel[i][j] = Math.exp(-(gamma * xSquare));
			}
		}
	}
	
	/**
	 *@param data1 value of data i
	 *@param data2 value of data j
	 *@param dot between dimension of data1 and dimension of data2
	 */
	public void PolynomialKernel(double gamma, double[][] data1, double[][] data2)
	{
		double dot;
		
		for(int i=0;i<data1.length;i++)
		{
			for(int j=0;j<data2.length;j++)
			{
				dot = 0.0;
				for(int k=0;k<dimNum;k++)
				{
					dot += data1[i][k] * data2[j][k];
				}
				kernel[i][j] = Math.pow((dot * gamma + 1),D);
			}
		}
	}
	
	/**
	 *@return testClass class of test data
	 */
	public int[] returnTestClass()
	{
		return testClass;
	}
	
	/**
	 *
	 */
	public double[] returnDecisionValue()
	{
		return decisionValue;
	}
}