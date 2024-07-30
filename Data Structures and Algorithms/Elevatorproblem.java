//Solving kattis elevetor trouble problem
//sample input 10 1 10 2 1  //ouptut 6
//100 2 1 1 0 // output "use the stairs"
//Originally taken from Arup's website and breifly changed and added comments
//https://open.kattis.com/problems/elevatortrouble

import java.util.*;

public class Elevatorproblem {

	public static void main(String[] args) {
	
		// Get input.
		Scanner stdin = new Scanner(System.in);
		int n = stdin.nextInt();
		int start = stdin.nextInt()-1; //-1 to adjust with array index
		int end = stdin.nextInt()-1; //-1 to adjust wth array index
		int up = stdin.nextInt();
		int down = stdin.nextInt();
		
		// Set up distance array. It can also fulfil the visited status array
		int[] dist = new int[n];
		Arrays.fill(dist, -1);  //fill the array witn -1
		dist[start] = 0; //start visiting from start index and mark distance as 0 (this also indicates visited status changed like BFS)
		
		// Put start in queue.
		LinkedList<Integer> q = new LinkedList<Integer>();
		q.add(start);   //add the start index to the queue
		
		// Run BFS.
		while (q.size() > 0) {
		
			// Get next item. (dequeue)
			int cur = q.poll();
		
			// Done!
			if (cur == end) break;

      //in bfs we explore cur and go to and enqueue all unvisited connection of cur.
      //in this case, we have only two connections (cur+up and cur-down), So, instead of writing a loop, we are explicitely writing code for two directions.
			
			// See if we can go up and if it leads us to a new place.
			if (cur+up < n && dist[cur+up] == -1) {
				dist[cur+up] = dist[cur] + 1;
				q.add(cur+up);
			}
			
			// Same for down.
			if (cur-down >= 0 && dist[cur-down] == -1) {
				dist[cur-down] = dist[cur] + 1;
				q.add(cur-down);
			}
		}
		
		// Ta da!
		if (dist[end] == -1)
			System.out.println("use the stairs");
		else
			System.out.println(dist[end]);
	}
}