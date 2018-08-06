package fun.deepsky.opennlp;

import opennlp.tools.tokenize.SimpleTokenizer;

public class Tokenizer {

	public static void main(String[] args) {
		String text = "中华人民共和国";
		SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;
		String tokens[] = simpleTokenizer.tokenize(text);
		
		for(String token:tokens) {
			System.out.println(token);
		}
		
	}
}
