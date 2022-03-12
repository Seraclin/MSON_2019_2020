package structures;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class InOrderIterator<T> implements Iterator<T> {

    private final Deque<BinaryTreeNode<T>> stack;
    private BinaryTreeNode<T> curNode;

    public InOrderIterator(BinaryTreeNode<T> root) {
        stack = new LinkedList<BinaryTreeNode<T>>();
        curNode = root;
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty() || curNode != null;
    }

    @Override
    public T next() {
        while (curNode != null && curNode.hasLeftChild()) {
            stack.push(curNode);
            curNode = curNode.getLeftChild();
        }
        T result = null;
        if (curNode == null) {
            curNode = stack.pop();
        }
        result = curNode.getData();

        if (curNode.hasRightChild()) {
            curNode = curNode.getRightChild();
        } else {
            curNode = null;
        }

        return result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
