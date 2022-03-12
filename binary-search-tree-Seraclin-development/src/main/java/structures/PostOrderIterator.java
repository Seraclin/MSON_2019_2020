package structures;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class PostOrderIterator<T> implements Iterator<T> {

    private final Deque<BinaryTreeNode<T>> stack;

    public PostOrderIterator(BinaryTreeNode<T> root) {
        stack = new LinkedList<BinaryTreeNode<T>>();
        findNextLeaf(root);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public T next() {
        BinaryTreeNode<T> result = stack.pop();

        if (!stack.isEmpty()) {
            BinaryTreeNode<T> top = stack.peek();
            if (top.hasLeftChild() && result == top.getLeftChild() && top.hasRightChild()) {
                findNextLeaf(top.getRightChild());
            }
        }
        return result.getData();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private void findNextLeaf(BinaryTreeNode<T> cur1) {
        BinaryTreeNode<T> cur = cur1;
        while (cur != null) {
            stack.push(cur);
            if (cur.hasLeftChild()) {
                cur = cur.getLeftChild();
            } else if (cur.hasRightChild()) {
                cur = cur.getRightChild();
            } else {
                cur = null;
            }
        }
    }

}
