package structures;

import java.util.EmptyStackException;

// work on test cases, implementation, toString
public class LinkedQueue<T> implements QueueInterface<T> {    // this implements QueueInterface
    private int count;
    private Node<T> front,rear;

    public LinkedQueue() {
        count = 0;
        front = rear = null;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        T result = front.getElement();
        front = front.getNext();
        count--;
        if (isEmpty()) {
            rear = null;
        }
        return result;
    }

    @Override
    public T peek() {
        return front.getElement();
    }

    @Override
    public QueueInterface<T> enqueue(T elem) {
        if (elem == null) {
            throw new NullPointerException();
        }
        Node nod = new Node<T>(elem);
        if (isEmpty()) {
            front = nod;
        } else {
            rear.setNext(nod);
        }
        rear = nod;
        count++;
        return this;
    }

    @Override
    public boolean isEmpty() {
        if (count == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder myString = new StringBuilder(); // utilize string builder
        myString.append("[");
        Node<T> next = front;
        myString.append(front.getElement());
        next = next.getNext();
        while (next != null) {
            myString.append(", ").append(next.getElement());
            next = next.getNext();

        }
        myString.append("]");
        return myString.toString();
    }


}
