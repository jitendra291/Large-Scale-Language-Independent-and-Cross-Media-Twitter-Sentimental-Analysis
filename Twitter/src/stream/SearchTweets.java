package stream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URI;
import java.util.Date;
import java.util.Vector;
import java.io.*;
import java.util.*;
import java.net.*;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

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

public class SearchTweets
{
   ConfigurationBuilder cb;
 
   public SearchTweets()
   {
     this.cb = new ConfigurationBuilder();
     this.cb.setJSONStoreEnabled(true);
     Config.setUserAccessTokens(this.cb);
   }
 
   public void search(String[] keywords, final FSDataOutputStream fsOutStream){
	TwitterStream twitterStream = null;//new TwitterStreamFactory(cb.build()).getInstance();
	StatusListener listener = new StatusListener() {
		
		@Override
		public void onException(Exception arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			
		}
		
		@Override
		public void onStatus(Status s) {
			// TODO Auto-generated method stub
			long count =0;
			if(!s.getUser().getLang().equals("en"))	return;
			try 
			{
					System.out.println("Entered Status");
					System.out.println(s.getText());
					count++;		
					System.out.println(count);
						 byte[] tweets = s.toString().getBytes();
				       fsOutStream.write(tweets);
					fsOutStream.write("\n".getBytes());
					fsOutStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		@Override
		public void onStallWarning(StallWarning arg0) {
			
		}
				@Override
		public void onScrubGeo(long userId, long upToStatusId) {
			// TODO Auto-generated method stub
			System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
		}
				@Override
		public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
			// TODO Auto-generated method stub
			System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());

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
 
   public static void main(String[] args)     throws IOException
   {
       FileSystem fs = FileSystem.get(new Configuration());
       Path newFilePath=new Path("/user/ranger/btp/tweets.txt");
       fs.createNewFile(newFilePath);
       FSDataOutputStream fsOutStream = fs.create(newFilePath);
       newFilePath = new Path("/user/ranger/btp/keywords.txt");
       BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(newFilePath)));
       Vector k = new Vector();
     SearchTweets st = new SearchTweets();
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
         System.out.println("Entering search");
     st.search(keywords, fsOutStream);
   }
}