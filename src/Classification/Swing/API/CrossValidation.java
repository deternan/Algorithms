package Swing.API;

import Swing.API.SVM.*;

/**
 *@author Chung-Jui Tu, 1093320134@cc.kuas.edu.tw
 *@version 1.0, 08/13/2005
 */			

/**
 *@param dataNum number of data
 *@param dimNum number of dimension of data
 *@param trainSecNum number of train data of k-1 section
 *@param testSecNum number of test data of 1 section
 *@param trainSecClass class of train data of k-1 section
 *@param realTestClass class of test data of 1 section
 *@param testClass class of test data
 *@param allRate sum of rate for k fold
 *@param rate rate of 1 fold
 *@param trainSecData value of train data of k-1 section
 *@param testSecData value of test data of 1 section 
 */

public class CrossValidation
{
	private int dataNum;
	private int dimNum;
	private int trainSecNum;
	private int testSecNum;
	private final int K = 10;
	private float allRate;
	private int trainSecClass[];
	private int realTestClass[];
	private int testClass[];
	private float rate[];
	private double trainSecData[][];
	private double testSecData[][];

	/**
	 *@param chooseMethod use method of choose
 	 *@param classNum number of class of data
 	 *@param dataClass class of data
 	 *@param separate involve of pointer of data of each section
 	 *@param gammaList list of gamma value
 	 *@param costList list of cost value
 	 *@param data value of data
 	 *@param trainSecP pointer of train data of k-1 section
 	 *@param wrongClass class of wrong by classification
	 */
	public CrossValidation(int[] chooseMethod, int classNum, int[] dataClass,int[][] separate, 
							double gamma, double cost, double[][] data, String fileName)
	{
		dataNum = data.length;
		dimNum = data[0].length;
		
		int trainSecP;
		int wrongClass;

//		for(int g=0;g<gammaList.length;g++)
//		{
//			for(int c=0;c<costList.length;c++)
//			{
				allRate = (float) 0.0;
				for(int test=0;test<K;test++)
				{
					testSecNum = separate[test].length;
					trainSecNum = dataNum - testSecNum;
					trainSecClass = new int[trainSecNum];
					realTestClass = new int[testSecNum];
					rate = new float[K];
					trainSecData = new double[trainSecNum][dimNum];
					testSecData = new double[testSecNum][dimNum];
					
					/*
					 *record data value of k-1 section for train data
					 */
					trainSecP = 0;
					for(int train=0;train<K;train++)
					{
						if(train != test)
						{
							for(int i=0;i<separate[train].length;i++)
							{
								trainSecClass[trainSecP] = dataClass[separate[train][i]];
						
								for(int j=0;j<dimNum;j++)
								{
									trainSecData[trainSecP][j] = data[separate[train][i]][j];
								}
								trainSecP++;
							}
						}
					}

					/*
					 *record data value of 1 section for test data
					 */
	    			for(int i=0;i<testSecNum;i++)
	    			{
	    				realTestClass[i] = dataClass[separate[test][i]];
	    			
	    				for(int j=0;j<dimNum;j++)
	    				{
	    					testSecData[i][j] = data[separate[test][i]][j];
	    				}			
	    			}

	    			MultiClassMethod(chooseMethod, classNum, trainSecClass, gamma, cost, trainSecData, testSecData);
					
	    			/*
	    			switch(chooseMethod[0])
					{
						case 0:
							break;
						case 1:
							
							break;
						case 2:
							MultiClassMethod(chooseMethod, classNum, trainSecClass, gamma, 
											cost, trainSecData, testSecData);
							break;
					}
					*/
			
					wrongClass = 0;
					for(int i=0;i<testSecNum;i++)
					{
						if(testClass[i] != realTestClass[i])
						{
							wrongClass++;
						}					
					}
					rate[test] = (float)(testSecNum - wrongClass) / (float)testSecNum;
					allRate = allRate + rate[test];
					
	//				System.out.println("class of wrong:"+wrongClass);
	//				System.out.println("rate of "+test+" fold:"+rate[test]);
				}
				allRate = (float) (allRate / 10.0);
	//			System.out.println("rate of all:"+allRate);
		
				//WriteOutput wo = new WriteOutput();
				//wo.writeRate(g, c, partical, partical_t, frequency_t, allRate, fileName);
//			}
			
//		}
	}
	
	/**
	 *@param chooseMethod use method of choose
	 *@param classNum number of class of data
	 *@param trainSecClass class of train data of k-1 section
	 *@param gamma value of gamma
	 *@param cost value of cost
	 *@param trainSecData value of train data of k-1 section
	 *@param testSecData value of test data of 1 section
	 */
	public void MultiClassMethod(int[] chooseMethod, int classNum, int[] trainSecClass, 
								double gamma, double cost, double[][] trainSecData, double[][] testSecData)
	{
		switch(chooseMethod[1])
		{
			case 0:
				break;
			case 1:	
				OneAgainstOne oao = new OneAgainstOne(chooseMethod, classNum, trainSecClass, 
													gamma, cost, trainSecData, testSecData);
				testClass = oao.returnTestClass();
				oao = null;
				break;
			case 2:
				OneAgainstAll oaa = new OneAgainstAll(chooseMethod, classNum, trainSecClass, 
													gamma, cost, trainSecData, testSecData);
				testClass = oaa.returnTestClass();
				oaa = null;
				break;
		}
	}
	
	/**
	 *@return allRate sum of rate for k fold
	 */
	public float returnAllRate()
	{
		return allRate;
	}
}