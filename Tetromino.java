package Tetris;

import Tetris.Board.Board;
import java.awt.*;
import java.util.Random;

public class Tetromino {
    // Loại của Tetromino: I, O, T, S, Z, J, L
    public enum TetrominoType {
        I, O, T, S, Z, J, L
    }

    // Ma trận hình dạng hiện tại của Tetromino
    private int[][] shape;

    // Vị trí hiện tại trên lưới
    private int row, col;

    // Vị trí bắt đầu khi khởi tạo
    private int rowStart = 0;
    private int colStart = 3;

    // Màu sắc và loại của khối
    private Color color;
    private TetrominoType type;
    private int rotation; // Góc xoay hiện tại (0–3)

    // Constructor khởi tạo Tetromino với loại cụ thể
    public Tetromino(TetrominoType type) {
        this.type = type;
        this.rotation = 0;
        this.shape = SHAPES[type.ordinal()][rotation];
        this.color = COLORS[type.ordinal()];
        this.row = rowStart;
        this.col = colStart;
    }

    // Tạo một Tetromino ngẫu nhiên
    public static Tetromino randomTetromino() {
        Random rand = new Random();
        TetrominoType[] types = TetrominoType.values();
        return new Tetromino(types[rand.nextInt(types.length)]);
    }

    // Đặt lại vị trí khối về vị trí bắt đầu
    public void resetPosition() {
        row = rowStart;
        col = colStart;
    }

    // Clone (tạo bản sao) của Tetromino hiện tại
    @Override
    public Tetromino clone() {
        Tetromino copy = new Tetromino(this.type);
        copy.shape = new int[this.shape.length][this.shape[0].length];
        for (int i = 0; i < this.shape.length; i++)
            copy.shape[i] = this.shape[i].clone();
        copy.color = this.color;
        copy.row = this.row;
        copy.col = this.col;
        return copy;
    }

    // Vẽ Tetromino lên màn hình
    public void draw(Graphics g, int xOffset, int yOffset, int sizeBox) {
        if (shape == null) return;
        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                if (shape[y][x] == 1) {
                    g.setColor(color);
                    g.fillRect(x * sizeBox + xOffset, yOffset + y * sizeBox, sizeBox, sizeBox);
                    g.setColor(Color.BLACK); // Viền đen cho từng ô
                    g.drawRect(x * sizeBox + xOffset, yOffset + y * sizeBox, sizeBox, sizeBox);
                }
            }
        }
    }

    // Xoay khối theo hướng dir (1: phải, -1: trái) có xét Wall Kick
    public void rotate(Board board, int dir) {
        int[][] newShape = rotateMatrix(shape, dir);
        int newRot = (rotation + dir + 4) % 4;

        // Thử tất cả offset Wall Kick
        for (int[] offset : WallKick.getKicks(type, rotation, newRot)) {
            int newRow = row + offset[1];
            int newCol = col + offset[0];
            if (board.isValid(newRow, newCol, newShape)) {
                shape = newShape;
                row = newRow;
                col = newCol;
                rotation = newRot;
                return;
            }
        }
    }

    // Xoay ma trận hình dạng theo hướng (1: phải, -1: trái)
    private int[][] rotateMatrix(int[][] mat, int dir) {
        int n = mat.length, m = mat[0].length;
        int[][] rotated = new int[m][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (dir == 1)
                    rotated[j][n - 1 - i] = mat[i][j]; // Xoay phải
                else
                    rotated[m - 1 - j][i] = mat[i][j]; // Xoay trái
            }
        }
        return rotated;
    }

    // ==== Getter và Setter ====
    public int getRow() { return row; }
    public int getCol() { return col; }
    public Color getColor() { return color; }
    public TetrominoType getType() { return type; }
    public int getRotation() { return rotation; }
    public int[][] getShape() { return shape; }

    public void setShape(int[][] shape) { this.shape = shape; }
    public void setRow(int row) { this.row = row; }
    public void setCol(int col) { this.col = col; }
    public void setColor(Color color) { this.color = color; }
    public void setType(TetrominoType type) { this.type = type; }
    public void setRotation(int rotation) { this.rotation = rotation; }

    // ==== Dữ liệu hình dạng và màu sắc ====
    private static final int[][][][] SHAPES = {
            // Các trạng thái xoay cho mỗi loại Tetromino
            { // I
                    {{0,0,0,0},{1,1,1,1},{0,0,0,0},{0,0,0,0}},
                    {{0,0,1,0},{0,0,1,0},{0,0,1,0},{0,0,1,0}},
                    {{0,0,0,0},{0,0,0,0},{1,1,1,1},{0,0,0,0}},
                    {{0,1,0,0},{0,1,0,0},{0,1,0,0},{0,1,0,0}}
            },
            { // O
                    {{1,1},{1,1}}, {{1,1},{1,1}}, {{1,1},{1,1}}, {{1,1},{1,1}}
            },
            { // T
                    {{0,1,0},{1,1,1},{0,0,0}}, {{0,1,0},{0,1,1},{0,1,0}},
                    {{0,0,0},{1,1,1},{0,1,0}}, {{0,1,0},{1,1,0},{0,1,0}}
            },
            { // S
                    {{0,1,1},{1,1,0},{0,0,0}}, {{0,1,0},{0,1,1},{0,0,1}},
                    {{0,0,0},{0,1,1},{1,1,0}}, {{1,0,0},{1,1,0},{0,1,0}}
            },
            { // Z
                    {{1,1,0},{0,1,1},{0,0,0}}, {{0,0,1},{0,1,1},{0,1,0}},
                    {{0,0,0},{1,1,0},{0,1,1}}, {{0,1,0},{1,1,0},{1,0,0}}
            },
            { // J
                    {{1,0,0},{1,1,1},{0,0,0}}, {{0,1,1},{0,1,0},{0,1,0}},
                    {{0,0,0},{1,1,1},{0,0,1}}, {{0,1,0},{0,1,0},{1,1,0}}
            },
            { // L
                    {{0,0,1},{1,1,1},{0,0,0}}, {{0,1,0},{0,1,0},{0,1,1}},
                    {{0,0,0},{1,1,1},{1,0,0}}, {{1,1,0},{0,1,0},{0,1,0}}
            }
    };

    // Màu tương ứng với mỗi loại Tetromino
    private static final Color[] COLORS = {
            Color.CYAN, Color.YELLOW, Color.MAGENTA,
            Color.GREEN, Color.RED, Color.BLUE, Color.ORANGE
    };
}
