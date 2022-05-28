package Ls;

/**
 * A Visitor implementation that iterates over the elements of a VisitablePath and prints them out recursively and,
 * if enabled with their sizes with proper indentation.
 * The size of directories needs to be recursively calculated. We use the CalculateSizeVisitor for that.
 *
 * @author Philipp Weinbrenner
 */

import visitor.Visitor;
import java.util.HashMap;
import java.io.File;

public class LsVisitor implements Visitor<VisitablePath> {
  /**
   * Whether the visitor should recursively go through the elements
   */
  private boolean recurse;

  /**
   * Whether the visitor should print out the elements sizes
   */
  private boolean printSizes;

  /**
   * Since the recursive calculation of file sizes might result in redundant calculations, we save the files
   * sizes (in bytes) we already calculated in this HashMap
   */
  private HashMap<File, Long> sizes;

  /**
   * The current indentation level; it is 0 in the beginning and increases [decreaes] before [after] each recursion level
   */
  private int indent;

  /**
   * Create a new LsVisitor.
   *
   * @param recursive Whether the visitor should visit Directories recursively
   * @param printSizes Whether the visitor should calculate and print file and cumulative directory sizes. This may become
   * computationally expensive.
   */
  public LsVisitor (boolean recursive, boolean printSizes) {
    this.recurse = recursive;
    this.printSizes = printSizes;
    this.indent = 0;
    this.sizes = new HashMap<File, Long>();
  }

  /**
   * Create a new LsVisitor that is not recursive and does not print out file and directory sizes.
   */
  public LsVisitor () {
    this(false, false);
  }

  /**
   * Visit a path, that means print out its information.
   */
  public boolean visit (VisitablePath p) {
    printIndent();

    if(printSizes) {
      if (!sizes.containsKey(p))
        computeSize(p);

      System.out.print(sizes.get(p) + "  ");
    }

    System.out.println(p.getAbsolutePath());

    return !shouldAbort(p);
  }
  
  @Override
  public void preRecursion(VisitablePath p) {
    indent += 2;
  }

  @Override
  public void postRecursion(VisitablePath p) {
    indent -= 2;
  }

  @Override
  public boolean shouldRecurse(VisitablePath p) {
    return this.recurse;
  }

  /**
   * Print as many spaces as the current indent level is and do not start a new line.
   */
  private void printIndent() {
    for(int i = 0; i < indent; i++)
      System.out.print(' ');
  }

  /**
   * Compute the size of a path.
   * For a file, that is the size on disk in bytes, for a directory, the cumulative sum of sizes of its child pathes.
   * @return The computed size in bytes, which also will be cached in the sizes HashMap.
   */
  private long computeSize(VisitablePath p) {
    CalculateSizeVisitor v = new CalculateSizeVisitor(p);
    p.accept(v);
    this.sizes.putAll(v.getSizes());
    this.sizes.put(p, v.getResult());
    return this.sizes.get(p);
  }

  /**
   * Called after each element has been visited, but before it might be resursed into.
   * The default implementation always returns false, but may of course be overriden.
   * @return true, if the visiting should stop
   */
  private boolean shouldAbort(VisitablePath p) {
    return false;
  }
}
