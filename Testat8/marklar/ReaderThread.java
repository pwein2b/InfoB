/**
 * A thread that reads from a PipedWriter and writes back to another Writer.
 * The PipedWriter needs to be connected using the connect() method, and the Writer to write to needs to be given in the constructor.
 *
 * @author Philipp Weinbrenner
 */

package marklar;
import java.io.PipedReader;
import java.io.Writer;
import java.io.IOException;
import java.io.BufferedReader;

public class ReaderThread extends PipedReader implements Runnable {
  private Writer out;

  /**
   * Construct a new ReaderThread.
   * @param out: The java.io.Writer where the output should be written to
   */
  public ReaderThread (Writer out) {
    this.out = out;
  }

  @Override
  public void run () {
    try {
      /* We need BufferedReader to read by lines: */
      BufferedReader br = new BufferedReader(this);
      while(true) {
        String line = br.readLine();
        out.write(line + "\n");
        out.flush();
      }
    } catch (IOException ex) {
      /* Pass the Exception to a global exception handler; we need an unchecked exception to do so:
       */
      throw new RuntimeException(ex);
    }
  }
}
