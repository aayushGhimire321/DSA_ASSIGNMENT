// Assume you were hired to create an application for an ISP, and there are n network devices, such as routers,
// that are linked together to provide internet access to users. You are given a 2D array that represents network
// connections between these network devices. write an algorithm to return impacted network devices, If there is
// a power outage on a certain device, these impacted device list assist you notify linked consumers that there is a
// power outage and it will take some time to rectify an issue.
// Input: edges= {{0,1},{0,2},{1,3},{1,6},{2,4},{4,6},{4,5},{5,7}}
// Target Device (On which power Failure occurred): 4
// Output (Impacted Device List) = {5,7}

package Q5;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NetworkDevices {

    public static List<Integer> findImpactedDevices(int[][] edges, int targetDevice) {
        // Build adjacency list representation of the network
        List<List<Integer>> adjacencyList = buildAdjacencyList(edges);

        // Set to keep track of visited devices
        Set<Integer> visited = new HashSet<>();

        // List to store impacted devices
        List<Integer> impactedDevices = new ArrayList<>();

        // Perform DFS starting from the target device
        dfs(adjacencyList, targetDevice, visited, impactedDevices);

        return impactedDevices;
    }

    private static List<List<Integer>> buildAdjacencyList(int[][] edges) {
        List<List<Integer>> adjacencyList = new ArrayList<>();
        int n = edges.length;

        // Initialize adjacency list
        for (int i = 0; i < n; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        // Build adjacency list from edges
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            adjacencyList.get(from).add(to);
            adjacencyList.get(to).add(from); // Assuming undirected connections
        }

        return adjacencyList;
    }

    private static void dfs(List<List<Integer>> adjacencyList, int currentDevice,
                            Set<Integer> visited, List<Integer> impactedDevices) {
        // Mark current device as visited
        visited.add(currentDevice);

        // Check if the current device is impacted
        if (currentDevice != -1) {
            impactedDevices.add(currentDevice);
        }

        // Explore neighbors
        for (int neighbor : adjacencyList.get(currentDevice)) {
            if (!visited.contains(neighbor)) {
                dfs(adjacencyList, neighbor, visited, impactedDevices);
            }
        }
    }

    public static void main(String[] args) {
        int[][] edges = {
            {0, 1}, {0, 2}, {1, 3}, {1, 6}, {2, 4}, {4, 6}, {4, 5}, {5, 7}
        };
        int targetDevice = 4;

        List<Integer> impactedDevices = findImpactedDevices(edges, targetDevice);
        System.out.println("Impacted Device List: " + impactedDevices);
    }
}
