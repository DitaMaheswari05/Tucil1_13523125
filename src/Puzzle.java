import java.util.*;

class Puzzle {
    int N, M, P;
    String S;
    List<char[][]> puzzle_blocks;
    HashSet<Character> huruf_blocks;
    char[][] papan;

    public Puzzle(int N, int M, int P, String S, List<char[][]> puzzle_blocks, HashSet<Character> huruf_blocks) {
        this.N = N;
        this.M = M;
        this.P = P;
        this.S = S;
        this.puzzle_blocks = puzzle_blocks;
        this.huruf_blocks = huruf_blocks;
        this.papan = new char[N][M];

        for (char[] row : papan) {
            Arrays.fill(row, '.');
        }
    }

    public char[][] getPapan() {
        char[][] papanCopy = new char[N][M];
        for (int i = 0; i < N; i++) {
            System.arraycopy(papan[i], 0, papanCopy[i], 0, M);
        }
        return papanCopy;
    }
}