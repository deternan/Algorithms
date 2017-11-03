package GA_KNN.featureselection.ga;

import java.util.LinkedList;

public class RouletteWheelSelection extends GAsVarible {
	private double fitSum;	
	private double randTemp;
	private double[] indivProb = new double[POPU_NUM];
	private double[] totalProb = new double[POPU_NUM];
	private LinkedList myNumber;
	
	public RouletteWheelSelection() 
	{
		init();
		probability();
		selection();
	}
		
	public void probability() 
	{		
		for(int i=0; i<POPU_NUM; i++) {
			fitSum += fit[i];
		}
		
		for(int i=0; i<POPU_NUM; i++) {
			indivProb[i] = fit[i] / fitSum;
		}
		
		totalProb[0] = indivProb[0];	
		for(int i=1; i<POPU_NUM; i++) {
			totalProb[i] = totalProb[i - 1] + indivProb[i];
		}
	}
		
	public void selection() 
	{
		for(int i=0; i<CHOOSE_NUM; i++) {
			randTemp = Integer.parseInt(myNumber.remove((int)(rand.nextDouble() * myNumber.size())).toString());
			randTemp = totalProb[(int)randTemp];
			
			if(randTemp <= totalProb[0]) {
				System.arraycopy(chrom[0], 0, parent[i], 0, chrom[0].length);
				parentPos[i] = 0;
			} else {
				for(int j=1; j<POPU_NUM; j++)
				{
					if(randTemp > totalProb[j - 1] && randTemp <= totalProb[j]) {
						System.arraycopy(chrom[j], 0, parent[i], 0, chrom[j].length);
						parentPos[i] = j;
					}
				}
			}
		}
	}
	
	public void init() 
	{
    	myNumber = new LinkedList();
    	for(int i=0 ; i<POPU_NUM ; i++) {
    		myNumber.add(new Integer(i));
    	}
  	}
}
