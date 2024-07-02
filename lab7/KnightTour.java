

import java.util.*;

public class KnightTour {

	// This is how a knight jumps...
	final public static int[] DX = {-2,-2,-1,-1,1,1,2,2};
	final public static int[] DY = {-1,1,-2,2,-2,2,-1,1};
	
	public static int n;
	public static char[][] board;
	
	public static void main(String[] args) {
	
		// Read in the board.
		Scanner stdin = new Scanner(System.in);
		n = stdin.nextInt();
		board = new char[n][];
		for (int i=0; i<n; i++)
			board[i] = stdin.next().toCharArray();
			
		// Find K and run a BFS from it.
		int s = find('K');
		System.out.println(bfs(s, 0));
	}
	
	// Runs a BFS from location s to location e.
	public static int bfs(int s, int e) {
	
		// Set up BFS.
		LinkedList<Integer> q = new LinkedList<Integer>();
		q.offer(s);
		
		// Store -1 for haven't visited...otherwise a distance array.
		int[] dist = new int[n*n];
		Arrays.fill(dist, -1);
		dist[s] = 0;
		
		// Run till the queue is done.
		while (q.size() > 0) {
		
			// Get the next item. If it's our destination, return.
			int cur = q.poll();
			if (cur == e) return dist[e];
			
			// Try jumping all 8 ways.
			for (int i=0; i<DX.length; i++) {
				
				// Here is where we would be.
				int nX = cur/n + DX[i];
				int nY = cur%n + DY[i];
				
				// If we're off the board or it's a banned square or we've been their before, skip it.
				if (!inbounds(nX, nY)) continue;
				if (board[nX][nY] == '#') continue;
				if (dist[nX*n+nY] != -1) continue;
				
				// Mark the distance and add to the queue!
				dist[nX*n+nY] = dist[cur] + 1;
				q.offer(nX*n+nY);
			}
		}
		
		// Never made it!
		return -1;
	}
	
	// Returns true iff (x, y) is inbounds.
	public static boolean inbounds(int x, int y) {
		return x >= 0 && x < n && y >= 0 && y<n;
	}
	
	// Returns the first location where character c occurs, or -1 if it doesn't.
	public static int find(char c) {
		for (int i=0; i<n; i++)
			for (int j=0; j<n; j++)
				if (board[i][j] == c)
					return i*n + j;
		return -1;
	}
}