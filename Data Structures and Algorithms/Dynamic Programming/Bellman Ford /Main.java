void Main(Main graph, int src) {


    int V = graph.V, E = graph.E;
    int dist[] = new int[V];

    // Step 1: Initialize distances from src to all other vertices as INFINITE
    for (int i = 0; i < V; ++i)
        dist[i] = Integer.MAX_VALUE;

    dist[src] = 0;

    // Step 2: Relax all edges |V| - 1 times. A simple shortest path from src to any other vertex can have at-most |V| - 1 edges
    for (int i = 1; i < V; ++i) 
    {
        for (int j = 0; j < E; ++j) 
        {
            int u = graph.edge[j].src;
            int v = graph.edge[j].dest;
            int weight = graph.edge[j].weight;

            if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v])
                dist[v] = dist[u] + weight;
        }
    }

    // Step 3: check for negative-weight cycles. The above step guarantees shortest distances if graph doesn’t contain negative weight cycle.
    //If we get a shorter path, then there is a cycle.
    for (int j = 0; j < E; ++j) 
    {
        int u = graph.edge[j].src;
        int v = graph.edge[j].dest;
        int weight = graph.edge[j].weight;

        if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) 
        {
            System.out.println("Graph contains negative weight cycle");
            return;
        }
    }
    `
    printArr(dist, V);
    }