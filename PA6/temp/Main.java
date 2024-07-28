/* COP 3503C Assignment 6
This program is written by: Kevin Dasrath */

import java.util.*;

class Main {
    
    public static int maxVal(int[][] values, int n){
        //create a table that will hold the max sum generated up to each point
        int[][] T = new int[2][n];
        
        //assign the first values of each row to the table
        T[0][0] = values[0][0];
        T[1][0] = values[1][0];
        

        //run for the table 
        for (int i = 1; i < n; ++i) {
            //for each value in the first row, either choose to not add a value because it is adjacent,
            // or to add the diagonal item AND the current entry (which ever is the max)
            T[0][i] = Math.max(T[0][i-1], T[1][i-1] + values[0][i]);
             //for each value in the second row, either choose to not add a value because it is adjacent,
            // or to add the diagonal item AND the current entry (which ever is the max)
            T[1][i] = Math.max(T[1][i-1], T[0][i-1] + values[1][i]);
        }
        //return the max of the two end values generated
        return  Math.max(T[0][n-1], T[1][n-1]);
        
    }

    public static void main(String[] args) 
    {
        Scanner stdin = new Scanner(System.in);
        
        int n = stdin.nextInt();
        int[][] data = new int[2][n];
        
        //taking in the values to the table
        for (int i = 0; i < 2; ++i) 
            for (int j = 0; j < n; ++j) 
                data[i][j] = stdin.nextInt();
            
        
        
        
        System.out.println(maxVal(data, n));
    }
}
