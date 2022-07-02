
import java.util.*;

public class FibonacciPartialSum {

    private static long getFibonacciPartialSumNaive(long m, long n) {
        return (10 + getFibonacciSumNaive(n) - getFibonacciSumNaive(m - 1)) % 10;
    }
    
    private static long getFibonacciSumNaive(long n) {
        if (n <= 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }

        long previous = 0;
        long current = 1;
        long sum = 1;
        
        long i = 1;
        while (i < n) {
            long temp = previous;
            previous = current;
            current = (current + temp) % 10;
            
            
            if (current == 1 && previous == 0) {
                break;
            }
            i += 1;
            sum = (sum + current) % 10;
        }

        if (i == n) {
            return sum;
        } else {
            return (sum * (n/i) + getFibonacciSumNaive(n % i)) % 10;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long from = scanner.nextLong();
        long to = scanner.nextLong();
        System.out.println(getFibonacciPartialSumNaive(from, to));
    }
}
