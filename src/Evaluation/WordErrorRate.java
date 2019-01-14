package Evaluation;

/*
 * WER (Word Error Rate) Evaluation 
 * 
 * version: January 10, 2019 09:21 AM
 * Last revision: January 14, 2019 09:49 AM
 * 
 * Author : Chao-Hsuan Ke
 * Institute: Delta Research Center
 * Company : Delta Electronics Inc. (Taiwan)
 * 
 */

/*
 * Reference
 * https://en.wikipedia.org/wiki/Word_error_rate
 * 
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class WordErrorRate {
	static final String GAP_CHAR = "_"; 	//Only For printing the final alignment

	private int[][] memoTable;
	private int[][][] predecessorIndexes;	//stores the index where the value at memoTable[i][j] came from (diagonal, above or left)

	private int gapPenalty;
//	private int vowelVowelMismatchPenalty;
//	private int consonantConsonantMismatchPenalty;
	private int vowelConsonantMismatchPenalty;
//	private int numberNumberMismatchPenalty;

	String seq1Aligned = ""; 	//Holds the actual sequence with gaps added
	String seq2Aligned = "";

	private String orifolder = "";
	private String orifile = "";
	private String tranfolder = "";
	private String tranfile = "";
	private BufferedReader oribfr;	
	private BufferedReader tranbfr;
	
	private Vector ori_vec = new Vector();
	private Vector tran_vec = new Vector();
	
	int substitutionsCount = 0;
	int delCount = 0;
	int insertCount = 0;
	int correctCount = 0;
	
	double WERrateSum = 0.0;
	double WERrateAverage = 0.0;
	
//	public SequenceAlignment_Chinese(int gapPenalty, int vowelVowelMismatchPenalty, int consonantConsonantMismatchPenalty, int vowelConsonantMismatchPenalty, int numberNumberMismatchPenalty) {
//		this.gapPenalty = gapPenalty;
//		this.vowelVowelMismatchPenalty = vowelVowelMismatchPenalty;
//		this.consonantConsonantMismatchPenalty = consonantConsonantMismatchPenalty;
//		this.vowelConsonantMismatchPenalty = vowelConsonantMismatchPenalty;
//		this.numberNumberMismatchPenalty = numberNumberMismatchPenalty;
//		
//		
//	}

	public WordErrorRate() throws Exception {
		
		// this(2, 1, 1, 3, 1);
		this.gapPenalty = 2;
//		this.vowelVowelMismatchPenalty = 1;
//		this.consonantConsonantMismatchPenalty = 1;
		this.vowelConsonantMismatchPenalty = 3;
//		this.numberNumberMismatchPenalty = 1;
		
		// ori file
		String Line = "";
		FileReader fr = new FileReader(orifolder + orifile);
		oribfr = new BufferedReader(fr);
				
		while((Line = oribfr.readLine())!=null)
		{								
			ori_vec.add(Remove_nonChineseSymbol(Line));
			//ori_vec.add(Line);
			
		}
		fr.close();
		oribfr.close();
	
		// tran file		
		FileReader tranfr = new FileReader(tranfolder + tranfile);
		tranbfr = new BufferedReader(tranfr);
				
		while((Line = tranbfr.readLine())!=null)
		{								
			tran_vec.add(Remove_nonChineseSymbol(Line));
			//tran_vec.add(Line);
		}
		tranfr.close();
		tranbfr.close();
		
		String oriStr = " ";
		String tranStr = " ";
		
		for(int i=0; i<ori_vec.size(); i++)
		{
			substitutionsCount = 0;
			delCount = 0;
			insertCount = 0;
			correctCount = 0;
						
			if(ori_vec.get(i).toString().length() == 0) {
				oriStr = " ";
			}else {
				oriStr = " "+ori_vec.get(i).toString().trim();
			}
			if(tran_vec.get(i).toString().length() == 0) {
				tranStr = " ";
			}else {
				tranStr = " "+tran_vec.get(i).toString().trim();
			}			
			
			calcOptimalAlignment(oriStr, tranStr, true);
			
			WER(substitutionsCount, delCount, insertCount, correctCount, oriStr, tranStr);
		}
		
		System.out.println("-------------------------------------------------------");
		WERrateAverage = WERrateSum / Double.valueOf(ori_vec.size());
		
		System.out.println("WER average: "+ WERrateAverage);
	}

	private void calcOptimalAlignment(String sequence1Original, String sequence2Original, boolean printResults) 
	{
		//String seq1 = sanitizeSequence(sequence1Original);
		//String seq2 = sanitizeSequence(sequence2Original);
		String seq1 = sequence1Original;
		String seq2 = sequence2Original;
		
		//Initialize 2D arrays for memoization
		memoTable = new int[seq1.length()][seq2.length()];
		predecessorIndexes = new int[seq1.length()][seq2.length()][2];
		
		//Array bounds are < seq1.length() (not <= ) since both sequences have a blank space @ the start
		//Fill 0th column
		for (int i = 0; i < seq1.length(); i++) {	// base case: j = 0
			memoTable[i][0] = i * this.gapPenalty;
			predecessorIndexes[i][0][0] = i - 1;
			predecessorIndexes[i][0][1] = 0;
		}
		//Fill 0th row
		for (int j = 0; j < seq2.length(); j++) {	// base case: i = 0
			memoTable[0][j] = j * this.gapPenalty;
			predecessorIndexes[0][j][0] = 0;
			predecessorIndexes[0][j][1] = j - 1;
		}
		//Set upper left with negative predecessor since it has no predecessor
		predecessorIndexes[0][0][0] = -1;
		predecessorIndexes[0][0][1] = -1;


		//Fill rest of memo table
		for (int j = 1; j < seq2.length(); j++) {
			for (int i = 1; i < seq1.length(); i++) {
				int alignedCharWithCharPenalty = mismatchPenalty(seq1.charAt(i), seq2.charAt(j)) + memoTable[i - 1][j - 1];	//case1: seq1[i] & seq2[j] aligned with each other
				int seq1CharWithGap = this.gapPenalty + memoTable[i - 1][j];		//case2: seq1 with gap
				int seq2CharWithGap = this.gapPenalty + memoTable[i][j - 1];		//case3: seq2 with gap
				//Calculate the min of 3 values & store predecessors
				if (alignedCharWithCharPenalty <= seq1CharWithGap && alignedCharWithCharPenalty <= seq2CharWithGap) {			//case1 is the min
					memoTable[i][j] = alignedCharWithCharPenalty;
					predecessorIndexes[i][j][0] = i - 1;
					predecessorIndexes[i][j][1] = j - 1;
				}
				else if (seq1CharWithGap <= alignedCharWithCharPenalty && seq1CharWithGap <= seq2CharWithGap) {	//case2 is the min
					memoTable[i][j] = seq1CharWithGap;
					predecessorIndexes[i][j][0] = i - 1;
					predecessorIndexes[i][j][1] = j;
				}
				else {	//case3 is the min
					memoTable[i][j] = seq2CharWithGap;
					predecessorIndexes[i][j][0] = i;
					predecessorIndexes[i][j][1] = j - 1;
				}
			}
		}

		if(printResults){
			//System.out.println("Aligning \""+sequence1Original+"\" with \""+sequence2Original+"\"");
			//System.out.println("Aligning  "+seq1+"	with	"+seq2+"");
//			System.out.println("Memoization table");
//			printTable(memoTable);
//			System.out.println("\nPredecessor table (where the values came from)");	
//			printTable3D(predecessorIndexes);

			//int minimumPenalty = memoTable[sequence1Original.length()][sequence2Original.length()];
			//int minimumPenalty = memoTable[seq1.length()][seq2.length()];
	 		//System.out.println("\n" + minimumPenalty + "\t is the Minimum penalty for aligning \""+sequence1Original+"\" with \""+sequence2Original+"\"");
			findAlignment(seq1, seq2, memoTable);
		}
	}

//	private void printTable(int[][] table) {
//		for (int[] row : table) {
//			for (int value : row) {
//				System.out.print(value + "\t");
//			}
//			System.out.println();
//		}
//	}
//
//	private void printTable3D(int[][][] table3D) {
//		for (int[][] row : table3D) {
//			for (int[] xyPair : row) {
//				System.out.print(Arrays.toString(xyPair) + "\t");
//			}
//			System.out.println();
//		}
//	}

	/*@
	 * 	seq1 = oriStr,
	 * seq1 = tranStr 
	 */				 
	//Retrace the memoTable to find the actual alignment, not just the minimum cost
	private void findAlignment(String seq1, String seq2, int[][] memoTable) {
		
		seq1Aligned = "";
		seq2Aligned = "";
		
		int i = seq1.length() - 1; //-1 since seq1 & seq2 have leading space
		int j = seq2.length() - 1;

		//Retrace the memoTable calculations. Stops when reaches the start of 1 sequence (so additional gaps may still need to be added to the other)
		while (i > 0 && j > 0) {
			if (memoTable[i][j] - mismatchPenalty(seq1.charAt(i), seq2.charAt(j)) == memoTable[i - 1][j - 1]) { //case1: both aligned
				seq1Aligned = seq1.charAt(i) + seq1Aligned;
				seq2Aligned = seq2.charAt(j) + seq2Aligned;
				i--;
				j--;
			}
			else if (memoTable[i][j] - this.gapPenalty == memoTable[i - 1][j]) { //case2: seq1 with gap
				seq1Aligned = seq1.charAt(i) + seq1Aligned;
				seq2Aligned = GAP_CHAR + seq2Aligned;
				i--;
			}
			else if (memoTable[i][j] - this.gapPenalty == memoTable[i][j - 1]) { //case3: seq2 with gap
				seq2Aligned = seq2.charAt(j) + seq2Aligned;
				seq1Aligned = GAP_CHAR + seq1Aligned;
				j--;
			}
		}
		//Now i==0 or j==0 or both. Finish by adding any additional leading gaps to the start of the sequence whose pointer ISN'T == 0
		while (i > 0) {		//Seq1 reached the beginning, print rest of seq2 & add gaps to seq2
			seq1Aligned = seq1.charAt(i) + seq1Aligned;
			seq2Aligned = GAP_CHAR + seq2Aligned;
			i--;
		}
		while (j > 0) {		//Seq2 reached the beginning, print rest of seq1 & add gaps to seq2
			seq2Aligned = seq2.charAt(j) + seq2Aligned;
			seq1Aligned = GAP_CHAR + seq1Aligned;
			j--;
		}

				
//		System.out.println("\nOptimal Alignment:\n" + seq1Aligned + "\n" + seq2Aligned + "\n");
		
		// number of correct words
		int correctCount = numberofcorrectwords(seq1Aligned, seq2Aligned);
		//  number of insertions
		int insertCount = numberofinsertions(seq1Aligned, seq2Aligned);
		// number of deletions
		int delCount = numberofdeletions(seq1Aligned, seq2Aligned);
		
		substitutionsCount = seq1.length() - correctCount - 1;
		
//		System.out.println("number of correct words	"+correctCount);
//		System.out.println("number of insertions	"+insertCount);
//		System.out.println("number of deletions	"+delCount);
	}

	private int mismatchPenalty(char char1, char char2) {
//		if (char1 == char2) {
//			return 0;
//		}
//		else if (consonants.contains(char1) && consonants.contains(char2)) {
//			return this.consonantConsonantMismatchPenalty;
//		}
//		else if (vowels.contains(char1) && vowels.contains(char2)) {
//			return this.vowelVowelMismatchPenalty;
//		}
//		else if (numbers.contains(char1) && numbers.contains(char2)){
//			return this.numberNumberMismatchPenalty;
//		}
//		return this.vowelConsonantMismatchPenalty;
		
		if (char1 == char2) {
			return 0;
		}else {
			return this.vowelConsonantMismatchPenalty;
		}
		
	}

	private static String Remove_Symbol(String input_str)
	{
		String str = input_str.replaceAll("[a-zA-Z0-9\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b]", "");
		
		return str;
	}
	
//	private String sanitizeSequence(String sequence)
//	{
//		return " " + sequence		//prepend a space @ the start. Allows for easier calls to mismatchPenalty() & array boundaries for size of memoTalbe to be "<" instead of "<="
//				.toLowerCase()
//				.replaceAll("[^a-z0-9]", "");	//remove all characters other than letters & digits (trims surrounding whitespace & removes spaces too). a-z matches the lowercase alphabet since previous line guarantees lowercase
//	}

	private int numberofdeletions(String ori, String tran) 
	{		
//		int oriNum = ori.length();
//		int tranNum = tran.length();
//		
//		if(oriNum > tranNum) {
//			delCount = oriNum - tranNum; 
//		}
		
		//Vector split_ori = new Vector();
		Vector split_tran = new Vector();
		//split_ori = Chinese_Split(ori);
		split_tran = Chinese_Split(tran);
		
		for(int i=0; i<split_tran.size(); i++)
		{	
			//System.out.println(split_tran.get(i));
			if(split_tran.get(i).toString().equalsIgnoreCase(GAP_CHAR)) {
				delCount++;
			}else if(split_tran.get(i).toString().contains(GAP_CHAR)) {
				delCount++;
			}
		}
		
		return delCount;
	}

	private int numberofinsertions(String ori, String tran) 
	{		
		Vector split_ori = new Vector();
		//Vector split_tran = new Vector();
		split_ori = Chinese_Split(ori);
		//split_tran = Chinese_Split(tran);
		
		for(int i=0; i<split_ori.size(); i++)
		{			
			if(split_ori.get(i).toString().equalsIgnoreCase(GAP_CHAR)) {
				insertCount++;
			}
		}
		
		return insertCount;
	}
	
	private int numberofcorrectwords(String ori, String tran) 
	{
		Vector split_ori = new Vector();
		Vector split_tran = new Vector();
		//split_ori = Chinese_Split_Correct(ori);
		//split_tran = Chinese_Split_Correct(tran);
		split_ori = Chinese_Split(ori);
		split_tran = Chinese_Split(tran);		
		Vector correctStr = new Vector();
			
		int errorCount = 0;
		
		for(int i=0; i<split_ori.size(); i++)
		{			
			for(int j=0; j<split_tran.size(); j++) {
				if(split_ori.get(i).toString().equalsIgnoreCase(split_tran.get(j).toString())) {
					correctCount++;
					correctStr.add(split_ori.get(i));
					
					break;
				}
			}			
		}
		
		errorCount = split_ori.size() - correctCount;
		
		//System.out.println(split_ori.size()+"	"+correctCount+"	"+errorCount);
		//System.out.println(split_ori.size()+"	"+correctCount);
		
		return correctCount;
	}
	
	private Vector Chinese_Split(String input)
	{
		int cha_num = 2;
		Vector split_vec = new Vector();
		// Remove Symbol
		String inputStr = Remove_nonChineseSymbol(input);		

		// Chinese Character split
		List<String> splitStringList = chineseSplitFunction(inputStr, cha_num);
		for (String split : splitStringList) {						
			split_vec.add(split.trim());					
		}
		
		return split_vec; 
	}
	
	public static List<String> chineseSplitFunction(String src, int bytes) 
	{
		try {
			if (src == null) {
				return null;
			}
			List<String> splitList = new ArrayList<String>();
			int startIndex = 0; 
			int endIndex = bytes > src.length() ? src.length() : bytes;
			while (startIndex < src.length()) {
				String subString = src.substring(startIndex, endIndex);
				
				
				while (subString.getBytes("GBK").length > bytes) {
					--endIndex;
					subString = src.substring(startIndex, endIndex);
				}
				splitList.add(src.substring(startIndex, endIndex));
				startIndex = endIndex;
				
				endIndex = (startIndex + bytes) > src.length() ? src.length() : startIndex + bytes;

			}
			return splitList;

		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}
	
	private String Remove_nonChineseSymbol(String input_str)
	{		
		String str = input_str.replaceAll("[a-zA-Z0-9\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b]", "");
		
		//System.out.println(str);
		
		return str;
	}
	
	private void WER(int S, int D, int I, int C, String ori, String tran)
	{
		double WERrate = 0;
		
		//WERrate = (Double.valueOf(D)+Double.valueOf(I))/(Double.valueOf(D)+Double.valueOf(C));
		WERrate = (Double.valueOf(D)+Double.valueOf(I)+Double.valueOf(S))/(Double.valueOf(D)+Double.valueOf(C)+Double.valueOf(S));
		System.out.println("Aligning "+ ori+"	"+tran);
		System.out.println(ori.length()+"	"+tran.length()+"	"+S+"	"+D+"	"+I+"	"+C);
		System.out.println(WERrate+"\n");
		
		if(WERrate>=0) {
			WERrateSum += WERrate;
		}			
	}
	
	public static void main(String[] args) {
		String[][] testSequences = {
//			{ "MEAN", "name" },		//case insensitivity test
//			{ "abc", "ab" },
//			{ "asdc", "gcasa" },
//			{ "abc", "bc" },
//			{ "ab", "zabz" },
//			{ "ab", "1ab" },
//			{ "2ab", "1ab1" },
			
			//{ "2ab", "1ab1" }
			{ " 如果拿地球總人口來比喻的話", " 如果拿地球總人口數做比喻，的" }
		};

		try {
			WordErrorRate sequenceAligner = new WordErrorRate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		String seq1 = Remove_Symbol(testSequences[0][0]);
//		String seq2 = Remove_Symbol(testSequences[0][1]);
				
//		sequenceAligner.calculateAndPrintOptimalAlignment(seq1, seq2);
	}

}
