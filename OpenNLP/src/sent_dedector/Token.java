package sent_dedector;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import snowballstemmer.PorterStemmer;

public class Token{

	public static void main(String[] args) throws IOException {
		String paragraph = "Hi. How are you? This is Mike.";
		 
		// always start with a model, a model is learned from training data
		InputStream is = new FileInputStream("en-sent.bin");
		SentenceModel model = new SentenceModel(is);
		SentenceDetectorME sdetector = new SentenceDetectorME(model);
	 
		String sentences[] = sdetector.sentDetect(paragraph);
	 
		System.out.println(sentences[0]);
		System.out.println(sentences[1]);
		is.close();
	
		PorterStemmer stemmer = new PorterStemmer();
        
		//set the word to be stemmed
		stemmer.setCurrent("hiiiiiiiiiiiiiiiiii");
		             
		//call stem() method. stem() method returns boolean value.
		if(stemmer.stem())
		{
		    //If stemming is successful obtain the stem of the given word
		    System.out.println(stemmer.getCurrent());
		    //System.out.println(stemmer.)
		}
	}
}
