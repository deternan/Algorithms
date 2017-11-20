
package KNN;

import KNN.Fitness_Manager;


public class Classify
{
	private static float classification_result;

	public Classify()
	{
		
	}
	
	public static void calculate()
	{
		Fitness_Manager FM = new Fitness_Manager();		
		classification_result = FM.r_population_fitness_temp_single();
		FM = null;
	}
	
	public static float return_result()
	{
		return classification_result;
	}
	
}

