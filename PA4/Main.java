/* COP 3503C Assignment 4
This program is written by: Kevin Dasrath */

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

        //print whether we've found a solution or not
        if (sol != -1)
		    System.out.println(sol);

        else System.out.println("Stuck, we need help!");

    }

    public static int bfs(int s, int e) {
	
        //HashSet to keep track of whether or not we've queued the teleport locations already, instead of 
        //manually checking the queue each time that we encounter a teleport spot to see if the enqueue process 
        //has been done already
        HashSet<Character> letters = new HashSet<>();
        
		LinkedList<Integer> q = new LinkedList<Integer>();
       
		q.offer(s);

        //used for distance from spawn & whether or not an index has been visited
        int[] dist = new int[m*n];
		Arrays.fill(dist, -1);
		dist[s] = 0;


        //while we dont have an empty queue
        while (q.size() > 0) 
        {
		
            
            //pull from the queue and return if it is our solution
            int cur = q.poll();
			if (cur == e) return dist[e];
            

			
            //checks all four spots possible to jump to
            for (int i=0; i<4; i++) 
            {

				// math for finding each of the corresponding positions
				int nX = cur/n + DX[i];
				int nY = cur%n + DY[i];


				// check whether we are in bounds / on a valid spot
				if (!inbounds(nX, nY)) continue;
				if (maze[nX][nY] == '!') continue;
				if (dist[nX*n+nY] != -1) continue;

                
				//Mark distance from start and add to the queue
                dist[nX*n+nY] = dist[cur] + 1;
				q.offer(nX*n+nY);
                
                Character c = maze[nX][nY];
                //check if we are on a TP spot
                if (c != '.' && c != '*' && c != '$') 
                {

                    //if the lettter is not already in our HashSet (aka if the letter has not been encountered yet)
                    if (letters.contains(c) == false) 
                    {

                        letters.add(c);

                        //the amount of times that letter shows up
                        int occ = instances(c);

                        //the locations of each letter in the array
                        int[] locations = locations(c, occ);

                        //adds all the possible teleport locations to the back of the queue (except for the one we first encountered)
                        for (int loc : locations) 
                        {
                            //will only add to the queue if it hasn't been enqueded already
                            if (dist[loc] == -1) 
                            {
                                //adds a value of two instead of 1 since it will take an extra step to reach 
                                //said teleport location (the extra step being the actual telportation)
                                dist[loc] = dist[cur] + 2;
                                q.offer(loc);
                            }
                        }

                    }
                    

                }
                
			}
		}
		
		//if we never find the end location, return -1
		return -1;

    }

    //check if the location is within bounds    
    public static boolean inbounds(int x, int y) 
    {
        return x >= 0 && x < m && y >= 0 && y<n;
    }
        

    // Returns the first location where character c occurs, or -1 if it doesn't.
      public static int find(char c) {
		for (int i=0; i<m; i++)
			for (int j=0; j<n; j++)
				if (maze[i][j] == c){
                    return i*n + j;
                }       
		return -1;
    }
   
    //returns the number of teleport locations for a given letter
    public static int instances(Character c){
        int res = 0;
        for (int i=0; i<m; i++)
			for (int j=0; j<n; j++)
				if (maze[i][j] == c)
					res++;
        return res;
    }

    //returns an array of all of the locations of the teleport spots
    public static int[] locations(char c, int occ) {
        int[] locations = new int[occ];
        int k = 0;

        for (int i = 0; i < m; ++i) 
            for (int j = 0; j < n; ++j) 
               if (maze[i][j] == c){
                    locations[k] = i * n + j; 
                    k++;
                }
        return locations;
    }
}
