package problems;

import java.util.Arrays;
import java.util.Iterator;

public class question2 {
	public static int zeros(int[][] first, int[][] second) {

		int zero = 0;
		int[][] sums = new int[first.length][first[0].length];
		for (int i = 0; i < first.length; i++) {
			for (int j = 0; j < first[0].length; j++) {
				System.out.print(first[i][j] + second[i][j] + " ");
				sums[i][j] = first[i][j] + second[i][j];
			}
			System.out.println();
		}

		/*
		 * 
		 * int row_sum = 0;
		 * 
		 * int colunm_sum = 0;
		 * 
		 * for(int i=0; i<sums.length; i++) {
		 * 
		 * for(int j=0; j<sums[0].length; j++) {
		 * 
		 * row_sum +=
		 * 
		 * }
		 * 
		 * }
		 * 
		 */

		for (int i = 0; i < first.length; i++) {
			int sum_row = 0;
			for (int j = 0; j < first[0].length; j++) {
				sum_row += first[i][j] + second[i][j];
			}
			System.out.println(i + "row sum is" + sum_row);
			if (sum_row == 0) {
				zero++;
			} else {
				sum_row = 0;
			}
		}

		for (int i = 0; i < first[0].length; i++) {
			
			int sum_column = 0;
			for (int j = 0; j < first.length; j++) {
				sum_column += first[j][i] + second[j][i];
			}
			System.out.println(i + "column sum is " + sum_column);
			if (sum_column == 0) {
				zero++;
			} else {
				sum_column = 0;
			}
		}
		return zero;
	}

	public static int contagious (boolean[] a) {

		String[] contarr = Arrays.toString(a).substring(1, Arrays.toString(a).length() - 1).split("false, ");

		contarr[contarr.length - 1] = contarr[contarr.length - 1].replace("false", "");
		Arrays.sort(contarr);
		for (int i = 0; i < contarr.length; i++) {

			System.out.println(contarr[i]);
		}
		return (int) Math.ceil((double) (contarr[contarr.length - 1].length()/6)/2);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean[] a = {true, false, true, true, true, true, true, true, true, false, true, false, false, true};
		System.out.println(contagious(a));
	}

}
