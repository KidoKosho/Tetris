package Tetris;

public class WallKick {
    // Kick data cho JLSTZ: 4 trạng thái (0 → R, R → 2, 2 → L, L → 0)
    private static final int[][][][] JLSTZ_KICKS = {
            // 0 -> R
            {{{0, 0}, {-1, 0}, {-1, +1}, {0, -2}, {-1, -2}}},
            // R -> 2
            {{{0, 0}, {+1, 0}, {+1, -1}, {0, +2}, {+1, +2}}},
            // 2 -> L
            {{{0, 0}, {+1, 0}, {+1, +1}, {0, -2}, {+1, -2}}},
            // L -> 0
            {{{0, 0}, {-1, 0}, {-1, -1}, {0, +2}, {-1, +2}}}
    };

    // Kick data cho I Tetromino: khác biệt hoàn toàn
    private static final int[][][][] I_KICKS = {
            // 0 -> R
            {{{0, 0}, {-2, 0}, {+1, 0}, {-2, -1}, {+1, +2}}},
            // R -> 2
            {{{0, 0}, {-1, 0}, {+2, 0}, {-1, +2}, {+2, -1}}},
            // 2 -> L
            {{{0, 0}, {+2, 0}, {-1, 0}, {+2, +1}, {-1, -2}}},
            // L -> 0
            {{{0, 0}, {+1, 0}, {-2, 0}, {+1, -2}, {-2, +1}}}
    };

    // Hàm xử lý wrap-around để tránh lỗi (3 + 1 = 0, 0 - 1 = 3)
    private static int transitionIndex(int from, int to) {
        return (from * 4 + to - from + 4) % 4;
    }

    public static int[][] getKicks(Tetromino.TetrominoType type, int from, int to) {
        if (type == Tetromino.TetrominoType.O) return new int[][]{{0, 0}};
        int index = transitionIndex(from, to);
        if (type == Tetromino.TetrominoType.I)
            return I_KICKS[index][0];
        else
            return JLSTZ_KICKS[index][0];
    }
}
