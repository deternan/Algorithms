package TF_IDF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DocumentParser_Chinese extends Parameters
{  
    private List termsDocsArray = new ArrayList<>();		// terms in each doc
    private List allTerms = new ArrayList<>(); 				// all terms in all docs
    private List tfidfDocsVector = new ArrayList<>();
 
    // -------------------------------------------------- 
 	private FileReader fr;
 	private BufferedReader br = null;
    // Video info
 	private Vector all_video_id;
    // Translation
 	private Big5_GB BG;
 	private String translation_str;	
 	// Stop Words
 	private Vector stopwords_vec;
 	// Segment Words
 	private Segmentation seg;
 	private Vector words_str_vec;
 	// doc words
 	private Vector doc_words_total;
 	
    public DocumentParser_Chinese() throws Exception
    {
    	all_video_id = new Vector();
    	stopwords_vec = new Vector();
    	words_str_vec = new Vector();
    	
    	Read_Stopwords();
    	Read_all_video();
    	
    	
    	//System.out.println(termsDocsArray.size());
    	//System.out.println(allTerms.size());
    	//System.out.println(tfidfDocsVector.size());
    }
    
    private void parseFiles(Vector input) throws FileNotFoundException, IOException 
    {
        /*
    	File[] allfiles = new File(filePath).listFiles();
        BufferedReader in = null;
        for (File f : allfiles) 
        {            
        	if (f.getName().endsWith(".txt")) {
                in = new BufferedReader(new FileReader(f));
                StringBuilder sb = new StringBuilder();
                String s = null;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                }
                String[] tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");   //to get individual terms
                for (String term : tokenizedTerms) {
                    if (!allTerms.contains(term)) {  //avoid duplicate entry
                        allTerms.add(term);
                    }
                }
                termsDocsArray.add(tokenizedTerms);
            }
        }
        */

    	//File[] allfiles = new File(filePath).listFiles();
        //BufferedReader in = null;
        //for (File f : allfiles) 
        {            
        	//if (f.getName().endsWith(".txt")) 
        	{
                //in = new BufferedReader(new FileReader(f));
                //StringBuilder sb = new StringBuilder();
                //String s = null;
                //while ((s = in.readLine()) != null) 
                {
                    //sb.append(s);
                }
                
                
                String[] tokenizedTerms = new String[input.size()];
                input.toArray(tokenizedTerms);                 
                //System.out.println(tokenizedTerms.length);
                
//                String[] tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");   //to get individual terms
                for (String term : tokenizedTerms) 
                {
                    if (!allTerms.contains(term)) 
                	{  //avoid duplicate entry
                        allTerms.add(term);
                    }
                }
                termsDocsArray.add(tokenizedTerms);
            }
        }
    	
    }
    
    public void tfIdfCalculator()
    {
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term requency inverse document frequency          
        //for (String[] docTermsArray : termsDocsArray) 
        for(int i=0; i<termsDocsArray.size(); i++)
        {
        	String[] docTermsArray = (String[]) termsDocsArray.get(i); 
        	double[] tfidfvectors = new double[allTerms.size()];
            int count = 0;
            //for (String terms : allTerms)
            for(int j=0; j<allTerms.size(); j++)
            {
                String terms = allTerms.get(j).toString();
            	tf = new TfIdf().tfCalculator(docTermsArray, terms);
                idf = new TfIdf().idfCalculator(termsDocsArray, terms);
                if((tf == 0) || (idf ==0)){
                	
                }
                tfidf = tf * idf;
                tfidfvectors[count] = tfidf;
                count++;
            }
            tfidfDocsVector.add(tfidfvectors);  		//storing document vectors;
        }
    }
    
    // Return tf_idf vector
    public List Return_tfidfDocsVector()
    {
    	return tfidfDocsVector;
    }
    
    // Return terms in each doc
    public List Return_termsDocsArray()
    {
    	return termsDocsArray;
    }
    
    // Return all terms in all docs
    public List Return_allTerms()
    {
    	return allTerms;
    }
    
    // Rrturn all video id
    public Vector Return_all_video_id()
    {
    	return all_video_id;
    }
    
    // Read Stopwords
    private void Read_Stopwords() throws Exception
	{
		StopWords st = new StopWords();
		stopwords_vec = st.Return_vec();		
	}	
    
    private void Read_all_video() throws Exception
	{
		File folder1 = new File(file_folder);
		String[] list1 = folder1.list();

		for (int i=0; i<list1.length; i++) 		
		{			
			Read_data(list1[i]);
		}
	}
    
    private void Read_data(String filename) throws Exception
    {
    	fr = new FileReader(file_folder + filename);
		br = new BufferedReader(fr);
		String line;
		String[] AfterSplit;		
		
		while (br.ready())	
		{
			line = br.readLine().toString();
			AfterSplit = line.split("\t");
			all_video_id.add(AfterSplit[0]);
			
			// Big5 GB Translation		
			translation_str = Big5_GB(AfterSplit[1]);					
			
			words_str_vec = Segmentation(translation_str);
			doc_words_total = Remove_StopWords(words_str_vec);
			
			//System.out.println(translation_str);
			//System.out.println(doc_words_total.size());
			
			parseFiles(doc_words_total);
		}
		fr.close();
		br.close();
    }
    
    // Big5 GB Translation
 	private String Big5_GB(String input_str)
 	{
 		BG = new Big5_GB(input_str);		
 		String translation_str = BG.Return_str();
 		//System.out.println(translation_str);
 		
 		return translation_str;
 	}
    
 	// Segment words 
 	private Vector Segmentation(String input_str) throws Exception
 	{
 		Vector words_str_vec_temp = new Vector(); 
 		seg = new Segmentation(input_str);
 		
 		words_str_vec_temp = seg.Return_vec(); 		
 		
 		return words_str_vec_temp;
 	}
 	
 	// Remove Stop words
 	private Vector Remove_StopWords(Vector input)
	{
 		Vector All_words_temp = new Vector();
 		boolean check;
		
		for(int i=0; i<input.size(); i++)
		{
			check = true;
			for(int j=0;j<stopwords_vec.size();j++)
			{
				if(input.get(i).toString().equalsIgnoreCase(stopwords_vec.get(j).toString())){
					check = false;
					break;
				}				
			}
			
			if(check == true)
			{
				// Term length
				if(input.get(i).toString().length() > 1)
				{
					All_words_temp.add(input.get(i));	
				}
				
			}
		}
		
		return All_words_temp;
	}
 	
}