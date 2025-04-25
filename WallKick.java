package Tetris;

public class WallKick {
    private static final int[][][] JLSTZ_KICKS = {
            {{0, 0}, {-1, 0}, {-1, +1}, {0, -2}, {-1, -2}},  // 0 > R
            {{0, 0}, {+1, 0}, {+1, -1}, {0, +2}, {+1, +2}},  // R > 2
            {{0, 0}, {+1, 0}, {+1, +1}, {0, -2}, {+1, -2}},  // 2 > L
            {{0, 0}, {-1, 0}, {-1, -1}, {0, +2}, {-1, +2}}   // L > 0
    };

    private static final int[][][] I_KICKS = {
            {{0, 0}, {-2, 0}, {+1, 0}, {-2, -1}, {+1, +2}},  // 0 > R
            {{0, 0}, {-1, 0}, {+2, 0}, {-1, +2}, {+2, -1}},  // R > 2
            {{0, 0}, {+2, 0}, {-1, 0}, {+2, +1}, {-1, -2}},  // 2 > L
            {{0, 0}, {+1, 0}, {-2, 0}, {+1, -2}, {-2, +1}}   // L > 0
    };

    public static int[][] getKicks(Tetromino.TetrominoType type, int from, int to) {
        if (type == Tetromino.TetrominoType.O) return new int[][]{{0, 0}};

        if (type == Tetromino.TetrominoType.I)
            return I_KICKS[from];
        else
            return JLSTZ_KICKS[from];
    }
}
