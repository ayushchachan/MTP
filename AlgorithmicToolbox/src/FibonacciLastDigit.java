
import java.util.*;

public class FibonacciLastDigit {

    private static int getFibonacciLastDigitNaive(int n) {
        if (n <= 1) {
            return n;
        }

        int previous = 0;
        int current = 1;

        for (int i = 0; i < n - 1; ++i) {
            int tmp_previous = previous;
            previous = current;
            current = (tmp_previous + current) % 10;
        }

        return current;
    }

    public static void main(String[] args) {

        for (int j = 0; j < 100; j++) {
            System.out.println("fib(" + j + ") mod 10 = " + getFibonacciLastDigitNaive(j));
        }
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int c = getFibonacciLastDigitNaive(n);
        System.out.println(c);
    }
}
