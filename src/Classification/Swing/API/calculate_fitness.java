/*****************************************
*	Class Name: calculate_fitness.java	 *
*	Version: 1.0	 	 				 *
*	Data: 2007/4/2		 				 *
*	Updata: 			 		 		 *
*	Author: Chao-Hsuan Ke		 		 *
*	E-Mail: a092205237@cc.kuas.edu.tw	 *
*****************************************/

package Swing.API;

import Swing.API.RandTenFold;
import Swing.API.CrossValidation;

public class calculate_fitness
{
	private int separate[][];
	public float population_fitness;
	
	public calculate_fitness(int[] chooseMethod,int dataNum,int classNum,int[] dataClass,
			double gamma,double cost,double[][] train_data_first,String fileName)
	{
		// fitness ----------------------------------
		float fitness_return=0;
		
		switch(chooseMethod[2])
		{
		case 0:					// Cross validation
			RandTenFold rtf = new RandTenFold(dataNum);
			separate = rtf.returnSeparate();
			rtf = null;
			CrossValidation cv = new CrossValidation(chooseMethod,classNum,dataClass,separate,gamma,cost,train_data_first,fileName);
			fitness_return = cv.returnAllRate();
			cv = null;
			break;
		case 1:					// Holdout
			
			break;
		}
		
		population_fitness = fitness_return;
	}
	
	public float r_population_fitness()
	{
		return population_fitness;
	}
	
}
