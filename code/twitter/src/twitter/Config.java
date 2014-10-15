package twitter;
 
import java.io.PrintStream;
import twitter4j.conf.ConfigurationBuilder;
 
 public class Config
 {
	// twitter user name & password in twitter developer and create new app then you will get accesstoken and all. 
   public static String username = "";
   public static String password = "";
   public static void setUserAccessTokens(ConfigurationBuilder cb)
   {
     cb.setOAuthConsumerKey("");
     cb.setOAuthConsumerSecret("");
     cb.setOAuthAccessToken("");
     cb.setOAuthAccessTokenSecret("");
 
     cb.setJSONStoreEnabled(true);
 
     System.out.println("true is set");
  }
 }
