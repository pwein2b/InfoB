package Ls;

/**
 * Recursively compute the size of a VisitablePath and store the result together with all sub-results of the recursion in a HashMap.
 * The size of a file is the size in bytes, as returned by java.io.File.length(); the size of a directory is the sum of sizes of its children.
 *
 * @author Philipp Weinbrenner
 */

import java.io.File;
import java.util.HashMap;
import visitor.Visitor;

public class CalculateSizeVisitor implements Visitor<VisitablePath> {
  private HashMap<VisitablePath, Long> sizes;
  private VisitablePath path;

  /**
   * Prepare a CalculateSizeVisitor to compute the size of the path p.
   */
  public CalculateSizeVisitor(VisitablePath p) {
    this(p, null);
  }

  /**
   * Prepare a CalculateSizeVisitor to compute the size of the path p.
   * @param sizes If it is believed the computation will be recursive and some of the recursion values are already known,
   * this parameter should be a HashMap of these pre-results. This HashMap itself will not be modified.
   * Initially, the size if assumed to be zero.
   */
  public CalculateSizeVisitor(VisitablePath p, HashMap<VisitablePath, Long> sizes) {
    if(sizes != null)
      this.sizes = new HashMap<VisitablePath, Long>(sizes);
    else
      this.sizes = new HashMap<VisitablePath, Long>();

    this.path = p;

    this.sizes.put(p, Long.valueOf(0));
  }

  public boolean visit (VisitablePath p) {
    if(p.isDirectory()) {
      if (p.equals(path))
        return true;

      CalculateSizeVisitor subVisitor = new CalculateSizeVisitor(p, sizes);
      p.accept(subVisitor);
      sizes.putAll(subVisitor.getSizes());
      sizes.put(p, subVisitor.getResult());
      sizes.put(path, Long.valueOf(sizes.get(path).longValue() + subVisitor.getResult()));
    } else {
      sizes.put(p, Long.valueOf(p.length()));
      sizes.put(path, Long.valueOf(sizes.get(path).longValue() + p.length()));
    }

    return true;
  }

  /**
   * Unfortunately, we need to perform the recursion ourselves.
   */
  @Override
  public boolean shouldRecurse (VisitablePath p) {
    return false;
  }

  /**
   * Get the HashMap with the intermediate and final results.
   * If a intitial HashMap was given to the constructor, its values will also be included.
   */
  public HashMap<VisitablePath, Long> getSizes() {
    return sizes;
  }

  /**
   * Return the result of the size computation, the size in bytes.
   */
  public long getResult() {
    try {
      return sizes.get(path).longValue();
    } catch (NullPointerException e) {
      throw new RuntimeException("CalculateSizeVisitor.getResult(): ?? \n" + sizes.toString() + "\n for path " + path.toString(), e);//TODO
    }
  }
}
