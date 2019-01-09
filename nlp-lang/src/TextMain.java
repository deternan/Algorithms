
/*
 * Get API
 * 
 * version: January 10, 2019 01:28 AM
 * Last revision: January 10, 2019 01:28 AM
 * 
 * Author : Chao-Hsuan Ke
 * E-mail : phelpske.dev at gmail dot com
 * 
 */

/*
 * Reference
 * https://github.com/luhuiguo/chinese-utils
 * 
 */

import java.io.IOException;
import com.luhuiguo.chinese.ChineseUtils;

public class TextMain {

	public TextMain() throws Exception {
		String SCinput = "头发发财";
		toTC(SCinput);
		
		String TCinput = "硬碟";
		toSC(TCinput);
	}
	
	private void toTC(String input) throws IOException {
		
		System.out.println(input + " => " + ChineseUtils.toTraditional(input));
		
	}
	
	private void toSC(String input) throws IOException {
		
		System.out.println(input + " => " + ChineseUtils.toSimplified(input));
	}
	
	 public static void main(String[] args)  {
		 try {
			TextMain tm = new TextMain();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	
}
