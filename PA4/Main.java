import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.*;




public class Main {



public static int m;
public static int n;

final public static int[] DX = {1, -1, 0, 0};
final public static int[] DY = {0, 0, -1, 1};

public static char[][] maze;

    public static void main(String[] args) {

    	Scanner stdin = new Scanner(System.in);
		m = stdin.nextInt();
        n = stdin.nextInt();
		maze = new char[m][n];
       
		for (int i=0; i<m; i++)
			maze[i] = stdin.next().toCharArray();
			
           

		// Find K and run a BFS from it.
		int s = find('*');
        int e = find('$');
        int sol = bfs(s, e);
        if (sol != -1)
		    System.out.println(sol);

        else System.out.println("Stuck, we need help!");

    }
    public static int bfs(int s, int e) {
	
		// Set up BFS.
		LinkedList<Integer> q = new LinkedList<Integer>();
		q.offer(s);
		
		// Store -1 for haven't visited...otherwise a distance array.
		int[] dist = new int[m*n];
		Arrays.fill(dist, -1);
		dist[s] = 0;

        while (q.size() > 0) {
		
			// Get the next item. If it's our destination, return.
			int cur = q.poll();
			if (cur == e) return dist[e];
			
			// Try jumping all 8 ways.
			for (int i=0; i<4; i++) {
				// Here is where we would be.
				int nX = cur/n + DX[i];
				int nY = cur%n + DY[i];

				// If we're off the board or it's a banned square or we've been their before, skip it.
				if (!inbounds(nX, nY)) continue;
				if (maze[nX][nY] == '!') continue;
				if (dist[nX*n+nY] != -1) continue;
				
				// Mark the distance and add to the queue!
				dist[nX*n+nY] = dist[cur] + 1;
				q.offer(nX*n+nY);
			}
		}
		
		// Never made it!
		return -1;

    }
        public static boolean inbounds(int x, int y) {
            return x >= 0 && x < m && y >= 0 && y<n;
        }
        
        // Returns the first location where character c occurs, or -1 if it doesn't.
      public static int find(char c) {
		for (int i=0; i<m; i++)
			for (int j=0; j<n; j++)
				if (maze[i][j] == c)
					return i*m + j;
		return -1;
    }
   

}
