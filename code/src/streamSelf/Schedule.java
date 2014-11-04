package streamSelf;

import java.util.Timer;

public class Schedule {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Timer time = new Timer();
		TweetStream ts = new TweetStream();
		int k =0;
		time.schedule(ts, 0, 60000);
		for (int i = 0; i <= 5; i++) {
			System.out.println("Execution in Main Thread...." + i);
			Thread.sleep(120000);
			if (i == 5) {
				System.out.println("Application Terminates");
				System.exit(0);
			}
		}
	}

}
