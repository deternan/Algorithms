package TF_IDF;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to read documents
 *
 * @author Mubin Shrestha
 */
public class DocumentParser 
{

    //This variable will hold all terms of each document in an array.
    private List termsDocsArray = new ArrayList<>();
    private List allTerms = new ArrayList<>(); //to hold all terms
    private List tfidfDocsVector = new ArrayList<>();

    /**
     * Method to read files and store in array.
     * @param filePath : source file path
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void parseFiles(String filePath) throws FileNotFoundException, IOException 
    {
        File[] allfiles = new File(filePath).listFiles();
        BufferedReader in = null;
        for (File f : allfiles) 
        {
            //System.out.println(f);
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

    }
    
    /**
     * Method to create termVector according to its tfidf score.
     */
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
            tfidfDocsVector.add(tfidfvectors);  //storing document vectors;
        }
    }
    
    public List Return_tfidfDocsVector()
    {
    	return tfidfDocsVector;
    }
    
    public List Return_termsDocsArray()
    {
    	return termsDocsArray;
    }
    
    public List Return_allTerms()
    {
    	return allTerms;
    }
    
}

