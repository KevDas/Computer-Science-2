import java.util.*;

class Main
{
  // Input: // Values (stored in array `v`)
  // Weights (stored in array `w`) // Total number of distinct items `n`
  // Knapsack capacity `W`
  public static int knapsack(int[] vals, int n)
  {
    // `T[i][j]` stores the maximum value of knapsack having weight
    // less than equal to `j` with only first `i` items considered.
    int[][] T = new int[n + 1][n + 1];
    
    // for all n * 2 participants, 
    for (int i = 0; i <= n; i++)
    {
      int count = 0;
      int y = n;

      for (int j = 0; j <= n; j++)
      {
         
            
         if ( j == 0 || i == 0)
            T[i][j] = 0;
            
         if (count > j)
            T[i][j] = T[i][j-1];

         else {
            
            int choice = Math.max(vals[i - 1], vals[y + j]);
            
           System.out.println("Comparisson: " + vals[i - 1] + ", " + vals[y + j] + ", choice is: " + choice);

            if (choice == vals[count + 1]){
              if (T[i][j - 2] + choice > T[i][j - 1] + vals[count + n]){
                T[i][j] = T[i][j - 2] + choice;
                count += 1;
              }
            
            else T[i][j] = T[i][j-1] + choice;
            count++;
            }

          
          }
          

      }
    }
    // return maximum value
    System.out.println(T[n][n]);
    return T[n][n];
  }
  public static void main(String[] args)
  {
    Scanner stdin = new Scanner(System.in);
    int n = stdin.nextInt();

    int [] vals = new int[2* n];

    for (int i = 0; i < 2 * n; ++i){
    
            vals[i] = stdin.nextInt();
        
    }

   
    // knapsack capacity
    System.out.println("Knapsack value is " + knapsack(vals, n));
  }

  
      
    
  }
