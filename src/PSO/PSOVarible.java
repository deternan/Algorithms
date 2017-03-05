package PSO;

import java.util.Random;

public class PSOVarible
{	
	public static final int PARTICLE_NUM = 30;			//采竤
	public static final int STOP_MOVE = 1000;			//采簿笆羆Ω计
	public static double W ;							//篋┦舦
	public static final double W_max = 0.9;
	public static final double W_min = 0.4;
	public static final double C1 = 2.0;
	public static double C2 = 2.0;
//	public static double s;								//sigmodㄧ计, 诀瞯[0,1]
	public static double[][] x;							//采竚
	public static double[][] v;							//采硉
	public static double[] fit;							//采続莱┦ㄧ计
	public static double[] pBest;						//采ō程ㄎ続莱ㄧ计
	public static double[][] pBestX;					//采ō程ㄎ続莱ㄧ计竚
	public static double gBest;							//采竤砰程ㄎ続莱ㄧ计
	public static double[] gBestX;						//采竤砰程ㄎ続莱ㄧ计竚
//	public static int[] xNum;							//–采ず疭紉计秖
//	public static int[] pBestXNum;						//и程ㄎ采ず疭紉计秖
//	public static int gBestXNum;						//竤砰程ㄎ采ず疭紉计秖
	public static double gBestFinal;					//簍衡猭程沧程ㄎ秆
	public static double[] gBestXFinal;					//簍衡猭程沧程ㄎ秆疭紉栋
	public static double gBestXFinal_terminal;
	public static Random rand;
	public static int SEED_num;
	public static int moveTime;							//簿笆Ω计
	public static int function_num = 3;					// 1:Rosenbrock ; 2:Rastrigrin; 3:Griewark
	
	// Test Function
	public static int dimNum = 10;
	public static double x_maxum;			public static double x_maxum_i;
	public static double x_minum;			public static double x_minum_i;
	public static double v_maxum;	//
	public static double v_minum;	//
	
	public static double max_value = Double.MIN_VALUE;
	public static double min_value = Double.MAX_VALUE;
}
