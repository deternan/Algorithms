package GA_KNN.featureselection.ga;

public class Mutation extends GAsVarible 
{
	public Mutation() 
	{
		getOffspring();
	}
	
	public void getOffspring() {
		for(int i=0; i<dimNum; i++) {
			if(rand.nextDouble() <= MUTATE_RATE) {
				chrom[POPU_NUM - 1][i] ^= 1;
			}
		}
	}
}
