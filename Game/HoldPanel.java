package Tetris.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Tetris.Tetromino;
import Tetris.Board.Board;

/**
 * Panel hiển thị khối Tetromino đang được giữ (Hold) trong game Tetris.
 * Cho phép người chơi click chuột để thực hiện hành động giữ (hold) nếu được phép.
 */
public class HoldPanel extends JPanel {
    private Tetromino held; // Khối Tetromino hiện tại đang được giữ
    private boolean usedThisTurn; // Cờ đánh dấu đã dùng hold trong lượt hiện tại chưa
    private Board board; // Tham chiếu đến Board chính để gọi hold()

    private int WidthHold = 120, HeightHold = 120; // Kích thước của Hold panel

    // Constructor mặc định (chủ yếu dùng trong test/khởi tạo sớm)
    public HoldPanel(){
        OnInit();
    }

    // Constructor có truyền board, cho phép click để hold
    public HoldPanel(Board board) {
        SetBoard(board); // Gán board và thêm sự kiện click
        OnInit();        // Khởi tạo panel
    }

    // Hàm khởi tạo giao diện của panel
    public void OnInit() {
        setPreferredSize(new Dimension(WidthHold, HeightHold));
        setBackground(Color.DARK_GRAY); // Màu nền tối
    }

    /**
     * Gán board và thêm MouseListener để lắng nghe sự kiện click chuột.
     */
    public void SetBoard(Board board) {
        this.board = board;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                board.hold(); // Khi click chuột sẽ gọi hàm hold của board
            }
        });
    }

    /**
     * Thiết lập Tetromino đang giữ và cập nhật trạng thái đã dùng trong lượt chưa.
     * @param t Khối Tetromino được giữ
     * @param usedThisTurn Đã giữ trong lượt hiện tại hay chưa
     */
    public void setHeld(Tetromino t, boolean usedThisTurn) {
        this.held = t;
        this.usedThisTurn = usedThisTurn;
        repaint(); // Vẽ lại panel
    }

    /**
     * Override phương thức paintComponent để vẽ Tetromino đang giữ.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (held != null) {
            int[][] shape = held.getShape();  // Ma trận biểu diễn hình dạng Tetromino
            g.setColor(held.getColor());      // Màu của Tetromino
            int row = shape.length;
            int col = shape[0].length;
            int sizeBox = 20;

            // Tính vị trí để vẽ ở giữa panel
            int xOffset = (WidthHold - sizeBox * col) / 2;
            int yOffset = (HeightHold - sizeBox * row) / 2;

            // Duyệt qua từng ô của Tetromino và vẽ
            for (int y = 0; y < row; y++) {
                for (int x = 0; x < col; x++) {
                    if (shape[y][x] == 1) {
                        g.setColor(held.getColor()); // Màu chính
                        g.fillRect(x * sizeBox + xOffset, y * sizeBox + yOffset, sizeBox, sizeBox);
                        g.setColor(Color.BLACK);     // Viền đen
                        g.drawRect(x * sizeBox + xOffset, y * sizeBox + yOffset, sizeBox, sizeBox);
                    }
                }
            }
        }
    }
}
