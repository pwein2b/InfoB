/**
 * An example Visitor that generates a HashSet of all elements that were visited.
 *
 * @author Philipp Weinbrenner
 */

package visitor;
import java.util.HashSet;

public class ToHashSetVisitor<E> implements Visitor<E> {
  private HashSet<E> result;

  public ToHashSetVisitor() {
    result = new HashSet<E>();
  }

  /**
   * Visit an element, that is, add it to the resulting HashSet.
   *
   * @return always true.
   */
  public boolean visit(E object) {
    result.add(object);
    return true;
  }

  /**
   * Return the HashSet created by this Visitor.
   */
  public HashSet<E> getResult() {
    return result;
  }
}
