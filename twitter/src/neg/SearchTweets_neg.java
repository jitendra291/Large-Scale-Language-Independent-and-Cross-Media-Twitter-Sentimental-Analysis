package neg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Vector;


import twitter4j.FilterQuery;
import twitter4j.MediaEntity;
import twitter4j.StallWarning;
//		import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;
import twitter4j.*;
 
public class SearchTweets_neg
{
   ConfigurationBuilder cb;
 
   public SearchTweets_neg()
   {
     this.cb = new ConfigurationBuilder();
     this.cb.setJSONStoreEnabled(true);
     config.setUserAccessTokens(this.cb);
   }
 
   public void search(String[] keywords, final BufferedWriter out){
	TwitterStream twitterStream = null;//new TwitterStreamFactory(cb.build()).getInstance();
	StatusListener listener = new StatusListener() {

		public void onStatus(Status s){
			//System.out.println(s.getText());
			if(!s.getUser().getLang().equals("en"))	return;
			try 
			{
					System.out.println(s.getLang()+" "+s.getText());
					
					out.write(DataObjectFactory.getRawJSON(s));
					out.write("\n");
					out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
			System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
		}

		public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
		}

		public void onScrubGeo(long userId, long upToStatusId) {
			System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
		}
		
		public void onException(Exception ex) {
			ex.printStackTrace();
		}

		@Override
		public void onStallWarning(StallWarning arg0) {
			// TODO Auto-generated method stub
			
		}
	};
     twitterStream = new TwitterStreamFactory(this.cb.build()).getInstance();
     System.out.println("Entering listener");
     twitterStream.addListener(listener);
     String [] language = null;
     language[0] = "fr";
     System.out.println("Out of Listener");
     if ((keywords == null) || (keywords.length == 0))
       twitterStream.sample();
     else
       twitterStream.filter(new FilterQuery().language(language));
   }
 
   public static void main(String[] args)
     throws IOException
   {
     SearchTweetsNeg st = new SearchTweetsNeg();
     String DBPath = "";
     String OutputFile = "";
     String DBPath_neg = "";
     String OutputFile_neg = "";
 
     //if (args.length < 2) {
     //  System.out.println("Usage: java SearchTweets <DBPath/> <OutputTweet File>\n DBPath must contain a file called 'keywords.txt' containing the keywords to track. ");
     //  System.exit(0);
     //}
     //else {
       DBPath = "/home/ranger/btp2k15/twitter/data/negative_emotions.txt";
       OutputFile = "/home/ranger/btp2k15/twitter/data/tweets_negative emotions.txt";
     //}
     System.out.println("DBPath:" + DBPath);
     FileReader fr = new FileReader(DBPath);
     //FileReader fr_neg = new FileReader(DBPath + "negative_emotions.txt");
     BufferedReader br = new BufferedReader(fr);
     Vector k = new Vector();
     while (true) {
       String keyword = br.readLine();
       if (keyword == null) break;
       if (!keyword.startsWith("//"))
         k.add(keyword);
     }
     String[] keywords = new String[k.size()];
     for (int i = 0; i < k.size(); i++) {
       keywords[i] = ((String)k.get(i));
     }
     Date d = new Date();
 
     System.out.println(OutputFile);
     FileWriter fstream = new FileWriter(OutputFile);
     BufferedWriter out = new BufferedWriter(fstream);
     System.out.println("Entering search");
     st.search(keywords, out);

   }
}

