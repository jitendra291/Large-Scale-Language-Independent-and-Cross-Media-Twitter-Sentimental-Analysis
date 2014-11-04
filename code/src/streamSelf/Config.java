package streamSelf;

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
	   public static void setUserAccessTokens1(ConfigurationBuilder cb)
	   {
	     cb.setOAuthConsumerKey("BB9YRzQ42gpUmUPC8iVT6y8Hb");
	     cb.setOAuthConsumerSecret("O56dgWaU8eKpyOycWUoVNeONr9SWN9sm7x5Od1SES6LbqNDiKo");
	     cb.setOAuthAccessToken("375078108-WTQvLrtn8JHc2as54lt9D3ayktWzD9SfKk8jAYHT");
	     cb.setOAuthAccessTokenSecret("54FZUk2xtKuymij4UUds5VqaIWwG8pU0li2Fjauv89hJ4");

	     cb.setJSONStoreEnabled(true);

	     System.out.println("true 2 is set");
	  }
}
