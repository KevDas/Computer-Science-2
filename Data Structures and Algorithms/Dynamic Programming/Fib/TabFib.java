import java.util.*;


public class TabFib{



    public static int TabFibFind(int n){

        if (n == 0) return 0;

        if (n == 1) return 1;

        int sol[] = new int[n + 1];

        sol[0] = 0;
        sol[1] = 1;

        for (int i = 2; i < n + 1; ++i){
            sol[i] = sol[i - 1] + sol[i - 2];
        }

        return sol[n];







    }


    public static void main(String[] args) {
        
        int n = 5;

        System.out.println(TabFibFind(n));
    }
}