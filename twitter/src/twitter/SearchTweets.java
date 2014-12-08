package twitter;
 
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
//		import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.internal.json.MediaEntityJSONImpl;
import twitter4j.internal.org.json.JSONObject;
import twitter4j.json.DataObjectFactory;
import twitter4j.*;
 
public class SearchTweets
{
   ConfigurationBuilder cb;
 
   public SearchTweets()
   {
     this.cb = new ConfigurationBuilder();
     this.cb.setJSONStoreEnabled(true);
     Config.setUserAccessTokens(this.cb);
   }
 
   public void search(String[] keywords, final BufferedWriter out){
	TwitterStream twitterStream = null;//new TwitterStreamFactory(cb.build()).getInstance();
	StatusListener listener = new StatusListener() {

		public void onStatus(Status s){
			//System.out.println(s.getText());
			if(!s.getUser().getLang().equals("en"))	return;
			try 
			{
					System.out.println("Entered Status");
					String in = DataObjectFactory.getRawJSON(s);
					out.write(in);
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
	};
     twitterStream = new TwitterStreamFactory(this.cb.build()).getInstance();
     System.out.println("Entering listener");
     twitterStream.addListener(listener);
     System.out.println("Out of Listener");
     if ((keywords == null) || (keywords.length == 0))
       twitterStream.sample();
     else
       twitterStream.filter(new FilterQuery().track(keywords));
   }
 
   public static void main(String[] args)
     throws IOException
   {
     SearchTweets st = new SearchTweets();
     String DBPath = "";
     String OutputFile = "";
     String DBPath_neg = "";
     String OutputFile_neg = "";
 
     //if (args.length < 2) {
     //  System.out.println("Usage: java SearchTweets <DBPath/> <OutputTweet File>\n DBPath must contain a file called 'keywords.txt' containing the keywords to track. ");
     //  System.exit(0);
     //}
     //else {
       DBPath = "/home/hk/btp/fwdfwaboutopinionminingstudentsproject/twitter/data/positive_emotions.txt";
       OutputFile = "/home/hk/btp/fwdfwaboutopinionminingstudentsproject/twitter/data/tweets_positive emotions.txt";
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
     //FilterQuery tweetFilterQuery = new FilterQuery(); // See 
     //tweetFilterQuery.track(new String[]{"Bieber", "Teletubbies"}); // OR on keywords
     //tweetFilterQuery.locations(new double[][]{new double[]{-126.562500,30.448674},
     //                new double[]{-61.171875,44.087585
     //                }}); // See https://dev.twitter.com/docs/streaming-apis/parameters#locations for proper location doc. 
     //Note that not all tweets have location metadata set.
     //tweetFilterQuery.language(new String[]{"en"}); // Note that language does not work properly on Norwegian tweets 
     //tweetFilterQuery.l
   }
}
