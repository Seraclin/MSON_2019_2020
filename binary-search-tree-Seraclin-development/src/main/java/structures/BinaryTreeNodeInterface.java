package structures;

public class BinaryTreeNodeInterface<T> implements BinaryTreeNode<T> {
    private BinaryTreeNode<T> leftChild;
    private BinaryTreeNode<T> rightChild;
    private T dataNode;

    public BinaryTreeNodeInterface(BinaryTreeNode<T> l1, T elem, BinaryTreeNode<T> r1) {
        leftChild = l1;
        rightChild = r1;
        dataNode = elem;
    }
    @Override
    public T getData() {
        return dataNode;
    }

    @Override
    public void setData(T data) {
        if (data == null) {
            throw new NullPointerException();
        }
        dataNode = data;
    }

    @Override
    public boolean hasLeftChild() {
        if (leftChild == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasRightChild() {
        if (rightChild == null) {
            return false;
        }
        return true;
    }

    @Override
    public BinaryTreeNode<T> getLeftChild() {
        if (leftChild == null) {
            throw new IllegalStateException();
        }
        return leftChild;
    }

    @Override
    public BinaryTreeNode<T> getRightChild() {
        if (rightChild == null) {
            throw new IllegalStateException();
        }
        return rightChild;
    }

    @Override
    public void setLeftChild(BinaryTreeNode<T> left) {
        leftChild = left;
    }

    @Override
    public void setRightChild(BinaryTreeNode<T> right) {
        rightChild = right;
    }
}
