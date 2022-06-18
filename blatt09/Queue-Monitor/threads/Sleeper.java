package threads;

import util.Queue;

/**
 * Simple {@code Thread} which continuously reads random values from the given
 * {@code Queue} and sleeps for as long as the currently read value determines.
 *
 * If no random value can be read from the queue, the thread blocks until it is notify()'ed on
 * the monitor object (the queue itself). The thread itself notify()'es after each read.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class Sleeper extends Thread {

   private Queue<Long> values;

   public Sleeper(Queue<Long> values) {
      this.values = values;
   }

   public void run() {
      try {
         while (true) {
            long value;

            synchronized (values) {
              if (values.empty())
                values.wait();

              value = values.deq();
              values.notify();

              System.out.println("Now sleeping for " + value + " ms");
            }

            this.sleep(value);

         }
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }
}
