// Implement Kruskal algorithm and priority queue using minimum heap


package Q3;

import java.util.*;

class Edge implements Comparable<Edge> {
    int source, destination, weight;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge edge) {
        return this.weight - edge.weight;
    }
}

class DisjointSet {
    int[] parent, rank;

    public DisjointSet(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public void union(int x, int y) {
        int xRoot = find(x);
        int yRoot = find(y);

        if (xRoot == yRoot) {
            return;
        }

        if (rank[xRoot] < rank[yRoot]) {
            parent[xRoot] = yRoot;
        } else if (rank[xRoot] > rank[yRoot]) {
            parent[yRoot] = xRoot;
        } else {
            parent[yRoot] = xRoot;
            rank[xRoot]++;
        }
    }
}

public class KruskalAlgorithm {
    public static List<Edge> minimumSpanningTree(List<Edge> edges, int numVertices) {
        List<Edge> result = new ArrayList<>();
        Collections.sort(edges); // Sort edges by weight in ascending order
        DisjointSet disjointSet = new DisjointSet(numVertices);

        for (Edge edge : edges) {
            int sourceRoot = disjointSet.find(edge.source);
            int destinationRoot = disjointSet.find(edge.destination);

            if (sourceRoot != destinationRoot) {
                result.add(edge);
                disjointSet.union(sourceRoot, destinationRoot);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 10));
        edges.add(new Edge(0, 2, 6));
        edges.add(new Edge(0, 3, 5));
        edges.add(new Edge(1, 3, 15));
        edges.add(new Edge(2, 3, 4));

        int numVertices = 4;
        List<Edge> minimumSpanningTree = minimumSpanningTree(edges, numVertices);

        System.out.println("Minimum Spanning Tree:");
        for (Edge edge : minimumSpanningTree) {
            System.out.println(edge.source + " - " + edge.destination + ": " + edge.weight);
        }
    }
}
