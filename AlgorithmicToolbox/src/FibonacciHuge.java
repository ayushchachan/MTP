
import java.util.*;

public class FibonacciHuge {

    private static long getFibonacciHugeNaive(long n, long m) {
        if (n <= 1) {
            return n;
        }

        long previous = 0;
        long current = 1;

        long i = 1;
        while (i < n) {
            long temp = previous;
            previous = current;
            current = (current + temp) % m;

            if (current == 1 && previous == 0) {
                break;
            }
            i += 1;
        }

        if (i == n) {
            return current;
        } else {
            return getFibonacciHugeNaive(n % i, m);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long m = scanner.nextLong();
        System.out.println(getFibonacciHugeNaive(n, m));
    }
}
