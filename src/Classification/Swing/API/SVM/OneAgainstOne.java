package Swing.API.SVM;

/**
 *@author Chung-Jui Tu, 1093320134@cc.kuas.edu.te
 *@version 1.0, 08/13/2005
 */

/**
 *@param trainSecNum number of train data of k-1 section
 *@param testSecNum number of test data of 1 section
 *@param dimNum number of dimension of data
 *@param trainNum number of train data
 *@param decisionClass decision the last class of test data
 *@param trainClass class of train data
 *@param testClass class of test data
 *@param trainData value of train data
 *@param nowSVM which one SVM of category now
 */


public class OneAgainstOne
{
	private int trainSecNum;
	private int testSecNum;
	private int dimNum;
	private int trainNum;
	private int decisionClass;
	private int trainClass[];
	private int testClass[];
	private double trainData[][];
	private String nowSVM;
	
	/**
	 *@param chooseMethod use method of choose
	 *@param classNum number of class of data
	 *@param trainSecClass class of train data of k-1 section
	 *@param gamma value of gamma
	 *@param cost value of cost
	 *@param trainSecData value of train data of k-1 section
	 *@param testSecData value of test data of 1 section
	 *@param classCountMax choose classification count of maximize for each test data 
	 *@param classCount record classification count of each test data
	 */
	public OneAgainstOne(int[] chooseMethod, int classNum, int[] trainSecClass, double gamma, 
						double cost, double[][] trainSecData, double[][] testSecData)
	{
		trainSecNum = trainSecData.length;
		testSecNum = testSecData.length;
		dimNum = trainSecData[0].length;
		
		int classCountMax;
		int classCount[][];
		
		classCount = new int[testSecNum][classNum];
		
		for(int class1=0;class1<classNum;class1++)
		{
			for(int class2=class1+1;class2<classNum;class2++)
			{
				/*
				 *each two class belongs to one SVM
				 */
				separateSVMs(class1+1, class2+1, trainSecNum, dimNum, trainSecClass, trainSecData);

				switch(chooseMethod[0])
				{
					case 0:
						break;
					case 1:
						nowSVM = Integer.toString(class1 + 1) + Integer.toString(class2 + 1);
						KASVM svm = new KASVM(trainClass, gamma, cost, trainData, testSecData, nowSVM);
						testClass = svm.returnTestClass();
						svm = null;
						break;
					case 2:
//						nowSVM = Integer.toString(class1 + 1) + Integer.toString(class2 + 1);
//						FuzzyKASVM fsvm = new FuzzyKASVM(trainClass, gamma, cost, trainData, testSecData, nowSVM);
//						testClass = fsvm.returnTestClass();
//						fsvm = null;
						break;
				}

				/*
				 *statistics the count of classification
				 */
				for(int i=0;i<testSecNum;i++)
				{
					if(testClass[i] == 1)
					{
						classCount[i][class1] = classCount[i][class1] + 1;
					}
					else if(testClass[i] == -1)
					{
						classCount[i][class2] = classCount[i][class2] + 1;
					}					
				}
			}
		}
		
		for(int i=0;i<testSecNum;i++)
		{
			classCountMax = -10000;
			for(int j=0;j<classNum;j++)
			{
				if(classCount[i][j] > classCountMax)
				{
					classCountMax = classCount[i][j];
					decisionClass = (j + 1);
				}
			}
			testClass[i] = decisionClass;
		}
	}
	
	/**
	 *@param class1 first class of each SVM
	 *@param class2 second class of each SVM
	 *@param trainSecNum number of train data of k-1 section
	 *@param dimNum number of dimension of data
	 *@param trainSecClass class of train data of k-1 section
	 *@param trainSecData value of train data of k-1 section
	 *@param trainDataP pointer of train data
	 */
	public void separateSVMs(int class1, int class2, int trainSecNum, int dimNum, int[] trainSecClass, 
	double[][] trainSecData)
	{
    	int trainDataP;
    	
    	trainNum = 0;
    	for(int i=0;i<trainSecNum;i++)
    	{
    		if((trainSecClass[i] == class1) || (trainSecClass[i] == class2))
    		{
    			trainNum++;
    		}
    	}
    	
		trainClass = new int[trainNum];
		trainData = new double[trainNum][dimNum];

		/*
		 *choose two class of each SVM
		 */
    	trainDataP = 0;
    	for(int i=0;i<trainSecNum;i++)
    	{
    		if((trainSecClass[i] == class1) || (trainSecClass[i] == class2))
    		{
    			if((trainSecClass[i] == class1))
    			{
    				trainClass[trainDataP] = 1;
    			}
    			else if((trainSecClass[i] == class2))
    			{
    				trainClass[trainDataP] = -1;
    			}
    			
    			for(int j=0;j<dimNum;j++)
    			{
    				trainData[trainDataP][j] = trainSecData[i][j];
    			}
    			trainDataP++;
    		}
    	}
    }
    
    /**
     *@return testClass class of test data by classification
     */
    public int[] returnTestClass()
    {
    	return testClass;
    }
}