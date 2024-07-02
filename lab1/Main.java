
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    
    
    public static void main(String args[]){
    // smaller = max heap
    PriorityQueue<Integer> smaller = new PriorityQueue<>(Collections.reverseOrder());

    // greater = min heap
    PriorityQueue<Integer> greater = new PriorityQueue<>();

    Scanner input = new Scanner(System.in);

    int n = input.nextInt();
    int data = input.nextInt();
    smaller.add(data);
    double median = data;
    System.out.println(median);
       


    for (int i = 1; i < n; ++i){

        int x = input.nextInt();

        

        if(smaller.size() > greater.size())
        {
            // if x is smaller than the median,
            // we add the top value of the max heap
            // to the bottom of the min heap and then 
            // add x to the max heap
            if (x < median)
            {
                greater.add(smaller.poll());
                smaller.add(x);
            }
            // if x is larger than the median, we
            // add it to the min heap
            else 
                greater.add(x);

            median = (double)(smaller.peek() + greater.peek())/2;

            
        }

        else if(smaller.size() < greater.size()){

            if (x > median)
            {
                smaller.add(greater.poll());
                greater.add(x);
            }
            else 
                smaller.add(x);

            median = (double)(smaller.peek() + (double)greater.peek())/2;
            
           


        }
        else{

        if (x > median){
            greater.add(x);

            median = greater.peek();
        }

        else{
            smaller.add(x);

            median = smaller.peek();
        }

        
        }
        System.out.println(median);

    }


    input.close();
    }

}
