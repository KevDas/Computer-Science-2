

import java.util.*;
import java.io.*;


class Main {
    static Scanner inp = new Scanner(System.in);
    private int[][] square;
    private boolean[] possible;
    private int totalSqs;
    private int magicConst;
    private int numsquares;


    public Main(int N)
    {
        if (N <= 2)
        {
            System.out.print("Only give values > 2 !!!");
            System.exit(1);  
        }

        square = new int[N][N]; // initialize with zeros by default
        magicConst = this.findMagicConstant(N);
        totalSqs = N * N;

        //initialize the possible array with True
        possible = new boolean[totalSqs];
        for (int i = 0; i < totalSqs; ++i)
            possible[i] = true;

        numsquares = 0;
    }

    public int findMagicConstant(int N)
    {
        int last_num = N*N;
        int sum = (N*(last_num + 1))/2;
        return sum;
    }

    public void fill(int row, int col)
    {
        // if the given square is a valid or not 
        if (validSquare())
        {
            System.out.println("Done!");
            for (int[] rws : square)
            {
                System.out.println(Arrays.toString(rws));
            }
        }

        //base case
        if (row == square.length)
        {
            //System.out.println(this);
            //numsquares++;
            return;
        }

        for(int i = 0; i < totalSqs; ++i)
        {
            if (possible[i])
            {
                square[row][col] = i + 1;
                possible[i] = false;

                int nextcol = col + 1;
                int nextrow = row;
                if (nextcol == square.length)
                {
                    nextrow += 1;
                    nextcol = 0;
                }

                //recursively explore other possibilities
                fill(nextrow, nextcol);

                //backtracking starts here
                square[row][col] = 0;
                possible[i] = true;
            }
        }
    }

    public boolean validSquare()
    {
        // row sum check

        for(int i = 0; i < square.length; ++i)
        {
            int sum = 0;

            for(int j = 0; j < square.length; ++j)
            {
                sum += square[i][j];

                if (square[i][j] == 0) return false;
            }

            if (sum != magicConst) return false;
        }

        // column sum check

        for (int i = 0; i < square.length; ++i)
        {
            int sum = 0;

            for (int j = 0; j < square.length; ++j)
            {
                sum += square[j][i];

                if (square[i][j] == 0) return false;
            }

            if(sum != magicConst) return false;
        }

        //diagsum1 check

        int diagsum1 = 0;

        for (int i = 0; i < square.length; ++i)
        {
            diagsum1 += square[i][i];
        }

        if (diagsum1 != magicConst) return false;


        //diagsum2 check

        int diagsum2 = 0;

        for (int i = 0; i < square.length; ++i)
        {
            diagsum2 += square[i][square.length - 1 - i];
        }

        if (diagsum2 != magicConst) return false;
        
        return true;

    }

    public static void main(String[] args)
    {
        //read in N
        int N = inp.nextInt();

        System.out.println("N = " + N);
        Main ms = new Main(N);
        ms.fill(0, 0);
    }
}
