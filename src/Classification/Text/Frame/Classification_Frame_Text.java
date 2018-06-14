package Text.Frame;

import java.io.IOException;

public class Classification_Frame_Text
{
	private String fileParent="C:\\Users\\Barry\\Desktop\\";	
	private String fileName="video_Training_data_Manually_20180530.arff";
//	// SVM
	private int classification_type = 1;		// 1:SVM					2:FSVM			
	private int multi_class_type = 1;			// 1:OAO					2:OAA
	private int estimate_type = 0;				// 0:Cross-validation		1:Holdout
	private int preprocess_type = 1;			// 0: No Normalize			1:Normalize
	
	private double gamma = 15;					// 0:all		
												// 1:2^4(16)	
												// 2:2^3(8)	
												// 3:2^2(4)	
												// 4:2^1(2)	
												// 5:2^0(1)
												// 6:2^-1(0.5)	
												// 7:2^-2(0.25)	
												// 8:2^-3(0.125)	
												// 9:2^-4(0.0625)
												// 10:2^-5(0.03125)	
												// 11:2^-6(0.015625)	
												// 12:2^-7(7.8125E-3)
												// 13:2^-8(3.90625E-3)	
												// 14:2^-9(1.953125E-3)
												// 15:2^-10(9.765625E-4)
												 
	
	private double cost= 0;						// 0:all
												// 1:2^12(4096)
												// 2:2^11(2048)
												// 3:2^10(1024)
												// 4:2^9(512)
												// 5:2^8(256)
												// 6:2^7(128)
												// 7:2^6(64)
												// 8:2^5(32)
												// 9:2^4(16)
												// 10:2^3(8)
												// 11:2^2(4)
												// 12:2^1(2)
												// 13:2^0(1)
												// 14:2^-1(0.5)
												// 15:2^-2(0.25)
	
	private double gammaList[];
	private double costList[];
	private int[] chooseMethod;
	// KNN
	private int K = 1;
	// Fitness Choice
	private int F_choice = 0;					// 0:SVM	1:KNN
	
	public Classification_Frame_Text()
	{
		
		chooseMethod = new int[5];
		chooseMethod[0] = classification_type;
		chooseMethod[1] = multi_class_type;
		chooseMethod[2] = estimate_type;
		chooseMethod[3] = preprocess_type;
		chooseMethod[4] = F_choice;					
		
		gammaListSet(gamma);
		costListSet(cost);
		
		Intermediary II = new Intermediary();
		try {
			II.get_paramater(fileParent, fileName, chooseMethod, gammaList, costList, K);
		} catch (IOException e1) {
			// TODO 自動產生 catch 區塊
			e1.printStackTrace();
		}
	}
	
	private void gammaListSet(double gamma)
	{
		int setExponent = 4;
		//int setExponent = -5;
		
		if(gamma == 0.0)
		{
			gammaList = new double[15];
		
			for(int i=0;i<15;i++)
			{
				gammaList[i] = Math.pow(2,setExponent--);
			}
		}
		else
		{
			gammaList = new double[1];
			gammaList[0] = gamma;
		}
	}
	
	private void costListSet(double cost)
	{
		int setExponent = 12;
		
		if(cost == 0.0)
		{
			costList = new double[15];
		
			for(int i=0;i<15;i++)
			{
				costList[i] = Math.pow(2,setExponent--);
			}
		}
		else
		{
			costList = new double[1];
			costList[0] = cost;
		}
	}
	
	
	public static void main(String[] args)
	{
		Classification_Frame_Text CFT = new Classification_Frame_Text();
		
	}
	
}
