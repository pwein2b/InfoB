/**
 * Test the Iterator returned by MyList
 *
 * @author Philipp Weinbrenner
 */

package iterator;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class TestIterator {
  MyList<Integer> testList;

  /**
   * Create a new test list with integers 0 to 10
   */
  public void initTestList() {
    testList = new MyList<Integer>();
    for (int i = 10; i >= 0; i--) // count down, because add() inserts at the beginning
      testList.add(Integer.valueOf(i));
  }

  private void testIterate() {
    try {
      initTestList();
      Iterator<Integer> it = testList.iterator();
      for(int i = 0; i <= 10; i++) {
        Integer element = it.next();
        if(!element.equals(Integer.valueOf(i)))
          System.out.println("Erwartete " + i + " aber es wurde " + element + " gefunden!");
        else
          System.out.println(" - " + i + " gefunden (passt)");
      }
    } catch (Exception e) {
      System.out.println("Unerwartete Exception " + e.getMessage());
      System.out.println(e.getStackTrace());
    }
  }

  private void testDelete () {
    // Strategie: alle geraden Zahlen entfernen
    try {
      initTestList();
      Iterator<Integer> it = testList.iterator();
      while(it.hasNext())
        if (it.next().intValue() % 2 == 0)
          it.remove();

      // Testen:
      it = testList.iterator();
      for(int i = 0; i <= 4; i++) {
        Integer e = it.next();
        if(!e.equals(Integer.valueOf(2 * i + 1)))
          System.out.println("Erwatete " + (2*i + 1) + ", aber fand: " + e);
        else
          System.out.println(" - " + e + " gefunden (passt)");
      }
    } catch (Exception e) {
      System.out.println("Unerwartete Exception " + e.getMessage());
      System.out.println(e.getStackTrace());
    }
  }

  private void testFailFast() {
    initTestList();

    Iterator<Integer> i = testList.iterator();

    try {
      testList.add(Integer.valueOf(20));
      i.hasNext();
      System.out.println("hasNext() hätte eine ConcurrentModificationException werfen sollen!");
    } catch (ConcurrentModificationException ex) {
      System.out.println("ConcurrenctModificationException gefangen - Funktioniert für hasNext");
    }

    i = testList.iterator();
    try {
      i.next();
      testList.delete();
      i.remove();
      System.out.println("hasNext() hätte eine ConcurrentModificationException werfen sollen!");
    } catch (ConcurrentModificationException ex) {
      System.out.println("ConcurrentModificationException gefangen - Funktioniert für hasNext");
    }
  }

  private void testExceptions() {
    initTestList();

    Iterator<Integer> i = testList.iterator();
    try {
      i.remove();
      System.out.println("Erwartete IllegalStateException - remove() vor next()!");
    } catch (IllegalStateException ex) {
      System.out.println("IllegalStateException in remove(): funktioniert");
    }

    i = testList.iterator();
    try {
      while(i.hasNext())
        i.next();
      i.next();
      System.out.println("Erwartete NoSuchElementException - next() nachdem hasNext() false war");
    } catch (NoSuchElementException ex) {
      System.out.println("NoSuchElementException in next() - funktioniert");
    }
  }

  public void performTests() {
    System.out.println("Iterieren über alle Elemente");
    testIterate();

    System.out.println();
    System.out.println("Löschen von Elementen");
    testDelete();

    System.out.println();
    System.out.println("Fail-Fast");
    testFailFast();

    System.out.println();
    System.out.println("Andere Exceptions");
    testExceptions();
  }

  public static void main(String[] args) {
    TestIterator t = new TestIterator();
    t.performTests();
  }
}
