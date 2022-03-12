package structures;

import java.util.Iterator;

public class BinaryTreeUtilityInterface implements BinaryTreeUtility {
    @Override
    public <T> Iterator<T> getPreOrderIterator(BinaryTreeNode<T> root) {
        if (root == null) {
            throw new NullPointerException();
        }
        return new PreOrderIterator<>(root);
    }

    @Override
    public <T> Iterator<T> getInOrderIterator(BinaryTreeNode<T> root) {
        if (root == null) {
            throw new NullPointerException();
        }
        return new InOrderIterator<>(root);
    }

    @Override
    public <T> Iterator<T> getPostOrderIterator(BinaryTreeNode<T> root) {
        if (root == null) {
            throw new NullPointerException();
        }
        return new PostOrderIterator<>(root);
    }

    @Override
    public <T> int getDepth(BinaryTreeNode<T> root) { // Root= 0, child of root = 1...
        if (root == null) {
            throw new NullPointerException("Root can't be null");
        }
        int getDepthL = 0;
        int getDepthR = 0;
        if (!root.hasLeftChild() && !root.hasRightChild()) {
            return 0;
        } else {
            if (root.hasLeftChild()) {
                getDepthL = getDepth(root.getLeftChild()) + 1;
            }
            if (root.hasRightChild()) {
                getDepthR = getDepth(root.getRightChild()) + 1;
            }
        }
        if (getDepthL > getDepthR) {
            return getDepthL;
        } else {
            return getDepthR;
        }
    }

    @Override
    public <T> boolean isBalanced(BinaryTreeNode<T> root, int tolerance) {
        if (root == null) {
            throw new NullPointerException();
        }
        if (tolerance < 0) {
            throw new IllegalArgumentException();
        }
        if (!root.hasLeftChild() && !root.hasRightChild()) {
            return true;
        }
        if (root.hasLeftChild() && root.hasRightChild() && Math.abs(getDepth(root.getLeftChild()) - getDepth(root.getRightChild())) <= tolerance) {
            return isBalanced(root.getLeftChild(), tolerance) && isBalanced(root.getRightChild(), tolerance);
        } else if (root.hasLeftChild() && !root.hasRightChild()  && (Math.abs(getDepth(root.getLeftChild())) + 1) <= tolerance) {
            return isBalanced(root.getLeftChild(),  tolerance);
        } else if (root.hasRightChild() && !root.hasLeftChild() && (Math.abs(getDepth(root.getRightChild())) + 1) <= tolerance) {
            return isBalanced(root.getRightChild(), tolerance);
        }
        return false;
    }

    @Override
    public <T extends Comparable<? super T>> boolean isBST(BinaryTreeNode<T> root) {
        if (root == null) {
            throw new NullPointerException();
        }
        return helperBST(root, null, null);
    }
    public<T extends Comparable<? super T>> boolean helperBST(BinaryTreeNode<T> root, T min, T max) {
        if (root == null) {
            throw new NullPointerException();
        }
        if (min != null && root.getData().compareTo(min) < 0) {
            return false;
        }
        if (max != null && root.getData().compareTo(max) >= 0) {
            return false;
        }
        boolean leftBST = !root.hasLeftChild() || helperBST(root.getLeftChild(), min, root.getData());
        boolean rightBST = !root.hasRightChild() || helperBST(root.getRightChild(), root.getData(), max);

        return leftBST && rightBST;
    }
}
