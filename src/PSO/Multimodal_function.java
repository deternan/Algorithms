package PSO;

public class Multimodal_function 
{

	public Multimodal_function()
	{
		
	}
	
	// mixture of multimodal optimization
	public double multimodal_Function_1(int Dim, double[] intput_)
	{
			double temp=0;	double temp2=0;
			double sum=0;
			
			for(int j=0; j<Dim; j++)
			{
				temp = Math.sin(intput_[j]);
				temp2 = (2 * intput_[j]) / 3;
				temp2 = Math.sin(temp2);
				sum += temp + temp2;
			}
			sum = 0 - sum;
			
			return sum;
	}
	
}
