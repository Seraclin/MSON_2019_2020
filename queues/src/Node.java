
public class Node<T> {
    private Node<T> next;
    private T element;
    public Node() {
        next = null;
        element = null;
    }

    public Node (T elem) {
        next = null;
        element = elem;
    }
    public Node getNext() {
        return next;
    }
    public void setNext(Node node) {
        next = node;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T elem) {
        element = elem;
    }
}