import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class SatReader {

    private int[][] graph;

    public SatReader(String fileName) {
        fromFile(fileName);
    }

    public SatReader() {}

    public void fromFile(String fileName) {
        File file = new File(fileName);
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        int literalsAmount = scanner.nextInt();
        scanner.nextLine();
        int clausesAmount = scanner.nextInt();
        scanner.nextLine();

        int size = literalsAmount * clausesAmount;

        graph = new int[size][size];

        String[] expression = scanner.nextLine()
                .replace("(", "")
                .replace(")", "")
                .split("\\.");

        HashMap<String, Integer> literalCodes = new HashMap<>();
        int codeIter = 1;

        int[] labels = new int[size];

        for (int i = 0; i < expression.length; i++) {
            String[] literals = expression[i].split("\\+");

            for (int j = 0; j < literals.length; j++) {
                int multiplier = literals[j].length() == 1 ? 1 : -1;
                String absLiteral = String.valueOf(literals[j].charAt(literals[j].length() - 1));

                int code;
                if (literalCodes.containsKey(absLiteral)) {
                    code = literalCodes.get(absLiteral) * multiplier;
                } else {
                    literalCodes.put(absLiteral, codeIter);
                    code = codeIter++ * multiplier;
                }

                labels[i * clausesAmount + j] = code;
            }
        }

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (row / 3 == col / 3)
                    continue;

                if (row == col)
                    continue;

                if (labels[row] == -labels[col])
                    continue;

                graph[row][col] = 1;
            }
        }
    }

    public int[][] getGraph() {
        return graph;
    }
}
