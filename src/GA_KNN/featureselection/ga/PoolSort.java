package GA_KNN.featureselection.ga;

public class PoolSort extends GAsVarible 
{
	private int poolTemp;
	private double fitTemp;
	private int numTemp;
	
	public PoolSort() 
	{
		sortting();
	}
	
	public void sortting() {
		for(int i=0; i<POOL_NUM - 1; i++) {
			for(int j=0; j<POOL_NUM - 1; j++) {
				if(genePoolFit[j + 1] > genePoolFit[j]) {
					fitTemp = genePoolFit[j];
					genePoolFit[j] = genePoolFit[j + 1];
					genePoolFit[j + 1] = fitTemp;
					
					numTemp = genePoolNum[j];
					genePoolNum[j] = genePoolNum[j + 1];
					genePoolNum[j + 1] = numTemp;
					
					for(int k=0; k<dimNum; k++) {
						poolTemp = genePool[j][k];
						genePool[j][k] = genePool[j + 1][k];
						genePool[j + 1][k] = poolTemp;
					}
				} else if(genePoolFit[j + 1] == genePoolFit[j]) {
					if(genePoolNum[j + 1] < genePoolNum[j]) {
						numTemp = genePoolNum[j];
						genePoolNum[j] = genePoolNum[j + 1];
						genePoolNum[j + 1] = numTemp;
						
						for(int k=0; k<dimNum; k++) {
							poolTemp = genePool[j][k];
							genePool[j][k] = genePool[j + 1][k];
							genePool[j + 1][k] = poolTemp;
						}
					}
				}
			}
		}
	}
}
