package dataCollection;


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
public class Data extends TimerTask
{
	Date d ;
	static ConfigurationBuilder cb;
	public static Twitter twitter = null;
	public static String path = "/user/ranger/btp/";
	static TwitterStream twitterStream = null;
	FSInputStream dsInputStream;
	static FSDataOutputStream fsOutStream;
	static FSDataOutputStream fsOutStreamES;
	static Path newFilePath;
	static FileSystem fs ;


	public Data() 
	{
		this.cb = new ConfigurationBuilder();
		this.cb.setJSONStoreEnabled(true);
		Config.setUserAccessTokens(this.cb);
	}

	public void search(String[] keywords) throws IOException{

		newFilePath = new Path(path+"entweets.txt");
		fs.createNewFile(newFilePath);
		fsOutStream = fs.create(newFilePath);
		newFilePath = new Path(path+"estweets.txt");
		fs.createNewFile(newFilePath);
		fsOutStreamES = fs.create(newFilePath);
		twitterStream = new TwitterStreamFactory(this.cb.build()).getInstance();
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

			if(s.getUser().getLang().equals("en")){
				try 
				{
					System.out.println("English Status");
					byte[] tweet = s.getText().getBytes();
					fsOutStream.write(tweet);
					fsOutStream.write("\n".getBytes());
					fsOutStream.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				}
			 if(s.getUser().getLang().equals("es")){
					try 
					{
						System.out.println("Spanish  Status");
						byte[] tweet = s.getText().getBytes();
						fsOutStreamES.write(tweet);
						fsOutStreamES.write("\n".getBytes());
						fsOutStreamES.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
					}
				else return ;
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
		else{
			String [] language = new String[2];
			language[0] = "en";
			language[1] = "es";
			FilterQuery fq = new FilterQuery();
			fq.track(keywords);
			fq.language(language);
			twitterStream.filter(fq);

		}}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			
			Path pt=new Path(path+"/emotions.txt");
			fs = FileSystem.get(new Configuration());
			BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(pt)));
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


			search(keywords);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();



		}
	}

}
