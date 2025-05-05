package Tetris.main;

import Tetris.Tetromino;

import javax.swing.*;
import java.util.Scanner;

/**
 * Lớp Main chịu trách nhiệm khởi tạo và quản lý luồng chính của game Tetris.
 * Áp dụng Singleton để đảm bảo chỉ có một thể hiện duy nhất.
 */
public class Main {
    public static Main instance;  // Singleton instance
    private JFrame frame;         // Cửa sổ chính của game

    /**
     * Trả về thể hiện duy nhất của lớp Main (Singleton Pattern).
     */
    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();  // Nếu chưa có thể hiện, tạo mới
        }
        return instance;  // Trả về thể hiện duy nhất
    }

    /**
     * Hàm main của chương trình. Mở game dựa trên đầu vào từ người dùng.
     */
    public static void main(String[] args) {
        Main.getInstance().OpenGame();  // Gọi phương thức mở game từ Singleton
    }

    /**
     * Mở game tùy vào giá trị nhập từ bàn phím.
     * - Nếu nhập 0: mở Tetris thường
     * - Ngược lại: mở TetrisGame (phiên bản khác)
     */
    public void OpenGame() {
        System.out.print("Nhap version cua Game:");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        if (n == 0) {
            Main.getInstance().setFrame(new Tetris());     // Game cơ bản
        } else {
            Main.getInstance().setFrame(new TetrisGame()); // Game mở rộng
        }
    }

    /**
     * Kết thúc game, hiển thị hộp thoại hỏi người chơi có muốn chơi lại không.
     * - Nếu có: đóng frame cũ, mở lại game mới cùng loại.
     * - Nếu không: thoát chương trình.
     */
    public void EndGame() {
        int option = JOptionPane.showConfirmDialog(frame, "Game Over! Chơi lại?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            frame.dispose();  // Đóng cửa sổ hiện tại

            // Mở lại game cùng kiểu
            if (frame instanceof Tetris)
                Main.getInstance().setFrame(new Tetris());
            else
                Main.getInstance().setFrame(new TetrisGame());
        } else {
            System.exit(0); // Thoát hẳn chương trình
        }
    }

    /**
     * Trả về cửa sổ game hiện tại.
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Gán cửa sổ game và hiển thị nó.
     * @param frame cửa sổ cần hiển thị
     */
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
