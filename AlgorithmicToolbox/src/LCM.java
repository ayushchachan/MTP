
import java.util.*;

public class LCM {

    private static long lcm_naive(int a, int b) {
        int g = gcd_naive(a, b);

        if (g == 1) {
            return (long) a * b;
        } else {
            return g * lcm_naive(a / g, b / g);
        }

    }

    private static int gcd_naive(int a, int b) {
        int x = a % b;

        if (x == 0) {
            return b;
        } else {
            return gcd_naive(b, x);
        }
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        System.out.println(lcm_naive(a, b));
    }
}
