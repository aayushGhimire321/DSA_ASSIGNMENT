package Q2;

import java.util.HashSet;
import java.util.Set;

public class SecretSharing {
    public static Set<Integer> secretSharing(int n, int[][] intervals, int firstPerson) {
        Set<Integer> knownSet = new HashSet<>();
        knownSet.add(firstPerson);

        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];

            for (int person = start; person <= end; person++) {
                knownSet.add(person);
            }
        }

        return knownSet;
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}};
        int firstPerson = 0;

        Set<Integer> knownSet = secretSharing(n, intervals, firstPerson);
        System.out.println(knownSet); // Output: [0, 1, 2, 3, 4]
    }
}
