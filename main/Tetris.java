package Tetris.main;

import Tetris.Board.Board;
import javax.swing.*;

public class Tetris extends JFrame{
    public Tetris() {
        JFrame frame = new JFrame("Tetris by Kido");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new Board());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
