package Tetris.GUI;
import Tetris.Board.Tetromino;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NextPanel extends JPanel {
    private List<Tetromino> nextPieces;
    private int WidthNextpanel = 100 , HeightNextpanel = 210;
    public NextPanel() {
        // Đặt kích thước mặc định ngay trong constructor
        setPreferredSize(new Dimension(WidthNextpanel, HeightNextpanel));
        setBackground(Color.DARK_GRAY);
    }
    public void setNextPieces(List<Tetromino> nextPieces) {
        this.nextPieces = nextPieces;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (nextPieces == null) return;

        int numberPieces = nextPieces.size();
        int YSpace = HeightNextpanel /(numberPieces*2+1);
        int y = YSpace;
        for (Tetromino t : nextPieces) {
            int shapeWidth = t.getShape()[0].length;
            int xOffset = (WidthNextpanel - shapeWidth*20) / 2;
            int sizeBox = 20;
            t.draw(g, xOffset, y,sizeBox);
            y += 2*YSpace;
        }
    }
}
