package GA_KNN.featureselection.ga;
import java.util.*;

public class CalculateGenePoolNum extends GAsVarible 
{
	public CalculateGenePoolNum() 
	{
		Arrays.fill(genePoolNum, 0);
		for(int i=0; i<POOL_NUM; i++) {
			for(int j=0; j<dimNum; j++){
				if(genePool[i][j] == 1) {
					genePoolNum[i]++;
				}
			}
		}
	}
}
