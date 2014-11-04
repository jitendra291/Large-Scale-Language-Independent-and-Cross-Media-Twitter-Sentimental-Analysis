package sent_dedector;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import snowballstemmer.PorterStemmer;

public class OpenNlpTest {
public static void main(String[] args) throws IOException {
    POSModel model = new POSModelLoader().load(new File("en-pos-maxent.bin"));
    PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
    POSTaggerME tagger = new POSTaggerME(model);
    String pathToSWN = "/home/jkhduser/workspace/OpenNLP/SentiWordNet_3.0.0_20130122.txt";
	SentiWordNetDemoCode sentiwordnet = new SentiWordNetDemoCode(pathToSWN);
    String input = "not drinking is a good.? !";
    input = input.replaceAll("(!|,|\\.|\\?)", "");
    ObjectStream<String> lineStream = new PlainTextByLineStream(new StringReader(input));

    perfMon.start();
    String line;
    while ((line = lineStream.read()) != null) {

        String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(line);
        String[] tags = tagger.tag(whitespaceTokenizerLine);

        POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
        System.out.println(sample.toString());

        PorterStemmer stemmer = new PorterStemmer();
        
        double total_sentiment=0;
        String to[] = sample.getSentence();
    	for(int i=0;i< to.length;i++){
    		//stemmer.setCurrent(sample.getSentence()[i]);
    		String temp=sample.getTags()[i];
    		String k="";
    		
    		if(temp.charAt(0)=='N'){
    			k="n";
    		}
    		else if (temp.charAt(0)=='J')
    		{k="a";
    		}
    		else if (temp.charAt(0)=='R')
    		{
    			k="r";
    		}
    		else if (temp.charAt(0)=='V')
    		{k="v";
    		}
    		
    		double val=sentiwordnet.extract(sample.getSentence()[i], k);
    		System.out.println(val +" : "+sample.getSentence()[i]+"_"+sample.getTags()[i]);
    		total_sentiment += val;
    	}
    	System.out.println("total_sentiment: "+total_sentiment);
        perfMon.incrementCounter();
    }
    
	
    perfMon.stopAndPrintFinalResult();
}
}