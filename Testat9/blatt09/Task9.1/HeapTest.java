import test.Assert;

import java.util.Comparator;

/**
 * Test the class Heap if it functions as it should. Uses assertions
 * that need to be activated with the -ea command line parameter
 *
 * @author Lars Huning
 */
public class HeapTest {

   public static void main(String[] args) {

      final Integer[] integers = {1, 5, 7, 2, 3, -1, 0};

      Heap<Integer> comparableHeap = new Heap<Integer>();
      final Integer[] sortedIntegers = {-1, 0, 1, 2, 3, 5, 7};

      System.out.println("Test Heap functions...");
      System.out.println("...do HeapSort on a Heap and test outcome...");
      Assert.arrayEquals(sortedIntegers, HeapSort.heapSort(comparableHeap,
            integers));

      System.out.println("...do HeapSort with a specific comparator...");
      Comparator<Integer> c = new IntegerReverseComparator();

      Heap<Integer> comparatorHeap = new Heap<Integer>(c);
      Integer[] reverseSortedIntegers = {7, 5, 3, 2, 1, 0, -1};
      Assert.arrayEquals(reverseSortedIntegers,
              HeapSort.heapSort(comparatorHeap, integers));

      //
      //new Heap<Integer>()


      System.out.println("...finished.");
   }


}
