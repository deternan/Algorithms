package GA_KNN.featureselection.ga;
import java.util.*;

public class CalculateChromNum extends GAsVarible 
{
	public CalculateChromNum() 
	{
		Arrays.fill(chromNum, 0);
		for(int i=0; i<POPU_NUM; i++) {
			for(int j=0; j<dimNum; j++){
				if(chrom[i][j] == 1) {
					chromNum[i]++;
				}
			}
		}
	}
}
