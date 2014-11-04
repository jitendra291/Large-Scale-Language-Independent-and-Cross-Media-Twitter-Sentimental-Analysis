package stream;

import java.util.Timer;

public class Schedule {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Timer time = new Timer(); // Instantiate Timer Object
		SearchTweets st = new SearchTweets();
		time.schedule(st, 0, 60000);
	}

}
