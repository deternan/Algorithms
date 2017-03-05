package PSO;

public class UpdateParticle extends PSOVarible
{
	
	public UpdateParticle()
	{
		for(int i=0; i<PARTICLE_NUM; i++) 
		{
			for(int j=0; j<dimNum; j++)
			{
				v[i][j] = W * v[i][j] + C1 * rand.nextDouble() * (pBestX[i][j] - x[i][j]) + C2 * rand.nextDouble() * (gBestX[j] - x[i][j]);
				
				if(v[i][j] >= v_maxum) {
					v[i][j] = v_maxum;
				} else if(v[i][j] <= v_minum) {
					v[i][j] = v_minum;
				}
				
				x[i][j] = x[i][j] + v[i][j];
			}
		}
	}
}