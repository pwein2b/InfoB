package threads;

import util.Queue;

/**
 * A simple {@code Thread} which continuously writes uniformly distribute random
 * values from 0 to {@code MAX_VALUE} to the given {@code Queue}. Will sleep
 * {@code SLEEP_TIME} after every insertion.
 *
 * If the Queue is full, the thread blocks through a provided monitor object. The monitor should
 * be notify()'ed once the queue is no longer full. This thread itself notify()'s as soon as it writes
 * to the queue.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 */
public class RandomGenerator extends Thread {

	private Queue<Long> randoms;

	public static final long MAX_VALUE = 3000;

	public static final long SLEEP_TIME = 1000;

	public RandomGenerator(Queue<Long> randoms) {
		this.randoms = randoms;
	}

	public void run() {
		try {

			while (true) {

				long random;

        synchronized (randoms) {
   				random = (long) (Math.random() * (double) MAX_VALUE);
	  			System.out.println("Now putting " + random);

          if(randoms.full())
            randoms.wait();

		  		randoms.enq(random);
          randoms.notify();
        }

				this.sleep(SLEEP_TIME);

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
