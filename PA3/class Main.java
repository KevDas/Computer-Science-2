class Main {
    private int V; // No. of vertices
    private LinkedList<Integer> adj[]; // Adjacency Lists
    // Constructor

    @SuppressWarnings("unchecked")
    Main(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList<Integer>();
    }

    // Function to add an edge into the graph
    void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    // prints BFS traversal from a given source s
    void BFS(int s) {
        // Mark all the vertices as not visited(By default
        // set as false)
        boolean visited[] = new boolean[V];
        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();
        // Mark the current node as visited and enqueue it
        visited[s] = true;
        queue.add(s);
        while (queue.size() != 0) {
            // Dequeue a vertex from queue and print it
            s = queue.poll();
            System.out.print(s + " ");
            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<Integer> i = adj[s].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
    }

    // prints BFS traversal from a given source s
    void BFS(int s) {
        // Mark all the vertices as not visited(By default
        // set as false)
        boolean visited[] = new boolean[V];
        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();
        // Mark the current node as visited and enqueue it
        visited[s] = true;
        queue.add(s);
        while (queue.size() != 0) {
            // Dequeue a vertex from queue and print it
            s = queue.poll();
            System.out.print(s + " ");
            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<Integer> i = adj[s].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
    }

    // Main function
 public static void main(String args[])
 {
 Main g = new Main(7);

 g.addEdge(0, 1);
 g.addEdge(1, 2);
 g.addEdge(1, 3);
 g.addEdge(2, 4);
 g.addEdge(2, 5);
 g.addEdge(4, 5);
 g.addEdge(4, 6);
 g.addEdge(5, 6);
 System.out.println("BFS starting from vertex 0)");
 g.BFS(0);
 }