import java.util.*;

public class BoardPiece {
    static char[][] papan;
    static final String[] COLORS = {
        "\u001B[30m", "\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m",
        "\u001B[35m", "\u001B[36m", "\u001B[37m", "\u001B[90m", "\u001B[91m",
        "\u001B[92m", "\u001B[93m", "\u001B[94m", "\u001B[95m", "\u001B[96m",
        "\u001B[97m", "\u001B[38;5;21m", "\u001B[38;5;27m", "\u001B[38;5;46m",
        "\u001B[38;5;82m", "\u001B[38;5;124m", "\u001B[38;5;130m", "\u001B[38;5;172m",
        "\u001B[38;5;184m", "\u001B[38;5;201m", "\u001B[38;5;208m"
    };
    static final String RESET = "\u001B[0m";

    // memunculkan board atau papan
    public static void printPapan(char[][] board) {
        for (char[] baris : board) {
            for (char cell : baris) {
                if (cell == '.') {
                    System.out.print(cell + " ");
                } else {
                    int colorIndex = (cell - 'A') % COLORS.length;
                    System.out.print(COLORS[colorIndex] + cell + RESET + " ");
                }
            }
            System.out.println();
        }
    }

    // mengecek apakah papan sudah penuh
    static boolean isPapanFull(char [][] papan) {
        for (char[] baris : papan) {
            for (char cell : baris) {
                if (cell == '.') {
                    return false;
                }
            }
        }
        return true;
    }

    // memberitahu apa saja yang bisa dilakukan oleh puzzle, seperti rotate, flip, dan lain-lain
    static List<char[][]> generateVariasi(char[][] piece) {
        Set<String> set = new HashSet<>();
        List<char[][]> variasi = new ArrayList<>();
        char[][] curr = piece;
        for (int i = 0; i < 4; i++) {
            if (set.add(Arrays.deepToString(curr))) {
                variasi.add(curr);
            }
            curr = rotateBlock(curr);
        }
        piece = flip(piece);
        curr = piece;
        for (int i = 0; i < 4; i++) {
            if (set.add(Arrays.deepToString(curr))) {
                variasi.add(curr);
            }
            curr = rotateBlock(curr);
        }
        return variasi;
    }

    // memutar searah 90 derajat
    public static char[][] rotateBlock(char[][] piece) {
        int baris = piece.length;
        int kolom = piece[0].length;
        char[][] putar = new char[kolom][baris];
        for (int a = 0; a < baris; a++) {
            for (int b = 0; b < kolom; b++) {
                putar[b][baris - 1 - a] = piece[a][b];
            }
        }
        return putar;
    }

    // memutar searah 180 derajat (membalik bagian puzzle nya)
    static char[][] flip(char[][] piece) {
        int baris = piece.length;
        char[][] balik = new char[baris][];
        for (int i = 0; i < baris; i++) {
            balik[i] = piece[baris - i - 1].clone();
        }
        return balik;
    }

    // mengecek apakah bagian puzzle bisa dimasukkan ke papan
    static boolean isFit(char[][] papan, char[][] piece, int a, int b) {
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                if (piece[i][j] != '.' && piece[i][j] != ' ' && papan[a + i][b + j] != '.') {
                    return false;
                }
            }
        }
        return true;
    }

    // memasukkan bagian puzzle ke papan
    static void placePiece(char[][] papan, char[][] piece, int a, int b, char label) {
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                if (piece[i][j] != '.' && piece[i][j] != ' ') {
                    papan[a + i][b + j] = label;
                }
            }
        }
    }


    // menghapus bagian puzzle dari papan
    static void removePiece(char[][] papan, char[][] piece, int a, int b) {
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                if (piece[i][j] != '.' && piece[i][j] != ' ') {
                    papan[a + i][b + j] = '.';
                }
            }
        }
    }
}