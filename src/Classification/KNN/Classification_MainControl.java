package KNN;

import java.io.IOException;

public class Classification_MainControl extends Parameters
{
	
	public Classification_MainControl()
	{
		Intermediary II = new Intermediary();
		try {			
			II.get_paramater(datamodel_fileParent, datamodel_fileName);
		} catch (IOException e1) {
			// TODO 
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		Classification_MainControl CFT = new Classification_MainControl();		
	}
	
}
