package TF_IDF;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TfIdfMain 
{
	//private static List abc = new ArrayList<>();	
	private static ArrayList<double[]> tfidf_list = new ArrayList<double[]>();
	private static ArrayList<String[]> allterms = new ArrayList<String[]>();
	private static ArrayList<String[]> termsDocs_terms = new ArrayList<String[]>();
	
	 public static void main(String args[]) throws FileNotFoundException, IOException
	 {
		 DocumentParser dp = new DocumentParser();
		 dp.parseFiles("D:\\Java code\\Test\\Document\\"); // give the location of source file
		 dp.tfIdfCalculator(); //calculates tfidf
		 
		 
		 //double[] stringArray2 = stringList.toArray( new String[0] );
		 //abc = dp.Return_tfidfDocsVector();
		 //System.out.println(abc.get(0).length);
		 
		 tfidf_list = (ArrayList<double[]>) dp.Return_tfidfDocsVector();
		 System.out.println(tfidf_list.get(0).length);
		 System.out.println(tfidf_list.get(1).length);
		 for(int i=0; i<tfidf_list.get(0).length; i++)
		 {
			 System.out.println(i+"	"+tfidf_list.get(0)[i]);
		 }
		 
		 termsDocs_terms = (ArrayList<String[]>) dp.Return_termsDocsArray();
		 System.out.println(termsDocs_terms.get(0).length);
		 System.out.println(termsDocs_terms.get(1).length);
		 //for(int i=0; i<termsDocs_terms.get(0).length; i++)
		 {
			 //System.out.println(i+"	"+termsDocs_terms.get(0)[i]);
		 }
		 
		 //termsDocsArray.size()
		 /*
		 allterms = (ArrayList<String[]>) dp.Return_allTerms();
		 System.out.println(allterms.size());
		 for(int i=0; i<allterms.size(); i++)
		 {
			 System.out.println(allterms.get(i));
		 }
		 */
	 }
}
