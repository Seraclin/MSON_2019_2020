package structure;

public class RecursiveLinkedList<T> implements ListInterface<T> {
    private int count;
    private Node<T> head, tail;
    public RecursiveLinkedList() {
        count = 0;
        head = tail = null;
    }
    @Override
    public int size() {
        return count;
    }

    @Override
    public ListInterface<T> insertFirst(T elem) {
        Node<T> temp = new Node<T>(elem);
        temp.setNext(head);
        head = temp;
        if (count == 0) {
            tail = temp;
        }
        count++;
        return this;
    }

    @Override
    public ListInterface<T> insertLast(T elem) {
        if (elem == null) {
            throw new NullPointerException();
        }
        Node<T> insertionNode = new Node<>(elem);
        insertionNode.setNext(null);
        if (count == 0) {
            head = insertionNode;
        } else {
            Node<T> end = get(head, 0, count - 1);
            end.setNext(insertionNode);
        }
        tail = insertionNode;
        count++;
        return this;
    }

    @Override
    public ListInterface<T> insertAt(int index, T elem) { // inserting at the wrong spot
        Node insertion = new Node<>(elem); // the new node we inserting
        if (index < 0 || (index > size())) {
            throw new IndexOutOfBoundsException();
        }
        if (elem == null) {
            throw new NullPointerException();
        } // a - b - c 1 >> a - d- b - c
        if (index == 0) {
            insertFirst(elem);
            return this;
        }
        if (index == count) {
            // why is this not working
            insertLast(elem);
            return this;
        }

        Node prev = traverse(head, head, 0, index); // find the node before the spot we want to insert
        Node occupying = prev.getNext(); // node occupying the spot
        insertion.setNext(occupying); // the new node has a next of the old one
        prev.setNext(insertion); // the previous node's next is the occupying node

        count++;

        return this;
    }

    @Override
    public T removeFirst() { // like poll
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        T temp = head.getElement();
        head = head.getNext(); // sets the node head to next one
        count--;

        return temp;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        if (count == 1) { // if there' s only one element
            T temp = head.getElement();
            head = tail = null;
            count--;
            return temp;
        }
        Node<T> previous = get(head, 0, count - 2); // the node one before the end
        Node<T> toRemove = previous.getNext();
        T temp = toRemove.getElement();
        previous.setNext(null); // nothing at the end now
        tail = previous; // the end is the one before it
        count--;
        return temp;
    }

    @Override
    public T removeAt(int index) { // TODO
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        } // a - b - c removing b >> a - c
        if (index == 0) {
            removeFirst();
        }
        Node<T> previous = traverse(head, head, 0, index); // the node before the one we want
        if (index == count - 1) {
            tail = previous;
        }
        Node<T> toRemove = previous.getNext(); // the one to remove
        T temp = toRemove.getElement();
        previous.setNext(toRemove.getNext()); // oneafter is now just after
        count--;
        return temp;
    }

    @Override
    public T getFirst() { // like peek
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        return head.getElement();
    }

    @Override
    public T getLast() { // This is okay, but insertion is messing up
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        if (count == 1) {
            return head.getElement();
        }
        return get(head, 0, count - 1).getElement();
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        if (index == 0) {
            return getFirst();
        }
        if (index == count - 1) {
            return getLast();
        }
        return get(head, 0, index).getElement();
    }

    @Override
    public boolean remove(T elem) { // TODO fix -1
        if (isEmpty()) {
            throw new IllegalStateException();
        }

        int index = contains(elem);
        if (index == -1) {
            return false;
        }
        if (index == 0) {
            removeFirst();
            return  true;
        }
        removeAt(index);
        return true;
    }

    @Override
    public int contains(T elem) { // private method- send in the elem to find into contains
        return contains(elem, head, 0); // start from the head
    }

    @Override
    public boolean isEmpty() {
        if (count == 0) {
            return true;
        }
        return false;
    }
    // Thus here, the helper methods for recursion begin...
    private int contains(T toFind, Node<T> toCheck, int currentIndex) {
        if (currentIndex >= this.size()) { // end of the list and didn't find it
            return -1;
        }
        if (toCheck.getElement().equals(toFind)) { // we find it
            return currentIndex;
        }
        return contains(toFind, toCheck.getNext(), currentIndex + 1);
    }
    private Node<T> get(Node<T> toCheck, int curIndex, int indexToFind) { // returns the node we want
        if (curIndex == indexToFind) {
            return toCheck;
        }
        return get(toCheck.getNext(), curIndex + 1, indexToFind);
    }
    private Node<T> traverse(Node<T> toCheck, Node<T> prev, int curIndex, int indexToFind) { // returns node before we want
        if (curIndex == indexToFind) { // we find the 1st instance, return the one before
            return prev;
        }
        Node<T> prev2 = toCheck;
        return traverse(toCheck.getNext(), prev2, curIndex + 1, indexToFind);
    }
    private Node<T> remove(Node<T> toCheck, Node<T> prev, int curIndex, T elemToFind) {
        if (curIndex >= count) {
            return null;
        }
        if (toCheck.getElement().equals(elemToFind)) { // we find the 1st instance, return the one before
            return prev;
        }
        Node<T> prev2 = toCheck;
        return remove(toCheck.getNext(), prev2, curIndex + 1, elemToFind);
    }
}
