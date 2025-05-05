package Tetris.main;

import Tetris.Game.HoldPanel;
import Tetris.Game.NextPanel;
import Tetris.Game.ScorePanel;
import Tetris.Board.Board;

import javax.swing.*;
import java.awt.*;

/**
 * Lớp TetrisGame đại diện cho cửa sổ giao diện chính của trò chơi Tetris.
 * Thiết lập GUI bao gồm các panel: Hold, Score, Next và bảng chơi chính.
 */
public class TetrisGame extends JFrame {
    private ScorePanel scorePanel;
    private NextPanel nextPanel;

    /**
     * Constructor khởi tạo cửa sổ game và giao diện trò chơi.
     */
    public TetrisGame() {
        setTitle("Tetris Game");                   // Tiêu đề cửa sổ
        setDefaultCloseOperation(EXIT_ON_CLOSE);  // Đóng chương trình khi tắt cửa sổ
        setResizable(false);                      // Không cho phép thay đổi kích thước
        setLayout(null);                          // Tạm thời bỏ layout
        setSize(500, 600);

        setLayout(new BorderLayout());            // Sử dụng BorderLayout để chia layout

        // ==== Panel bên trái: HoldPanel ở trên + ScorePanel ở dưới ====
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        HoldPanel HoldPanel = new HoldPanel();     // Panel giữ khối Tetromino
        leftPanel.add(HoldPanel, BorderLayout.NORTH);

        scorePanel = new ScorePanel();             // Panel điểm số, cấp độ, dòng hoàn thành
        leftPanel.add(scorePanel, BorderLayout.SOUTH);

        // ==== Panel bên phải: NextPanel hiển thị các khối tiếp theo ====
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        nextPanel = new NextPanel();               // Panel khối sắp tới
        rightPanel.add(nextPanel, BorderLayout.NORTH);

        // ==== Bảng chơi chính nằm giữa ====
        Board board = new Board(scorePanel, nextPanel, HoldPanel);

        // Thêm các panel vào vị trí phù hợp trong giao diện chính
        add(leftPanel, BorderLayout.WEST);     // Bên trái
        add(board, BorderLayout.CENTER);       // Trung tâm
        add(rightPanel, BorderLayout.EAST);    // Bên phải

        // Tự động điều chỉnh kích thước dựa theo nội dung
        pack();
        setLocationRelativeTo(null);           // Căn giữa màn hình
        setVisible(true);                      // Hiển thị cửa sổ
    }
}
