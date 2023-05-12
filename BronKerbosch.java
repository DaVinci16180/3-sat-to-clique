import java.util.*;

public class BronKerbosch {

    private static Set<Integer> difference(Set<Integer> a, Set<Integer> b) {
        Set<Integer> diff = new HashSet<Integer>(a);
        diff.removeAll(b);
        return diff;
    }

    private static void bronKerbosch(Set<Integer> R, Set<Integer> P, Set<Integer> X, Set<Set<Integer>> cliques) {
        if (P.isEmpty() && X.isEmpty()) {
            cliques.add(R);
            return;
        }

        Set<Integer> pCopy = new HashSet<Integer>(P);
        for (int v : pCopy) {
            Set<Integer> neighbors = new HashSet<Integer>();
            for (int n : P) {
                if (Graph.adjMatrix[v][n] == 1) {
                    neighbors.add(n);
                }
            }
            bronKerbosch(union(R, Set.of(v)), intersection(P, neighbors), intersection(X, neighbors), cliques);
            P.remove(v);
            X.add(v);
        }
    }

    private static Set<Integer> intersection(Set<Integer> a, Set<Integer> b) {
        Set<Integer> inter = new HashSet<Integer>(a);
        inter.retainAll(b);
        return inter;
    }

    private static Set<Integer> union(Set<Integer> a, Set<Integer> b) {
        Set<Integer> union = new HashSet<Integer>(a);
        union.addAll(b);
        return union;
    }

    public static int findMaxCliques() {
        Set<Set<Integer>> cliques = new HashSet<Set<Integer>>();
        Set<Integer> vertices = new HashSet<Integer>();
        for (int i = 0; i < Graph.numVertices; i++) {
            vertices.add(i);
        }
        bronKerbosch(new HashSet<Integer>(), vertices, new HashSet<Integer>(), cliques);
        int maxCliqueSize = 0;
        Set<Integer> maxClique = new HashSet<Integer>();
        for (Set<Integer> clique : cliques) {
            if (clique.size() > maxCliqueSize) {
                maxCliqueSize = clique.size();
                maxClique = clique;
            }
        }
        System.out.println("Max clique size: " + maxCliqueSize);
        System.out.println("Max clique: " + maxClique);

        return maxCliqueSize;
    }

    public static boolean f(int[][] graph, int k) {
        Graph.setLength(graph.length);
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if (graph[i][j] == 1)
                    Graph.addEdge(i, j);
            }
        }

        int max = findMaxCliques(); // outputs: Max clique size: 3; Max clique: [1, 2, 3]
        return max >= k;
    }
}

class Graph {
    public static int[][] adjMatrix;
    public static int numVertices;

    public static void setLength(int numVertices) {
        Graph.numVertices = numVertices;
        adjMatrix = new int[numVertices][numVertices];
    }

    public static void addEdge(int i, int j) {
        adjMatrix[i][j] = 1;
        adjMatrix[j][i] = 1;
    }
}