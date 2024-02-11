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

import java.util.*;

class Solution {
    public int minMovesToCollectKeys(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int keyCount = 0;

        Set<Character> keys = new HashSet<>();
        Set<String> visited = new HashSet<>();

        // Find the initial position and count the number of keys
        int startX = -1, startY = -1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    startX = i;
                    startY = j;
                } else if (Character.isLowerCase(grid[i][j])) {
                    keyCount++;
                }
            }
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startX, startY, 0, 0}); // x, y, steps, collectedKeys

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0];
            int y = curr[1];
            int steps = curr[2];
            int collectedKeys = curr[3];

            if (Character.isLowerCase(grid[x][y]) && !keys.contains(grid[x][y])) {
                keys.add(grid[x][y]);
                collectedKeys |= 1 << (grid[x][y] - 'a'); // Mark the key as collected
            }

            if (keys.size() == keyCount) {
                return steps;
            }

            for (int[] dir : dirs) {
                int nx = x + dir[0];
                int ny = y + dir[1];

                if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] != 'W') {
                    if (!visited.contains(nx + "-" + ny + "-" + collectedKeys) && (Character.isLowerCase(grid[nx][ny]) || grid[nx][ny] == 'P' || keys.contains(Character.toLowerCase(grid[nx][ny])))) {
                        visited.add(nx + "-" + ny + "-" + collectedKeys);
                        queue.offer(new int[]{nx, ny, steps + 1, collectedKeys});
                    }
                }
            }
        }

        return -1; // Impossible to collect all keys
    }
}

public class Main {
    public static void main(String[] args) {
        char[][] grid = {
            {'S', 'P', 'q', 'P', 'P'},
            {'W', 'W', 'W', 'P', 'W'},
            {'r', 'P', 'Q', 'P', 'R'}
        };

        Solution solution = new Solution();
        System.out.println(solution.minMovesToCollectKeys(grid)); // Output: 8
    }
}
