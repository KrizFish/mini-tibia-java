import javax.swing.*;
import java.awt.event.KeyEvent;

public class Game extends JFrame {
    private GamePanel gamePanel;

    public Game() {
        setTitle("Mini Tibia - Java Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        gamePanel = new GamePanel();
        add(gamePanel);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Game());
    }
}
