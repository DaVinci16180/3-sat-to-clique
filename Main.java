import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        SatReader sat = new SatReader("example.txt");
        BronKerbosch.f(sat.getGraph(), sat.getClausesAmount());
        boolean out = CliqueVerifier.verify(sat.getGraph(), new int[]{0, 6, 7});

        System.out.println(out);
    }
}
