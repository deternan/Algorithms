package GA_KNN.featureselection.ga;

import java.util.LinkedList;

public class InitialPopulation extends GAsVarible 
{
	private LinkedList myNumber;
	
	public InitialPopulation() 
	{
		initialChrom();
	}
	
	
	public void initialChrom() 
	{
		
		for(int i=0; i<POPU_NUM; i++)
		{
			init();
			for(int j=0; j<(i + 1) * (dimNum / POPU_NUM); j++) {
				chrom[i][Integer.parseInt(myNumber.remove((int)(rand.nextDouble() * myNumber.size())).toString())] = 1;		
			}
		}
	}
	
	
	
	
	public void init() 
	{
    	myNumber = new LinkedList();
    	for(int i=0 ; i<dimNum ; i++) {
    		myNumber.add(new Integer(i));
    	}
  	}
}
