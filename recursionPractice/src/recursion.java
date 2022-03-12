public class recursion
{
    public static void main(String[] args) {
        /* Recursion is calling a method within itself.
        Recursion is like for/while loops. They take O (n) time depending on the operation.
        Mainly used: fibonacci numbers (too slow in contests), tower of Hanoi, DFS graph, tree traversals, knapsack (not dynamic).
            - You would need to use a memo table to keep track of numbers.
        Recursion is simplifying. You need base case(s) and make it return something that simplifies itself.

         */
        System.out.println(recurse1(3)); // should be 6
        System.out.println(recurse1(0)); // should be 0

        System.out.println(fibonacci(0)); // should be 0
        System.out.println(fibonacci(1)); // should be 1
        System.out.println(fibonacci(5)); // should return 5 (0,1,1,2,3,5)

        System.out.println(strCount("geckogeckohamstergecko", "gecko")); // should return 3
        System.out.println(strCount("aaabaabaaaa", "aa")); // should return 4
        System.out.println(strCount("bbccddd", "aa")); // should return 0


    }

    public static int recurse1 (int bunnies) { // find how many ears bunnies have if they have two ears each
        if (bunnies == 0) { // base case
            return 0;
        }
        return recurse1 (bunnies-1)+2; // returns number of bunnies, adding two ears each time, and decrementing by 1
    }
    public static int fibonacci (int n) { // returns nth fibo number; fibo is where we begin with 0 , 1
        // we then add the previous term to in order to make the term (0,1,1,2,3,5,8...)
        if (n == 0) { // base case #1
            return 0;
        }
        if (n == 1) { //base case #2
            return 1;
        }
        return fibonacci(n-1)+fibonacci(n-2);
    }
    public static int strCount(String str, String sub) { // return number of instances String sub is in String str
        if (str.length() < sub.length()) {
            return 0;
        }

        if ((str.substring(0,sub.length())).equals(sub)) {
            return strCount(str.substring(sub.length()), sub) + 1;
        }
        return strCount(str.substring(1), sub);
    }
}
