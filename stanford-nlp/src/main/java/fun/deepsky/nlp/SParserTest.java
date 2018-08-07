package fun.deepsky.nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.*;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.trees.international.pennchinese.ChineseTreebankLanguagePack;
import edu.stanford.nlp.util.CoreMap;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;



public class SParserTest {

	public void testTokenizer() throws IOException {
        String text = "我爱中国，郑小婉是我的妻子。";
        // split the document into sentences.
        for (String sentence : ChineseDocumentToSentenceProcessor.fromPlainText(text)) {
            System.out.println(sentence);
        }
        // token english
        PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer(new StringReader(text),
                new CoreLabelTokenFactory(), "");

        while (ptbt.hasNext()) {
            CoreLabel word = ptbt.next();
            System.out.println(word);
        }

        // no effect on chinese.
        ChineseTreebankLanguagePack tlp = new ChineseTreebankLanguagePack();
        TokenizerFactory ctf = tlp.getTokenizerFactory();
        Tokenizer<Word> ct = ctf.getTokenizer(new StringReader(text));

        while (ct.hasNext()) {
            Word word = ct.next();
            System.out.println(word);
        }


        // finally we select lucene's StandardAnalyzer to get token.
        Analyzer st = new StandardAnalyzer();
        TokenStream ts = st.tokenStream("", text);
        ts.reset();
        while (ts.incrementToken()) {
            System.out.println(ts.getAttribute(CharTermAttribute.class).toString()+"====");
        }
    }

    public void testSegmenter() {
        StanfordCoreNLP pipeline = new StanfordCoreNLP("CoreNLP-chinese.properties");
        Annotation document = pipeline.process("我爱中国。郑小婉是我的妻子。");
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        StringBuilder result = new StringBuilder();
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                result.append(word).append(" ");
            }
        }
        System.out.println(result.toString());
    }

    public void testTagger() {
        MaxentTagger tagger = new MaxentTagger("edu/stanford/nlp/models/pos-tagger/chinese-distsim/chinese-distsim.tagger");
        String text = "我 爱 中国 。郑小婉 是 我 的 妻子 。";
        // text is splitted with space internally
        List<List<HasWord>> sentences = tagger.tokenizeText(new BufferedReader(new StringReader(text)));
        for (List<? extends HasWord> sentence : sentences) {
            List<edu.stanford.nlp.ling.TaggedWord> tSentence = tagger.tagSentence(sentence);
            System.out.println(tSentence);
        }
    }


    public void testParser() {
        String text = "郑小婉是我的妻子。";
        String grammars = "edu/stanford/nlp/models/lexparser/xinhuaFactoredSegmenting.ser.gz";
        LexicalizedParser lp = LexicalizedParser.loadModel(grammars);


        //lp.setOptionFlags(new String[]{"-outputFormat", "penn,typedDependenciesCollapsed"});
        Tree tree = lp.parse(text);
        tree.pennPrint();
        //TokenizerFactory<CoreLabel> f = PTBTokenizer.coreLabelFactory();
        ChineseTreebankLanguagePack tlp = new ChineseTreebankLanguagePack();
        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
        GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
        Collection<TypedDependency> tdl = gs.typedDependenciesCollapsed();
        System.out.println(tdl.toString());


        String s = "";
        for (int i = 0; i < tdl.size(); i++) {
            TypedDependency td = (TypedDependency) tdl.toArray()[i];
            s += td.gov().word() + " " + td.reln().toString() + " " + td.dep().word() + "\n";
        }
        System.out.println(s);
    }


    public void testPipeline() {
        StanfordCoreNLP pipeline = new StanfordCoreNLP("CoreNLP-chinese.properties");
        Annotation annotation = pipeline.process("我爱中国。郑小婉是我的妻子。");


        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        CoreMap sentence = sentences.get(1);


        List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);
        System.out.println("分词" + "\t " + "标注" + "\t " + "实体识别");
        System.out.println("-----------------------------");
        for (CoreLabel token : tokens) {
            String word = token.getString(TextAnnotation.class);
            String pos = token.getString(PartOfSpeechAnnotation.class);
            String ner = token.getString(NamedEntityTagAnnotation.class);
            System.out.println(word + "\t " + pos + "\t " + ner);
        }
    }


    public void testCommand() {
        String[] arg = {"-encoding", "utf-8",
                "-outputFormat", "penn,typedDependenciesCollapsed",
                "edu/stanford/nlp/models/lexparser/xinhuaFactoredSegmenting.ser.gz",
                "src/main/resources/dp.txt"};
        LexicalizedParser.main(arg);
    }

    public static void main(String[] args) throws IOException {
        SParserTest test = new SParserTest();
        System.out.println("......test tokenizer......");
        test.testTokenizer();
        System.out.println("......test segmenter......");
        test.testSegmenter();
        System.out.println("......test tagger......");
        test.testTagger();
        System.out.println("......test parser......");
        test.testParser();
        System.out.println("......test pipeline......");
        test.testPipeline();
        System.out.println("......test command......");
        test.testCommand();
    }
    
	
}
