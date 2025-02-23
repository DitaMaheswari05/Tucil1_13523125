// import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.*;


public class PuzzleSolver {
    static char[][] papan;
    static List<char[][]> bagian;
    static long iterasi = 0;
    static long waktuMulai;


    public static void main(String[] args) {
        System.out.println("\r\n" + //
                        "██     ██ ███████ ██       ██████  ██████  ███    ███ ███████     ████████  ██████                    \r\n" + //
                        "██     ██ ██      ██      ██      ██    ██ ████  ████ ██             ██    ██    ██                   \r\n" + //
                        "██  █  ██ █████   ██      ██      ██    ██ ██ ████ ██ █████          ██    ██    ██                   \r\n" + //
                        "██ ███ ██ ██      ██      ██      ██    ██ ██  ██  ██ ██             ██    ██    ██                   \r\n" + //
                        " ███ ███  ███████ ███████  ██████  ██████  ██      ██ ███████        ██     ██████                    \r\n" + //
                        "                                                                                                      \r\n" + //
                        "                                                                                                      \r\n" + //
                        "██  ██████      ██████  ██    ██ ███████ ███████ ██      ███████ ██████      ██████  ██████   ██████  \r\n" + //
                        "██ ██    ██     ██   ██ ██    ██    ███     ███  ██      ██      ██   ██     ██   ██ ██   ██ ██    ██ \r\n" + //
                        "██ ██    ██     ██████  ██    ██   ███     ███   ██      █████   ██████      ██████  ██████  ██    ██ \r\n" + //
                        "██ ██ ▄▄ ██     ██      ██    ██  ███     ███    ██      ██      ██   ██     ██      ██   ██ ██    ██ \r\n" + //
                        "██  ██████      ██       ██████  ███████ ███████ ███████ ███████ ██   ██     ██      ██   ██  ██████  \r\n" + //
                        "       ▀▀                                                                                             \r\n" + //
                        "                                                                                                      \r\n" + //
                        ""); // ini iseng aja sih kak, mau nyoba nyoba aja
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan nama file: ");
        String fileName = scanner.nextLine().trim();

        Path filePath = Paths.get("test", fileName); // folder txt nya ada di test ya kak

        try {
            Puzzle puzzle = InputOutput.bacaFilePuzzle(filePath.toString());
            papan = new char[puzzle.N][puzzle.M];
            bagian = puzzle.puzzle_blocks;
            

            for (char[] baris : papan) Arrays.fill(baris, '.');

            waktuMulai = System.currentTimeMillis();
            BoardPiece.papan = papan;
            boolean solusi = solve(0);
            long waktuSelesai = System.currentTimeMillis();

            if (solusi) {
                BoardPiece.printPapan(papan);
            } else {
                System.out.println("Tidak ada solusi");
            }

            System.out.println("Waktu pencarian: " + (waktuSelesai - waktuMulai) + "ms");
            System.out.println("Banyak kasus yang ditinjau: " + iterasi);

            System.out.print("Apakah ingin menyimpan solusi? (ya/tidak): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("ya")) {
                System.out.print("Masukkan nama file solusi: ");
                String outputFile = scanner.nextLine();
                InputOutput.outputFile(papan, outputFile);
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }

        System.out.print("Apakah ingin menyimpan solusi sebagai gambar? (ya/tidak): ");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("ya")) {
            System.out.print("Masukkan nama file gambar (tanpa ekstensi): ");
            String outputFile = scanner.nextLine();
            PuzzleImage.savePuzzleImage(papan, outputFile + ".png");
        }
    }

    static boolean solve(int idx) {
        if (idx == bagian.size()) return BoardPiece.isPapanFull(papan);
        iterasi++;
        char[][] piece = bagian.get(idx);
        List<char[][]> variasi = BoardPiece.generateVariasi(piece);

        for (char[][] macam : variasi) {
            for (int a = 0; a <= papan.length - macam.length; a++) {
                for (int b = 0; b <= papan[0].length - macam[0].length; b++) {
                    if (BoardPiece.isFit(papan, macam, a, b)) {
                        BoardPiece.placePiece(papan, macam, a, b, (char) ('A' + idx));
                        if (solve(idx + 1)) return true;
                        BoardPiece.removePiece(papan, macam, a, b);
                    }
                }
            }
        }
        return false;
    }
}