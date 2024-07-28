import java.util.*;


public class Main{


    public static int Solve(int[] w, int[] v, int n, int W){

        int T[][] = new int[n + 1][W + 1];

        for (int i = 0; i <= n; ++i){

            for (int j = 0; j <= n; ++j){

                if (j == 0 || i == 0)
                    T[i][j] = 0;

                // if weight is greater than goal
                T[i][j] = T[i][j - 1];

                else 
                    T[i][j] = Math.max(T[i][j - 1], T[i][i - 1][j - 1] + w[i]);

                
            }
        }

    }



    public static void main(String[] args) {

        Scanner in = new Scanner(Sytem.in);

        int n = in.nextInt();
        int v[] = new int[n];
        int w[] = new int[n];
        int W = in.nextInt(); 
        for (int i = 0; i < n; ++i){
            v[i] = in.nextInt();
            w[i] = in.nextInt();

        }

        Solve(v, w, n, W);

    }
}