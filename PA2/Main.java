
/*COP 3503C Assignment 2
This program is written by: Kevin Dasrath 
*/
import java.util.Scanner;

public class Main {

    // Matrix dimensions & amount of words
    static int M;
    static int N;
    static int S;
    static Scanner in = new Scanner(System.in);

    // prints out solution if one is found
    void printSolution(char sol[][]) {
        System.out.println();
        for (int i = 0; i < M; i++) {
            System.out.print("[");
            for (int j = 0; j < N; j++) {
                System.out.print(sol[i][j]);
                if (j < N - 1)
                    System.out.print(", ");
            }

            System.out.print("]\n");

        }
    }

    // checks if current index is valid / holds the value we are looking for
    boolean isValid(char matrix[][], int x, int y, int i, char words[]) {
        if (x >= 0 && x < M && y >= 0 && y < N) {
            if (matrix[x][y] == words[i])
                return true;
        }

        return false;
    }

    // main function that takes in the matrix & a word to check if present
    boolean findSol(char matrix[][], char[] words) {

        // empty matrix that solution will be held in
        char sol[][] = new char[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                sol[i][j] = ' ';
            }
        }
        System.out.print("\nLooking for ");
        for (char c : words) {
            System.out.print(c);
        }

        // loops through and checks every index in the matrix to see if it is that start
        // of the word
        // if the index is, and the word is found, then solution is printed, otherwise,
        // it is reported to the user that
        // the word is not present
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {

                if (findSolUtil(matrix, i, j, 0, sol, words) == true) {
                    printSolution(sol);
                    return true;
                }
            }
        }
        System.out.println();
        for (char c : words) {
            System.out.print(c);
        }
        System.out.print(" not found!" + "\n");
        return false;

    }

    // secondary function that checks the surrounding indecies
    boolean findSolUtil(char matrix[][], int x, int y, int i, char sol[][], char words[]) {

        // final letter case
        if (i == (words.length - 1) && matrix[x][y] == words[i]) {
            for (int k = 0; k < i; ++k) {
                if (sol[x][y] == words[k])
                    return false;
            }
            sol[x][y] = matrix[x][y];
            return true;
        }

        if (isValid(matrix, x, y, i, words) == true) {

            // checking to see if the index we are on is already present in our solution
            for (int k = 0; k < i; ++k) {
                if (sol[x][y] == words[k]) {
                    return false;
                }

            }

            sol[x][y] = matrix[x][y];

            // recursive check for each direction, starting with R, D, L, U and then the
            // diaganols
            if (isValid(matrix, x, y + 1, i + 1, words) && findSolUtil(matrix, x, y + 1, i + 1, sol, words))
                return true;

            if (isValid(matrix, x + 1, y, i + 1, words) && findSolUtil(matrix, x + 1, y, i + 1, sol, words))
                return true;

            if (isValid(matrix, x, y - 1, i + 1, words) && findSolUtil(matrix, x, y - 1, i + 1, sol, words))
                return true;

            if (isValid(matrix, x - 1, y, i + 1, words) && findSolUtil(matrix, x - 1, y, i + 1, sol, words))
                return true;

            if (isValid(matrix, x + 1, y + 1, i + 1, words) && findSolUtil(matrix, x + 1, y + 1, i + 1, sol, words))
                return true;

            if (isValid(matrix, x - 1, y + 1, i + 1, words) && findSolUtil(matrix, x - 1, y + 1, i + 1, sol, words))
                return true;

            if (isValid(matrix, x - 1, y - 1, i + 1, words) && findSolUtil(matrix, x - 1, y - 1, i + 1, sol, words))
                return true;
            if (isValid(matrix, x + 1, y - 1, i + 1, words) && findSolUtil(matrix, x + 1, y - 1, i + 1, sol, words))
                return true;

            // if all directions checked and the next letter is not found, then the index we
            // are on is wiped and we return
            sol[x][y] = ' ';
            return false;
        }
        return false;

    }

    public static void main(String args[]) {
        Main matrix = new Main();
        M = in.nextInt();
        N = in.nextInt();
        S = in.nextInt();

        char array[][] = new char[M][N];
        // 2D char array of words to allow each index to be accessed as a char
        char[][] words = new char[S][];

        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < N; ++j)
                array[i][j] = in.next().charAt(0);
        }

        for (int i = 0; i < S; ++i) {
            String temp = in.next();
            words[i] = temp.toCharArray();
        }

        for (int i = 0; i < S; i++)
            matrix.findSol(array, words[i]);
    }
}
