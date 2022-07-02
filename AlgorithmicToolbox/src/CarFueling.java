
import java.util.*;

public class CarFueling {

    static int computeMinRefills(int dist, int tank, int[] stops) {
        int count_fill = 0;

        int current_pos = 0;
        int i = 0;
        int current_tank_fuel = tank;

        while (i < stops.length) {
//            System.out.println("current_pos = " + current_pos);
//            System.out.println("count_fill = " + count_fill);
//            System.out.println("current_tank_fuel = " + current_tank_fuel);
            if (stops[i] - current_pos > tank) {
                return -1;
            }

            if (stops[i] - current_pos > current_tank_fuel) {
                current_tank_fuel = tank;
                count_fill++;
            } else {
                current_tank_fuel -= stops[i] - current_pos;
                current_pos = stops[i];
                i++;
            }
        }
//        System.out.println("current_pos = " + current_pos);
//        System.out.println("count_fill = " + count_fill);
        if (dist - current_pos > tank) {
            return -1;
        } else if (dist - current_pos > current_tank_fuel) {
            current_tank_fuel = tank;
            count_fill++;
        }
        return count_fill;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dist = scanner.nextInt();
        int tank = scanner.nextInt();
        int n = scanner.nextInt();
        int stops[] = new int[n];
        for (int i = 0; i < n; i++) {
            stops[i] = scanner.nextInt();
        }

        System.out.println(computeMinRefills(dist, tank, stops));
    }
}
