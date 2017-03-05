package PSO;

public class CalculateBestParticle extends PSOVarible
{
	
	public CalculateBestParticle(int init) 
	{
		// �Ĥ@���Y����l�ưʧ@
		if(init == 0) {
			initialPBest();
		}else{
			calculatePBest();
		}
		
		calculateGBest();
		calculateGBestFinal();
	}
	
	/*�p������l�̨έ�, �]�N�O�ۤv����*/
	public void initialPBest() 
	{
		for(int i=0; i<PARTICLE_NUM; i++) 
		{
			pBest[i] = fit[i];
			for(int j=0; j<dimNum; j++) 
			{
				pBestX[i][j] = x[i][j];
			}
		}
	}
	
	/*�p�����̨έ�*/
	public void calculatePBest()
	{
		for(int i=0; i<PARTICLE_NUM; i++) 
		{
			if(fit[i] < pBest[i]) {
				pBest[i] = fit[i];
				for(int j=0; j<dimNum; j++) 
				{
					pBestX[i][j] = x[i][j];
				}
			}
		}
	}
	
	/*�p��s��̨έ�*/
	public void calculateGBest() 
	{
		for(int i=0; i<PARTICLE_NUM; i++) 
		{
			if(pBest[i] < gBest) {
				gBest = pBest[i];
				for(int j=0; j<dimNum; j++) 
				{
					gBestX[j] = pBestX[i][j];
				}
			}
		}
	}
	
	
	/*
	public void calculateGBest() 
	{
		for(int i=0; i<PARTICLE_NUM; i++) 
		{
			if(fit[i] < gBest) {
				gBest = fit[i];
				for(int j=0; j<dimNum; j++) 
				{
					gBestX[j] = x[i][j];
				}
			}
		}
	}
	*/
	
	
	/*�p��PSO����̨έ�*/
	public void calculateGBestFinal()
	{
		if(gBest < gBestFinal) {
			gBestFinal = gBest;
			for(int i=0; i<dimNum; i++) {
				gBestXFinal[i] = gBestX[i];
			}
		}
//		System.out.println(gBestFinal);
	}
}