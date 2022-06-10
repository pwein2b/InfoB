/**
 * A simple TimerTask that regurlarly checks whether the size of a path has changed, and prints out a message
 * in that case. The size of a directory is defined as the sum of the sizes of its children.
 *
 * The size calculation reuses code from Testat 7.
 *
 * @author Philipp Weinbrenner
 */

import java.util.TimerTask;
import java.util.Timer;
import java.io.File;
import Ls.CalculateSizeVisitor;
import Ls.VisitablePath;

public class FileWatcher extends TimerTask {
  protected File file;
  protected long old_size;
  protected VisitablePath vp;

  public FileWatcher (File path) {
    this.file = path;
    vp = new VisitablePath(path);
    old_size = computeSize();
  }

  /**
   * Compute the size of the file and update the old_size property
   */
  public long computeSize () {
    CalculateSizeVisitor v = new CalculateSizeVisitor(vp);
    vp.accept(v);
    old_size = v.getResult();
    return old_size;
  }

  @Override
  public void run () {
    long old_size = this.old_size;
    if (old_size != computeSize())
      printMessage();
  }

  /**
   * Print the message that the size has changed, and the new size contained in the "old_size" property
   */
  protected void printMessage () {
    System.out.println(file.toString() + ": " + old_size + " bytes");
  }

  public static void main(String[] args) {
    String fn = args[0];
    File f = new File(fn);
    FileWatcher fw = new FileWatcher(f);

    /* When the process is cancelled for some reason, display a last message: */
    Runtime.getRuntime().addShutdownHook(new Thread () {
      @Override
      public void run () {
        fw.computeSize();
        fw.printMessage();
      }
    });

    Timer t = new Timer();
    t.schedule(fw, 1000, 1000);
  }
}
