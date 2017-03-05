package PSO;

import java.util.Random;

public class PSOVarible
{	
	public static final int PARTICLE_NUM = 30;			//�ɤl�s���j�p
	public static final int STOP_MOVE = 1000;			//�ɤl���ʪ��`����
	public static double W ;							//�D���v����
	public static final double W_max = 0.9;
	public static final double W_min = 0.4;
	public static final double C1 = 2.0;
	public static double C2 = 2.0;
//	public static double s;								//sigmod���, �N����v[0,1]
	public static double[][] x;							//�ɤl����m
	public static double[][] v;							//�ɤl���t��
	public static double[] fit;							//�ɤl���A���ʨ�ƭ�
	public static double[] pBest;						//�ɤl�ۨ����̨ξA����ƭ�
	public static double[][] pBestX;					//�ɤl�ۨ��̨ξA����ƭȪ���m
	public static double gBest;							//�ɤl�s�骺�̨ξA����ƭ�
	public static double[] gBestX;						//�ɤl�s��̨ξA����ƭȪ���m
//	public static int[] xNum;							//�C�Ӳɤl�����S�x�ƶq
//	public static int[] pBestXNum;						//�ۧڳ̨βɤl�����S�x�ƶq
//	public static int gBestXNum;						//�s��̨βɤl�����S�x�ƶq
	public static double gBestFinal;					//�t��k�̲ת��̨θ�
	public static double[] gBestXFinal;					//�t��k�̲ת��̨θѪ��S�x���X
	public static double gBestXFinal_terminal;
	public static Random rand;
	public static int SEED_num;
	public static int moveTime;							//���ʪ�����
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
