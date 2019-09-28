/*****************************************
*	Class Name: Fitness_Manager.java	 *
*	Version: 1.0	 	 				 *
*	Data: 2007/4/2		 				 *
*	Updata: 			 		 		 *
*	Author: Chao-Hsuan Ke		 		 *
*	E-Mail: a092205237@cc.kuas.edu.tw	 *
*****************************************/

package Swing.API;

import Swing.API.calculate_fitness;
import Swing.API.KNN.KNN_fitness;
import Swing.Frame.Intermediary;

public class Fitness_Manager extends Intermediary
{
	public float population_fitness_temp_single;	
	
	public Fitness_Manager()
	{
		
	}
	
	public void calculate()
	{
		switch(chooseMethod[4])
		{
		case 0:
			calculate_fitness cf = new calculate_fitness(chooseMethod,dataNum,classNum,dataClass,
					gamma,cost,this.data,fileName);
			population_fitness_temp_single = cf.r_population_fitness();
			
			break;
		case 1:
			KNN_fitness knn = new KNN_fitness(K_num,Intermediary.data,dataClass,dimNum,classNum);
			population_fitness_temp_single = knn.r_KNN_fitness();
			knn = null;
			break;
		}
	}
	
	public float r_population_fitness_temp_single()
	{
		return population_fitness_temp_single;
	}
	
}
