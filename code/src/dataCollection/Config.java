package dataCollection;

import twitter4j.conf.ConfigurationBuilder;

public class Config {
	public static void setUserAccessTokens(ConfigurationBuilder cb)
	   {
	     cb.setOAuthConsumerKey("c830kQmEXIQiuptu96TybZlrB");
	     cb.setOAuthConsumerSecret("QXPbMbV8DtxWgmivonkRrCmK5wh1cRFo2EfOJYIMldRDr2x4ml");
	     cb.setOAuthAccessToken("1048626140-nKg8mqOkgzXywsxlX4Gkt4V7OMRS15SI785yXsA");
	     cb.setOAuthAccessTokenSecret("GhCC0tde6cZjhtJfPidA2PvgisciHbgmhcDKyALDxnHs8");

	     cb.setJSONStoreEnabled(true);

	     System.out.println("true 1 is set");
	  }
}
