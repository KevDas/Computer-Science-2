// Skip List Implementation. Most part was discussed in the class

import java.util.*;

public class Main {

	final public static int NEG_INF = Integer.MIN_VALUE;
	final public static int POS_INF = Integer.MAX_VALUE;

	public static Random rndObj = new Random();

	private ArrayList<node> levels;
	private int size;

	// Makes an empty list.
	public Main() {

		// Initially, I am just one level with min and max.
		levels = new ArrayList<node>();
		levels.add(buildLevel(0));
		size = 1;
	}

	// This builds level id to be an empty level.
	public node buildLevel(int id) {
		node first = new node(NEG_INF, id);
		node last = new node(POS_INF, id);
		first.next = last;
		last.prev = first;
		return first;
	}

	// Returns a list of nodes at each level that are right before value.
	public ArrayList<node> search(int value) {

		// Store answers here.
		ArrayList<node> res = new ArrayList<node>();
		node cur = levels.get(size - 1);

		// We search from top, so that we can skip terms.
		for (int i = size - 1; i >= 0; i--) {

			// Go down this level until we're right before an equal or bigger item.
			while (cur.next.data < value)
				cur = cur.next;

			// This is the floor of value on this list, so add it.
			res.add(cur);

			// Go down to the next level.
			if (i > 0)
				cur = cur.down;
		}

		// So the list will be in the proper order, since I built it backwards.
		Collections.reverse(res);

		// Stores all of the relevant pointers.
		return res;
	}

	// ****************************
	// Inserts value into the set, returns true iff the value was inserted. (False
	// means the
	// value was already in the set.
	public boolean insert(int value) {

		// Find all the "previous" nodes.
		ArrayList<node> beforeList = search(value);

		// This value is already in the set.
		if (beforeList.get(0).next.data == value)
			return false;

		// Temp pointer I will use.
		node curn = null;

		int i = 0;

		// Farthest we'll go up the lists.
		while (i <= size) {

			// intentionally generate 1 for first level as we need to at least insert it
			int val = i == 0 ? 1 : rndObj.nextInt(2);
			// int val = rndObj.nextInt(2);
			if (val == 0)
				break;

			// We've decided to create this node.
			node newn = new node(value, i);

			// Not necessary for bottom level.
			if (i > 0) {
				curn.up = newn;
				newn.down = curn;
			}

			// Special case where we are adding a new level to our list.
			// We add the level and then connect it to the rest of the lists.
			if (i == size) {
				node nextL = buildLevel(size);
				levels.add(nextL);
				connectLastLevel();
				beforeList.add(nextL);
			}

			// Lots of patching... (joining newn between prev and next)
			node tmpLow = beforeList.get(i);
			node tmpNext = tmpLow.next;
			newn.prev = tmpLow;
			newn.next = tmpNext;
			tmpLow.next = newn;
			tmpNext.prev = newn;

			// Need to update the object's size and get out.
			if (i == size) {
				size++;
				break;
			}

			// Go up next level.
			i++;
			curn = newn;
		}

		// We inserted it.
		return true;
	}

	// Deletes value from the list. Returns true if value was in the list and was
	// deleted.
	// Returns false if value wasn't in the list and takes no action.
	public boolean delete(int value) {

		// search to see if value is in list
		ArrayList<node> beforeList = search(value);

		node bottom = beforeList.get(0);

		// false if value not present and end function
		if (bottom.next.data != value)
			return false;

		node tmp = bottom.next;

		// while tmp is still present on a level, delete it and set its
		// pointers equal to each others
		while (tmp != null) {

			node prevNode = tmp.prev;
			node nextNode = tmp.next;

			// sets its prev. nodes next == tmp's next
			prevNode.next = nextNode;
			// sets its next nodes prev. == tmp's prev
			nextNode.prev = prevNode;

			// traverse up to next level
			tmp = tmp.up;
		}

		if (size > 1 && topLevelSize() == 2) {
			levels.remove(size - 1);
			size--;
		}
		return true;
	}

	// Returns the number of items on the top level.
	private int topLevelSize() {
		node cur = levels.get(size - 1);
		int sz = 0;
		while (cur != null) {
			cur = cur.next;
			sz++;
		}
		return sz;
	}

	// Connects the last level to the rest of the lists.
	public void connectLastLevel() {

		// We can obtain both of these.
		node top = levels.get(levels.size() - 1);
		node below = levels.get(levels.size() - 2);

		// Link left sides up and down.
		top.down = below;
		below.up = top;

		// End of top list.
		top = top.next;

		// Go to end of second to top list.
		while (below.data != POS_INF)
			below = below.next;

		// Link right sides up and down.
		top.down = below;
		below.up = top;
	}

	// For debugging.
	public void printAllLevels() {
		System.out.println(levels.size() + " and " + size);
		for (int i = 0; i < size; i++) {
			System.out.print("Level " + i + ": ");
			printLevel(i);
		}
		System.out.println("---------------------------");
	}

	// Prints level id. For debugging.
	public void printLevel(int id) {
		node cur = levels.get(id);
		while (cur != null) {
			System.out.print(cur.data + " ");
			cur = cur.next;
		}
		System.out.println();
	}

	// Basic insert test.
	public static void basicInsertTest() {

		// Create the object.
		Main mine = new Main(); // this constructor creates a level as well

		// Do 100 inserts.
		for (int i = 0; i < 10; i++) {

			// Generate the item.
			int item = rndObj.nextInt(1000);
			System.out.println("Gen " + item);

			// Insert it.
			boolean flag = mine.insert(item);

			// Print what happened.
			if (flag)
				System.out.println("Inserted " + item);
			else
				System.out.println("Rejected " + item);

			// See all the lists.
			mine.printAllLevels();
		}
		// Let us delete
		Scanner sc = new Scanner(System.in);
		int item = 0;
		while (item != -1) {

			System.out.println("Enter an item to delete: ");
			item = sc.nextInt();
			boolean flag = mine.delete(item);
			if (flag) {
				System.out.println("Deleted " + item);
				mine.printAllLevels();
			} else
				System.out.println(item + " can not be deleted.");
		}

	}

	// Returns all the items in the skip list in order.
	public ArrayList<Integer> getList() {
		node bottom = levels.get(0);
		ArrayList<Integer> res = new ArrayList<Integer>();
		bottom = bottom.next;
		while (bottom.data != POS_INF) {
			res.add(bottom.data);
			bottom = bottom.next;
		}
		return res;
	}

	// A large test of random inserts followed by random deletes.
	public static void largeTestRandom(int testSize) {

		int[] insert = new int[testSize];
		for (int i = 0; i < testSize; i++)
			insert[i] = rndObj.nextInt(2000000);
		int[] del = new int[testSize];
		for (int i = 0; i < testSize; i++)
			del[i] = rndObj.nextInt(2000000);

		Main myset = new Main();

		long sSkip = System.currentTimeMillis();

		for (int i = 0; i < testSize; i++)
			myset.insert(insert[i]);

		long eSkipmid = System.currentTimeMillis();
		System.out.println("Skip list insertion took " + (eSkipmid - sSkip) + " ms.");

		for (int i = 0; i < testSize; i++)
			myset.delete((del[i]));

		long eSkip = System.currentTimeMillis();
		System.out.println("Skip list deletion took " + (eSkip - eSkipmid) + " ms.");
		System.out.println("Skip list actions took " + (eSkip - sSkip) + " ms.");

		// Now do it with a tree st.
		TreeSet<Integer> ts = new TreeSet<Integer>();
		long sTS = System.currentTimeMillis();

		for (int i = 0; i < testSize; i++)
			ts.add(insert[i]);

		long mTS = System.currentTimeMillis();

		System.out.println("tree set insertion took " + (mTS - sTS) + " ms.");

		for (int i = 0; i < testSize; i++)
			if (ts.contains(del[i]))
				ts.remove(del[i]);

		long eTS = System.currentTimeMillis();
		System.out.println("tree set deletion took " + (eTS - mTS) + " ms.");
		System.out.println("tree set actions took " + (eTS - sTS) + " ms.");

	}

	// Returns all items of the treeset in order.
	public static ArrayList<Integer> get(TreeSet<Integer> ts) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		while (ts.size() > 0)
			res.add(ts.pollFirst());
		return res;

	}

	public static void main(String[] args) {

		// basicInsertTest(); // this is just to test whether your insert and delete
		// function work or not.
		int[] tests = { 50000, 100000, 150000, 200000, 250000, 300000, 350000, 400000, 450000, 500000 };

		for (int i = 0; i < (tests.length); i++) {
			System.out.println("\nTest size: " + tests[i] + "\n=======");
			largeTestRandom(tests[i]);
		}
	}

}

class node {

	public int data;
	public node next;
	public node prev;
	public node up;
	public node down;
	public int level;

	public node(int myval, int mylev) {
		data = myval;
		level = mylev;
		next = null;
		prev = null;
		up = null;
		down = null;
	}

}