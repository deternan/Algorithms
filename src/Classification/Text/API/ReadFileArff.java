package Text.API;

import java.io.IOException;
import java.io.RandomAccessFile;

/*
 *@author Chung-Jui Tu, 1093320134@cc.kuas.edu.tw
 *@version 1.0, 08/12/2005
 */



/*
 *read filename
 *@param noteNum number of comment
 *@param dataNum number of data
 *@param dimNum number of dimension
 *@param classNum number of class of data
 *@param dataClass class of data
 *@param stringData data of string
 *@param filePath path of file
 *@param className name of class of data
 */
public class ReadFileArff
{
	private int dataClass[];
	private int noteNum;
	private int dataNum;
	private int dimNum;
	private int classNum;
	private String filePath;
	private String className[];
	private String stringData[][];
	
	public ReadFileArff(String filePath)
	{
		this.filePath = filePath;
		readNoteDataNum();
		readDataDimNum();
		readClassName();
		readData();
	}
	
	/*
	 *@param lineString string of each line of data
	 */
	public void readNoteDataNum()
	{
		String lineString = "";
		
		try
		{
			RandomAccessFile raf = new RandomAccessFile(filePath,"r");
			
			while((lineString = raf.readLine()) != null)
			{
				if(lineString.startsWith("@") || lineString.startsWith("%"))
				{
					noteNum++;
				}
				else
				{
					dataNum++;
				}
			}
			raf.close();
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
		finally
		{
			System.out.println("number of comment:"+noteNum);
			System.out.println("number of data:"+dataNum);
		}
	}
	
	/*
	 *read dimension of data
	 *@param lineChar character of each line of data
	 *@param lineString string of each line of data
	 */
	public void readDataDimNum()
	{
		String lineString = null;
		char[] lineChar;
		
		try
		{
			RandomAccessFile raf = new RandomAccessFile(filePath,"r");
			
			/*
			 *read first line without comment
			 */
			for(int i=0;i<noteNum+1;i++)
			{
				lineString = raf.readLine();
			}
			lineChar = lineString.toCharArray();
			
			for(int i=0;i<lineChar.length;i++)
			{
				if(lineChar[i] == ',')
					dimNum++;
			}
			raf.close();
		}
		catch(IOException e)
		{
			System.err.println(e);
		}
		finally
		{
			System.out.println("number of dimension:"+dimNum);
		}
	}
	
	/*
	 *read name of class of data
	 *@param isSave check whether read string of "{"
	 *@param classP pointer of className
	 *@param lineString string of each line of data
	 *@param classString string of class of data
	 *@param lineChar character of each line of data
	 */
	public void readClassName()
	{
		boolean isSave = false;
		int classP = 0;
		String lineString = "";
		String classString = "";
		char lineChar[];
		
		try
		{
			RandomAccessFile raf = new RandomAccessFile(filePath,"r");
			
			/*
			 *read the last line of comment
			 */
			for(int i=0;i<noteNum-1;i++)
			{
				lineString = raf.readLine();
			}
			lineChar = lineString.toCharArray();
			
			for(int i=0;i<lineChar.length;i++)
			{
				if((lineChar[i] == ',') || (lineChar[i] == '}'))
					classNum++;
			}
			
			className = new String[classNum];
			
			for(int i=0;i<lineChar.length;i++)
			{
				if(isSave == true)
				{
					if(lineChar[i] == ',')
					{
						className[classP++] = classString.trim();
						classString = "";
					}
					else if(lineChar[i] == '}')
					{
						className[classP] = classString.trim();
						isSave = false;
					}
					else
					{
						classString = classString + lineChar[i];
					}
				}
				
				if(lineChar[i] == '{')
					isSave = true;
			}
			raf.close();
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
		finally
		{
			System.out.println("number of class:"+classNum);
		}
	}
	
	/*
	 *read value of data
	 *@param column column of data i.e. dimension
	 *@param lineString string of each line of data
	 *@param dataString string of each value of data
	 *@param lineChar character of each line of data
	 */
	public void readData()
	{
		int column = 0;
		String lineString = "";
		String dataString = "";
		char lineChar[];
		
		stringData = new String[dataNum][dimNum];
		dataClass = new int[dataNum];
		
		try
		{
			RandomAccessFile raf = new RandomAccessFile(filePath,"r");
			
			for(int i=0;i<noteNum;i++)
			{
				lineString = raf.readLine();
			}
			
			for(int row=0;row<dataNum;row++)
			{
				lineString = raf.readLine();
				lineChar = lineString.toCharArray();
				for(int i=0;i<lineChar.length;i++)
				{
					if(lineChar[i] == ',')
					{
						stringData[row][column++] = dataString.trim();
						dataString = "";
					}
					else
					{
						dataString = dataString + lineChar[i];
						
						/*
						 *set class value of data
						 */
						if(i == lineChar.length - 1)
						{
							for(int j=0;j<classNum;j++)
							{
								if(dataString.trim().equals(className[j]))
								{
									dataClass[row] = j+1;
								}
							}
							dataString = "";
							column = 0;
						}
					}
				}
			}
			raf.close();
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
		//System.out.println(stringData[0][0]);
	}
	
	/*
	 *@return stringData string of data
	 */
	public String[][] returnStringData()
	{
		return stringData;
	}
	
	/*
	 *@return dataClass class of data
	 */
	public int[] returnDataClass()
	{
		return dataClass;
	}
	
	/*
	 *@return classNum number of class of data
	 */
	public int returnClassNum()
	{
		return classNum;
	}

}