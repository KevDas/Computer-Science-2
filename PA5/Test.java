/* COP 3503C Assignment 5
This program is written by: Your Full Name */

import java.util.*;



public class Test{
// A class to store a graph edge


public static void main(String[] args)
{

    Scanner stdin = new Scanner(System.in);

    
     int C = stdin.nextInt();
     int R = stdin.nextInt();
     int S = stdin.nextInt();
    
    List<Edge> edges = new ArrayList<>();

    for (int i = 0; i < R; ++i){
        int a = stdin.nextInt();
        int b = stdin.nextInt();
        int c = stdin.nextInt();
        edges.add(new Edge(a - 1, b - 1, c));
        if (a != S)
        edges.add(new Edge(b - 1, a - 1, c));
    }
   
    int L = stdin.nextInt();

    Graph graph = new Graph(edges, C);

   
    findShortestPaths(graph, S - 1, C, L);
    
}

public static void findShortestPaths(Graph graph, int source, int n, int L){

    int city = 0;
    int road = 0;
    
    if (L == 0) city++;
    else {
    boolean treasure[] = new boolean[n];
    treasure[source] = true;
    boolean roadT[][] = new boolean[n][n];
    // create a min-heap and push source node having distance 0
    PriorityQueue<Node> minHeap;
    minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.weight));
    minHeap.add(new Node(source, 0));

    // set initial distance from the source to `v` as infinity
    List<Integer> dist;
    dist = new ArrayList<>(Collections.nCopies(n, Integer.MAX_VALUE));

    // distance from the source to itself is zero
    dist.set(source, 0);

    // boolean array to track vertices for which minimum
    // cost is already found
    boolean[] done = new boolean[n];
    done[source] = true;

    // stores predecessor of a vertex (to a print path)
    int[] prev = new int[n];
    prev[source] = -1;

    // run till min-heap is empty
    while (!minHeap.isEmpty()){

        // Remove and return the best vertex
        Node node = minHeap.poll();
        
        // get the vertex number

        //u is the current value in the queue were examining 
        int u = node.vertex;

        // do for each neighbor `v` of `u`
        for (Edge edge: graph.adjList.get(u)){
        
            int v = edge.dest;

            int weight = edge.weight;

            if ((dist.get(u) + weight == L) && (!treasure[v])){
                city++;
                System.out.println("treasure found at city: " + v);
                treasure[v] = true; 
            }
                
            else if ((dist.get(u) < L) && (dist.get(u) + weight > L) && (!roadT[u][v])){
                road++;
                if (weight/2 + dist.get(v) == L){
                    roadT[v][u] = true;
                }
                
                System.out.println("Treasure found on road: "+ u + ", " + v);
            }
                
            // Relaxation step
            if (!done[v] && 
            
            
            //stopping point 7/7/24
            //if the distance from u to v + the weight of the edge is less than the distance to v currently, that is the new shortest path
            /*ex) if 1-> 5 is 50 , but 1 - > 4 -> 5 is 30 + 5 (with 30 being the cost to get to 4 from 1,
             then that is the new shortest path to 5 since it is a lesser cost) */
            (dist.get(u) + weight) < dist.get(v)){



                // set the distance to get to vertex v as the shorter distance we just discovered
                dist.set(v, dist.get(u) + weight);
                //updates the last node before v to u (for printing path)
                prev[v] = u;
                //adds v with the new cheaper path to the min heap
                minHeap.add(new Node(v, dist.get(v)));

            }
        }
        // mark vertex `u` as done so it will not get picked up again

        //once a node has been visited for the first time, then it will get marked as done? 
        done[u] = true;

    }

    }
    
    System.out.println("In city: " + city);
    System.out.println("On the road: " + road);
}


}

class Edge
{
    int source, dest, weight;

    public Edge(int source, int dest, int weight)
    {
        this.source = source;
        this.dest = dest;
        this.weight = weight;
    }
}

// A class to store a heap node
class Node
{
    int vertex, weight;

    public Node(int vertex, int weight)
    {
        this.vertex = vertex;
        this.weight = weight;
    }
}

// A class to represent a graph object
class Graph
{
// A list of lists to represent an adjacency list
    List<List<Edge>> adjList = null;
    // Constructor
    Graph(List<Edge> edges, int n)
    {
        adjList = new ArrayList<>();

        for (int i = 0; i < n; i++) 
        {
            adjList.add(new ArrayList<>());
                      

        }   
    
        // add edges to the directed graph
        for (Edge edge: edges) 
        {
            adjList.get(edge.source).add(edge);
             
            
        }
    }
}