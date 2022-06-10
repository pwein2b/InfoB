/**
 * Read text from standard input and use the given Marklar Translation algorithm as defined in the exercise to write translated text back on standard output.
 *
 * @author Philipp Weinbrenner
 */

package marklar;
import java.util.concurrent.Executors;
import java.util.concurrent.Executor;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class MarklarTranslator {
  public static void main(String[] args) {
    WriterThread writer = new WriterThread(System.in);
    ReplaceWordWriter replace = new ReplaceWordWriter(new OutputStreamWriter(System.out), "Marklar");
    ReaderThread reader = new ReaderThread(replace);

    try {
      reader.connect(writer);
    } catch (IOException ex) {
      System.err.println("Unable to set up writers and readers: " + ex.toString());
      System.exit(1);
    }

    Executor executor = Executors.newCachedThreadPool();
    executor.execute(writer);
    executor.execute(reader);

    Runtime.getRuntime().addShutdownHook(new Thread () {
      public void run () {
        try {
          writer.close();
          reader.close();
        } catch (IOException ex) {
          System.out.println("Unable to close reader and writer thread: " + ex.toString());
        }
      }
    });
  }
}
