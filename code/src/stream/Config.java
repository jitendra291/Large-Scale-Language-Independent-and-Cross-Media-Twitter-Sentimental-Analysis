package stream;
import twitter4j.conf.ConfigurationBuilder;

 public class Config
 {
        // twitter user name & password in twitter developer and create new app then you will get accesstoken and all. 
   public static String username = "sahil_kharb";
   public static String password = "sahilkharb";
   public static void setUserAccessTokens(ConfigurationBuilder cb)
   {
     cb.setOAuthConsumerKey("c830kQmEXIQiuptu96TybZlrB");
     cb.setOAuthConsumerSecret("QXPbMbV8DtxWgmivonkRrCmK5wh1cRFo2EfOJYIMldRDr2x4ml");
     cb.setOAuthAccessToken("1048626140-nKg8mqOkgzXywsxlX4Gkt4V7OMRS15SI785yXsA");
     cb.setOAuthAccessTokenSecret("GhCC0tde6cZjhtJfPidA2PvgisciHbgmhcDKyALDxnHs8");

     cb.setJSONStoreEnabled(true);

     System.out.println("true is set");
  }
 }

