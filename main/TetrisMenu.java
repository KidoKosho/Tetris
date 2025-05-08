package Tetris.main;
import javax.swing.*;
import java.awt.*;

public class TetrisMenu extends JFrame {

    public TetrisMenu() {
        setTitle("Tetris Menu");
        setSize(300, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Canh giữa màn hình
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("TETRIS");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.CYAN);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = new JButton("Start Game");
        JButton howToPlayButton = new JButton("Cách chơi");

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        howToPlayButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.setMaximumSize(new Dimension(180, 40));
        howToPlayButton.setMaximumSize(new Dimension(180, 40));

        startButton.addActionListener(e -> {
            // TODO: Gọi game chính tại đây
            App.getInstance().PlayGame();
            // new TetrisGame().start();  // Ví dụ nếu bạn có class game riêng
        });

        howToPlayButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "▶ Dùng các phím mũi tên để điều khiển:\n" +
                            "←: Di chuyển trái\n" +
                            "→: Di chuyển phải\n" +
                            "↓: Rơi nhanh\n" +
                            "↑: Xoay khối\n" +
                            "\nHoàn thành hàng để ghi điểm!",
                    "Cách chơi",
                    JOptionPane.INFORMATION_MESSAGE);   
        });

        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(startButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(howToPlayButton);

        add(panel);
        setVisible(true);
    }
}
