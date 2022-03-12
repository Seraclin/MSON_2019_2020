/******************************************************************************
 *  Name:   Samantha Lin
 *  Compilation:  javac-algs4 RandomizedQueue.java
 *  Execution:    java-algs4 RandomizedQueue
 *  Dependencies: Item.java Iterator.java
 *
 *  Description:  Implementing a Queue that chooses elements at random using an array
 ******************************************************************************/
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private int size;
  private Item[] list;
  public RandomizedQueue() {
    size = 0; // the actual end of the array
    list = (Item[]) new Object[2];
  }

  /**
   * Resize the array to a specified size.
   * @param newSize new size of the array.
   */
  private void resize (int newSize) {
    list = Arrays.copyOf(list, newSize);
  }
  /**
   * Checks if the queue contains any elements.
   * @return the queue is empty or not.
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Outputs the number of elements in the queue.
   * @return the number of elements in the queue.
   */
  public int size() {
    return size;
  }

  /**
   * Adds an item into the queue.
   * @param item to add to the queue.
   * @throws NullPointerException if 'item' is null.
   */
  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    if (size() == list.length) { // if the actual end and physical end are equal to each other
      resize(2 * list.length);
    }
    list[size] = item;
    size++;
  }

  /**
   * Remove a random item and return it.
   * @return the removed item.
   * @throws NoSuchElementException if the queue is empty.
   */
  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    int rand = StdRandom.uniform(0, size);
    Item temp = list[rand];
    list[rand] = list[size - 1];
    list[size - 1] = null;
    size--; // delete the size
    if (size() > 0 && size() == list.length / 4) { // shrink the list when physical size is a quarter of the size
      resize(list.length / 2);
    }
    return temp;
  }

  /**
   * Returns a random item but does not remove it.
   * @return A random item.
   * @throws NoSuchElementException if the queue is empty.
   */
  public Item sample() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    return list[StdRandom.uniform(0, size)];
  }
  /**
   * <p>
   * Method returns an iterator for RandomizedQueue.
   * Each run will produce a list of randomly ordered elements.
   * </p>
   *
   * @return an iterator over items in order from front to end.
   */
  public Iterator<Item> iterator() {
    return new RandomizedIterator(list, size);
  }
  private class RandomizedIterator implements Iterator<Item> {
    private Item[] i;
    private int size;
    RandomizedIterator(Item[] items, int s) {
      size = s - 1;
      i = java.util.Arrays.copyOf(items, s);
      StdRandom.shuffle(i);
    }
    @Override
    public boolean hasNext() {
      return size >= 0;
    }

    @Override
    public Item next() {
      if (!hasNext()) throw new NoSuchElementException("No more elements to iterate on");
      return i[size--];
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
  public static void main(String[] args) {
    // unit testing (optional)
  }
}