package KNN;

import KNN.Intermediary;
import KNN.calculate_fitness;
import KNN.KNN_fitness;

public class Fitness_Manager extends Intermediary
{
	public float population_fitness_temp_single;	
	
	public Fitness_Manager()
	{
		KNN_fitness knn = new KNN_fitness(K_num,Intermediary.data,dataClass,dimNum,classNum);
		population_fitness_temp_single = knn.r_KNN_fitness();
		knn = null;
	}
	
	public float r_population_fitness_temp_single()
	{
		return population_fitness_temp_single;
	}
	
}
