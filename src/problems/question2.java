package problems;

import java.util.Arrays;
import java.util.Iterator;

public class question2 {
	public static int zeros(int[][] first, int[][] second) {
		//Take two 2d arrays of integers as parameters. 
		//Assume these two matrices have the same dimensions.
		//Sum the two matrices, then return how many rows and columns consist of only 0's. 
		
		int zero = 0;
		for(int i=0; i<first.length; i++) {
			int sum_row = 0;
			for(int j=0; j<first[0].length; j++) {
				sum_row += first[i][j]+second[i][j];
			}
			//System.out.println(i+"row sum is"+sum_row);
			if(sum_row == 0) {
				zero++;
			}else {
				sum_row = 0;
			}
		}
		for(int i=0; i<first[0].length; i++) {
			int sum_column = 0;
			for(int j=0; j<first.length; j++) {
				sum_column += first[j][i]+second[j][i];
			}
			//System.out.println(i+"column sum is "+sum_column);
			if(sum_column == 0) {
				zero++;
			}else {
				sum_column = 0;
			}
		}
		
		return zero;
	}

	public static int contagious (boolean[] a) {
		String[] contarr = Arrays.toString(a).substring(1, Arrays.toString(a).length() - 1).split("false, ");
		contarr[contarr.length - 1] = contarr[contarr.length - 1].replace("false", "");
		Arrays.sort(contarr);
		return (int) ((double) (contarr[contarr.length - 1].length()/6)/2);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] first = {{5, 3, -1}, {-2, 4, 0}};
		int[][] second = {{3, -3, 6}, {2, -4, 0}};
		int [][]arr= {{5, 3, -1},{-2,4,0},{2,-4,0}, {2,-4,0}, {2,-4,0}, {2,-4,0}, {2,-4,0}, {2,-4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1} };
		int [][]arr2= {{3, -3, 6},{2, -4, 0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {3, -3, 6}, {3, -3, 6}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}};
		
		boolean[] cont = {true, true, false, true, false, false, true, true ,true, true, false, true};
		
		System.out.println(zeros(arr, arr2));
		System.out.println(contagious(cont));
	}

}
