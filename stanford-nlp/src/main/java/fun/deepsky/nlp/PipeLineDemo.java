package fun.deepsky.nlp;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;

public class PipeLineDemo {

	public static void main(String[] args) {
		
		String text = "赵晓杰投保了三者险100万，车损险200万,不计免赔";
		
		//使用默认配置
		//StanfordCoreNLP pipeline = new StanfordCoreNLP("CoreNLP-chinese.properties");

        //自定义功能 （1）
        /*
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
		*/
        //自定义功能(2) 自己在项目中建一个properties 文件，然后在文件中设置模型属性，可以参考1中的配置文件
        /* 
        String[] args = new String[] {"-props", "properties/CoreNLP-Seg-CH.properties"};
        Properties properties = StringUtils.argsToProperties(args);
        StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
        */

        //自定义功能(3)
        StanfordCoreNLP pipeline = new StanfordCoreNLP(PropertiesUtils.asProperties(
                "annotators", "tokenize,ssplit,pos,lemma,ner",
                "ssplit.isOneSentence", "true",
                "tokenize.language", "zh",
                "ner.model","edu/stanford/nlp/models/ner/chinese.misc.distsim.crf.ser.gz",
                "segment.model", "newmodel.ser.gz",
               // "segment.model", "edu/stanford/nlp/models/segmenter/chinese/ctb.gz",
                "segment.sighanCorporaDict", "edu/stanford/nlp/models/segmenter/chinese",
                "segment.serDictionary", "edu/stanford/nlp/models/segmenter/chinese/dict-chris6.ser.gz",
                "segment.sighanPostProcessing", "true"
        ));
		Annotation annotation = new Annotation(text);
		pipeline.annotate(annotation);
		
		List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        CoreMap sentence = sentences.get(0);

        List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);
        System.out.println("字/词" + "\t " + "词性" + "\t " + "实体标记");
        System.out.println("-----------------------------");
        for (CoreLabel token : tokens) {
            String word = token.getString(TextAnnotation.class);
            String pos = token.getString(PartOfSpeechAnnotation.class);
            String ner = token.getString(NamedEntityTagAnnotation.class);
            System.out.println(word + "\t " + pos + "\t " + ner);
        }
	}
}
