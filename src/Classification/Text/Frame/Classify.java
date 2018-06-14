/*****************************************
*	Class Name: Classify.java	 		 *
*	Version: 1.0	 	 				 *
*	Data: 2007/4/2		 				 *
*	Updata: 			 		 		 *
*	Author: Chao-Hsuan Ke		 		 *
*	E-Mail: a092205237@cc.kuas.edu.tw	 *
*****************************************/

package Text.Frame;

import Text.API.Fitness_Manager;


public class Classify
{
	private static float classification_result;

	public Classify()
	{
		
	}
	
	public static void calculate()
	{
//		  ­pºâ Fitness value Ãþ§O
		Fitness_Manager FM = new Fitness_Manager();
		FM.calculate();
		classification_result = FM.r_population_fitness_temp_single();
		FM = null;
	}
	
	public static float return_result()
	{
		return classification_result;
	}
	
}

