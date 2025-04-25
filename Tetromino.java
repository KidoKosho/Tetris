package Tetris;

import java.awt.Color;
import java.util.Random;

public class Tetromino {
    public int[][] shape;
    public int row, col;
    public Color color;
    public TetrominoType type;
    public int rotation;

    public enum TetrominoType {
        I, O, T, S, Z, J, L
    }
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
    private static final int[][][][] SHAPES = {
            // I
            {
                    {{0,0,0,0}, {1,1,1,1}, {0,0,0,0}, {0,0,0,0}},
                    {{0,0,1,0}, {0,0,1,0}, {0,0,1,0}, {0,0,1,0}},
                    {{0,0,0,0}, {0,0,0,0}, {1,1,1,1}, {0,0,0,0}},
                    {{0,1,0,0}, {0,1,0,0}, {0,1,0,0}, {0,1,0,0}}
            },
            // O
            {
                    {{1,1},{1,1}},
                    {{1,1},{1,1}},
                    {{1,1},{1,1}},
                    {{1,1},{1,1}}
            },
            // T
            {
                    {{0,1,0}, {1,1,1}, {0,0,0}},
                    {{0,1,0}, {0,1,1}, {0,1,0}},
                    {{0,0,0}, {1,1,1}, {0,1,0}},
                    {{0,1,0}, {1,1,0}, {0,1,0}}
            },
            // S
            {
                    {{0,1,1}, {1,1,0}, {0,0,0}},
                    {{0,1,0}, {0,1,1}, {0,0,1}},
                    {{0,0,0}, {0,1,1}, {1,1,0}},
                    {{1,0,0}, {1,1,0}, {0,1,0}}
            },
            // Z
            {
                    {{1,1,0}, {0,1,1}, {0,0,0}},
                    {{0,0,1}, {0,1,1}, {0,1,0}},
                    {{0,0,0}, {1,1,0}, {0,1,1}},
                    {{0,1,0}, {1,1,0}, {1,0,0}}
            },
            // J
            {
                    {{1,0,0}, {1,1,1}, {0,0,0}},
                    {{0,1,1}, {0,1,0}, {0,1,0}},
                    {{0,0,0}, {1,1,1}, {0,0,1}},
                    {{0,1,0}, {0,1,0}, {1,1,0}}
            },
            // L
            {
                    {{0,0,1}, {1,1,1}, {0,0,0}},
                    {{0,1,0}, {0,1,0}, {0,1,1}},
                    {{0,0,0}, {1,1,1}, {1,0,0}},
                    {{1,1,0}, {0,1,0}, {0,1,0}}
            }
    };

    private static final Color[] COLORS = {
            Color.CYAN, Color.YELLOW, Color.MAGENTA, Color.GREEN,
            Color.RED, Color.BLUE, Color.ORANGE
    };

    public Tetromino(TetrominoType type) {
        this.type = type;
        this.rotation = 0;
        this.shape = SHAPES[type.ordinal()][rotation];
        this.color = COLORS[type.ordinal()];
        this.row = 0;
        this.col = 3;
    }

    public static Tetromino randomTetromino() {
        Random rand = new Random();
        TetrominoType[] types = TetrominoType.values();
        return new Tetromino(types[rand.nextInt(types.length)]);
    }

    public void rotate(Board board, int dir) {
        int[][] newShape = rotateMatrix(shape, dir);
        int newRot = (rotation + dir + 4) % 4;

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

    private int[][] rotateMatrix(int[][] mat, int dir) {
        int n = mat.length, m = mat[0].length;
        int[][] rotated = new int[m][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (dir == 1)
                    rotated[j][n - 1 - i] = mat[i][j];
                else
                    rotated[m - 1 - j][i] = mat[i][j];
        return rotated;
    }
}
