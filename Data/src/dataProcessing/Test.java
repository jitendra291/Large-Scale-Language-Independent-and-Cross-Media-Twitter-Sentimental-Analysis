package dataProcessing;

import java.util.HashMap;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello");
		HashMap<String, Integer> hm = new HashMap<>();
		Scanner in = new Scanner(System.in);
		for(int i =0;i<5;i++)
		{
			String str = in.next();
			Integer t = hm.get(new String(str));
			if(t==null)
				{
				t = 1;
				}
			else
				{
				t++;
				}
			hm.put(str, t);
		}
		for(int i =0;i<5;i++)
		{
			String str = in.next();
			Integer t = hm.get(new String(str));
		if(t==null){
			System.out.println("no such value");
		}
		else
			System.out.println(t);
			
		}
	}

}
