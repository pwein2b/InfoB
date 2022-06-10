package Ls;

/**
 * Represent a filesystem path that can be visited.
 * This class just subclasses java.io.File for simplicity and adds the visitor implementation.
 *
 * @author Philipp Weinbrenner
 */

import visitor.*;
import java.io.File;

public class VisitablePath extends File implements Visitable<VisitablePath> {
  /**
   * Create a VisitablePath from a pathname.
   */
  public VisitablePath(String pathname) {
    super(pathname);
  }

  /**
   * Create a new VisitablePath from a parent and a child pathname string. See the File.File(String, String)
   * documentation.
   */
  public VisitablePath(String parent, String child) {
    super(parent, child);
  }

  /**
   * Create a new VisitablePath from an exisitng java.io.File object
   */
  public VisitablePath(File path) {
    this(path.getAbsolutePath());
  }

  /**
   * Accept a visitor.
   * The visitor's preRecursion, postRecursion and shouldRecurse methods will be honoured.
   */
  public void accept(Visitor<VisitablePath> v) {
    if(v.visit(this) == false)
      return;

    try {
      recursiveAccept(v);
    } catch (CancelRecursionException ex) {
      return;
    }
  }

  private void recursiveAccept(Visitor<VisitablePath> v) throws CancelRecursionException {
    File[] files = listFiles();
    if (files == null)
      return;

    for(int i = 0; i < files.length; i++) {
      VisitablePath file = new VisitablePath(files[i]);
      if(!v.visit(file))
        throw new CancelRecursionException();

      if(file.isDirectory()) {
        if (v.shouldRecurse(file)) {
          v.preRecursion(file);
          (new VisitablePath(file)).recursiveAccept(v);
          v.postRecursion(file);
        } /* end: perform recursion */
      }
    } /* end: loop over elements */
  }

  /**
   * A helper class for recursively accepting a visitor. Should be thrown if the recursion should be canceled
   * preliminarily. Does nothing special, and the user should never notice this exception being thrown.
   */
  private class CancelRecursionException extends Exception {
    public CancelRecursionException() {
      super("recursion should be cancelled.");
    }
  }
}
