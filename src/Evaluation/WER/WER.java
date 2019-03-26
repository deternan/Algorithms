package Evaluation.WER;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/*
 * WER (Word Error Rate) Evaluation 
 * 
 * version: March 26, 2019 09:25 AM
 * Last revision: March 26, 2019 09:25 AM
 * 
 * 
 */

/*
 * Reference
 * https://github.com/romanows/WordSequenceAligner
 * 
 */

import Evaluation.WER.WordSequenceAligner.Alignment;

public class WER {

	private String orifolder = "";
	private String orifile = "";
	private String tranfolder = "";
	private String tranfile = "";
	private BufferedReader oribfr;	
	private BufferedReader tranbfr;
	FileReader fr;
	
	private String ori_str = "";
	private String tran_str = "";
	// 
	WordSequenceAligner werEval = new WordSequenceAligner();
	
	public WER() throws Exception {
		
		String Line = "";
		// ori file		
		fr = new FileReader(orifolder + orifile);
		oribfr = new BufferedReader(fr);		
		while ((Line = oribfr.readLine()) != null) {			
			ori_str += Line + " ";
		}
		fr.close();
		oribfr.close();		
		
		// translated file
		fr = new FileReader(tranfolder + tranfile);
		tranbfr = new BufferedReader(fr);
		while ((Line = tranbfr.readLine()) != null) {			
			tran_str += Line + " ";
		}
		fr.close();
		tranbfr.close();
				
		ori_str = Remove_Symbol(ori_str.toLowerCase());
		tran_str = Remove_Symbol(tran_str.toLowerCase());
		//System.out.println(ori_str.length());
		//System.out.println(tran_str.length());
		
		String [] ref = ori_str.split(" ");
		String [] hyp = tran_str.split(" ");
		//System.out.println(ref.length);
		//System.out.println(hyp.length);
		
		Alignment a = werEval.align(ref, hyp);
		System.out.println(a);
	}
	
	private static String Remove_Symbol(String input_str)
	{
		//String str = input_str.replaceAll("[a-zA-Z0-9\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b]", "");
		String str = input_str.replaceAll("[\\pP+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]", "");
		
		return str;
	}
	
	public static void main(String[] args) {
		try {
			WER ss = new WER();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
