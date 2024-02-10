//Implement ant colony algorithm solving travelling a salesman problem

package Q5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ACO_TSP {
    // Define the number of ants
    private static final int NUM_ANTS = 10;
    // Define the number of iterations
    private static final int NUM_ITERATIONS = 100;
    // Define the pheromone evaporation rate
    private static final double EVAPORATION_RATE = 0.5;
    // Define the pheromone initial value
    private static final double INITIAL_PHEROMONE = 1.0;
    // Define the alpha parameter
    private static final double ALPHA = 1.0;
    // Define the beta parameter
    private static final double BETA = 2.0;

    // Define the distances between cities (adjacency matrix)
    private static final double[][] DISTANCES = {
        {0, 10, 15, 20},
        {10, 0, 35, 25},
        {15, 35, 0, 30},
        {20, 25, 30, 0}
    };

    // Define the pheromone levels between cities (initially set to INITIAL_PHEROMONE)
    private static double[][] pheromones = new double[DISTANCES.length][DISTANCES[0].length];

    public static void main(String[] args) {
        // Initialize pheromones
        initializePheromones();

        // Run the ant colony optimization algorithm
        List<Integer> shortestPath = runACO();

        // Output the shortest path found
        System.out.println("Shortest path found: " + shortestPath);
    }

    private static void initializePheromones() {
        for (int i = 0; i < pheromones.length; i++) {
            Arrays.fill(pheromones[i], INITIAL_PHEROMONE);
        }
    }

    private static List<Integer> runACO() {
        List<Integer> shortestPath = null;
        double shortestDistance = Double.MAX_VALUE;

        // Run multiple iterations of the ant colony optimization algorithm
        for (int iteration = 0; iteration < NUM_ITERATIONS; iteration++) {
            // Create a list to store the solutions found by each ant
            List<List<Integer>> antSolutions = new ArrayList<>();

            // Generate solutions for each ant
            for (int ant = 0; ant < NUM_ANTS; ant++) {
                List<Integer> antSolution = generateAntSolution();
                antSolutions.add(antSolution);

                // Update the shortest path found so far
                double distance = calculateDistance(antSolution);
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    shortestPath = new ArrayList<>(antSolution);
                }
            }

            // Update pheromone levels based on the solutions found by ants
            updatePheromones(antSolutions);
        }

        return shortestPath;
    }

    private static List<Integer> generateAntSolution() {
        List<Integer> antSolution = new ArrayList<>();
        boolean[] visited = new boolean[DISTANCES.length];
        int currentCity = 0; // Start from the first city

        // Visit each city exactly once
        for (int i = 0; i < DISTANCES.length - 1; i++) {
            visited[currentCity] = true;
            int nextCity = selectNextCity(currentCity, visited);
            antSolution.add(currentCity);
            currentCity = nextCity;
        }

        antSolution.add(currentCity); // Return to the first city to complete the cycle
        return antSolution;
    }

    private static int selectNextCity(int currentCity, boolean[] visited) {
        // Calculate the probabilities of selecting each unvisited city
        double[] probabilities = new double[DISTANCES.length];
        double totalProbability = 0;

        for (int i = 0; i < DISTANCES.length; i++) {
            if (!visited[i]) {
                probabilities[i] = Math.pow(pheromones[currentCity][i], ALPHA) * Math.pow(1.0 / DISTANCES[currentCity][i], BETA);
                totalProbability += probabilities[i];
            }
        }

        // Select the next city based on the probabilities
        double randomValue = new Random().nextDouble() * totalProbability;
        double cumulativeProbability = 0;

        for (int i = 0; i < DISTANCES.length; i++) {
            if (!visited[i]) {
                cumulativeProbability += probabilities[i];
                if (cumulativeProbability >= randomValue) {
                    return i;
                }
            }
        }

        // This should not happen if the probabilities are calculated correctly
        throw new IllegalStateException("Failed to select next city");
    }

    private static double calculateDistance(List<Integer> path) {
        double distance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            int from = path.get(i);
            int to = path.get(i + 1);
            distance += DISTANCES[from][to];
        }
        return distance;
    }

    private static void updatePheromones(List<List<Integer>> antSolutions) {
        // Evaporate pheromones
        for (int i = 0; i < pheromones.length; i++) {
            for (int j = 0; j < pheromones[i].length; j++) {
                pheromones[i][j] *= (1 - EVAPORATION_RATE);
            }
        }

        // Deposit pheromones
        for (List<Integer> solution : antSolutions) {
            double pheromoneDelta = 1.0 / calculateDistance(solution);
            for (int i = 0; i < solution.size() - 1; i++) {
                int from = solution.get(i);
                int to = solution.get(i + 1);
                pheromones[from][to] += pheromoneDelta;
                pheromones[to][from] += pheromoneDelta; // Assuming symmetric TSP
            }
        }
    }
}
