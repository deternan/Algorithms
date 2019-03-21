package CoreNLP;

/*
 * 
 *  Reference
 *  
 *  https://my.oschina.net/u/1397325/blog/233738
 *  
 *  CoreNLP
 *  https://blog.sectong.com/blog/corenlp_segment.html
 *  http://stanfordnlp.github.io/CoreNLP/api.html#interpreting-the-output
 *  http://stanfordnlp.github.io/CoreNLP/human-languages.html#another-language
 *  
 */

/*
 * JAR
 * ZHConverter.jar
 * 
 */

import java.util.List;
import java.util.Properties;

import com.spreada.utils.chinese.ZHConverter;

import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.Mention;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;
import edu.stanford.nlp.util.StringUtils;

public class CoreNLP_Keywords 
{
	String sim_Str;
	
	public CoreNLP_Keywords()
	{
		/* 
		 //Properties props = new Properties();      
		 Properties props = PropertiesUtils.asProperties("props", "StanfordCoreNLP-chinese.properties");
	     props.put("annotators", "tokenize, ssplit, pos");    		  
	     StanfordCoreNLP pipeline = new StanfordCoreNLP(props);      
	          
	     String text = "三星、 LG、松下、樂視都有可能在大會上展示自家的電視新品。樂視或將把無邊框的設計帶到電視上；而不久前方收購了 QD Vision 的三星，大概會進一步展現其在量子點技術上的實力，去年的 CES 上，三星就推出了14款使用了量子點技術的電視產品，且同時搭載曲面設計及 HDR 特性，很是賺了一番眼球";               // 输入文本  
	          
	     Annotation document = new Annotation(text);
	     pipeline.annotate(document);
	     List<CoreMap> sentences = document.get(SentencesAnnotation.class);  
	     
	     //System.out.println(sentences.size());
	     for(CoreMap sentence: sentences) 
	     {  
	         for (CoreLabel token: sentence.get(TokensAnnotation.class)) 
	         {  
	                
	             String word = token.get(TextAnnotation.class);  
	             String pos = token.get(PartOfSpeechAnnotation.class);  	                 
	             System.out.println(word+"\t"+pos+"\t");  
	         }  
	     }
	     */
		
		/*
		String text = "李克强主持召开国务院常务会议";
		Annotation document = new Annotation(text);
		Properties props = PropertiesUtils.asProperties("props", "StanfordCoreNLP-chinese.properties");		
		StanfordCoreNLP corenlp = new StanfordCoreNLP(props);
		corenlp.annotate(document);		
		//List<CoreMap> sentences = document.get(SentencesAnnotation.class);		
		List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
		CoreMap sentence = sentences.get(0);
		
		List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);
		for (CoreLabel token : tokens) 
		{
            String word = token.getString(TextAnnotation.class);
            String pos = token.getString(PartOfSpeechAnnotation.class);
            String ner = token.getString(NamedEntityTagAnnotation.class);
            System.out.println(word + "\t " + pos + "\t " + ner);
        }
		*/
		
		String text = "那除了会可能之外恶意遗弃一起死还有一个非常大的这个影响的程度他甚至于有可能超越ＷＰＳ呢它是一个什么样的一个别提拿到这个包这个路爆燃共同";		
		sim_Str = Sim2Tra(text);		
				
		Annotation document = new Annotation(sim_Str);
        Properties props = StringUtils.argsToProperties("-props", "StanfordCoreNLP-chinese.properties");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        pipeline.annotate(document);
                
        for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) 
        {            
            for (Mention m : sentence.get(CorefCoreAnnotations.CorefMentionsAnnotation.class)) 
            {
                System.out.println("\t" + m);
            }
        }
		
	}
	
	private String Sim2Tra(String input)
	{
		ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
        
		String simplifiedStr = converter.convert(input);
        System.out.println(simplifiedStr);
        /* 輸出結果: 有背光的机械式键盘 */
 
        //簡體轉繁體
        //String traditionalStr = ZHConverter.convert(simplifiedStr, ZHConverter.TRADITIONAL);
        //System.out.println(traditionalStr);
		
		return simplifiedStr;
	}
	
	public static void main(String[] a)
	{
		CoreNLP_Keywords pos = new CoreNLP_Keywords();
	}
	
}
