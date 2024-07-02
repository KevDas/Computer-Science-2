import java.util.*;
import java.io.*;



public class Main {

    public static int n;//no of nodes in graph
    public static int e; // no of edges
    public static ArrayList[] graph;

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        // Read in data
        n = scanner.nextInt();
        e = scanner.nextInt();
        graph = new ArrayList[n];
        for (int i = 0; i < n; ++i){
            graph[i] = new ArrayList<Integer>();
        }

        //Read in edges
        for (int i = 0; i < e; ++i){
            int v1 = scanner.nextInt() - 1;
            int v2 = scanner.nextInt() - 1;
            graph[v1].add(v2);
            graph[v2].add(v1);
        }

        // Store items to be removed
        int[] remList = new int[n];

        for (int i = 0; i < n; ++i)
            remList[i] = scanner.nextInt() - 1;

            //producing answers backwards, so store them. 
            boolean[] res = new boolean[n];
            Disjointset dj = new Disjointset(n);
            res[n-1] = true;
            boolean[] inGraph = new boolean[n];
            inGraph[remList[n-1]] = true;

            //go backwards through the list of deletions
            for (int i = n-2; i >= 0; --i){

                int item = remList[i];
                for (int j = 0; j < graph[item].size(); ++j){
                    int next = (Integer)(graph[item].get(j));
                    if (inGraph[next]) {
                        dj.union(item, next);
                    }
                }

                res[i] = (dj.numTrees == i+1);
                inGraph[item] = true;
            }

            for (int i = 0; i < n; ++i){
                if (res[i])
                    System.out.println("YES");
                else 
                    System.out.println("NO");
            }


    }
}

    //define a class Disjointset

    class Disjointset{
        // define a parent array of pair class which store (ID, height)
        private pair[] parents;
        //define a variable called numtrees
        public int numTrees;
        //define a constructor {
            
            //Create the initial state of a disjoint set of n elements, 0 to n-1
            public Disjointset(int n){
                parents = new pair[n];
                for (int i = 0; i < n; ++i){
                    parents[i] = new pair(i, 0);
                }
                numTrees = n;
            }
            //All nodes start as leaf nodes
            //assign a value to components variable
        //}

        //find , union

        //define a find function which returns the root node of the tree storing ID
        public int find(int ID){

            if (ID != parents[ID].getID()) {
                parents[ID].setID(find(parents[ID].getID()));
            }
            return parents[ID].getID();

        }
        //define union function which add's a new node to existing graph
        public boolean union(int n1, int n2){

            int root1 = find(n1);
            int root2 = find(n2);

            if (root1 == root2) return false;

            if (parents[root1].getHeight() > parents[root2].getHeight())
                parents[root2].setID(root1);

            else if (parents[root2].getHeight() > parents[root1].getHeight())
                parents[root1].setID(root2);

            else {

                parents[root2].setID(root1);
                parents[root1].addHeight();
            }
            
            numTrees--;
            return true;
        }


    }

    // define your pair class here
    class pair{
        private int ID;
        private int height;

        public pair(int n, int h){
            ID = n;
            height = h;
        }

        public int getHeight(){
            return height;
        }

        public int getID(){
            return ID;
        }

        public void addHeight(){
            height++;
        }

        public void setID(int n){
            ID = n;
        }

    }


