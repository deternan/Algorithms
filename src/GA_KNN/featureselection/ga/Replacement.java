package GA_KNN.featureselection.ga;

public class Replacement extends GAsVarible 
{
	
	public Replacement() 
	{
		compare();
	}
		
	public void compare() {
		for(int i=0; i<POOL_NUM; i++) {
			if(genePoolFit[i] > fit[parentPos[i]]) {
				fit[parentPos[i]] = genePoolFit[i];
				chromNum[parentPos[i]] = genePoolNum[i];
				for(int j=0; j<dimNum; j++) {
					chrom[parentPos[i]][j] = genePool[i][j];
				}
			} else if(genePoolFit[i] == fit[parentPos[i]]) {
				if(genePoolNum[i] < chromNum[parentPos[i]]) {
					fit[parentPos[i]] = genePoolFit[i];
					chromNum[parentPos[i]] = genePoolNum[i];
					for(int j=0; j<dimNum; j++) {
						chrom[parentPos[i]][j] = genePool[i][j];
					}
				}
			}
		}
	}
}
