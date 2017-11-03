package GA_KNN.featureselection.ga;

import java.util.Random;

import readwrite.read.ReadVarible;

public abstract class GAsVarible extends ReadVarible 
{
	
	public static final int POPU_NUM = 30;				// popular number
	public static final int GEN_NUM = 100;				// generation times
	public static final double MUTATE_RATE = 0.01;		// mutation rate
	public static final double XOVER_RATE = 0.8;		// cross rate
	public static final int CHOOSE_NUM = (int)(POPU_NUM * XOVER_RATE);	
	public static final int POOL_NUM = CHOOSE_NUM;		// number of chromosome
	public static double[] fit;							// fitness value array
	public static int[][] chrom;						// chromosome array
	public static int[] seedParent;	
	public static int[][] parent;	
	public static int[] chromNum;	
	public static int[] parentPos;	
	public static int[] offspring;	
	public static int[][] genePool;	
	public static double[] genePoolFit;
	public static int[] genePoolNum;	
	public static Random rand;
	
	public static int seednum = 13;
	
	public static long startTime;
	public static long stopTime;
	public static long alltime;
	public static long allsec_d;
	public static long day;
	public static long hour;
	public static long min;
	public static long sec;
	
}
