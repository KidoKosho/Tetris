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
    private ScorePanel scorePanel = null;
    private NextPanel nextPanel = null;
    private HoldPanel holdPanel = null;

    public TetrisGame() {
        setupWindow();
        initComponents();
        assembleGUI();
    }

    /** Cài đặt thuộc tính của cửa sổ chính */
    private void setupWindow() {
        setTitle("Tetris Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setSize(500, 600);
    }

    /** Khởi tạo các thành phần GUI */
    private void initComponents() {
        holdPanel = new HoldPanel();
        scorePanel = new ScorePanel();
        nextPanel = new NextPanel();
    }

    /** Gắn các thành phần vào cửa sổ */
    private void assembleGUI() {
        // ==== Panel bên trái: HoldPanel + ScorePanel ====
        JPanel leftPanel = new JPanel(new BorderLayout());
        if (holdPanel != null) leftPanel.add(holdPanel, BorderLayout.NORTH);
        if (scorePanel != null) leftPanel.add(scorePanel, BorderLayout.SOUTH);

        // ==== Panel bên phải: NextPanel ====
        JPanel rightPanel = new JPanel(new BorderLayout());
        if (nextPanel != null) rightPanel.add(nextPanel, BorderLayout.NORTH);

        // ==== Bảng chơi chính ====
        Board board = new Board(scorePanel, nextPanel, holdPanel);

        // ==== Thêm các panel vào cửa sổ ====
        add(leftPanel, BorderLayout.WEST);
        add(board, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
