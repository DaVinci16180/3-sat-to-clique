public class CliqueVerifier {

    public static boolean verify(int[][] graph, int[] verticesIndex) {
        // O(k^2)
        for (int i = 0; i < verticesIndex.length; i++) {
            for (int j = 0; j < verticesIndex.length; j++) {
                if (i == j)
                    continue;

                if (graph[verticesIndex[i]][verticesIndex[j]] != 1)
                    return false;
            }
        }

        return true;
    }
}
