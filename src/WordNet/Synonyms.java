package WordNet;

/*
 * version: June 22, 2015 03:37 PM
 * Last revision: June 22, 2015 03:37 PM
 * 
* Author : Chao-Hsuan Ke
*/

/*
 * JAR
 * edu.mit.jwi_2.3.3.jar
 */

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;

public class Synonyms 
{
	private IDictionary dict;
	private String input_str = "cat";
	
	public Synonyms() throws IOException
	{		
//		testDictionary();
		
		// look up first sense of the input word
		IIndexWord idxWord = dict.getIndexWord (input_str, POS.NOUN);
		IWordID wordID = idxWord . getWordIDs () . get (0) ;
		IWord word = dict.getWord ( wordID ) ;
		//System.out.println (" Id = " + wordID ) ;
		//System.out.println (" Lemma = " + word . getLemma () ) ;
		//System.out.println (" Gloss = " + word . getSynset () . getGloss () ) ;
		
		getSynonyms(dict);
	}
	
	private void getSynonyms (IDictionary dict)
	{
		// look up first sense of the input word
		IIndexWord idxWord = dict.getIndexWord (input_str, POS.NOUN);
		IWordID wordID = idxWord.getWordIDs().get(0); // 1st meaning
		IWord word = dict.getWord(wordID) ;
		ISynset synset = word . getSynset () ;
		
		// iterate over words associated with the synset
		for(IWord w : synset.getWords())
			System.out.println ("aa	"+w.getLemma());
	}
	
	private void getHypernyms (IDictionary dict)
	{
		IIndexWord idxWord = dict.getIndexWord (input_str, POS.NOUN);
		IWordID wordID = idxWord.getWordIDs ().get (0) ; // 1st meaning
		IWord word = dict.getWord(wordID) ;
		ISynset synset = word.getSynset() ;
		
		// get the hypernyms
		List < ISynsetID > hypernyms =
		synset.getRelatedSynsets(Pointer.HYPERNYM);

		List < IWord > words ;
		for( ISynsetID sid : hypernyms ) 
		{
			words = dict.getSynset(sid).getWords() ;
			System.out.print( sid + " {") ;
			for( Iterator < IWord > i = words.iterator() ; i.hasNext() ;) 
			{
				System.out.print ( i.next().getLemma() ) ;
				if( i.hasNext () )
					System.out.print (", ") ;
			}
			System.out.println("}");
		}
	}
	
	private void testDictionary() throws IOException
	{
		// construct the URL to the Wordnet dictionary directory
		String wnhome = "D:\\Eclipse\\E4E\\Word\\";		
		String path = wnhome + "dict";
		//URL url = new URL ("file", null , path);
		URL url = new URL("file", null , path);
		
		// construct the dictionary object and open it
		dict = new Dictionary(url) ;
		dict.open();
	}
	
	public static void main(String[] args)
	{
		try {
			Synonyms syn = new Synonyms();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
