package GA_KNN.readwrite.read;
import java.io.*;


public class ReadDatabase extends ReadVarible
{
	private int commaIndex;	
	private int dataIndex;	
	private int classIndex;	
	private int forwardBraceIndex;
	private int reverseBraceIndex;
	private String lineStr;
	
	public ReadDatabase() 
	{		
		readDataNum();		
		readDimNum();		
		readClassName();
		readData();		
		covertData();
		covertClass();
		check_normalize();
	}
	
	public void readDataNum() {
		try {
			FileReader fr = new FileReader(readPath + fileName);
			BufferedReader br = new BufferedReader(fr);
			while((lineStr = br.readLine()) != null) 
			{
				//if(!(lineStr.startsWith("@")))
				if((lineStr.indexOf("@")!=0) && (lineStr.indexOf("%")!=0) && (lineStr.length() > 0))
				{
					dataNum++;					
				}
			}
			br.close();
			fr.close();
		} catch(IOException e) {
			System.err.println(e);
		} finally {
			lineStr = null;
			System.out.println("資料的數量:" + dataNum);
		}
	}
	
	public void readDimNum() 
	{	
		boolean data_check = false;
		try {
			FileReader fr = new FileReader(readPath + fileName);
			BufferedReader br = new BufferedReader(fr);
			while((lineStr = br.readLine()) != null) 
			{
				if(data_check == true){
					commaIndex += 1;
					String temp[];
					temp = lineStr.split(",");
					//dimNum++;
					dimNum = temp.length - 1;
					//System.out.println("dim"+lineStr);
					data_check = false;
				}
				
				if(lineStr.equalsIgnoreCase("@DATA")){
					data_check = true;					
				}
			}
			br.close();
			fr.close();
		} catch(IOException e) {
			System.err.println(e);
		} finally {
			lineStr = null;
			commaIndex = 0;
			System.out.println("資料的特徵維度:" + dimNum);
		}

	}
		
	public void readClassName() 
	{
		classNum = 1;
		try {
			FileReader fr = new FileReader(readPath + fileName);
			BufferedReader br = new BufferedReader(fr);
			
			while(!(lineStr = br.readLine()).startsWith("@ATTRIBUTE class")) {
				;
			}
			
			forwardBraceIndex = lineStr.indexOf("{", forwardBraceIndex) + 1;
			reverseBraceIndex = lineStr.indexOf("}", reverseBraceIndex);
			lineStr = lineStr.substring(forwardBraceIndex, reverseBraceIndex);
			
			while((commaIndex = lineStr.indexOf(",", commaIndex)) != -1) {
				commaIndex += 1;
				classNum++;
			}
			
			className = new String[classNum];
			
			for (int i=0; i<classNum; i++) {
				commaIndex = lineStr.indexOf(",", commaIndex);
				
				if (commaIndex != -1) {
					className[i] = lineStr.substring(classIndex, commaIndex);
					classIndex = commaIndex + 1;
					commaIndex += 1;
				} else {
					className[i] = lineStr.substring(classIndex);
					classIndex = 0;
					commaIndex = 0;
				}
			}
			
			br.close();
			fr.close();
		} catch(IOException e) {
			System.err.println(e);
		} finally {
			lineStr = null;
			classIndex = 0;
			commaIndex = 0;
			System.out.println("資料的類別數量:" + className.length);
		}
	}
		
	public void readData() 
	{
		dataStr = new String[dataNum][dimNum];
		classStr = new String[dataNum];
		int i = 0;
		try {
			FileReader fr = new FileReader(readPath + fileName);
			BufferedReader br = new BufferedReader(fr);
			
			while(i<dataNum) 
			{
				if((lineStr = br.readLine()) != null) 
				{
					//if(!(lineStr.startsWith("@") && !(lineStr.startsWith("%"))))
					if((lineStr.indexOf("@")!=0) && (lineStr.indexOf("%")!=0) && (lineStr.length() > 0))
					{						
						for(int j=0; j<dimNum + 1; j++) 
						{
							commaIndex = lineStr.indexOf(",", commaIndex);
														
							if(commaIndex != -1) {								
								dataStr[i][j] = lineStr.substring(dataIndex, commaIndex);
								dataIndex = commaIndex + 1;
								commaIndex += 1;
								//System.out.print(i+"	"+dataStr[i][j]+" ");
							} else {
								classStr[i] = lineStr.substring(dataIndex);
								dataIndex = 0;
								commaIndex = 0;
								//System.out.println(classStr[i]);
							}
						}
						i++;
					}
				}
			}			
			br.close();
			fr.close();
		} catch(IOException e) {
			System.err.println(e);
		} finally {
			lineStr = null;
			dataIndex = 0;
			commaIndex = 0;			
		}
	}
		
	public void covertData() {
		data = new double[dataNum][dimNum];
		for(int i=0; i<dataNum; i++) 
		{
			for(int j=0; j<dimNum; j++) {
				data[i][j] = Double.parseDouble(dataStr[i][j]);
			}
		}
	}
	
	private void check_normalize()
	{
		if(normalize_check==false){
			System.out.println("No_normalize");
		}else if(normalize_check==true){
			System.out.println("normalize");
			normalize();
		}
	}
	
	private void normalize()
	{
		double upper = 1.0;
		double lower = 0.0;
		double dataMax[] = new double[dimNum];
		double dataMin[] = new double[dimNum];
		
		for (int j=0;j<dimNum;j++) {
			dataMax[j] = -100000.0;
			dataMin[j] = 100000.0;
			for (int i=0;i<dataNum;i++) {
				if (data[i][j] > dataMax[j]) {
					dataMax[j] = data[i][j];
				}
				if (data[i][j] < dataMin[j]) {
					dataMin[j] = data[i][j];
				}
			}
		}
		 	
		for (int j=0;j<dimNum;j++) 
		{
			for (int i=0;i<dataNum;i++) {
				data[i][j] = lower + ((upper - lower) * ((data[i][j] - dataMin[j]) / (dataMax[j] - dataMin[j])));				
			}
		}	
	}
		
	public void covertClass() {
		classValue = new int[dataNum];
		for(int i=0; i<dataNum; i++) {
			for(int j=0; j<classNum; j++) {
				if(classStr[i].equals(className[j])) { 
					classValue[i] = (j + 1);
				}
			}
		}
	}
}