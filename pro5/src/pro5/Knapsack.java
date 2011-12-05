package pro5;

import java.util.*;     // Scanner
import java.io.*;       // File, etc.

/*
 * Basic knapsack problem:
 *
 * We are given n objects and a knapsack.  Each object has a positive
 * weight and a positive value.  The knapsack is limited in the weight
 * that it can carry.  Fill the knapsack in a way that maximizes the
 * value of the included objects, where you are allowed to take a
 * fraction of an object.
 *
 * Author:  Timothy Rolfe
 *
 * Based on Brassard and Bratley, _Fundamentals_of_Algorithmics_,
 * pp. 202-04.
 *
 * Note:  The array "avail" is sorted several times, each with a
 *        different criterion for sorting.  Only the last time is
 * the optimal knapsack obtained (when sorted by value per weight).
 */

public class Knapsack
{
// Instance-scope fields
   int       n;    // Number of objects

   Items[]    avail;// Available objects for use
   Items[]    used; // objects actually used
   int       nUsed;

   double    maxWeight; // Weight limit of the knapsack.

   static Scanner console = new Scanner(System.in);

// Easy-out for IOExceptions --- throw them back to the operating system
   public static void main ( String[] args ) throws Exception
   {
      Knapsack knap = new Knapsack();
      String lineIn;
      String[] header = { "ranked by increasing weight",
                          "ranked by decreasing value",
                          "ranked by decreasing value per weight" };
      int k;            // Loop variable

      System.out.print ("Input file name:  ");
      if ( args.length < 1 )
         lineIn = console.nextLine();
      else
      {  lineIn = args[0];  System.out.println (args[0]);  }

      Scanner inp = new Scanner (new File (lineIn));

      knap.initialize(inp);  selah();
      for ( k = 0; k < 3; k++ )
      {  knap.fill(k);
         knap.report(header[k]);
         selah();
      }
   }

/*
 * Data file presumption:
 *
 * 1. Weight ceiling for the knapsack (one number on the line)
 * 2. Number of candidate objects (one number on the line)
 * ...That many number PAIRS:  weight  value
 */
   void initialize(Scanner inp) throws Exception
   {  String lineIn;

      lineIn = inp.nextLine();
      maxWeight = Double.parseDouble(lineIn.trim());

      lineIn = inp.nextLine();
      n = Integer.parseInt(lineIn.trim());
   // Arrays "used" and "avail" are initialized here.
      used  = new Items[n];
      avail = new Items[n];

      System.out.println ("\nData accepted:  n = " + n);
      System.out.println ("Individual object descriptions follow.");
      for ( int k = 0; k < n; k++ )
      {  double wt  = inp.nextDouble(),
                val = inp.nextDouble();

         avail[k] = new Items(wt, val);
         System.out.printf ( "w:  %1.0f   v:  %1.0f  v/w:  %2.2f\n",
                             wt, val, val/wt );
      }
   }

// Fill the knapsack (i.e., move items from the heap into used[])
   void fill(int basis)
   {  int  k;
      Items nxt;
      double remain = maxWeight;

      nUsed = 0;

   // Put the available items into requested order
      Items.setBasis(basis);
      java.util.Arrays.sort(avail);
      for ( k = 0 ; remain > 0 && k < avail.length ; k++ )
      {  nxt = avail[k];
         if ( nxt.w <= remain )
            nxt.x = 1;
         else
            nxt.x = remain / nxt.w;
         remain -= nxt.x * nxt.w;
         used[nUsed++] = nxt;
      }
   }

// Show the contents --- as number pairs and fraction used.
   void report(String header)
   {  double sigVal = 0;

      System.out.printf ("\n%1.0f total weight %s\n",maxWeight, header );
      System.out.println ("Knapsack contents:");
      for ( int k = 0; k < nUsed; k++ )
      {  System.out.printf("(%1.0f, %1.0f) --- %2.2f used.\n",
                           used[k].w, used[k].v, used[k].x);
         sigVal += used[k].v * used[k].x;
      }
      System.out.printf ("Total value:   %2.2f\n",sigVal );
   }

   static void selah()
   {  System.out.print("\nPress ENTER to continue:  ");
      console.nextLine();
   }
}

/*
 * Class of items for inclusion in the knapsack
 */
class Items implements Comparable
{  double w,   // For each object, the weight of the object
          v,   // For each object, the value of the object
          x;   // For each object, the fraction of the object used
   double vpw; // For each object, the value-per-weight ratio
// Basis for sorting:  0:  w   ascending,
//                     1:  v   descending
//                     x:  vpw descending
   static private int basis = 2;

   Items(double w, double v)
   {  this.x = 0; this.w = w; this.v = v; this.vpw = v / w;  }

   Items(Items c)
   {  this.w = c.w; this.w = c.w; this.v = c.v; this.vpw = c.vpw; }

   public static void setBasis (int basis)
   {   Items.basis = basis;  }

// Comparable insists on the following method signature:
   public int compareTo(Object x)
   {  Items rt = (Items) x;

   // Basis for ordering is set in the static field basis
      switch (basis)
      {  // ascending
         case 0:   return w   < rt.w  ? -1 : w   > rt.w   ? +1 : 0;
         // descending
         case 1:   return v   < rt.v  ? +1 : v   > rt.v   ? -1 : 0;
         // descending
         default:  return vpw < rt.vpw? +1 : vpw > rt.vpw ? -1 : 0;
      }
   }

}
/*    Results of a run

Input file name:  Knap.inp

Data accepted:  n = 5
Individual object descriptions follow.
w:  10   v:  20  v/w:  2.00
w:  20   v:  30  v/w:  1.50
w:  30   v:  66  v/w:  2.20
w:  40   v:  40  v/w:  1.00
w:  50   v:  60  v/w:  1.20

Press ENTER to continue:

100 total weight ranked by increasing weight
Knapsack contents:
(10, 20) --- 1.00 used.
(20, 30) --- 1.00 used.
(30, 66) --- 1.00 used.
(40, 40) --- 1.00 used.
Total value:   156.00

Press ENTER to continue:

100 total weight ranked by decreasing value
Knapsack contents:
(30, 66) --- 1.00 used.
(50, 60) --- 1.00 used.
(40, 40) --- 0.50 used.
Total value:   146.00

Press ENTER to continue:

100 total weight ranked by decreasing value per weight
Knapsack contents:
(30, 66) --- 1.00 used.
(10, 20) --- 1.00 used.
(20, 30) --- 1.00 used.
(50, 60) --- 0.80 used.
Total value:   164.00

Press ENTER to continue:
*/
