package problems;

import java.util.Iterator;
import java.util.Scanner;

public class question1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a1[] = {3, 5, 7, 8, 10, 14, 17, 18, 21};
		int[][] a2 = {{0, 0, 0}, 
				      {0, 0, 0}, 
				      {0, 0, 0}};
		int length = (int) Math.sqrt(a1.length);
		int[] a3 = new int[a1.length];
		
		int x = 0;
		int y = length - 1; 
		int a1index = 0;
		
		System.out.println("length: " + length);
		while (true) {
			length--;
			if (length == 0) {
				break;
			}
			for (int i = 0; i < length; i++) {
				System.out.println("x: " + x + " y: " + y);

				a2[x][y] = a1[a1index];
				x++;
				a1index++;
			}
			
			for (int i = 0; i < length; i++) {
				System.out.println("x: " + x + " y: " + y);
				a2[x][y] = a1[a1index];
				y--;
				a1index++;
			}
			
			for (int i = 0; i < length; i++) {
				System.out.println("x: " + x + " y: " + y);
				a2[x][y] = a1[a1index];
				x--;
				a1index++;
			}
			
			for (int i = 0; i < length; i++) {
				System.out.println("x: " + x + " y: " + y);
				a2[x][y] = a1[a1index];
				y++;
				a1index++;
			}
			

		}
		
		
		for(int i = 0; i<a2.length; i++)
		{
		    for(int j = 0; j< a2.length; j++)
		    {
		        System.out.print(a2[i][j] + " ");
		    }
		    System.out.println();
		}
		
	}

}
