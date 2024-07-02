
/*Summer 24
 * COP 3503C Assignment 3
 * THis program is written ny: Kevin Dasrath
 */
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner stdin = new Scanner(System.in);

        // gathering user inputs
        int n = stdin.nextInt();
        int m = stdin.nextInt();
        int d = stdin.nextInt() + 1;

        Main mySet = new Main(n);

        // array of unions to perform
        edge[] edges = new edge[m];

        // array of edges to delete
        int[] deletions = new int[d];

        for (int i = 0; i < m; ++i) {
            int t1 = stdin.nextInt();
            int t2 = stdin.nextInt();
            edges[i] = new edge(t1, t2);
        }

        for (int i = 1; i < d; ++i) {
            deletions[i] = stdin.nextInt() - 1;
            edges[deletions[i]].setExists(false); // boolean to keep track of whether or not an edge has been marked for
                                                  // deletion
        }

        // perform the union if not marked
        for (int i = 0; i < m; ++i) {
            if (edges[i].getExists()) {
                mySet.union(edges[i].getValue1(), edges[i].getValue2());
            }
        }

        int[] sums = new int[d];
        int[] weights = new int[n];

        // connectivity of a the "final" set
        for (int i = 0; i < n; ++i) {
            int tmp = mySet.find(i);
            weights[tmp]++;
        }

        // connectivity of a the "final" set
        for (int i = 0; i < n; ++i) {
            if (weights[i] > 0) {
                sums[d - 1] += weights[i] * weights[i];
            }
        }

        // unionizing the edges marked for deletion, working backwards
        for (int i = d - 1; i > 0; --i) {
            edge e = edges[deletions[i]];
            int root1 = mySet.find(e.getValue1());
            int root2 = mySet.find(e.getValue2());

            // if making the edge doesnt form a new connection, the union operationg doesnt
            // matter. So, just bypass and set the sum equal since no new inner connections
            // are formed.
            if (root1 != root2) {
                sums[i - 1] = sums[i] - weights[root1] * weights[root1]
                        - weights[root2] * weights[root2];
                mySet.union(root1, root2);
                int newRoot = mySet.find(root1);
                weights[newRoot] = weights[root1] + weights[root2];
                sums[i - 1] += weights[newRoot] * weights[newRoot];
            } else {
                sums[i - 1] = sums[i];
            }
        }

        for (int i = 0; i < d; ++i)
            System.out.println(sums[i]);

    }

    private pair[] parents;

    // Create the initial state of a disjoint set of n elements, 0 to n-1.
    public Main(int n) {

        // All nodes start as leaf nodes.
        parents = new pair[n];
        for (int i = 0; i < n; i++)
            parents[i] = new pair(i, 0); // 0 is height 0. parent[i]'s parent is i now
    }

    // Returns the root node of the tree storing id.
    public int find(int id) {
        // I am the root of the tree)
        if (id == parents[id].getID())
            return id;
        // Find my parent's root.
        int res = find(parents[id].getID());

        // if res is not mu existing parent, make it parent
        if (res != parents[id].getID()) {
            // Attach me directly to the root of my tree.
            parents[id].setID(res);

            parents[res].decHeight(); // decrease height as id is leveled up
        }
        return res;
    }

    public boolean union(int id1, int id2) {

        // Find the parents of both nodes.
        int root1 = find(id1);
        int root2 = find(id2);

        // No union needed.
        if (root1 == root2)
            return false;

        // Attach tree 2 to tree 1
        if (parents[root1].getHeight() > parents[root2].getHeight()) {
            parents[root2].setID(root1);
        }

        // Attach tree 1 to tree 2
        else if (parents[root2].getHeight() > parents[root1].getHeight()) {
            parents[root1].setID(root2);
        }

        // Same height case - just attach tree 2 to tree 1, adjust height.
        else {
            parents[root2].setID(root1);
            parents[root1].incHeight();
        }

        // We successfully did a union.
        return true;
    }

    // Just represents this object as a list of each node's parent.
    public String toString() {

        String ans = "";
        for (int i = 0; i < parents.length; i++) {
            if (i == parents[i].getID()) // print the height if the node is root
                ans = ans + "(" + i + ", " + parents[i].getID() + ":" + parents[i].getHeight() + ") ";
            else
                ans = ans + "(" + i + ", " + parents[i].getID() + ") ";
        }
        return ans;
    }
}

class pair {

    private int ID;
    private int height;

    public pair(int myNum, int myHeight) {
        ID = myNum;
        height = myHeight;
    }

    public int getHeight() {
        return height;
    }

    public int getID() {
        return ID;
    }

    public void incHeight() {
        height++;
    }

    public void decHeight() {
        height--;
    }

    public void setID(int newID) {
        ID = newID;
    }
}

// object created to track edges to be made
// used as an array in main, where each edge keeps track of the order it was
// input, the values to union, and whether or not it is marked for deletion
class edge {

    private int[] values;
    private boolean exists;

    public edge(int val1, int val2) {
        values = new int[2];
        values[0] = val1 - 1;
        values[1] = val2 - 1;
        exists = true;
    }

    public int getValue1() {
        return values[0];
    }

    public int getValue2() {
        return values[1];
    }

    public boolean getExists() {
        return exists;
    }

    public void setExists(boolean status) {
        exists = status;
    }

}
