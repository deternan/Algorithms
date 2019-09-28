package Swing.API;

/**
 *@author Chung-Jui Tu, 1093320134@cc.kuas.edu.tw
 *@version 1.0, 08/13/2005
 */

/**
 *@param check check pointer of data whether choose
 *@param section size of each section
 *@param separate k fold separate pointer of data
 *@param random record section that remain pointer of data
 *@param K how many fold is
 */

 import java.util.*;
 
public class RandTenFold
{
	private int section;
	private int separate[][]; 
	private final int K = 10;
	private LinkedList myNumber;
	private Random rand;
	private final int SEED = 0;

	public RandTenFold(int dataNum)
	{
		section = (int)(dataNum / (double)K);
		
		separate = new int[K][];
		
		separate[K - 1] = new int[section + (dataNum - (section * K))];
		for(int i=0;i<K-1;i++)
		{
			separate[i] = new int[section];
		}
		
		
		init(dataNum);
		rand = new Random();
    	rand.setSeed(SEED);
		for(int i=0;i<K;i++)
		{
			
			for(int j=0;j<separate[i].length;j++)
			{
				separate[i][j] = Integer.parseInt(myNumber.remove((int)(rand.nextDouble()*myNumber.size())).toString());
			}
		}
	}
	
	private void init(int dataNum)
  	{
    	myNumber = new LinkedList();
    	for(int i = 0 ; i < dataNum ; i++)
      		myNumber.add(new Integer(i));
  	}
	
	/**
	 *@return separate k fold separate pointer of data
	 */
	public int[][] returnSeparate()
	{
		return separate;
	}
}