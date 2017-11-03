package GA_KNN.featureselection.ga;

import java.util.LinkedList;

public class Crossover extends GAsVarible 
{
	private int xoverChrom1;	
	private int xoverChrom2;	
	private int xoverPoint1;	
	private int xoverPoint2;	
	private int pointTemp;
	private LinkedList myNumber;
	
	public Crossover() 
	{
		init();
		getOffspring();
	}
	
	public void getOffspring() 
	{
		for(int i=0; i<CHOOSE_NUM / 2; i++) 
		{			
			xoverChrom1 = Integer.parseInt(myNumber.remove((int)(rand.nextDouble() * myNumber.size())).toString());
			xoverChrom2 = Integer.parseInt(myNumber.remove((int)(rand.nextDouble() * myNumber.size())).toString());
						
			xoverPoint1 = 0;
			xoverPoint2 = 0;
			
			while(xoverPoint1 == xoverPoint2) 
			{
				xoverPoint1 = (int)Math.round(rand.nextDouble() * (dimNum - 1));
				xoverPoint2 = (int)Math.round(rand.nextDouble() * (dimNum - 1));
			}
			
			if(xoverPoint1 > xoverPoint2) {
				pointTemp = xoverPoint1;
				xoverPoint1 = xoverPoint2;
				xoverPoint2 = pointTemp;
			}
						
			for(int j=xoverPoint1; j<=xoverPoint2; j++) 
			{
				pointTemp = parent[xoverChrom1][j];
				parent[xoverChrom1][j] = parent[xoverChrom2][j];
				parent[xoverChrom2][j] = pointTemp;
			}
			
			for(int j=0; j<dimNum; j++) 
			{
				genePool[xoverChrom1][j] = parent[xoverChrom1][j];
				genePool[xoverChrom2][j] = parent[xoverChrom2][j];
			}
		}
	}
	
	public void init() {
    	myNumber = new LinkedList();
    	for(int i=0 ; i<CHOOSE_NUM ; i++) {
    		myNumber.add(new Integer(i));
    	}
  	}
}
