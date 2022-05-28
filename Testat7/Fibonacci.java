import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Class to calculate the Fibonacci number. Uses a HashMap to reduce the
 * calculation cost.
 *
 * The HashMap cache is written into the file 'fibonacci.ser' in the working directory and read
 * reconstructed upon next start to increase efficiency.
 *
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * @author Philipp Weinbrenner (the Serialization part)
 */
public class Fibonacci {

  private static HashMap<Integer, Long> fibonacciHash;

  /**
   * Fill HashMap with initial key-value-pairs.
   */
  static {
    fibonacciHash = new HashMap<>();
    fibonacciHash.put(0, 0L);
    fibonacciHash.put(1, 1L);
  }

  /**
   * Calculates the fibonacci value of n.
   *
   * @param n a natural number >= 0 to calculate the fibonacci value of
   * @return fibonacci value of n
   */
  public static long fibonacci(int n) {
    if (n < 0) {
      throw new IllegalArgumentException("n = " + n);
    }
    return getFibonacci(n);
  }

  private static long getFibonacci(int n) {
    if (fibonacciHash.containsKey(n)) {
      return fibonacciHash.get(n);
    } else {
      long nMinus1 = getFibonacci(n - 1);
      long nMinus2 = getFibonacci(n - 2);
      long fibonacci = nMinus1 + nMinus2;

      fibonacciHash.put(n, fibonacci);
      return fibonacci;
    }
  }

  public static void main(String[] args) {
    if (args.length != 1) {
      printUsage();
      return;
    }
    try {
      /* Read the fibonacci cache, if it exists */
      File fibfile = new File("fibonacci.ser");
      if(fibfile.exists() && !fibfile.isDirectory()) {
        FileInputStream fis = new FileInputStream("fibonacci.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        fibonacciHash = (HashMap<Integer, Long>)ois.readObject();
        fis.close();
      }
    } catch (FileNotFoundException ex) {
      System.err.println("Java does not work: File 'fibonacci.ser' exists, is not a directory, but throws a FileNotFoundException");
      System.err.println(ex.toString());
    } catch (IOException ex) {
      System.err.println("Trying to read from 'fibonacci.ser':");
      System.err.println(ex.toString());
      /* Even when such an exception is thrown, we can continue the computation with the near-empty default HashSet */
    } catch (ClassNotFoundException|ClassCastException ex) {
      System.err.println("Cannot properly deserialize 'fibonacci.ser':");
      System.err.println(ex.toString());
    }

    try {
      System.out.println(fibonacci(Integer.parseInt(args[0])));
    } catch (IllegalArgumentException ex) {
      printUsage();
      return; /* don't save the HashMap since nothing interesting has changed in it anyway */
    }

    try {
      /* Write the hash map back */
      FileOutputStream fos = new FileOutputStream("fibonacci.ser");
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(fibonacciHash);
      fos.close();
    } catch (IOException ex) {
      System.err.println("Trying to write HashMap to 'fibonacci.ser':");
      System.err.println(ex.toString());
    }
  }

  private static void printUsage() {
    System.out.println("java calc/Fiboncci n");
    System.out.println("n must be a natural number >= 0");
  }
}
