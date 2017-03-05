package PSO;

import java.io.IOException;

public class PSO_Optimal_number extends PSOVarible
{
	private static int SEED_num;
	
	private static double gBestFinal_rint;
	private static double gBestFinal;
	private static double average = 0;
	
	private static double max_value;
	private static double min_value;
	
	// prime
	private static int prime_start = 0;
	private static int prime_end = 7920;	// 545	// 7920
	private static int prime_num = 0;
	private static int[] prime;
	
	public static void main(String[] args)
	{
//		System.out.println(Double.MIN_VALUE);
		
		// 先算出有幾個 prime
		for(int i=prime_start;i<prime_end;i++)
		{
			int b=0;
			for(int j=1;j<=i;j++)
			{
				if(i%j==0){
					b++;
				}
			}
			if(b==2){ 
				prime_num++;
		    }
		}
		
		prime = new int[prime_num];
		
		// 將prime記錄下來
		int prime_temp = 0;
		for(int i=prime_start;i<prime_end;i++)
		{
			int b=0;
			for(int j=1;j<=i;j++)
			{
				if(i%j==0){
					b++;
				}
			}
			if(b==2){ 
				prime[prime_temp++] = i;
		    }
		}
		
		// 初使變數設定
		switch(function_num)
		{
		case 1:
			// Rosenbrock
			
			x_maxum = 100.0;		v_maxum = x_maxum;		//x_maxum_i = 30;
			x_minum = -100.0;	v_minum = x_minum;			//x_minum_i = -30;
			x_maxum_i = 30;
			x_minum_i = 15;
//			x_maxum = 100.0;		v_maxum = x_maxum;		
//			x_minum = -100.0;		v_minum = x_minum;		
			
			break;
		case 2:
			// Rastrigrin
			
			x_maxum = 5.12;		v_maxum = x_maxum;			//x_maxum_i = 5.12;
			x_minum = -5.12;	v_minum = x_minum;			//x_minum_i = -5.12;
			x_maxum_i = 5.12;
			x_minum_i = 2.56;
//			x_maxum = 10.0;		v_maxum = x_maxum;			x_maxum_i = 5.12;
//			x_minum = -10.0;	v_minum = x_minum;			x_minum_i = 2.56;
			break;
		case 3:
			// Griewark
			
			x_maxum = 600.0;	v_maxum = x_maxum;			//x_maxum_i = 600.0;
			x_minum = -600.0;	v_minum = x_minum;			//x_minum_i = -600.0;
			x_maxum_i = 600;
			x_minum_i = 300;
//			x_maxum = 600.0;	v_maxum = x_maxum;			x_maxum_i = 600;
//			x_minum = -600.0;	v_minum = x_minum;			x_minum_i = 300;
			break;
		case 4:
			// Schaffer f6
			dimNum = 2;
			x_maxum = 100.0;	v_maxum = x_maxum;			x_maxum_i = 100.0;
			x_minum = -100.0;	v_minum = x_minum;			x_minum_i = 50.0;
			break;
		case 5:
			// Sphere
			x_maxum = 100.0;	v_maxum = x_maxum;			x_maxum_i = 100.0;
			x_minum = -100.0;	v_minum = x_minum;			x_minum_i = 50.0;
			break;
		}
		
		System.out.println("PARTICLE_NUM: "+PARTICLE_NUM);
		System.out.println("STOP_MOVE: "+STOP_MOVE);
		System.out.println("dimNum: "+dimNum);
		
		for(int i=0;i<prime.length;i++)
		{
			SEED_num = prime[i];
//			System.out.print((i+1)+"	SEED_num="+SEED_num+"	");
			
			try {
				PSO pso = new PSO(SEED_num);
				gBestFinal = pso.r_gBestFinal();
				max_value = pso.max_value;
				min_value = pso.min_value;
			} catch (IOException e) {
				// TODO 自動產生 catch 區塊
				e.printStackTrace();
			}
			
//			gBestFinal_rint = Math.rint(gBestFinal*1000000000)/1000000000;
//			average = average + gBestFinal_rint;
//			System.out.println(gBestFinal_rint);
			
			average = average + gBestFinal;
			System.out.println(gBestFinal);
		}
		
		average = average / prime.length;
		
		System.out.println("--------------------------------");
		System.out.println("Average: "+average);
		System.out.println("Max_value: "+max_value);
		System.out.println("Min_value: "+min_value);
	}
	
}