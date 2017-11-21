package KNN;

import KNN.Intermediary;
import KNN.KNN_fitness;

public class Fitness_Manager extends Intermediary
{
	public float population_fitness_temp_single;	
	
	public Fitness_Manager()
	{
		KNN_fitness knn = new KNN_fitness(Intermediary.datamodel_data, datamodel_dataClass, datamodel_dimNum, datamodel_classNum);
		population_fitness_temp_single = knn.r_KNN_fitness();
		knn = null;
	}
	
	public float r_population_fitness_temp_single()
	{
		return population_fitness_temp_single;
	}
	
}
