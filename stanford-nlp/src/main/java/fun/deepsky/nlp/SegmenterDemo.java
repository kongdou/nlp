package fun.deepsky.nlp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;

/**
 * 
 * @author deepsky
 *
 */
public class SegmenterDemo {


	public static void main(String args[]) throws Exception {
		// 设置utf-8输出格式
		System.setOut(new PrintStream(System.out, true, "utf-8"));
		Properties props = new Properties();
		props.setProperty("sighanCorporaDict", "edu/stanford/nlp/models/segmenter/chinese");
		props.setProperty("serDictionary", "edu/stanford/nlp/models/segmenter/chinese/dict-chris6.ser.gz,data/prpall.txt");
		if (args.length > 0) {
			props.setProperty("testFile", args[0]);
		}
		props.setProperty("inputEncoding", "UTF-8");
		props.setProperty("sighanPostProcessing", "true");
		// 设置分词器
		CRFClassifier<CoreLabel> segmenter = new CRFClassifier<>(props);
		segmenter.loadClassifierNoExceptions("edu/stanford/nlp/models/segmenter/chinese/ctb.gz", props);
	    //segmenter.loadClassifierNoExceptions("edu/stanford/nlp/models/segmenter/chinese/pku.gz", props);
		for (String filename : args) {
			segmenter.classifyAndWriteAnswers(filename);
		}
		// 设置输入
		String sample = "车牌号京N100N3 车损险50万 三者险100万";
		//使用Stanford分词
		List<String> segmented = segmenter.segmentString(sample);
		System.out.println(segmented);
	}
}
