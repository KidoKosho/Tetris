package Tetris;
import javax.swing.*;
import java.awt.*;
class ScorePanel extends JPanel {
    public ScorePanel() {
        setPreferredSize(new Dimension(100, 150));
        setBackground(Color.DARK_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.drawString("SCORE", 35, 20);
        g.drawString("0", 45, 40);
        g.drawString("LEVEL", 35, 70);
        g.drawString("1", 45, 90);
        g.drawString("LINES", 35, 120);
        g.drawString("0", 45, 140);
    }
}