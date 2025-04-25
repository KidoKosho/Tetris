package Tetris;

import javax.swing.*;

public class Tetris {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris by Kido");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new Board());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
