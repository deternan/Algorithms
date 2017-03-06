package TF_IDF;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TfIdfMain 
{
	// All video id 
	private Vector all_video;
	// TF-IDF
	private static ArrayList<double[]> tfidf_list = new ArrayList<double[]>();
	private static ArrayList<String[]> allterms = new ArrayList<String[]>();
	private static ArrayList<String[]> termsDocs_terms = new ArrayList<String[]>();
	// Output
	
	public TfIdfMain() throws Exception
	{
		 DocumentParser_Chinese dp_ch = new DocumentParser_Chinese();
		 dp_ch.tfIdfCalculator();
		 
		 
		 // tfidf list
		 tfidf_list = (ArrayList<double[]>) dp_ch.Return_tfidfDocsVector();			 
		 for(int i=0; i<tfidf_list.size(); i++)
		 {
			//for(int j=0; j<tfidf_list.get(i).length; j++)
			{
				//System.out.print(tfidf_list.get(i)[j]+",");
			}
			//System.out.println();
		 }
		 	 
	}
	
	 public static void main(String args[]) throws Exception
	 {	
		 TfIdfMain tfidf = new TfIdfMain(); 
	 }
	 
}