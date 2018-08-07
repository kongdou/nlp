package fun.deepsky.nlp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;

/**
 * DocumentPreprocessor 类处理文本
 * @author deepsky
 *
 */
public class DpTest {

	public static void main(String[] args) throws Exception {
		File file = new File("src\\main\\resources\\dp.txt");
		Reader reader = new FileReader(file);
		
		DocumentPreprocessor dp = new DocumentPreprocessor(reader);
		
		Iterator<List<HasWord>> it = dp.iterator();
		
		while(it.hasNext()) {
			List<HasWord> sentence = it.next();
			for(HasWord token:sentence) {
				System.out.println(token);
			}
		}
		
	}
}
