package PSO;

import java.io.IOException;
import java.util.Random;

public class PSO extends PSOVarible
{
	private double valueTemp;
	private double repeatNum;
	
//	private Multimodal_function MF;
	
	public PSO(int SEED_num) throws IOException
	{
		this.SEED_num = SEED_num;
		
		Initional();
		
		/*��l�Ʋɤl�s, ��l�ƾA����, ��l�Ƴ̨έ�*/
		new InitialParticleSwarm();
		/*�p��A����ƭ�*/
		new FitnessValue();
		/*��ܨC�Ӳɤl����T*/
		//for(int i=0; i<PARTICLE_NUM; i++) 
		{
			//System.out.println(i+"	"+fit[i]);
		}

		
		/*�p�����̨έ�(pBest)�M�s��̨έ�(gBest)*/
		new CalculateBestParticle(0);
		
		/*PSO�����i�沾��*/
		while((moveTime < STOP_MOVE))
		{
			moveTime++;
			
//////////////////////////////////////////////////////////////////////
			if(gBest == valueTemp){
				repeatNum++;
			}else{
				repeatNum = 0;
				valueTemp = gBest;
			}
			
			if(repeatNum == 6 && (moveTime != STOP_MOVE - 1)) 
			{
				repeatNum = 0;
				for(int j=0;j<dimNum;j++)
					{
						x[0][j] = x_maxum;
						x[1][j] = x_minum;
					}
			}
//////////////////////////////////////////////////////////////////////
			
			W = W_max - W_min;
			W = W/STOP_MOVE;
			W = W*moveTime;
			W = W_max - W;
			
			/*�ɤl����m��s�P�t�ק�s*/
			new UpdateParticle();
			/*�p��ɤl���A����ƭ�*/
			new FitnessValue();
			
			/*�p�����̨έ�(pBest)�M�s��̨έ�(gBest)*/
			new CalculateBestParticle(moveTime);
			
//			System.out.println((moveTime)+"	"+Math.rint(gBestFinal*100000)/100000);
//			System.out.println((moveTime)+"	"+gBestFinal);
			
			if(moveTime == STOP_MOVE){
				gBestXFinal_terminal = Math.rint(gBestFinal*100000)/100000;
				check();
			}
		}
	}
	
	private void Initional()
	{
		/*PSO�һݰ}�C�ŧi*/
		x = new double[PARTICLE_NUM][dimNum];
		v = new double[PARTICLE_NUM][dimNum];
		pBest = new double[PARTICLE_NUM];
		pBestX = new double[PARTICLE_NUM][dimNum];
		gBestX = new double[dimNum];
		fit = new double[PARTICLE_NUM];
		gBestXFinal = new double[dimNum];
		rand = new Random();
		rand.setSeed(SEED_num);
		
		moveTime = 0;
		gBest = Double.MAX_VALUE;
		gBestFinal = Double.MAX_VALUE;
	}
	
	private void check()
	{
		if(gBestXFinal_terminal <= min_value){
			min_value = gBestXFinal_terminal;
		}
		
		if(gBestXFinal_terminal >= max_value){
			max_value = gBestXFinal_terminal;
		}
	}
	
	public double r_gBestFinal()
	{
		return gBestFinal;
	}
	
	public static void main(String[] args) 
	{
		try {
			PSO pso = new PSO(20);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}