package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener, KeyListener {
    private final int ROWS = 20, COLS = 10, SIZE = 30;
    private Timer timer;
    private int[][] grid = new int[ROWS][COLS];
    private Color[][] gridColor = new Color[ROWS][COLS];
    private Tetromino current;

    public Board() {
        setPreferredSize(new Dimension(COLS * SIZE, ROWS * SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        spawn();
        timer = new Timer(500, this);
        timer.start();
    }

    public void spawn() {
        current = Tetromino.randomTetromino();
    }

    public boolean isValid(int row, int col, int[][] shape) {
        for (int i = 0; i < shape.length; i++)
            for (int j = 0; j < shape[0].length; j++)
                if (shape[i][j] == 1) {
                    int r = row + i, c = col + j;
                    if (r < 0 || r >= ROWS || c < 0 || c >= COLS || grid[r][c] == 1)
                        return false;
                }
        return true;
    }

    public void place() {// Ve current tai vi tri day
        for (int i = 0; i < current.shape.length; i++)
            for (int j = 0; j < current.shape[0].length; j++)
                if (current.shape[i][j] == 1) {
                    int r = current.row + i, c = current.col + j;
                    grid[r][c] = 1;
                    gridColor[r][c] = current.color;
                }
    }

    public void clearLines() {//Check xem co hang nao du khong
        for (int r = ROWS - 1; r >= 0; r--) {
            boolean full = true;
            for (int c = 0; c < COLS; c++)
                if (grid[r][c] == 0) {
                    full = false;
                    break;
                }
            if (full) {
                for (int i = r; i > 0; i--) {
                    grid[i] = grid[i - 1].clone();
                    gridColor[i] = gridColor[i - 1].clone();
                }
                grid[0] = new int[COLS];
                gridColor[0] = new Color[COLS];
                r++;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

    // Vẽ ghost piece
        Tetromino ghost = current.clone();
        while (isValid(ghost.row + 1, ghost.col, ghost.shape)) {
            ghost.row++;
        }
        g2d.setColor(new Color(0, 255, 255, 100)); // Cyan mờ
        g2d.setStroke(new BasicStroke(2));
        for (int i = 0; i < ghost.shape.length; i++) {
            for (int j = 0; j < ghost.shape[0].length; j++) {
                if (ghost.shape[i][j] == 1) {
                    int x = (ghost.col + j) * SIZE;
                    int y = (ghost.row + i) * SIZE;
                    g2d.drawRect(x, y, SIZE, SIZE);
                }
            }
        }
        // Draw placed blocks
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] == 1) {
                    g.setColor(gridColor[r][c]);
                    g.fillRect(c * SIZE, r * SIZE, SIZE, SIZE);
                }
                g.setColor(new Color(100, 100, 100, 80));
                g.drawRect(c * SIZE, r * SIZE, SIZE, SIZE);
            }
        // Draw current piece
        g.setColor(current.color);
        for (int i = 0; i < current.shape.length; i++)
            for (int j = 0; j < current.shape[0].length; j++)
                if (current.shape[i][j] == 1) {
                    int x = (current.col + j) * SIZE;
                    int y = (current.row + i) * SIZE;
                    g.fillRect(x, y, SIZE, SIZE);

                    // Vẽ viền
                    g.setColor(new Color(100, 100, 100, 80));
                    g.drawRect(x, y, SIZE, SIZE);

                    // Quay lại màu của khối để tiếp tục vẽ đúng màu
                    g.setColor(current.color);
                }
    }

    public void actionPerformed(ActionEvent e) {
        if (isValid(current.row + 1, current.col, current.shape)) {
            current.row++;
        } else {
            Draw();
        }
        repaint();
    }
    public void Draw(){
        place();
        clearLines();
        spawn();
        if (!isValid(current.row, current.col, current.shape)) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Over!");
        }
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT && isValid(current.row, current.col - 1, current.shape)) current.col--;
        if (code == KeyEvent.VK_RIGHT && isValid(current.row, current.col + 1, current.shape)) current.col++;
        if (code == KeyEvent.VK_DOWN && isValid(current.row + 1, current.col, current.shape)) current.row++;
        if (code == KeyEvent.VK_UP) current.rotate(this, +1); // Rotate right
        if (code == KeyEvent.VK_Z) current.rotate(this, -1);// Rotate left
        if (code == KeyEvent.VK_V) Draw();
        if (code == KeyEvent.VK_SPACE) {
//            Draw();
            while(isValid(current.row, current.col, current.shape)) current.row++;
            current.row--;
        }
        repaint();
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}
