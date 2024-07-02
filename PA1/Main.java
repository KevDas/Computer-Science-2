/* COP 3503C Assignment 1
 * This program is written by: Kevin Dasrath
 */
 import java.util.Scanner;
 import java.util.HashSet;
 
 public class Main {
 
    static Scanner scan;

    //function for sorted list 
    public static int[] getCandidatePair(int A[], int target){
             
        //pointers to the start and end of the list of game's point values
        int n1 = 2;
        int n2 = A.length - 2;

        int pair[] = {-1 , -1};

        //loops through values up until we've searched through all n 
        while (n1 < n2)
        {
            //adds the elements pointed to by the front and end pointers
            int res = A[n1] + A[n2];

            // if they add up to the target, we return them
            if (res == target)
            {
                pair[0] = A[n1];
                pair[1] = A[n2];
                return pair;
            }
            //increment pointers elsewise
            else if (res > target) n2--;
            else if (res < target) n1++;
        }
        // if no pair is found, we return {-1 , -1}
        return pair;
    }
    
    //function for unsorted list
    public static int[] unsortedGetCandidatePair(int A[], int target){
        
        //HashSet to keep track of the values we've used
        HashSet<Integer> values = new HashSet<Integer>();
            
        int n = A[1];
        int[] pair = {-1, -1};
 
            //loops through points needed to play games
            for (int i = 2; i < 2 + n; ++i)
            {
                // checks to see if the complementary value is already in our HashSet
                // if so, that value and the complementary value will be returned
                if (values.contains(target - A[i]))
                {
                     pair[0] = target - A[i];
                     pair[1] = A[i];
                }
                //otherwise, adds the value we just checked to the HashSet
                else values.add(A[i]);
            }
        // if a pair is not found, {-1. -1} is returned
        return pair;
    }
 
    public static void main(String[] args){
 
        scan = new Scanner(System.in);
        int n = scan.nextInt(); // number of test cases to process
        int sorted, elements; // values of sort status and # of games that we can play
         
        int[][] array = new int[n][2]; //array that will hold all values passed through 
        int target[] = new int[n]; // array that will hold all target values
        int[] pair = new int[2]; // array that will hold pairs 
         
        for (int i = 0; i < n; ++i)
        {
            sorted = scan.nextInt();  
            elements = scan.nextInt();

            array[i] = new int[3 + elements]; //resizing each sub-array
            array[i][0] = sorted;
            array[i][1] = elements;
             
             
            for (int j = 2; j < elements +3; ++j)
            {
                array[i][j] = scan.nextInt();
            }     
            target[i] = array[i][2+elements];    
        }
        for (int i = 0; i < n; i++)
        {
            if (array[i][0] == 1) pair = getCandidatePair(array[i], target[i]); // if data is sorted
            else pair = unsortedGetCandidatePair(array[i], target[i]); // if data is unsorted
             
            // if pair does not exist
            if (pair[0] == -1 && pair[1] == -1) 
                System.out.println("Test case#" + (i+1) + ": No way you can spend exactly " + target[i] + " points.");
            // if pair exists    
            else 
                System.out.println("Test case#" + (i+1) + ": Spend " + target[i] + " points by playing the games with " + pair[0] + " points and " + pair[1] + " points.");    
        }
    }
}
 
 