package dataCollection;

import java.util.Timer;

public class Schedule {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Timer time = new Timer(); // Instantiate Timer Object
		Data dt = new Data();
		time.schedule(dt, 0, 600000);
	}

}
