public class Main {
    public static void main(String[] args) {
        Queue q = new Queue();
        q.push(1);
        q.push(2);
        q.push(3);
        q.push(4);
        System.out.println("Size: "+ q.size());
        System.out.println("Top: "+ q.top());

        for (int i = 0 ; i < 4; i++)
        {
            System.out.println(q.pop());
        }
    }
}
