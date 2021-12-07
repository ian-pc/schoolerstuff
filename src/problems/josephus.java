package problems;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class josephus {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		int size = sc.nextInt();
		
		
		
		ArrayList<Integer> al = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			al.add(i);
		}
		
		while(al.size() != 1) {
		    int index = 1;
		    Iterator<Integer> iterator = al.iterator();
		    while(true) {
		    	if (iterator.hasNext()) {
			        iterator.next();
			        if(index++ % 2 == 0) {
			            iterator.remove();
			        }
		    	} else {
		    		if (al.size()==2) {
		    			al.remove(1);
		    		} else {
			    		al.remove(0);
		    		}
		    		break;
		    	}
		        
		    }
		} 

	    System.out.println(al);
		
	}

}
