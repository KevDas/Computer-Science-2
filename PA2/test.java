/* COP 3503C Assignment 2
This program is written by: Kevin Dasrath */

import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {

    static Scanner scan = new Scanner(System.in);
    static int M, N, S;
    static char[][] array;
    static String[] words;
    static char[][] charWords;

    class Matrix {

        Character[][] array;

        int M;
        int N;
        boolean wordFound;

    }

Character[][] isSpot(char[][] matrix,int x, int y char[] word, int idx){

   

    if (matrix[x][y] == word[idx])
        return true;
    return false;
  
    
}

    Matrix getSolution(char[][] matrix, char[] word) {

        Character[][] Solution = new Character[M][N];
        Arrays.fill(Solution, '_');
        int idx = 0;
        int i = 0, j = 0;
        while (found == false) {
            while (i < M) {

                while (j < N) {
                    Solution.matrix = isSpot(matrix, idx, j);
                    if (Solution.wordFound = true)
                        break;
                }

            }

        }

        return Solution;
    }

    void fillArray() {
        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < N; ++j)
                array[i][j] = scan.next().charAt(0);
        }
    }

    void getWords() {

        for (int i = 0; i < S; ++i) {
            words[i] = scan.next();
            charWords[i] = words[i].toCharArray();
        }
    }

    public static void main(String[] args) {
        
        Main matrix = new Main();

        M = scan.nextInt();
        N = scan.nextInt();
        S = scan.nextInt();


        
        matrix.fillArray(0);
        matrix.getWords();

        for (int i = 0; i < S; ++i)
            matrix.getSolution(charWords[S]);
       
    
       
        

        }
}

*/