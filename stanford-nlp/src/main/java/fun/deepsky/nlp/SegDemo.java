package fun.deepsky.nlp;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class SegDemo {
	
	public static CRFClassifier<CoreLabel>segmenter; 

	static { 

	// 设置一些初始化参数

	Properties props = new Properties(); 

	props.setProperty("sighanCorporaDict","edu/stanford/nlp/models/segmenter/chinese"); 

	props.setProperty("serDictionary","edu/stanford/nlp/models/segmenter/chinese/dict-chris6.ser.gz"); 
	
	
	props.setProperty("inputEncoding","UTF-8"); 

	props.setProperty("sighanPostProcessing","true"); 

	segmenter = new CRFClassifier<CoreLabel>(props); 
	segmenter.loadClassifierNoExceptions("newmodel.ser.gz",props); 
	//segmenter.loadClassifierNoExceptions("edu/stanford/nlp/models/segmenter/chinese/ctb.gz",props); 
	segmenter.flags.setProperties(props);

	} 

	public static String doSegment(String sent) {

	String[] strs =(String[]) segmenter.segmentString(sent).toArray(); 

	StringBuffer buf= new StringBuffer(); 

	for (String s :strs) { 

	buf.append(s +"/"); 

	} 

	System.out.println("segmentedres: " + buf.toString()); 

	return buf.toString();

	} 

	public static void main(String[] args) { 


	//String readFileToString = FileUtils.readFileToString(new File("IFENG-8.txt")); 

	String doSegment = doSegment("赵晓杰投保了三者险100万，车损险200万,不计免赔"); 

	System.out.println(doSegment);

	//ExtractDemo extractDemo= new ExtractDemo(); 

	//System.out.println(extractDemo.doNer(doSegment));

	System.out.println("Complete!");

	} 

}
