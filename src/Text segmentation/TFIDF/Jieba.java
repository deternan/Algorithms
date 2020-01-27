package TFIDF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Vector;

import Hybrid.JiebaSegmenter;
import Hybrid.JiebaSegmenter.SegMode;

public class Jieba extends Parameters
{
	private BufferedReader bfr;
	private StringBuffer sb = new StringBuffer();
	
	private Vector jieba_words;
	private String Line = "";
	
	public Jieba() throws Exception
	{
		Read_Source();
		
		// Jieba
		jieba_words = new Vector();		
		JiebaSegmenter segmenter = new JiebaSegmenter();
		segmenter.process(Line, SegMode.SEARCH).toString();
		
		//System.out.println(segmenter.process(input_sentence, SegMode.SEARCH).toString());

		jieba_words = segmenter.Return_tokens_vec();
		for(int i=0;i<jieba_words.size();i++)
		{
			System.out.println(jieba_words.get(i));
		}
		
	}
	
	private void Read_Source() throws Exception
	{
		
		String temp;
		File file = new File(CKIP_folder + CKIP_in + CKIP_file);
		FileReader fr = new FileReader(CKIP_folder + CKIP_in + CKIP_file);
		BufferedReader br = new BufferedReader(fr);
		while ((temp = br.readLine()) != null) {			
			Line+= temp;
		}
		//System.out.println("Jieba: "+Line);
		fr.close();
		br.close();	
	}
	
}
