package util;

import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

/**
 * An implementation of a Queue with a limited capacity.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 * @param <E>
 */
public class Queue<E> {

	/**
	 * Holds the objects stored by this {@code Queue}.
	 */
	private Object[] objects;
	/**
	 * index of the first instance stored by this {@code Queue}.
	 */
	private int first;
	/**
	 * number of elements contained in this {@code Queue}
	 */
	private int size;

  /**
   * Locks access to the values
   */
  private final ReentrantLock lock = new ReentrantLock();
  private final Condition enqueue_possible = lock.newCondition();
  private final Condition dequeue_possible = lock.newCondition();

	/**
	 * @param capacity
	 *            number of objects which may be hold in this {@code Queue}.
	 */
	public Queue(int capacity) {
		this.objects = new Object[capacity];
		this.first = 0;
		this.size = 0;
	}

	/**
	 * Inserts {@code o} at the first free position of this {@code Queue}
	 * 
	 * @param o
	 *            object to be inserted
	 * 
	 * @throws RuntimeException
	 *             if this {@code Queue} is already full
	 */
	public void enq(E o) {
    lock.lock();
		if (this.full()) {
      try {
  			enqueue_possible.await();
      } catch (InterruptedException ex) {
        lock.unlock();
        throw new RuntimeException(ex);
      }
		}

		objects[(first + size) % objects.length] = o;
		size++;

    if(size != objects.length)
      enqueue_possible.signal();
    if (size != 0)
      dequeue_possible.signal();

    lock.unlock();
	}

	/**
	 * Removes the object at the first position of this {@code Queue}.
	 * 
	 * @return the removed object
	 * @throws NoSuchElementException
	 *             if this {@code Queue} is already empty
	 */
	public E deq() {
    lock.lock();

		if (this.empty()) {
      try {
  			dequeue_possible.await();
      } catch (InterruptedException ex) {
        lock.unlock();
        throw new RuntimeException(ex);
      }
		}

		E o = (E) objects[first];
		first = (first + 1) % objects.length;
		size--;

    if(size != objects.length)
      enqueue_possible.signal();
    if (size != 0)
      dequeue_possible.signal();

    lock.unlock();
		return o;
	}

	/**
	 * Returns the object at the first position of this {@code Queue}
	 * 
	 * @return the first element of this {@code Queue}
	 * @throws NoSuchElementException
	 *             if this {@code Queue} is already empty
	 */
	public E front() {
		if (this.empty()) {
			throw new NoSuchElementException();
		}
		return (E) objects[first];
	}

	/**
	 * 
	 * @return {@code true} if this {@code Queue} is empty
	 */
	public boolean empty() {
		return this.size == 0;
	}

	/**
	 * 
	 * @return {@code true} if this {@code Queue} is full
	 */
	public boolean full() {
		return this.size == objects.length;
	}

}
