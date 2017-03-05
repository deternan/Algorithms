package PSO;

public class InitialParticleSwarm extends PSOVarible
{
	
	public InitialParticleSwarm()
	{
		
		for(int i=0; i<PARTICLE_NUM; i++)
		{
			for(int j=0;j<dimNum;j++)
			{
				x[i][j] = rand.nextDouble() * x_maxum;
				
				if(x[i][j]>x_maxum_i){
					x[i][j] = x_maxum_i;
				}else if(x[i][j] < x_minum_i){
					x[i][j] = x_minum_i;
				}
			}
			
			for(int j=0; j<dimNum; j++) 
			{
				v[i][j] = rand.nextDouble()*v_maxum;
			}
		}
	}
	
}