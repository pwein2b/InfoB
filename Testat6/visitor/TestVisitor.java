/**
 * Test the Visitable implementation in MyList.
 *
 * @author Philipp Weinbrenner
 */

package visitor;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;

public class TestVisitor {
  public static void main(String[] args) {
    /* List of elements to use, and MyList containing them: */
    LinkedList<Integer> elements = new LinkedList<Integer>();
    MyList<Integer> list = new MyList<Integer>();
    for (int i = 0; i < 20; i++) {
      elements.add(Integer.valueOf(i));
      list.add(Integer.valueOf(i));
    }

    System.out.println("Liste der Elemente:");
    for(Integer i : elements)
      System.out.print(i + " ");
    System.out.println("\n");

    /* Test collecting all elements with ToHashSetVisitor */
    ToHashSetVisitor<Integer> v1 = new ToHashSetVisitor<Integer>();
    list.accept(v1);
    HashSet<Integer> hs_result = v1.getResult();
    if(hs_result.containsAll(elements))
      System.out.println("ToHashSetVisitor: wie erwartet alle Elemente besucht");
    else
      System.out.println("ToHashSetVisitor hat nicht alle Elemente besucht!");

    if(!elements.containsAll(hs_result))
      System.out.println("ToHashSetVisitor hat weitere Elemente besucht!");

    System.out.println("Elemente im Ergebnis von ToHashSetVisitor:");
    for(Integer i : hs_result)
      System.out.print(i + " ");
    System.out.println("\n");

    /* Test collecting five elements with ToLimitArrayVisitor */
    ToLimitArrayVisitor<Integer> v2 = new ToLimitArrayVisitor<Integer>(5);
    list.accept(v2);
    ArrayList<Integer> la_result = v2.getResult();

    if (la_result.size() != 5)
      System.out.println("ToLimitArrayVisitor: das Ergebnis-Array sollte genau 5 Elemente haben");
    else
      System.out.println("ToLimitArrayVisitor: Wie erwaret 5 Elemente besucht");

    if(!elements.containsAll(la_result))
      System.out.println("ToLimitArrayVisitor: unerwartete Elemente wurden in das Ergebnis aufgenommen");

    System.out.println("Elemente im Ergebnis von ToLimitArrayVisitor:");
    for(Integer i : la_result)
      System.out.print(i + " ");
    System.out.println();


    System.out.println("Tests abgeschlossen.");
  }
}
