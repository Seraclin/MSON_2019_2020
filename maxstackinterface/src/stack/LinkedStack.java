package stack;

public class LinkedStack<Integer> implements maxStackInterface<Integer> {
    private int count;
    private Node top;
    private final maxStackInterface<Integer> stack;

    public LinkedStack(maxStackInterface<Integer> stack) {
        this.stack = stack;
        count = 0;
        top = null;
    }

    public LinkedStack() {
        this.stack = null;
        count = 0;
        top = null;
    }

    @Override
    public int pop() {
        if (isEmpty())
            throw new IllegalStateException("Stack");
        int result = top.getElement();
        top = top.getNext();
        count--;

        return result;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int top() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack");
        }
        return top.getElement();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int size() {

        return count;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void push(int elem) {
        Node temp = new Node(elem);
        temp.setNext(top);
        top = temp;
        count++;

    }

    @Override
    public int max() {
        stack.push(0);
        int temp = stack.top();
        if (temp <= top.getElement()) {
            stack.push(top.getElement());
        }
        return temp;
    }
}
