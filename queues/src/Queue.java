public class Queue<T> {
    private LinkedStack<T> stack1; // the queue
    private LinkedStack<T> stack2; // stack that takes in push() and then pushes into stack1, when needed by top()/pop()

    public Queue() {
        stack1 = new LinkedStack();
        stack2 = new LinkedStack();
    }

    public T pop() {
        if (!stack1.isEmpty()) {
            return stack1.pop();
        } else if (!stack2.isEmpty()) {
            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
            return stack1.pop();
        }
        return null;
    }

    public int size() {
        return stack1.size()+stack2.size();
    }

    public void push(T elem) {
        stack2.push(elem);

    }
    public T top() {
        if (!stack1.isEmpty()) {
            return stack1.top();
        } else if (!stack2.isEmpty()) {
            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
            return stack1.top();
        }
        return null;
    }


    public boolean isEmpty() {
        if (stack1.isEmpty() && stack2.isEmpty()) {
            return true;
        }
        return false;
    }



}
