package Tetris;
import javax.swing.*;
import java.awt.*;
class NextPanel extends JPanel {
    public NextPanel() {
        setPreferredSize(new Dimension(100, 200));
        setBackground(Color.DARK_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.drawString("NEXT", 35, 15);
    }
}
