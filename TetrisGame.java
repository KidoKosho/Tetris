package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class TetrisGame extends JFrame {
    public TetrisGame() {
        setTitle("Tetris Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setSize(500, 600);

        setLayout(new BorderLayout());

        // Panel trái: Hold + Score
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        leftPanel.add(new HoldPanel(), BorderLayout.NORTH);
        leftPanel.add(new ScorePanel(), BorderLayout.SOUTH);

        // Panel phải: Next
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(new NextPanel(), BorderLayout.NORTH);

        // Bàn chơi chính
        Board board = new Board();

        add(leftPanel, BorderLayout.WEST);
        add(board, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TetrisGame::new);
    }
}
