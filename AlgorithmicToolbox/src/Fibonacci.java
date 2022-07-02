
import java.util.Scanner;

public class Fibonacci {

    private static long calc_fib(int n) {
        if (n <= 1) {
            return n;
        }
        long previous = 0;
        long current = 1;

        for (int j = 1; j < n; j++) {
            long temp = previous;
            previous = current;
            current = temp + current;
        }
        return current;
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        System.out.println(calc_fib(n));
    }
}
