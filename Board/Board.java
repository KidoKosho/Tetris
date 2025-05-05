package Tetris.Board;

import Tetris.Tetromino;
import Tetris.Game.ScorePanel;
import Tetris.Game.HoldPanel;
import Tetris.Game.NextPanel;
import Tetris.main.Main;
import Tetris.main.TetrisGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

// Lớp Board đại diện cho bảng chính của game Tetris
public class Board extends JPanel implements ActionListener, KeyListener {
    private List<Tetromino> queue = new ArrayList<>(); // Hàng đợi các mảnh sắp tới
    private ScorePanel scorePanel;
    private HoldPanel holdPanel;
    private NextPanel nextPanel;
    private final int ROWS = 20, COLS = 10, SIZE = 30; // Kích thước bảng chơi
    private Timer timer;
    private int[][] grid = new int[ROWS][COLS]; // Ma trận lưu các ô đã được đặt mảnh
    private Color[][] gridColor = new Color[ROWS][COLS]; // Màu sắc tương ứng với các ô
    private Tetromino current; // Mảnh hiện tại đang rơi
    int score = 0;
    int level = 1;
    int linesCleared = 0;

    // Constructor khi có các panel phụ
    public Board(ScorePanel scorePanel, NextPanel nextPanel, HoldPanel holdPanel) {
        this.scorePanel = scorePanel;
        this.nextPanel = nextPanel;
        this.holdPanel = holdPanel;
        holdPanel.SetBoard(this);
        OnInit();
    }

    // Constructor mặc định (không dùng panel)
    public Board(){
        OnInit();
    }

    // Hàm khởi tạo board
    public void OnInit() {
        setPreferredSize(new Dimension(COLS * SIZE, ROWS * SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        // Thêm 3 mảnh đầu vào hàng đợi
        for (int i = 0; i < 3; i++) queue.add(Tetromino.randomTetromino());

        spawn(); // Sinh mảnh đầu tiên
        timer = new Timer(500, this); // Thiết lập tốc độ ban đầu
        timer.start();
    }

    // Sinh mảnh mới từ hàng đợi
    public void spawn() {
        current = queue.remove(0);
        queue.add(Tetromino.randomTetromino());

        // Cập nhật các mảnh kế tiếp trong panel bên
        if (nextPanel != null) {
            nextPanel.setNextPieces(new ArrayList<>(queue));
        }
    }

    // Kiểm tra tính hợp lệ của vị trí đặt mảnh
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

    // Đặt mảnh hiện tại vào lưới (grid)
    public void place() {
        int[][] shape = current.getShape();
        int row = current.getRow();
        int col = current.getCol();
        for (int i = 0; i < shape.length; i++)
            for (int j = 0; j < shape[0].length; j++)
                if (shape[i][j] == 1) {
                    int r = row + i, c = col + j;
                    grid[r][c] = 1;
                    gridColor[r][c] = current.getColor();
                }
    }

    // Xử lý xóa dòng nếu đầy
    public void clearLines() {
        int lines = 0;
        for (int r = ROWS - 1; r >= 0; r--) {
            boolean full = true;
            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] == 0) {
                    full = false;
                    break;
                }
            }
            if (full) {
                for (int i = r; i > 0; i--) {
                    grid[i] = grid[i - 1].clone();
                    gridColor[i] = gridColor[i - 1].clone();
                }
                grid[0] = new int[COLS];
                gridColor[0] = new Color[COLS];
                r++; // Kiểm lại dòng này
                lines++;
            }
        }

        // Cập nhật điểm số và tốc độ nếu có dòng bị xóa
        if (lines > 0) {
            linesCleared += lines;
            score += lines * 100;
            level = linesCleared / 10 + 1;

            if (scorePanel != null) {
                scorePanel.updateScore(score, level, linesCleared);
            }

            int delay = Math.max(80, 500 - (level - 1) * 35);
            timer.setDelay(delay);
        }
    }

    // Vẽ mọi thứ lên màn hình
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawGhostPiece(g2d);      // Mảnh bóng
        drawPlacedBlocks(g);      // Mảnh đã đặt
        drawCurrentPiece(g);      // Mảnh hiện tại
    }

    // Vẽ bóng (ghost piece) rơi xuống vị trí thấp nhất
    private void drawGhostPiece(Graphics2D g2d) {
        Tetromino ghost = current.clone();
        int[][] shape = ghost.getShape();

        while (isValid(ghost.getRow() + 1, ghost.getCol(), shape)) {
            ghost.setRow(ghost.getRow() + 1);
        }

        g2d.setColor(new Color(0, 255, 255, 100));
        g2d.setStroke(new BasicStroke(2));

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] == 1) {
                    int x = (ghost.getCol() + j) * SIZE;
                    int y = (ghost.getRow() + i) * SIZE;
                    g2d.fillRect(x + 1, y + 1, SIZE - 2, SIZE - 2);
                }
            }
        }
    }

    private Tetromino heldPiece = null;
    private boolean usedHold = false;

    // Xử lý giữ mảnh
    public void hold() {
//        if (usedHold) return;  // bat cai nay len khi nao muon trong game chi duoc 1 lan

        Tetromino temp = heldPiece;
        heldPiece = current;
        usedHold = true;

        if (temp == null) {
            spawn();
        } else {
            current = temp;
            current.resetPosition();
        }

        if (holdPanel != null) {
            holdPanel.setHeld(heldPiece, usedHold);
        }

        repaint();
    }

    // Vẽ các block đã được cố định
    private void drawPlacedBlocks(Graphics g) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] == 1) {
                    g.setColor(gridColor[r][c]);
                    g.fillRect(c * SIZE, r * SIZE, SIZE, SIZE);
                }
                g.setColor(new Color(100, 100, 100, 80));
                g.drawRect(c * SIZE, r * SIZE, SIZE, SIZE);
            }
        }
    }

    // Vẽ mảnh hiện tại đang rơi
    private void drawCurrentPiece(Graphics g) {
        int[][] shape = current.getShape();
        int row = current.getRow();
        int col = current.getCol();

        g.setColor(current.getColor());
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] == 1) {
                    int x = (col + j) * SIZE;
                    int y = (row + i) * SIZE;
                    g.fillRect(x, y, SIZE, SIZE);

                    g.setColor(new Color(100, 100, 100, 80));
                    g.drawRect(x, y, SIZE, SIZE);

                    g.setColor(current.getColor());
                }
            }
        }
    }

    // Hàm gọi mỗi lần timer chạy (di chuyển mảnh xuống)
    public void actionPerformed(ActionEvent e) {
        if (isValid(current.getRow() + 1, current.getCol(), current.getShape())) {
            current.setRow(current.getRow() + 1);
        } else {
            Draw();
        }
        repaint();
    }

    // Xử lý khi mảnh chạm đáy
    public void Draw(){
        place();
        clearLines();
        spawn();
        if (!isValid(current.getRow(), current.getCol(), current.getShape())) {
            timer.stop();
            Main.getInstance().EndGame();
        }
    }

    // Xử lý phím bấm
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT && isValid(current.getRow(), current.getCol() - 1, current.getShape()))
            current.setCol(current.getCol() - 1);
        if (code == KeyEvent.VK_RIGHT && isValid(current.getRow(), current.getCol() + 1, current.getShape()))
            current.setCol(current.getCol() + 1);
        if (code == KeyEvent.VK_DOWN && isValid(current.getRow() + 1, current.getCol(), current.getShape()))
            current.setRow(current.getRow() + 1);
        if (code == KeyEvent.VK_UP)
            current.rotate(this, +1);
        if (code == KeyEvent.VK_Z)
            current.rotate(this, -1);
        if (code == KeyEvent.VK_SPACE) {
            // Xử lý rơi thẳng xuống (Hard Drop)
            while (isValid(current.getRow() + 1, current.getCol(), current.getShape())) {
                current.setRow(current.getRow() + 1);
            }
            Draw(); // Đặt mảnh luôn khi đã rơi xuống đáy
        }

        if (code == KeyEvent.VK_V) {
            // Đặt mảnh ngay lập tức vào vị trí hiện tại
            Draw();
        }
        repaint();
    }

    // Reset lại game
    public void resetGame() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                grid[r][c] = 0;
                gridColor[r][c] = null;
            }
        }
        spawn();
        timer.restart();
        repaint();
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}
