
/**
 * A {@link LinkedStack} is a stack that is implemented using a Linked List structure
 * to allow for unbounded size.
 *
 * @param <T> the elements stored in the stack
 */
public class LinkedStack<T> {

    private int count;
    private Node<T> top;
    public LinkedStack() {
        count = 0;
        top = null;
    }
    public T pop() {
        T result = top.getElement();
        top = top.getNext();
        count--;

        return result;
    }

    public T top() {
        return top.getElement();
    }

    public boolean isEmpty() {
        return count == 0;
    }


    public int size() {

        return count;
    }

    public void push(T elem) {
        Node<T> temp = new Node<T>(elem);
        temp.setNext(top);
        top = temp;
        count++;

    }

}
