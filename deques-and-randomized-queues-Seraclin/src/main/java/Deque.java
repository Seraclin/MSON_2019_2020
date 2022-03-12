/******************************************************************************
 *  Name:    Samantha Lin
 *  Compilation:  javac-algs4 Deque.java
 *  Execution:    java-algs4 Deque
 *  Dependencies: Item.java Iterator.java
 *
 *  Description:  Implementing a Deque using linked structures. Ok.
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int count;
    private LinearNode<Item> front, rear;

    private class LinearNode<Item> {
        private LinearNode<Item> next;
        private LinearNode<Item> previous;
        private Item element;

        public LinearNode() {
            next = null;
            element = null;
            previous = null;
        }

        public LinearNode(Item elem) {
            next = null;
            previous = null;
            element = elem;
        }

        public LinearNode<Item> getNext() {
            return next;
        }

        public LinearNode<Item> getPrevious() {
            return previous;
        }

        public void setNext(LinearNode<Item> node) {
            next = node;
        }

        public void setPrevious(LinearNode<Item> node) {
            previous = node;
        }


        public Item getElement() {
            return element;
        }

        public void setElement(Item elem) {
            element = elem;
        }
    }

    public Deque() {
        count = 0;
        front = null;
        rear = null;
    }

    /**
     * Checks if the deque is empty.
     *
     * @return whether the deque contains any elements.
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Output the number of elements in the deque.
     *
     * @return the number of elements in the deque.
     */
    public int size() {
        return count;
    }

    /**
     * Insert an element at the front of the deque.
     *
     * @param item to insert.
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        LinearNode<Item> temp = new LinearNode<Item>(item);
        temp.setPrevious(null); // make parent null
        if (front != null) {
            temp.setNext(front); // make the insertion have the front as child
            front.setPrevious(temp); // make the front have the insertion as parent
        }
        front = temp; // make the insertion the new front
        if (count == 0) {
            rear = temp;
        }
        count++;
    }

    /**
     * Insert an element at the end of the deque.
     *
     * @param item to insert.
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        LinearNode<Item> temp = new LinearNode<Item>(item);
        temp.setNext(null); // set the insertion with child as null
        if (rear != null) {
            temp.setPrevious(rear); // set the insertion to have current rear as parent
            rear.setNext(temp); // set the current rear to have insertion as child
        }
        rear = temp; // set the rear to be the insertion
        if (count == 0) {
            front = temp;
        }
        count++;
    }

    /**
     * Remove an element from the front of the deque.
     *
     * @return the element that was removed.
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (count == 1) { // if there' s only one element
            LinearNode<Item> temp = front;
            front = null;
            rear = null;
            count--;
            return temp.getElement();
        }
        LinearNode<Item> ret = front; // this gets the current front
        front = ret.getNext(); // make the child node the head
        front.setPrevious(null); // set the child's parent to be null

        count--;
        return ret.getElement(); // return the item from the front
    }

    /**
     * Remove an element from the end of the deque.
     *
     * @return the element that was removed.
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (count == 1) { // if there' s only one element
            LinearNode<Item> temp = front;
            front = null;
            rear = null;
            count--;
            return temp.getElement();
        }
        LinearNode<Item> ret = rear; // get the current rear
        rear = ret.getPrevious(); // make the rear the parent of the current one
        rear.setNext(null); // set the child to be null
        count--;
        return ret.getElement(); // return the item from the end
    }

    /**
     * <p>
     * Method returns an iterator for Deque.
     * </p>
     *
     * @return an iterator over items in order from front to end.
     */
    public Iterator<Item> iterator() {
        return new DequeIterator(front);
    }

    private class DequeIterator implements Iterator<Item> {
        private LinearNode<Item> cur;

        public DequeIterator(LinearNode<Item> root) {
            cur = root;
        }

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item temp = cur.getElement();
            cur = cur.getNext();
            return temp;
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