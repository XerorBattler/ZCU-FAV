package pro5;

public class Example {

/**
 * function: knapsack 0-1
 * Computes the maximum value of the knapsack
 *
 * Inputs: two arrays - a[], c[] and an integer b
 * Output: the maximum value achieved
 * 
 *
 * @param  int[] a, c, int b
 */
		 
    public static Knap knapsack01(int[] a, int[] c, int b) {
       // instantiate knap the dynamic programming matrix
       int[][] knap = new int[a.length+1][b];
       // initial row  - solutions to the subproblems with no items selected
       for (int i = 0; i < b; i++) 
           knap[0][i] = 0;
       // process one item at a time for each weight
       for (int k = 1; k <= a.length; k++)
           for (int y = 1; y <= b; y++) 
                if (y < a[k-1]) 
                    knap[k][y-1] = knap[k-1][y-1];
                else
                    if (y > a[k-1])
                            knap[k][y-1] = Math.max(knap[k-1][y-1], knap[k-1][y-1-a[k-1]] + c[k-1]);
                    else
                            knap[k][y-1] = Math.max(knap[k-1][y-1], c[k-1]);
       return new Knap(knap, knap[a.length][b-1]);
    }
    public static int[] findKnap(int[][] knap, int b, int[] a) {
	int k = a.length;
        int[] soln = new int[k];
	for (int j = 0; j < k; j++)
		soln[j] = 0;
        while (k > 0) { 
	    int val = knap[k][b-1];           
   	    while(knap[k-1][b-1] == val) 
                k--;
	    soln[k-1] = 1;
	    b -= a[k-1];
	    k--;
	}
	return soln;
    }
}