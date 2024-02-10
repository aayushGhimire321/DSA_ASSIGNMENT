// You are the manager of a clothing manufacturing factory with a production line of super sewing machines. The
// production line consists of n super sewing machines placed in a line. Initially, each sewing machine has a certain
// number of dresses or is empty.
// For each move, you can select any m (1 <= m <= n) consecutive sewing machines on the production line and pass
// one dress from each selected sewing machine to its adjacent sewing machine simultaneously.
// Your goal is to equalize the number of dresses in all the sewing machines on the production line. You need to
// determine the minimum number of moves required to achieve this goal. If it is not possible to equalize the number
// of dresses, return -1.
// Input: [2, 1, 3, 0, 2]
// Output: 5
// Example 1:
// Imagine you have a production line with the following number of dresses in each sewing machine: [2, 1, 3, 0, 2].
// The production line has 5 sewing machines.
// Here's how the process works:
// 1. Initial state: [2, 1, 3, 0, 2]
// 2. Move 1: Pass one dress from the second sewing machine to the first sewing machine, resulting in [2, 2, 2,
// 0, 2]
// 3. Move 2: Pass one dress from the second sewing machine to the first sewing machine, resulting in [3, 1, 2,
// 0, 2]
// 4. Move 3: Pass one dress from the third sewing machine to the second sewing machine, resulting in [3, 2, 1,
// 0, 2]
// 5. Move 4: Pass one dress from the third sewing machine to the second sewing machine, resulting in [3, 3, 0,
// 0, 2]
// 6. Move 5: Pass one dress from the fourth sewing machine to the third sewing machine, resulting in [3, 3, 1,
// 0, 1]
// After these 5 moves, the number of dresses in each sewing machine is equalized to 1. Therefore, the minimum
// number of moves required to equalize the number of dresses is 5.



package Q2;
public class MinMovesToEqualizeDresses {

    public static int minMovesToEqualize(int[] machines) {
        int totalDresses = 0;
        int n = machines.length;

        for (int machine : machines) {
            totalDresses += machine;
        }

        if (totalDresses % n != 0) {
            // If the total number of dresses cannot be equally distributed among all machines, return -1
            return -1;
        }

        int averageDresses = totalDresses / n;
        int moves = 0;
        int dressesToMove = 0;

        for (int i = 0; i < n; i++) {
            // Calculate the dresses needed to equalize the current machine
            dressesToMove += machines[i] - averageDresses;

            // Update the maximum moves needed so far
            moves = Math.max(moves, Math.max(Math.abs(dressesToMove), machines[i] - averageDresses));

            // Update the dresses needed to equalize the next machine
            if (i < n - 1) {
                machines[i + 1] += dressesToMove;
            }
        }

        return moves;
    }

    public static void main(String[] args) {
        int[] machines = {2, 1, 3, 0, 2};
        System.out.println(minMovesToEqualize(machines)); // Output: 5
    }
}


