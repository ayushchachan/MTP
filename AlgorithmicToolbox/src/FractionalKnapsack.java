
import java.util.Scanner;

public class FractionalKnapsack {

    private static double getOptimalValue(int capacity, int[] values, int[] weights) {
        double value = 0;
        int num_inspect = 0;

        double[] val_per_wt = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            val_per_wt[i] = ((double) values[i]) / weights[i];
        }

        int cap_left = capacity;

        while (num_inspect < values.length && cap_left > 0) {
            int j = findMaxIndex(val_per_wt);

            if (cap_left >= weights[j]) {
                cap_left -= weights[j];
                value += values[j];
                val_per_wt[j] = 0;
            } else {
                value += cap_left * val_per_wt[j];
                cap_left = 0;
            }
            num_inspect++;
        }
        return value;
    }

    public static void insertionSort(double[] A) {
        for (int i = 1; i < A.length; i++) {

            for (int j = i - 1; j >= 0; j--) {
                if (A[j] > A[j + 1]) {          // swap A[i] <--> A[j]
                    double temp = A[j + 1];
                    A[j + 1] = A[j];
                    A[j] = temp;
                } else {
                    break;
                }
            }
        }
    }

    public static int findMaxIndex(double[] A) {
        double max = A[0];
        int max_index = 0;
        for (int i = 1; i < A.length; i++) {
            if (A[i] > max) {
                max = A[i];
                max_index = i;
            }
        }
        return max_index;
    }

    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        int capacity = scanner.nextInt();
        int[] values = new int[n];
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();
            weights[i] = scanner.nextInt();
        }
        System.out.println(getOptimalValue(capacity, values, weights));
    }
}
