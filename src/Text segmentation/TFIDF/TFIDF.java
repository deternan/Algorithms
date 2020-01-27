package TFIDF;

/**
* Author: WuLC
* Date:   2016-05-22 17:46:15
* Last modified by:   WuLC
* Last Modified time: 2016-05-23 23:31:25
* Email: liangchaowu5@gmail.com
************************************************************
* Function:get keywords of file through TF-IDF algorithm
* Input: path of directory of files that need to extract keywords
* Output: keywords of each file
*
* Revised at June 01, 2017
*/


import java.io.*;
import java.util.*;

import Hybrid.Parameters;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.lc.nlp.parsedoc.*;


public class TFIDF extends Parameters
{
	private static Vector ckip_words = new Vector();		
    private static int keywordsNumber = 5;
    
    public TFIDF() throws Exception
    {
    	// Jieba
    	Jieba();
    }
    
    /**
     * change the number of keywords,default 5
     * @param keywordNum(int): number of keywords that need to be extracted
     */
    public static void setKeywordsNumber(int keywordNum)
    {
        keywordsNumber = keywordNum;
    }

	/**
     * calculate TF value of each word in terms of  the content of file
     * @param fileContent(String): content of file
     * @return(HashMap<String, Float>): "words:TF value" pairs
     */
    public static HashMap<String, Float> getTF(Vector ckip_words)
    {    
    	List<Term> terms=new ArrayList<Term>();
        ArrayList<String> words = new ArrayList<String>();
       
        // Terms
        //terms=HanLP.segment(fileContent);
        //System.out.println("getTF: "+ckip_words.size());
        
        //for(Term t:terms)
        for(int i=0;i<ckip_words.size();i++)
        {
        	//if(TFIDF.shouldInclude(t))
        	if(ckip_words.get(i).toString().length() > 1)
        	{
        		//words.add(t.word);
        		words.add(ckip_words.get(i).toString());
        	}      		
        }
        
        // get TF values
    	 HashMap<String, Integer> wordCount = new HashMap<String, Integer>();
    	 HashMap<String, Float> TFValues = new HashMap<String, Float>();
    	 for(String word : words)
         {
             if(wordCount.get(word) == null)
             {
            	 wordCount.put(word, 1);
             }
             else
             {
            	 wordCount.put(word, wordCount.get(word) + 1);
             }
         }
    	 
         int wordLen = words.size();
         //traverse the HashMap
         Iterator<Map.Entry<String, Integer>> iter = wordCount.entrySet().iterator(); 
         while(iter.hasNext())
         {
             Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>)iter.next();
             TFValues.put(entry.getKey().toString(), Float.parseFloat(entry.getValue().toString()) / wordLen);
           //System.out.println(entry.getKey().toString() + " = "+  Float.parseFloat(entry.getValue().toString()) / wordLen);
         }
         return TFValues;
     } 
  
    
    /**
     * judge whether a word belongs to stop words
     * @param term(Term): word needed to be judged
     * @return(boolean):  if the word is a stop word,return false;otherwise return true    
     */
    public static boolean shouldInclude(Term term)
    {
        return CoreStopWordDictionary.shouldInclude(term);
    }
      
    
    /**
     * calculate TF values for each word of each file under a directory
     * @param dirPath(String): path of the directory
     * @throws Exception 
     * @return(HashMap<String,HashMap<String, Float>>): path of file and its  corresponding "word-TF Value" pairs
     * @throws IOException
     */
    public static HashMap<String,HashMap<String, Float>> tfForDir(String dirPath) throws Exception 
    {
        HashMap<String, HashMap<String, Float>> allTF = new HashMap<String, HashMap<String, Float>>();
        List<String> filelist = ReadDir.readDirFileNames(dirPath);
        
        File folder = new File(dirPath);
        File[] listOfFiles = folder.listFiles();
        
        //for(String file : filelist)        
        for (File file : listOfFiles)
        {
            HashMap<String, Float> dict = new HashMap<String, Float>();
            //String content = ReadFile.loadFile(listOfFiles[i]); // remember to modify the loadFile method of class ReadFile
            //dict = TFIDF.getTF(content);
            CKIP_Parser(file.getName());
            dict = TFIDF.getTF(ckip_words);
            //allTF.put(file, dict);
            //System.out.println(allTF);
            allTF.put(file.toString(), dict);
        }
        return allTF;
    }

    
    /**
     * calculate IDF values for each word  under a directory
     * @param dirPath(String): path of the directory
     * @throws Exception 
     * @return(HashMap<String, Float>): "word:IDF Value" pairs
     */
    public static HashMap<String, Float> idfForDir(String dirPath) throws Exception
    {
    	List<String> fileList = new ArrayList<String>();
    	//fileList = ReadDir.readDirFileNames(dirPath);
    	//int docNum = fileList.size();  
    	
        Map<String, Set<String>> passageWords = new HashMap<String, Set<String>>();        
        // get words that are not repeated of a file 
        
        File folder = new File(dirPath);
        File[] listOfFiles = folder.listFiles();
        int docNum = listOfFiles.length;
        
        //for(String file : filelist)        
        for (File file : listOfFiles)
        {   
        	List<Term> terms=new ArrayList<Term>();
            Set<String> words = new HashSet<String>();
            //String content = ReadFile.loadFile(filePath); // remember to modify the loadFile method of class ReadFile               
            //terms=HanLP.segment(content);
            
            CKIP_Parser(file.getName());
            
            //for(Term t:terms)
            //System.out.println("getTF: "+ckip_words.size());
            
            //for(Term t:terms)
            for(int i=0;i<ckip_words.size();i++)
            {
            	//if(TFIDF.shouldInclude(t))
            	if(ckip_words.get(i).toString().length() > 1)
            	{
            		//words.add(t.word);
            		words.add(ckip_words.get(i).toString());
            	}      		
            }
            passageWords.put(file.toString(), words);
        }
        
        // get IDF values
        HashMap<String, Integer> wordPassageNum = new HashMap<String, Integer>();
        //System.out.println(passageWords);
        //for(String filePath : fileList)
        for (File file : listOfFiles)
        {
            Set<String> wordSet = new HashSet<String>();
            //wordSet = passageWords.get(filePath);
            wordSet = passageWords.get(file.toString());
            //System.out.println(file.toString());
            //System.out.println(wordSet);
            //CKIP_Parser(file.getName());
            
            //for(int i=0;i<ckip_words.size();i++)
            for(String word: wordSet)
            {           	
                if(wordPassageNum.get(word) == null)
                	wordPassageNum.put(word,1);
                else             
                	wordPassageNum.put(word, wordPassageNum.get(word) + 1);
//            	if(wordPassageNum.get(ckip_words.get(i)) == null)
//                	wordPassageNum.put(ckip_words.get(i).toString(), 1);
//                else             
//                	wordPassageNum.put(ckip_words.get(i).toString(), wordPassageNum.get(ckip_words.get(i).toString()) + 1);
            }
        }
        //System.out.println(wordPassageNum);
        
        HashMap<String, Float> wordIDF = new HashMap<String, Float>(); 
        Iterator<Map.Entry<String, Integer>> iter_dict = wordPassageNum.entrySet().iterator();
        while(iter_dict.hasNext())
        {
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>)iter_dict.next();
            float value = (float)Math.log( docNum / (Float.parseFloat(entry.getValue().toString())) );
            wordIDF.put(entry.getKey().toString(), value);
            
            //System.out.println(entry.getKey().toString() + "=" +value);
            //System.out.println(entry.getKey()+"	"+entry.getValue());
        }
        return wordIDF;
    }

    
    /**
     * calculate TF-IDF value for each word of each file under a directory
     * @param dirPath(String): path of the directory
     * @throws Exception 
     * @return(Map<String, HashMap<String, Float>>): path of file and its corresponding "word:TF-IDF Value" pairs
     */
    public static Map<String, HashMap<String, Float>> getDirTFIDF(String dirPath) throws Exception
    {
        HashMap<String, HashMap<String, Float>> dirFilesTF = new HashMap<String, HashMap<String, Float>>();  
        HashMap<String, Float> dirFilesIDF = new HashMap<String, Float>(); 
        
        dirFilesTF = TFIDF.tfForDir(dirPath);
        dirFilesIDF = TFIDF.idfForDir(dirPath);
        
        Map<String, HashMap<String, Float>> dirFilesTFIDF = new HashMap<String, HashMap<String, Float>>(); 
        Map<String,Float> singlePassageWord= new HashMap<String,Float>();
        
                        
        File folder = new File(dirPath);
        File[] listOfFiles = folder.listFiles();
        
        //List<String> fileList = new ArrayList<String>();
        //fileList = ReadDir.readDirFileNames(dirPath);
        //for (String filePath: fileList)      
        for (File file : listOfFiles)
        {
        	HashMap<String,Float> temp= new HashMap<String,Float>();
        	//singlePassageWord = dirFilesTF.get(filePath);
        	//System.out.println(file.toString());
        	singlePassageWord = dirFilesTF.get(file.toString());
        	Iterator<Map.Entry<String, Float>> it = singlePassageWord.entrySet().iterator();
        	
        	//System.out.println(file+"	"+dirFilesTF);
        	//System.out.println(dirFilesIDF);
        	while(it.hasNext())
        	{
        		Map.Entry<String, Float> entry = it.next();
        		String word = entry.getKey();
        		Float TFIDF = entry.getValue()*dirFilesIDF.get(word);
        		temp.put(word, TFIDF);
        	}
        	//dirFilesTFIDF.put(filePath, temp);
        	dirFilesTFIDF.put(file.toString(), temp);
        }
        return dirFilesTFIDF;
    }
 
    
    /**
     * get keywords of each file under a certain directory 
     * @param dirPath(String): path of directory
     * @param keywordNum(int): number of keywords to extract
     * @throws Exception 
     * @return(Map<String,List<String>>): path of file and its corresponding keywords
     */
    public static Map<String,List<String>> getKeywords(String dirPath) throws Exception
    {
    	//List<String> fileList = new ArrayList<String>();
    	//fileList = ReadDir.readDirFileNames(dirPath);    	
    	//System.out.println(fileList);
    	
    	File folder = new File(dirPath);
        File[] listOfFiles = folder.listFiles();
    	
    	// calculate TF-IDF value for each word of each file under the dirPath
    	Map<String, HashMap<String, Float>> dirTFIDF = new HashMap<String, HashMap<String, Float>>(); 
    	dirTFIDF = TFIDF.getDirTFIDF(dirPath);
    	
    	Map<String,List<String>> keywordsForDir = new HashMap<String,List<String>>(); 
    	
    	//for (String file:fileList)
    	for (File file : listOfFiles)
    	{
    		//System.out.println(file);
    		Map<String,Float> singlePassageTFIDF= new HashMap<String,Float>();
    		//singlePassageTFIDF = dirTFIDF.get(file);
    		singlePassageTFIDF = dirTFIDF.get(file.toString());
    		
    		//sort the keywords in terms of TF-IDF value in descending order
	        List<Map.Entry<String,Float>> entryList=new ArrayList<Map.Entry<String,Float>>(singlePassageTFIDF.entrySet());	        
	
	        Collections.sort(entryList,new Comparator<Map.Entry<String,Float>>()
	        {
	        	@Override
	        	public int compare(Map.Entry<String,Float> c1,Map.Entry<String,Float> c2)
	        	{
	        		return c2.getValue().compareTo(c1.getValue()); 	        		
	        	}
	        }
	        );
	        	        
	       // get keywords 
            List<String> systemKeywordList=new ArrayList<String>();
            for(int k=0;k<keywordsNumber;k++)
            {
            	try
            	{
            		systemKeywordList.add(entryList.get(k).getKey());
            	}
            	catch(IndexOutOfBoundsException e)
            	{
            		continue;
            	}
            }
            
            //keywordsForDir.put(file, systemKeywordList);
            keywordsForDir.put(file.toString(), systemKeywordList);
        }
        return keywordsForDir;
    }
          
    private static void CKIP_Parser(String filename)
	{		
		try {
			// ckip
			ckipsvr_parser cp = new ckipsvr_parser(filename);
			ckip_words = cp.words;
//			for(int i=0;i<ckip_words.size();i++){
//				System.out.println(ckip_words.get(i));
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
    
    private void Jieba() throws Exception
    {
    	// Jieba
    	Jieba jieba = new Jieba();
    }
    
}
