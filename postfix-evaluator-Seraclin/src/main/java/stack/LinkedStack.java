package stack;

/**
 * A {@link LinkedStack} is a stack that is implemented using a Linked List structure
 * to allow for unbounded size.
 *
 * @param <T> the elements stored in the stack
 */
public class LinkedStack<T> implements StackInterface<T> {

  /**
   * {@inheritDoc}.
   */
  private int count;
  private Node<T> top;
  public LinkedStack() {
    count = 0;
    top = null;
  }
  @Override
  public T pop() throws StackUnderflowException {
    if (isEmpty())
      throw new StackUnderflowException("Stack");
    T result = top.getElement();
    top = top.getNext();
    count--;

    return result;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public T top() throws StackUnderflowException {
    if (isEmpty()) {
      throw new StackUnderflowException("Stack");
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
  public void push(T elem) {
    Node<T> temp = new Node<T>(elem);
    temp.setNext(top);
    top = temp;
    count++;

  }

}
