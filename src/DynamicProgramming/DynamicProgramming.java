package DynamicProgramming;

public class DynamicProgramming 
{
	
	public DynamicProgramming()
	{
		String sequenceA ="CCATTGACAA";
		String sequenceB = "ACTGGAAT";
		
		int gap = 2, substitution = 1, match = 0;

		int[][] opt = new int[sequenceA.length() + 1][sequenceB.length() + 1];
		
		for (int i = 1; i <= sequenceA.length(); i++)
		    opt[i][0] = opt[i - 1][0] + gap;
		for (int j = 1; j <= sequenceB.length(); j++)
		    opt[0][j] = opt[0][j - 1] + gap;

		for (int i = 1; i <= sequenceA.length(); i++) {
		    for (int j = 1; j <= sequenceB.length(); j++) {
		        int scoreDiag = opt[i - 1][j - 1] +
		                (sequenceA.charAt(i-1) == sequenceB.charAt(j-1) ?
		                    match : 
		                    substitution); 
		        int scoreLeft = opt[i][j - 1] + gap; 
		        int scoreUp = opt[i - 1][j] + gap; 
		        
		        opt[i][j] = Math.min(Math.min(scoreDiag, scoreLeft), scoreUp);
		    }
		}

		for (int i = 0; i <= sequenceA.length(); i++) {
		    for (int j = 0; j <= sequenceB.length(); j++)
		        System.out.print(opt[i][j] + "\t");
		    System.out.println();
		}
	}

	public static void main(String[] args)
	{
		DynamicProgramming DP = new DynamicProgramming();
	}
	
}
