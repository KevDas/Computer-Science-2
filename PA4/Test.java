/* COP 3503C Assignment 4
This program is written by: Kevin Dasrath */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;

public class Test {

    public static int m;
    public static int n;

    // Moveset for a given index (down, up, left right)
    final public static int[] DX = {1, -1, 0, 0};
    final public static int[] DY = {0, 0, -1, 1};

    public static char[][] maze;

    public static void main(String[] args) {

        Scanner stdin = new Scanner(System.in);

        // Keep track of the maze 
        m = stdin.nextInt();
        n = stdin.nextInt();
        maze = new char[m][n];

        // Define characters in maze
        for (int i = 0; i < m; i++) {
            maze[i] = stdin.next().toCharArray();
        }

        // Find our starting and ending locations    
        int s = find('*');
        int e = find('$');
        int sol = bfs(s, e);
        if (sol != -1)
            System.out.println(sol);
        else
            System.out.println("Stuck, we need help!");
    }

    public static int bfs(int s, int e) {

        HashSet<Character> visitedLetters = new HashSet<>();
        Queue<Integer> q = new LinkedList<>();
        q.offer(s);

        // Used for distance from spawn & whether or not an index has been visited
        int[] dist = new int[m * n];
        Arrays.fill(dist, -1);
        dist[s] = 0;

        // While we don't have an empty queue
        while (!q.isEmpty()) {

            // Pull from the queue and return if it is our solution
            int cur = q.poll();
            if (cur == e) return dist[e];

            // Check all four possible moves
            for (int i = 0; i < 4; i++) {

                // Math for finding each of the corresponding positions
                int nX = cur / n + DX[i];
                int nY = cur % n + DY[i];

                // Check whether we are in bounds / on a valid spot
                if (!inbounds(nX, nY)) continue;
                if (maze[nX][nY] == '!') continue;
                if (dist[nX * n + nY] != -1) continue;

                char c = maze[nX][nY];

                // Check whether we are on a teleport spot or not
                if (c != '.' && c != '*') {
                    if (!visitedLetters.contains(c)) {
                        visitedLetters.add(c);

                        // The locations of each letter in the array
                        for (int loc : getLocations(c)) {
                            if (dist[loc] == -1) {
                                dist[loc] = dist[cur] + 1;
                                q.offer(loc);
                            }
                        }
                    }
                } else {
                    // Mark the distance and add to the queue if it's a valid spot
                    dist[nX * n + nY] = dist[cur] + 1;
                    q.offer(nX * n + nY);
                }
            }
        }

        // Never made it!
        return -1;
    }

    public static boolean inbounds(int x, int y) {
        return x >= 0 && x < m && y >= 0 && y < n;
    }

    // Returns the first location where character c occurs, or -1 if it doesn't.
    public static int find(char c) {
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (maze[i][j] == c)
                    return i * n + j;
        return -1;
    }

    public static int[] getLocations(char c) {
        LinkedList<Integer> locations = new LinkedList<>();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (maze[i][j] == c)
                    locations.add(i * n + j);

        return locations.stream().mapToInt(i -> i).toArray();
    }
}
