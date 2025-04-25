package Tetris;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class HoldPanel extends JPanel {
    public HoldPanel() {
        setPreferredSize(new Dimension(150 , 150));
        setBackground(Color.DARK_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.drawString("HOLD", 40, 15);
    }
}