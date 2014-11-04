package streamSelf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
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
import twitter4j.conf.ConfigurationBuilder;

public class TweetStream extends TimerTask{

	static Date d ;
	ConfigurationBuilder cb,cb1;
	Twitter twitter = null;
	String path  = "/user/ranger/btp/";
	TwitterStream twitterStream = null;
	FSDataOutputStream fsOutStream ;
	Path newFilePath;
	FileSystem fs ;
	List<String>trends = null;
	
	public  void getTrendsByWoeid(int woeid)
			throws TwitterException {
		cb = new ConfigurationBuilder();
		cb.setJSONStoreEnabled(true);
		Config.setUserAccessTokens(cb);
		cb1 = new ConfigurationBuilder();
		twitter = new TwitterFactory(this.cb.build()).getInstance();

		trends = new ArrayList<String>();
		Trends ttrends = twitter.getPlaceTrends(woeid);
		Trend[] trend = ttrends.getTrends();
		for (Trend t : trend) {
			trends.add(t.getName());
		}
		
}
	public void searchTweets(String str) throws IOException
	{
		cb1 = new ConfigurationBuilder();
		cb1.setJSONStoreEnabled(true);
		Config.setUserAccessTokens1(cb1);
		String [] keywords = new String[trends.size()];
		for (int i = 0; i < trends.size(); i++) {
			keywords[i] = ((String)trends.get(i));	
		}
		newFilePath = new Path(path+str+"/tweets.txt");
		fs.createNewFile(newFilePath);
		fsOutStream = fs.create(newFilePath);
		twitterStream = new TwitterStreamFactory(this.cb1.build()).getInstance();
		StatusListener listener = new StatusListener() {
			
			@Override
			public void onException(Exception arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStatus(Status s) {
				// TODO Auto-generated method stub
				if(!s.getUser().getLang().equals("en"))	return;
				System.out.println("Status is entered");
				byte[] tweet = s.toString().getBytes();
				try {
					fsOutStream.write(tweet);
					fsOutStream.write("\n".getBytes());
					fsOutStream.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				
				
			}
			
			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		twitterStream.addListener(listener);
		if(keywords==null||keywords.length==0)
			twitterStream.sample();
		else
			twitterStream.filter(new FilterQuery().track(keywords));
	}
	public void createTrendFile(String name) throws IOException
	{
		newFilePath = new Path(path+name+"/trends.txt");
		fs.createNewFile(newFilePath);
		fsOutStream = fs.create(newFilePath);
		System.out.println("Trends are being upadted...............");
		for(int i = 0;i<trends.size();i++)
		{
			fsOutStream.write(trends.get(i).toString().getBytes());
			fsOutStream.writeBytes("\n");
		}
		fsOutStream.flush();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		d = new Date();
		String str = d.toString();
		str = "Day_"+str.substring(8,10)+"_Min_"+str.substring(14, 16);
		try {
			fs = FileSystem.get(new Configuration());
			newFilePath = new Path(path+str);
			fs.mkdirs(newFilePath);
			getTrendsByWoeid(23424848);
			createTrendFile(str);
			searchTweets(str);
		} catch (IOException | TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	}
	
