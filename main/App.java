package Tetris.main;

import javax.swing.*;

/**
 * Lớp Main chịu trách nhiệm khởi tạo và quản lý luồng chính của game Tetris.
 * Áp dụng Singleton để đảm bảo chỉ có một thể hiện duy nhất.
 */
public class App {
    public static App instance;  // Singleton instance
    private JFrame frame;         // Cửa sổ chính của game
    private TetrisMenu menu;
    /**
     * Trả về thể hiện duy nhất của lớp Main (Singleton Pattern).
     */
    public static App getInstance() {
        if (instance == null) {
            instance = new App();  // Nếu chưa có thể hiện, tạo mới
        }
        return instance;  // Trả về thể hiện duy nhất
    }

    /**
     * Hàm main của chương trình. Mở game dựa trên đầu vào từ người dùng.
     */
    public void OpenMenu(){
        if(frame!=null) frame.dispose();
        if(menu != null) menu.dispose();
        menu = new TetrisMenu();
    }

    /**
     * Mở game tùy vào giá trị nhập từ bàn phím.
     * - Nếu nhập 0: mở Tetris thường
     * - Ngược lại: mở TetrisGame (phiên bản khác)
     */
    public void PlayGame() {
        if(frame!=null) frame.dispose();
        if(menu != null) menu.dispose();
        App.getInstance().setFrame(new TetrisGame()); // Game mở rộng
    }

    /**
     * Kết thúc game, hiển thị hộp thoại hỏi người chơi có muốn chơi lại không.
     * - Nếu có: đóng frame cũ, mở lại game mới cùng loại.
     * - Nếu không: thoát chương trình.
     */
    public void EndGame() {
        Object[] options = {"Chơi lại", "Menu"};
        int option = JOptionPane.showOptionDialog(
                frame,
                "Game Over! Chơi lại?",
                "Game Over",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0] // nút mặc định là "Chơi lại"
        );
        if (option == JOptionPane.YES_OPTION) {
            PlayGame();
        } else {
            OpenMenu();
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
