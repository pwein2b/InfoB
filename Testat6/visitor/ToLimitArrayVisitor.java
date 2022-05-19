/**
 * An example Visitor implementation that visits at most n elements and collects them in an ArrayList.
 * The number n will be pre-defined.
 *
 * @author Philipp Weinbrenner
 */

package visitor;
import java.util.ArrayList;

public class ToLimitArrayVisitor<E> implements Visitor<E> {
  private ArrayList<E> result;
  private int limit;
  private int count;

  /**
   * Create a new ToLimitArrayVisitor
   *
   * @param n The maximum number of elements to visit and collect
   */
  public ToLimitArrayVisitor(int n) {
    this.limit = n;
    this.count = 0;

    result = new ArrayList<E>(n);
  }

  public boolean visit(E object) {
    result.add(object);
    count++;

    // Stop if the array is full
    return !(count == limit);
  }

  /**
   * Return the number of elements visited
   */
  public int getCount() {
    return count;
  }

  /**
   * Return the array of visited objects
   */
  public ArrayList<E> getResult() {
    return result;
  }
}
