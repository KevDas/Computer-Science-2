/*Example implementation of the Dijkstra's Alg. */

import java.util.*;

class Dijkstra{
// A class to store a graph edge


public static void main(String[] args)
{

    // initialize edges as per the above diagram
    // (u, v, w) represent edge from vertex `u` to vertex `v` having weight `w`
    List<Edge> edges = Arrays.asList(
    new Edge(0, 1, 50), new Edge(0, 3, 10), new Edge(0, 2, 45),
    new Edge(1, 2, 10),new Edge(1, 3, 15), 
    new Edge(2, 4, 30), 
    new Edge(3, 4, 15), new Edge(3, 0, 10),  
    new Edge(4, 1, 20), new Edge(4, 2, 35), 
    new Edge(5, 4, 3)
    );

    // total number of nodes in the graph (labelled from 0 to 5)
    int n = 6;

    // construct graph
    Graph graph = new Graph(edges, n);

    // run the Dijkstra’s algorithm from every node just to get shortest paths from source to all the vertices
    //In general, if you have a specific source, call this function by findShortestPaths(graph, source, n);
    for (int source = 0; source < n; source++) 
    {
        findShortestPaths(graph, source, n);
    }
}

//Run Dijkstra’s algorithm on a given graph
public static void findShortestPaths(Graph graph, int source, int n){

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
        int u = node.vertex;

        // do for each neighbor `v` of `u`
        for (Edge edge: graph.adjList.get(u)){
        
            int v = edge.dest;
            int weight = edge.weight;

            // Relaxation step
            if (!done[v] && (dist.get(u) + weight) < dist.get(v)){
                dist.set(v, dist.get(u) + weight);
                prev[v] = u;
                minHeap.add(new Node(v, dist.get(v)));
            }
        }
        // mark vertex `u` as done so it will not get picked up again
        done[u] = true;
    }

    //printing the path
    List<Integer> route = new ArrayList<>();
    for (int i = 0; i < n; i++){
        if (i != source && dist.get(i) != Integer.MAX_VALUE){

            getRoute(prev, i, route);

            System.out.printf("Path (%d —> %d): Minimum cost = %d, Route = %s\n",
            source, i, dist.get(i), route);
            route.clear();
        }
    }
}

private static void getRoute(int[] prev, int i, List<Integer> route){

    if (i >= 0){
            
    getRoute(prev, prev[i], route);
    route.add(i);
    }
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