package Text.API;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/*
 *@author Chung-Jui Tu, 1093320134@cc.kuas.edu.tw
 *@version 1.0, 08/12/2005
 */
 

/*
 *read the list of filename
 *@param filePath path of file
 *@param nameList list of filename
 */
public class ReadFileList
{
	private String filePath;
	private String nameList[];
	
	public ReadFileList(String filePath)
	{
		this.filePath = filePath;
	}
	
	/*
	 *return name of file
	 *@param listP pointer of list of filename
	 *@param listNum number of list of filename
	 *@param lineStr string of each line of data
	 *@return nameList return list of filename
	 *@exception IOException
	 *@exception FileNotFoundException
	 */
	public String[] returnFileName()
	{
		int listP = 0;
		int listNum = 0;
		String lineStr = null;
		
		try
		{
			FileReader fr = new FileReader(filePath);
			LineNumberReader lnr = new LineNumberReader(fr);

			while((lineStr = lnr.readLine()) != null)
			{
				listNum++;
			}
		
			nameList = new String[listNum];
			
			lnr.close();
			fr.close();
			
			fr = new FileReader(filePath);
			lnr = new LineNumberReader(fr);
			
			while((lineStr = lnr.readLine()) != null)
			{
				nameList[listP++] = lineStr;
			}
			
			lnr.close();
			fr.close();
		}
		catch(IOException e)
		{
			System.err.println(e);
		}
		finally
		{
			return nameList;
		}
	}
}