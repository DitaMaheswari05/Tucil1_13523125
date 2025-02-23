import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class PuzzleImage {
    public static void savePuzzleImage(char[][] papan, String filename) {
        int cellSize = 50; // ukuran 50x50 px
        int lebar = papan[0].length * cellSize;
        int tinggi = papan.length * cellSize;

        BufferedImage image = new BufferedImage(lebar, tinggi, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // 26 warna berbeda untuk setiap blok puzzle (A sampai Z), sesuai RGB
        Color[] colors = {
            new Color(169, 169, 169), // Abu Abu Terang
            new Color(255, 0, 0),     // Merah
            new Color(0, 255, 0),     // Hijau
            new Color(255, 255, 0),   // Kuning
            new Color(0, 0, 255),     // Biru
            new Color(255, 0, 255),   // Magenta
            new Color(0, 255, 255),   // Cyan
            new Color(255, 255, 255), // Putih
            new Color(128, 128, 128), // Abu-abu gelap
            new Color(255, 99, 71),   // Tomato
            new Color(144, 238, 144), // Light Green
            new Color(255, 215, 0),   // Gold
            new Color(65, 105, 225),  // Royal Blue
            new Color(186, 85, 211),  // Medium Orchid
            new Color(32, 178, 170),  // Light Sea Green
            new Color(240, 248, 255), // Alice Blue
            new Color(25, 25, 112),   // Midnight Blue
            new Color(70, 130, 180),  // Steel Blue
            new Color(50, 205, 50),   // Lime Green
            new Color(0, 255, 127),   // Spring Green
            new Color(139, 0, 0),     // Dark Red
            new Color(210, 105, 30),  // Chocolate
            new Color(218, 165, 32),  // Goldenrod
            new Color(255, 228, 181), // Moccasin
            new Color(255, 105, 180), // Hot Pink
            new Color(255, 140, 0)    // Dark Orange
            };

        // Background putih
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, lebar, tinggi);

        // Gambar lingkaran untuk setiap blok puzzle
        for (int baris = 0; baris < papan.length; baris++) {
            for (int kolom = 0; kolom < papan[0].length; kolom++) {
                char c = papan[baris][kolom];
                if (c != '.') {
                    g2d.setColor(colors[(c - 'A') % colors.length]); // Ambil warna berdasarkan huruf blok
                    g2d.fillOval(kolom * cellSize, baris * cellSize, cellSize, cellSize); // Gambar lingkaran

                    // Tambahkan label huruf di tengah lingkaran
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                    FontMetrics metrics = g2d.getFontMetrics();
                    int x = kolom * cellSize + (cellSize - metrics.stringWidth(String.valueOf(c))) / 2;
                    int y = baris * cellSize + ((cellSize - metrics.getHeight()) / 2) + metrics.getAscent();
                    g2d.drawString(String.valueOf(c), x, y);
                }
            }
        }

        g2d.dispose();

        try {
            File outputFile = new File("solusi/" + filename);   // di save ke folder solusi
            ImageIO.write(image, "PNG", outputFile);
            System.out.println("Solusi disimpan dalam file: " + filename);
        } catch (Exception e) {
            System.out.println("Gagal menyimpan gambar: " + e.getMessage());
        }
    }
}