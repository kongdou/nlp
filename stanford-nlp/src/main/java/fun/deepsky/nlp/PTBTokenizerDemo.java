package fun.deepsky.nlp;

import java.io.StringReader;
import java.util.List;

import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class PTBTokenizerDemo {

	public static void main(String[] args) {
		LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/xinhuaFactored.ser.gz");  
		String text = "我爱北京天安门，天安门上太阳升。";
		//text = "张晓光投保了三者险100万，车损险200万,不计免赔";
		   PTBTokenizer ptb = new PTBTokenizer(new StringReader(text),new CoreLabelTokenFactory(),null);
		   while(ptb.hasNext()) {
			   System.out.println(ptb.next());
		   }
	}
}
