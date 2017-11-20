/*****************************************
*	Class Name: calculate_fitness.java	 *
*	Version: 1.0	 	 				 *
*	Data: 2007/4/2		 				 *
*	Updata: 			 		 		 *
*	Author: Chao-Hsuan Ke		 		 *
*	E-Mail: a092205237@cc.kuas.edu.tw	 *
*****************************************/

package KNN;

import KNN.RandTenFold;
import KNN.CrossValidation;

public class calculate_fitness
{
	private int separate[][];
	public float population_fitness;
		
	public calculate_fitness(int dataNum, int classNum, int[] dataClass, double[][] train_data_first, String fileName)
	{
		// fitness ----------------------------------
		float fitness_return=0;
		
		RandTenFold rtf = new RandTenFold(dataNum);
		separate = rtf.returnSeparate();
		rtf = null;			
		CrossValidation cv = new CrossValidation(classNum,dataClass,separate,train_data_first,fileName);
		fitness_return = cv.returnAllRate();
		cv = null;
		
		population_fitness = fitness_return;
	}
	
	public float r_population_fitness()
	{
		return population_fitness;
	}
	
}
