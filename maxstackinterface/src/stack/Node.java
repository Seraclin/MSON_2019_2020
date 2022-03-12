package stack;

public class Node {
    Node next;
    Integer element;
    public Node() {
        next = null;
        element = null;
    }

    public Node(Integer elem) {
        next = null;
        element = elem;
    }

    public Node getNext() {
        return next;
    }
    public void setNext(Node node) {
        next = node;
    }

    public Integer getElement() {
        return element;
    }

    public void setElement(Integer elem) {
        element = elem;
    }


}
