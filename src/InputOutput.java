import java.io.*;
import java.util.*;

public class InputOutput {
    
    // Membaca masukan file .txt
    public static Puzzle bacaFilePuzzle(String fileName) {
        int N = 0, M = 0, P = 0;
        String S = "";
        HashSet<Character> huruf_blocks = new HashSet<>();
        List<char[][]> puzzle_blocks = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            if (line == null || line.trim().isEmpty()) {
                throw new IOException("Isi file kosong! Coba lagi.");
            }
            // Membaca baris pertama
            String[] first_line = line.trim().split(" ");
            if (first_line.length != 3) {
                throw new IOException("Block Puzzle belum sesuai! Coba lagi.");
            }
            N = Integer.parseInt(first_line[0]);
            M = Integer.parseInt(first_line[1]);
            P = Integer.parseInt(first_line[2]);
            
            // Membaca baris kedua
            line = reader.readLine();
            if (line == null || line.trim().isEmpty()) {
                throw new IOException("Tipe Puzzle belum ada! Coba lagi.");
            }
            S = line.trim();
            if (!(S.equals("DEFAULT") || S.equals("CUSTOM"))) {
                throw new IOException("Tipe Puzzle belum sesuai! Coba lagi.");
            }
            
            // Membaca baris-baris selanjutnya/membaca block puzzle
            List<String> block = new ArrayList<>();
            char current_block = ' ';

            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    char jenis_block = line.trim().charAt(0);
                    huruf_blocks.add(jenis_block);
                    if (block.isEmpty() || jenis_block == current_block) {
                        block.add(line);
                        current_block = jenis_block;
                    } else {
                        puzzle_blocks.add(convertBlockToMatrix(block));
                        block.clear();
                        block.add(line);
                        current_block = jenis_block;
                    }
                }
            }
            if (!block.isEmpty()) {
                puzzle_blocks.add(convertBlockToMatrix(block));
            }
            
            reader.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return new Puzzle(N, M, P, S, puzzle_blocks, huruf_blocks);
    }

    // membaca txt menjadi matriks
    public static char[][] convertBlockToMatrix(List<String> block) {
        int baris = block.size();
        int kolom = block.stream().mapToInt(String::length).max().orElse(0);
        // for (String line : block) {
        //     kolom = Math.max(kolom, line.length());
        // }
        char[][] matriks = new char[baris][kolom];
        for (int i = 0; i < baris; i++) {
            String line = block.get(i);
            for (int j = 0; j < kolom; j++) {
                matriks[i][j] = (j < line.length()) ? line.charAt(j) : ' ';
            }
        }
        return matriks;
    }

    public static void outputFile(char[][] papan, String fileName) {
        String filePath = "solusi/" + fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (char[] baris : papan) {
                for (char c : baris) {
                    writer.write(c);
                }
                writer.newLine();
            }
            System.out.println("Solusi berhasil disimpan di: " + filePath);
        } catch (IOException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
}