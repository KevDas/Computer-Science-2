import java.util.*;



class Test
{

    public static int findSum(int[] vals, int[] pos, int N){

        int[][] T = new int[2][N];

        int allowed = 0;
        int j = N; 

        for (int i = 0; i < 2; ++i){

            for (int j = 0; j < N; ++j){

                
            }

            
        }


    }




    public static void main(String[] args) {
        
        Scanner in = new Scanner(System.in);

        int N = in.nextInt();
        int[] values = new int[N * 2];
        int[] positions = new int[N * 2];

        for (int i = 0; i < N * 2; ++i){
            values[i] = in.nextInt();
            positions[i] = values[i]%2;
        }

        System.out.println(findSum(values, positions, N));


    }
}