import java.util.*;

public class FibonacciSumSquares {
    private static long getFibonacciSumSquaresNaive(long n) {
        if (n <= 1) {
            return n;
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
            sum = (sum + current*current) % 10;
        }

        if (i == n) {
            return sum;
        } else {
            return (sum * (n/i) + getFibonacciSumSquaresNaive(n % i)) % 10;
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long s = getFibonacciSumSquaresNaive(n);
        System.out.println(s);
    }
}

