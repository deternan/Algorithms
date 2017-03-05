package PSO;

public class FitnessValue extends PSOVarible
{
	private double temp;
	private double temp1;
	private double temp2;
	private double total;
	private double sum;
	private double multiplication;
	
	public FitnessValue()
	{		
		switch(function_num)
		{
		case 0:
			// Sphere
			for(int i=0;i<PARTICLE_NUM;i++)
			{
				temp1 = 0;
				sum = 0;
				for(int j=0;j<dimNum-1;j++)
				{
					temp1 = Math.pow(x[i][j], 2);
					sum = sum+temp1;
				}
				fit[i] = sum;
			}
			break;
		case 1:
			// Rosenbrock
			for(int i=0;i<PARTICLE_NUM;i++)
			{
				temp1 = 0;
				temp2 = 0;
				sum = 0;
				for(int j=0;j<dimNum-1;j++)
				{
					temp1 = Math.pow(x[i][j], 2);
					temp1 = x[i][j+1]-temp1;
					temp1 = Math.pow(temp1, 2);
					temp1 = 100*temp1;
					temp2 = x[i][j]-1;
					temp2 = Math.pow(temp2, 2);
					sum = sum+temp1+temp2;
				}
				fit[i] = sum;
			}
			break;
		case 2:
			// Rastrigrin
			for(int i=0;i<PARTICLE_NUM;i++)
			{
				sum = 0;
				total = 0;
				temp1 = 0;
				temp2 = 0;
				for(int j=0;j<dimNum;j++)
				{
					temp1 = Math.pow(x[i][j], 2);
					temp2 = 2*Math.PI*x[i][j];
					temp2 = Math.cos(temp2);
					temp2 = 10*temp2;
					total = temp1-temp2+10;
					
					sum = sum+total;
				}
				fit[i] = sum;
			}
			break;
		case 3:
			// Griewark
			for(int i=0;i<PARTICLE_NUM;i++)
			{
				temp = 0;
				temp2 = 0;
				multiplication=1;
				sum = 0;
				
				for(int j=1;j<=dimNum;j++)
				{
					temp = temp+Math.pow(x[i][j-1], 2);
					temp2 = x[i][j-1]/(Math.pow(j, 0.5));
					temp2 = Math.cos(temp2);
					multiplication = multiplication*temp2;
				}
				temp = temp/4000;
				sum = temp-multiplication+1;
				
				fit[i] = sum;
			}
			break;
		case 4:
			// Schaffer f6
			for(int i=0;i<PARTICLE_NUM;i++)
			{
				temp = 0;
				temp1 = 0;
				temp2 = 0;
				total = 0;
				sum = 0;
				
				temp1 = Math.pow(x[i][0], 2);
				temp2 = Math.pow(x[i][1], 2);
				
				temp = temp1 + temp2;
				temp = Math.pow(temp, 0.5);
				temp = Math.sin(temp);
				temp = Math.pow(temp, 2);
				temp = temp - 0.5;
				
				total = temp1 + temp2;
				total = 0.001 * total;
				total = 1 + total;
				total = Math.pow(total, 2);
				
				sum = temp / total;
				sum = sum + 0.5;
				
				fit[i] = sum;
			}
			break;
		case 5:
			// Sphere
			for(int i=0;i<PARTICLE_NUM;i++)
			{
				temp = 0;
				sum = 0;
				
				for(int j=0;j<dimNum;j++)
				{
					temp = Math.pow(x[i][j], 2);
					sum = sum + temp;
				}
				fit[i] = sum;
			}
			break;
		}
	}
	
}