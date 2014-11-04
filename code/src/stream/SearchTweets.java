package stream;
import trends.*;

import java.util.TimerTask;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
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
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;
import twitter4j.*;

import java.util.*;
public class SearchTweets extends TimerTask
{
	Date d ;
	ConfigurationBuilder cb,cb1;
	public static Twitter twitter = null;
	public static String path = "/user/ranger/btp/";
	TwitterStream twitterStream = null;
	FSDataOutputStream fsOutStream ;
	Path newFilePath;
	FileSystem fs ;


	public SearchTweets() 
	{
		this.cb = new ConfigurationBuilder();
		this.cb.setJSONStoreEnabled(true);
		Config.setUserAccessTokens(this.cb);
		//new TwitterStreamFactory(cb.build()).getInstance();
		this.cb1 = new ConfigurationBuilder();
		this.cb1.setJSONStoreEnabled(true);
		Config.setUserAccessTokens(this.cb1);


	}

	public void search(List trends, String count) throws IOException{
		String[] keywords = new String[trends.size()];
		for (int i = 0; i < trends.size(); i++) {
			keywords[i] = ((String)trends.get(i));	
		}
		newFilePath = new Path(path+count+"/tweets.txt");
		fs.createNewFile(newFilePath);
		fsOutStream = fs.create(newFilePath);
		twitterStream = new TwitterStreamFactory(this.cb1.build()).getInstance();
		StatusListener listener = new StatusListener() {
			long count =0;
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

				if(!s.getUser().getLang().equals("en"))	return;
				try 
				{
					System.out.println("Entered Status");
					count++;		
					System.out.println(count);
					byte[] tweet = s.toString().getBytes();
					fsOutStream.write(tweet);
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
		System.out.println("Entering listener");
		twitterStream.addListener(listener);
		System.out.println("Out of Listener");
		if ((keywords == null) || (keywords.length == 0))
			twitterStream.sample();
		else
			twitterStream.filter(new FilterQuery().track(keywords));
	}

	public void headStart(List trends,String count) throws IOException 
	{
		fs = FileSystem.get(new Configuration());
		newFilePath=new Path(path+count);
		fs.mkdirs(newFilePath);
		newFilePath = new Path(path+count+"/keywords.txt");
		fs.createNewFile(newFilePath);
		fsOutStream= fs.create(newFilePath);

		for(int i = 0;i<trends.size();i++)
		{
			fsOutStream.write(trends.get(i).toString().getBytes());
			fsOutStream.writeBytes("\n");
		}
		fsOutStream.flush();
		String[] keywords = new String[trends.size()];
		for (int i = 0; i < trends.size(); i++) {
			keywords[i] = ((String)trends.get(i));	
		}

	}
	public  List getTrendsByWoeid(int woeid)
			throws TwitterException {
		twitter = new TwitterFactory(this.cb.build()).getInstance();

		List trendNames = new ArrayList();
		Trends trends = twitter.getPlaceTrends(woeid);
		Trend[] trend = trends.getTrends();
		for (Trend t : trend) {
			trendNames.add(t.getName());
		}
		return trendNames;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub


		try {
			d = new Date();
			String str = d.toString();
			str = "Day_"+str.substring(8,10)+"_Min_"+str.substring(14, 16);
			List keywords = getTrendsByWoeid(23424848);
			headStart(keywords,str);
			search(keywords, str);
		} catch (IOException | TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();



		}
	}

}
