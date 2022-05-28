package Ls;

/**
 * A class to use this simple 'ls' implentation. Main entry point for command line.
 *
 * Invocation:
 *      java Ls [-r] [-s] [--] (path)
 *
 *  -r: list paths recursively
 *  -s: show sizes in bytes
 *  --: Optional. Tells us that arguments have ended. This is useful if you have a path that's named '-r' or so.
 *  path: path to start at
 *
 * @author Philipp Weinbrenner
 */

public class Ls {
  public static void printInvocation() {
    System.err.println("Invocation:");
    System.err.println("     java Ls [-r] [-s] [--] (path)");
    System.err.println("");
    System.err.println(" -r: list paths recursively");
    System.err.println(" -s: show sizes in bytes");
    System.err.println(" --: Optional. Tells us that arguments have ended. This is useful if you have a path that's named '-r' or so.");
    System.err.println(" path: path to start at");
  }

  public static void main(String[] args) {
    /* Parse the command line */
    int argc = 0;
    boolean recursive = false, sizes = false;
    String path = null;

    for (argc = 0; argc < args.length; argc++) {
      String arg = args[argc];
      if(arg.charAt(0) != '-') {
        break;
      }

      if(arg.equals("-r"))
        recursive = true;
      else if (arg.equals("-s"))
        sizes = true;
      else if (arg.equals("-h")) {
        printInvocation();
        return;
      } else if (arg.equals("--")) {
        argc--;
        break;
      } else {
        System.err.println("Unrecognized argument '" + arg + "'");
        printInvocation();
        return;
      }
    }

    try {
      path = args[argc];
    } catch (ArrayIndexOutOfBoundsException ex) {
      path = System.getProperty("user.dir");
    }

    /* Create the objects and start the actual work */
    LsVisitor vs = new LsVisitor(recursive, sizes);
    VisitablePath p = new VisitablePath(path);
    if (!p.exists()) {
      System.err.println("Can not find path at " + p.getAbsolutePath() + "!");
      return;
    }

    p.accept(vs);
  }
}
