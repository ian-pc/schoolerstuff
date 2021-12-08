package problems;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;




public class josephus {
	
	public static void run(int j) {
		System.out.println(j);
		
		int size = j;
		
		ArrayList<Integer> al = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			al.add(i);
		}
		
		while(al.size() > 2) {
		    int ogSize = al.size();
		    int index = 1;
		    Iterator<Integer> iterator = al.iterator();
		    while(iterator.hasNext()) {
		        iterator.next();
		        if(index++ % 2 == 0) {
		            iterator.remove();
		        }
		    }
		    if (ogSize % 2 == 1) {
		    	al.remove(0);
		    }
			System.out.println(al);
		} 
		if (al.size() == 2) {
			al.remove(1);
		}

		System.out.println(al);
	    System.out.println("he" +  (al.get(0)+1));
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		run(sc.nextInt());
		
		
		
	}

}
