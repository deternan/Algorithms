package PSO;

public class CalculateBestParticle extends PSOVarible
{
	
	public CalculateBestParticle(int init) 
	{
		// 第一次即為初始化動作
		if(init == 0) {
			initialPBest();
		}else{
			calculatePBest();
		}
		
		calculateGBest();
		calculateGBestFinal();
	}
	
	/*計算個體初始最佳值, 也就是自己本身*/
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
	
	/*計算個體最佳值*/
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
	
	/*計算群體最佳值*/
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
	
	
	/*計算PSO整體最佳值*/
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