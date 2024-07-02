/* CS2
Java code that implements disjontsets with Union by height and path compression*/

import java.util.*;

public class Main2 {

    // A little driver program to test our class.
    public static void main(String[] args) {

        Scanner stdin = new Scanner(System.in);

        int n = stdin.nextInt();
        int m = stdin.nextInt();
        int d = stdin.nextInt() + 1;

        Main2 mySet = new Main2(n);
        edge[] edges = new edge[m];
        int[] deletions = new int[d];

        for (int i = 0; i < m; ++i) {
            int t1 = stdin.nextInt();
            int t2 = stdin.nextInt();
            edges[i] = new edge(t1, t2);
        }

        for (int i = 1; i < d; ++i) {
            deletions[i] = stdin.nextInt() - 1;
            edges[deletions[i]].setExists(false);
        }

        for (int i = 0; i < m; ++i) {
            if (edges[i].getExists() == true)
                mySet.union(edges[i].getValue1(), edges[i].getValue2());
        }

        int sums[] = new int[d];

        for (int i = d - 1; i >= 0; --i) {
            int children[] = new int[n];

            for (int k = 0; k < n; ++k) {
                children[mySet.find(k)] += 1;
            }

            for (int j = 0; j < n; ++j) {
                sums[i] += children[j] * children[j];
            }
            mySet.union(edges[deletions[i]].getValue1(), edges[deletions[i]].getValue2());
        }

        for (int i = 0; i < d; ++i)
            System.out.println(sums[i]);

    }

    private pair[] parents;

    // Create the initial state of a disjoint set of n elements, 0 to n-1.
    public Main2(int n) {

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