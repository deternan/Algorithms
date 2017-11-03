package GA_KNN.featureselection.ga;

public class Sort extends GAsVarible 
{
	private int chromTemp;
	private double fitTemp;
	private int numTemp;
	
	public Sort() 
	{
		sortting();
	}
	
	public void sortting() 
	{
		for(int i=0; i<POPU_NUM - 1; i++) 
		{
			for(int j=0; j<POPU_NUM - 1; j++) 
			{
				if(fit[j + 1] > fit[j]) {
					fitTemp = fit[j];
					fit[j] = fit[j + 1];
					fit[j + 1] = fitTemp;
					
					numTemp = chromNum[j];
					chromNum[j] = chromNum[j + 1];
					chromNum[j + 1] = numTemp;
					
					for(int k=0; k<dimNum; k++) {
						chromTemp = chrom[j][k];
						chrom[j][k] = chrom[j + 1][k];
						chrom[j + 1][k] = chromTemp;
					}
				} else if(fit[j + 1] == fit[j]) {
					if(chromNum[j + 1] < chromNum[j]) {
						numTemp = chromNum[j];
						chromNum[j] = chromNum[j + 1];
						chromNum[j + 1] = numTemp;
						
						for(int k=0; k<dimNum; k++) {
							chromTemp = chrom[j][k];
							chrom[j][k] = chrom[j + 1][k];
							chrom[j + 1][k] = chromTemp;
						}
					}
				}
			}
		}
	}
}
