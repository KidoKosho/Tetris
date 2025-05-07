package Tetris.Game;

import javax.swing.*;
import java.awt.*;

/**
 * ScorePanel hiển thị thông tin: điểm số (Score), cấp độ (Level), và số dòng đã xóa (Lines).
 */
public class ScorePanel extends JPanel {
    private int scoreLabel; // Điểm số
    private int levelLabel; // Cấp độ
    private int linesLabel; // Số dòng đã xóa

    /**
     * Constructor khởi tạo ScorePanel với layout và giá trị mặc định.
     */
    public ScorePanel() {
        setPreferredSize(new Dimension(100, 150)); // Kích thước cố định
        setBackground(Color.DARK_GRAY);            // Nền tối
        setLayout(new GridLayout(3, 1));           // Bố cục 3 hàng
        scoreLabel = 0;
        levelLabel = 1;
        linesLabel = 0;
    }

    /**
     * Cập nhật điểm số, cấp độ và số dòng và yêu cầu vẽ lại giao diện.
     * @param score Điểm mới
     * @param level Cấp độ mới
     * @param lines Số dòng mới
     */
    public void updateScore(int score, int level, int lines) {
        this.scoreLabel = score;
        this.levelLabel = level;
        this.linesLabel = lines;
        repaint(); // Vẽ lại panel sau khi cập nhật dữ liệu
    }

    /**
     * Vẽ nội dung của ScorePanel: Score, Level, Lines
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE); // Màu chữ
        g.setFont(new Font("Arial", Font.BOLD, 10));
        // Vẽ text tại các vị trí cố định
        g.drawString("SCORE: " + scoreLabel, 35, 30);
        g.drawString("LEVEL: " + levelLabel, 35, 80);
        g.drawString("LINES: " + linesLabel, 35, 130);
    }
}
