package structures;

import java.util.Iterator;

public class BinarySearchTreeInterface<T extends Comparable<? super T>> implements BinarySearchTree<T> {
    int size;
    BinaryTreeNode<T> root;

    public BinarySearchTreeInterface() {
        size = 0;
        root = null;

    }

    @Override
    public BinarySearchTree<T> add(T toAdd) {
        if (toAdd == null) {
             throw new NullPointerException();
        }
        BinaryTreeNode<T> temp = new BinaryTreeNodeInterface<T>(null, toAdd, null);
        Comparable<T> comparableElement = (Comparable<T>) toAdd;

        if (isEmpty()) {
            root = temp;
        } else {
            BinaryTreeNode<T> cur = root;
            boolean added = false;
            while (!added) {
                if (comparableElement.compareTo(cur.getData()) < 0) {
                    if (!cur.hasLeftChild()) {
                        cur.setLeftChild(temp);
                        added = true;
                    } else {
                        cur = cur.getLeftChild();
                    }
                } else {
                    if (!cur.hasRightChild()) {
                        cur.setRightChild(temp);
                        added = true;
                    } else {
                        cur = cur.getRightChild();
                    }
                }
            }
        }
        size++;
        return this;
    }

    @Override
    public boolean contains(T toFind) {
        if (toFind == null) {
            throw new NullPointerException();
        }
        if (isEmpty()) {
            return false;
        }
        if (toFind == root) {
            return true;
        }
        return containsHelper(root, toFind);
    }

    public boolean containsHelper(BinaryTreeNode<T> root, T toFind) {
        if (isEmpty()) {
            return false;
        }
        if (root.getData().compareTo(toFind) == 0) {
            return true;
        } else if (root.getData().compareTo(toFind) > 0 && root.hasLeftChild()) {
            return containsHelper(root.getLeftChild(), toFind);
        } else if (root.getData().compareTo(toFind) < 0 && root.hasRightChild()) {
            return containsHelper(root.getRightChild(), toFind);
        }
        return false;
    }

    @Override
    public boolean remove(T toRemove) {
        if (!contains(toRemove))
            return false;
        BinaryTreeNode<T> parent = parentFinder(root, toRemove);
        BinaryTreeNode<T> child = getNode(toRemove);
        size--;
        return removeHelper(parent, child);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T getMinimum() {
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        BinaryTreeNode<T> cur = root;
        T min = root.getData();
        while (cur.hasLeftChild()) {
            min = cur.getLeftChild().getData();
            cur = cur.getLeftChild();
        }
        return min;
    }

    @Override
    public T getMaximum() {
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        BinaryTreeNode<T> cur = root;
        T max = root.getData();
        while (cur.hasRightChild()) {
            max = cur.getRightChild().getData();
            cur = cur.getRightChild();
        }
        return max;
    }

    @Override
    public BinaryTreeNode<T> toBinaryTreeNode() {
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        return root;
    }

    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeUtilityInterface().getInOrderIterator(root);
    }

    // Here starts everything for remove...
    public BinaryTreeNode<T> parentFinder(BinaryTreeNode<T> current, T child) {
        // find parent node of the T that's passed in
        if (!current.hasLeftChild() && !current.hasRightChild() || current == null) {
            return null;
        } else {
            if ((current.hasLeftChild() && current.getLeftChild().getData().compareTo(child) == 0) ||
                    (current.hasRightChild()) && current.getRightChild().getData().compareTo(child) == 0) {
                return current;
            } else {
                if (current.getData().compareTo(child) > 0 && current.hasLeftChild()) {
                    return parentFinder(current.getLeftChild(), child);
                } else if (current.getData().compareTo(child) < 0 && current.hasRightChild()) {
                    return parentFinder(current.getRightChild(), child);
                }
            }
        }
        return null;
    }

    public BinaryTreeNode<T> getNode(T data) {
        // return the node that contains the data
        // it's important to get the node as you need to know if it has any children
        // when you remove it
        if (!contains(data)) {
            return null;
        }
        boolean found = false;
        BinaryTreeNode<T> cur = root;
        while (!found) {
            if (cur.getData().compareTo(data) == 0) {
                found = true;
            } else {
                if (cur.getData().compareTo(data) > 0 && cur.hasLeftChild()) {
                    cur = cur.getLeftChild();
                } else if (cur.getData().compareTo(data) < 0 && cur.hasRightChild()) {
                    cur = cur.getRightChild();
                }
            }
        }
        return cur;
    }

    private boolean removeHelper(BinaryTreeNode<T> parent, BinaryTreeNode<T> toRemove) {
        if (toRemove.hasLeftChild() && toRemove.hasRightChild()) {
            return removeNodeWithTwoChildren(parent, toRemove);
        } else if (!toRemove.hasLeftChild() && !toRemove.hasRightChild()) {
            return removeNodeWithNoChildren(parent, toRemove);
        } else if ((toRemove.hasLeftChild() && !toRemove.hasRightChild()) || (!toRemove.hasLeftChild() && toRemove.hasRightChild())) {
            return removeNodeWithOneChild(parent, toRemove);
        }

        return false;
    }
    private boolean removeNodeWithTwoChildren(BinaryTreeNode<T> parent, BinaryTreeNode<T> toRemove) {
        // To remove a node with two children, we first find the value we are going to
        // replace it with. the replacement is found by first getting the right child of the toRemove node
        // and then traversing as far left on that right child to find the value in the tree that comes after this node.
        // Set the data in the node we are "removing" to be the value of the
        // replacement node.
        // We then have to remove the node which we used as a replacement.
        // Note that it will either be a leaf node with no children or only have a right child (you should utilize the below helper methods to remove it)

        BinaryTreeNode<T> replacementNode = replacement(toRemove);
        T replacementValue = replacementNode.getData();

        BinaryTreeNode<T> parentOfReplacementNode = parentFinder(root, replacementValue);
        boolean leftChild = true;
        if (parent != null && parentOfReplacementNode.hasRightChild() &&
                parentOfReplacementNode.getRightChild().getData().compareTo(replacementNode.getData()) == 0) {
            leftChild = false;
        }
       if (!toRemove.hasLeftChild() && !toRemove.hasRightChild()) {
           removeNodeWithNoChildren(parentOfReplacementNode, replacementNode);
       } else if (!toRemove.hasLeftChild() && toRemove.hasRightChild()) {
           removeNodeWithOneChild(parentOfReplacementNode, replacementNode);
       }
        toRemove.setData(replacementValue);
       return true;
    }
    private BinaryTreeNode<T> replacement (BinaryTreeNode<T> toRemove) {
        BinaryTreeNode<T> cur = toRemove;
        if (toRemove.hasRightChild()) {
            cur = toRemove.getRightChild(); // get the right child of toRemove
        }
        while (cur.hasLeftChild()) { // traverse left on that right child until we find the value.
            cur = cur.getLeftChild();
        }
        return cur;
    }

    private boolean removeNodeWithNoChildren(BinaryTreeNode<T> parent, BinaryTreeNode<T> toRemove) {
        // Figure out if it's the left or right child of the parent and then set it to null
        // if the parent is null, then you must be removing the root so you need to update
        // the root to null
        if (parent == null) {
            root = null;
            return true;
        }
        boolean leftChild = true;
        if (parent.hasRightChild() && parent.getRightChild().getData().compareTo(toRemove.getData()) == 0) {
            leftChild = false;
        }
        if (leftChild) {
           parent.setLeftChild(null);
        } else {
            parent.setRightChild(null);
        }
        return true;
    }

    private boolean removeNodeWithOneChild(BinaryTreeNode<T> parent, BinaryTreeNode<T> toRemove) {
        // Determine which node parent should link to instead of the
        // node we are removing.
        // If toRemove has a left child we will promote its left child otherwise we will promote its right child
        // If the node we are removing is the root node its child becomes the new root node.
        // Set the left or right child to be the thing we are promoting
        if (parent == null) {
            if (root.hasRightChild()) {
                root = root.getRightChild();
            } else if (root.hasLeftChild()) {
                root = root.getLeftChild();
            }
        }
        boolean leftChild = true;
        if (parent != null && parent.hasRightChild() && parent.getRightChild().getData().compareTo(toRemove.getData()) == 0) {
            leftChild = false;
        }
        if (leftChild) {
            if (toRemove.hasLeftChild() && parent != null) {
                parent.setLeftChild(toRemove.getLeftChild());
            } else if (toRemove.hasRightChild() && parent != null) {
                parent.setLeftChild(toRemove.getRightChild());
            }
        } else {
            if (toRemove.hasLeftChild()) {
                parent.setRightChild(toRemove.getLeftChild());
            } else if (toRemove.hasRightChild()) {
                parent.setRightChild(toRemove.getRightChild());
            }
        }
        return true;
    }
}