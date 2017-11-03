package GA_KNN.featureselection.ga;

public class DeleteGenePoolRepeat extends GAsVarible 
{
	private String checkStringZero;
	private String checkString1;
	private String checkString2;

	public DeleteGenePoolRepeat() 
	{
		checkStringZero = "";
		for(int j=0; j<dimNum; j++) {
			checkStringZero += "0";
		}
		for(int i=0; i<POOL_NUM; i++) {
			checkString1 = "";
			for(int j=0; j<dimNum; j++) {
				checkString1 += genePool[i][j];
			}
			/*若是字串內容全部為零, 表示此染色體已被刪除, 不做處理*/
			if(!checkString1.equals(checkStringZero)) {
				for(int j=(i+1); j<POOL_NUM; j++) {
					checkString2 = "";
					for(int k=0; k<dimNum; k++) {
						checkString2 += genePool[j][k];
					}
					if(checkString1.equals(checkString2)) {
						/*刪除基因池中相同的染色體*/
						for(int k=0; k<dimNum; k++) {
							genePool[j][k] = 0;
						}
					}
				}
			}
		}
	}
}
