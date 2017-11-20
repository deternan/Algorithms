package KNN;

import java.io.IOException;

public class Classification_MainControl extends Parameters
{
	private String fileParent="C:\\Users\\Barry.Ke\\Desktop\\eTube sub\\word2vec\\";	
	private String fileName="subtitle_word2vec.vec.arff";
//	// SVM
//	private int classification_type=1;			// 1:SVM					2:FSVM			
//	private int multi_class_type=1;				// 1:OAO					2:OAA
//	private int estimate_type=0;				// 0:Cross-validation		1:Holdout	
//	private int[] chooseMethod;
	
	public Classification_MainControl()
	{
		Intermediary II = new Intermediary();
		try {			
			II.get_paramater(fileParent, fileName);
		} catch (IOException e1) {
			// TODO 自動產生 catch 區塊
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		Classification_MainControl CFT = new Classification_MainControl();		
	}
	
}
