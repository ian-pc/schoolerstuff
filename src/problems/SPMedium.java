package problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class SPMedium {
	
	private static int med(Integer in) {
		
		String temp = Integer.toString(in);

		Character[] num = new Character[temp.length()];

		for (int i = 0; i < temp.length(); i++){
		    num[i] = temp.charAt(i);
		}
		
        int i;
        
        for (i = num.length - 1; i > 0; i--)
        {
            if (num[i] < num[i - 1]) {
                break;
            }
        }
         
        if (i == 0)
        {
            return -1;
        }
        else
        {
            int x = num[i - 1], min = i;
             
           
            for (int j = i + 1; j < num.length; j++)
            {
                if (num[j] > x && num[j] < num[min])
                {
                    min = j;
                }
            }
 
            char temp2 = num[i - 1];
            num[i - 1] = num[min];
            num[min] = temp2;
 
            
            Character[] temp4 = Arrays.copyOfRange(num, i, num.length);
            
            Arrays.sort(temp4, Collections.reverseOrder());
            
            for (int j = i; j < num.length; j++) {
				num[j] = temp4[j - i];
			}
            
            
            //done
            String temp3 = "";
            for (i = 0; i < num.length; i++) {
            	temp3 += num[i];
            }
            return Integer.parseInt(temp3);
            
        }
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println(med(sc.nextInt()));
	}

}
