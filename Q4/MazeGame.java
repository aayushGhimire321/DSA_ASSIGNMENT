// You are given a 2D grid representing a maze in a virtual game world. The grid is of size m x n and consists of
// different types of cells:
// 'P' represents an empty path where you can move freely. 'W' represents a wall that you cannot pass through. 'S'
// represents the starting point. Lowercase letters represent hidden keys. Uppercase letters represent locked doors.
// You start at the starting point 'S' and can move in any of the four cardinal directions (up, down, left, right) to
// adjacent cells. However, you cannot walk through walls ('W').
// As you explore the maze, you may come across hidden keys represented by lowercase letters. To unlock a door
// represented by an uppercase letter, you need to collect the corresponding key first. Once you have a key, you can
// pass through the corresponding locked door.
// For some 1 <= k <= 6, there is exactly one lowercase and one uppercase letter of the first k letters of the English
// alphabet in the maze. This means that there is exactly one key for each door, and one door for each key. The letters
// used to represent the keys and doors follow the English alphabet order.
// Your task is to find the minimum number of moves required to collect all the keys and reach the exit point. The
// exit point is represented by 'E'. If it is impossible to collect all the keys and reach the exit, return -1.
// Example:
// Input: grid = [ ["S","P","P","P"], ["W","P","P","E"], ["P","b","W","P"], ["P","P","P","P"] ]
// Input: grid = ["SPaPP","WWWPW","bPAPB"]
// Output: 8
// The goal is to Collect all key 

package Q4;

import java.util.LinkedList;
import java.util.Queue;

public class MazeGame {

    public int minStepsToCollectKeys(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int totalKeys = 0;

        int[] start = null;

        // Initialize visited array to keep track of visited states
        boolean[][][] visited = new boolean[m][n][64]; // 64 is 2^6 representing all possible key combinations

        // Find the starting point and count the total number of keys
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char cell = grid[i][j];
                if (cell == 'S') {
                    start = new int[]{i, j, 0}; // Initial state with no keys
                } else if (cell >= 'a' && cell <= 'f') {
                    totalKeys++;
                }
            }
        }

        // If there are no keys, return 0 (already at the exit)
        if (totalKeys == 0) {
            return 0;
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(start);
        visited[start[0]][start[1]][start[2]] = true;

        int[] directions = {-1, 0, 1, 0, -1};

        int steps = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] current = queue.poll();

                for (int k = 0; k < 4; k++) {
                    int ni = current[0] + directions[k];
                    int nj = current[1] + directions[k + 1];

                    // Check if the new position is within bounds
                    if (ni >= 0 && ni < m && nj >= 0 && nj < n) {
                        char cell = grid[ni][nj];

                        if (cell == 'E' && current[2] == (1 << totalKeys) - 1) {
                            return steps; // Reached the exit with all keys collected
                        }

                        if (cell == 'P' || cell == 'S' || (cell >= 'a' && cell <= 'f')) {
                            int keyState = current[2];

                            if (cell >= 'a' && cell <= 'f') {
                                keyState |= 1 << (cell - 'a'); // Collect the key
                            }

                            if (!visited[ni][nj][keyState]) {
                                queue.offer(new int[]{ni, nj, keyState});
                                visited[ni][nj][keyState] = true;
                            }
                        } else if (cell >= 'A' && cell <= 'F') {
                            int requiredKey = 1 << (cell - 'A');

                            if ((current[2] & requiredKey) != 0 && !visited[ni][nj][current[2]]) {
                                queue.offer(new int[]{ni, nj, current[2]});
                                visited[ni][nj][current[2]] = true;
                            }
                        }
                    }
                }
            }

            steps++;
        }

        return -1; // No solution found
    }

    public static void main(String[] args) {
        MazeGame mazeGame = new MazeGame();
    
        char[][] grid1 = {
                {'S', 'P', 'P', 'P'},
                {'W', 'P', 'P', 'E'},
                {'P', 'b', 'W', 'P'},
                {'P', 'P', 'P', 'P'}
        };
    
        char[][] grid2 = {
                {'S', 'P', 'a', 'P', 'P'},
                {'W', 'W', 'W', 'P', 'W'},
                {'b', 'P', 'A', 'P', 'B'}
        };
    
        System.out.println(mazeGame.minStepsToCollectKeys(grid1)); // Output: 8
        System.out.println(mazeGame.minStepsToCollectKeys(grid2)); // Output: -1 (No solution)
    }
}
