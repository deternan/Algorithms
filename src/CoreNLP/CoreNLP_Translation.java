package CoreNLP;

/*
 * Simplified Chinese and Traditional Chinese
 * 
 * version: March 21, 2019 01:26 PM
 * Last revision: March 21, 2019 01:26 PM
 * 
 * Author : Chao-Hsuan Ke
 * Institute: Delta Research Center
 * Company : Delta Electronics Inc. (Taiwan)
 * 
 */

/*
 * JAR
 * ZHConverter.jar
 * 
 */

import com.spreada.utils.chinese.ZHConverter;

public class CoreNLP_Translation 
{	
	public CoreNLP_Translation()
	{		
		String text = "那除了会可能之外恶意遗弃一起死还有一个非常大的这个影响的程度他甚至于有可能超越ＷＰＳ呢它是一个什么样的一个别提拿到这个包这个路爆燃共同";		
		
		Sim2Tra(text);
		
		//Tra2Sim(text);
	}
	
	private void Sim2Tra(String input)
	{		
		ZHConverter converter = ZHConverter.getInstance(ZHConverter.TRADITIONAL);
		
		String simplifiedStr = converter.convert(input);
        System.out.println(simplifiedStr);
	}
	
	private void Tra2Sim(String input)
	{		
		ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);

        String traditionalStr = converter.convert(input);
        System.out.println(traditionalStr);		
	}
	
	public static void main(String[] a)
	{
		CoreNLP_Translation tra = new CoreNLP_Translation();
	}
	
}
