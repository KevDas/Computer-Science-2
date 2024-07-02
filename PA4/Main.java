import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.*;

public class Main {



public static int m;
public static int n;

//moveset for a given index (down, up, left right)
final public static int[] DX = {1, -1, 0, 0};
final public static int[] DY = {0, 0, -1, 1};

public static char[][] maze;

    public static void main(String[] args) {


    	Scanner stdin = new Scanner(System.in);

        //keep track of the maze 
		m = stdin.nextInt();
        n = stdin.nextInt();
		maze = new char[m][n];
       
        //define characters in maze
		for (int i=0; i<m; i++)
			maze[i] = stdin.next().toCharArray();
			
           

        //find our starting and ending locations    
		int s = find('*');
        int e = find('$');
        int sol = bfs(s, e);
        if (sol != -1)
		    System.out.println(sol);

        else System.out.println("Stuck, we need help!");

    }

    public static int bfs(int s, int e) {
	
        HashSet<Character> letters = new HashSet<>();
		LinkedList<Integer> q = new LinkedList<Integer>();
		q.offer(s);
		
        //used for distance from spawn & whether or not an index has been visited
        int[] dist = new int[m*n];
		Arrays.fill(dist, -1);
		dist[s] = 0;

        //while we dont have an empty queue
        while (q.size() > 0) {
		
            //pull from the queue and return if it is our solution
            int cur = q.poll();
			if (cur == e) return dist[e];
			
            //checks all four spots possible to jump to
            for (int i=0; i<4; i++) {

				// math for finding each of the corresponding positions
				int nX = cur/n + DX[i];
				int nY = cur%n + DY[i];

				// check whether we are in bounds / on a valid spot
				if (!inbounds(nX, nY)) continue;
				if (maze[nX][nY] == '!') continue;
				if (dist[nX*n+nY] != -1) continue;


                //check whether we are on a teleport spot or not
				if (maze[nX][nY] != '.' || maze[nX][nY] != '!' ) {
                    
                    if (letters.contains(maze[nX][nY]) == false) {
                        //the amount of times that letter shows up
                    int occ = instances(maze[nX][nY]);

                    //the locations of each letter in the array
                    int[] locations = locations(maze[nX][nY], occ);

                    int z = 0;
                    while (z < occ) q.offer(locations[z]);
                    
                    }
                    

                }

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
   
    public static int instances(char c){
        int res = 0;
        for (int i=0; i<m; i++)
			for (int j=0; j<n; j++)
				if (maze[i][j] == c)
					res++;

        return res;
    }

    public static int[] locations(char c, int occ){
        int[] locations = new int[occ];
        int k = 0;

        for (int i = 0; i < m; ++i) 
            for (int j = 0; j < n; ++j) 
               if (maze[i][j] == c){
                    locations[k] = i*m + j;
                    k++;
                }
            
                
        

        return locations;
    }

}
