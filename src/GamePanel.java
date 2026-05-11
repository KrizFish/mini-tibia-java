import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int TILE_SIZE = 32;
    
    private Player player;
    private Enemy[] enemies;
    private NPC[] npcs;
    private TileMap map;
    
    private boolean[] keys = new boolean[256];
    private int fps = 0;
    private long lastFpsTime = System.currentTimeMillis();
    
    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        
        // Inicializar componentes
        map = new TileMap(25, 19); // 800/32 = 25, 600/32 = 18.75
        player = new Player(400, 300, TILE_SIZE);
        
        // Criar inimigos com diferentes tipos
        enemies = new Enemy[3];
        enemies[0] = new Enemy(200, 200, TILE_SIZE, "Goblin");
        enemies[1] = new Enemy(600, 400, TILE_SIZE, "Orc");
        enemies[2] = new Enemy(300, 500, TILE_SIZE, "Spider");
        
        // Criar NPCs
        npcs = new NPC[2];
        npcs[0] = new NPC(100, 100, TILE_SIZE, "Bartender", "Bem-vindo ao Mini Tibia!");
        npcs[1] = new NPC(700, 100, TILE_SIZE, "Guard", "Cuidado com os monstros!");
        
        // Listeners de teclado
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
                if (e.getKeyCode() < 256) {
                    keys[e.getKeyCode()] = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() < 256) {
                    keys[e.getKeyCode()] = false;
                }
            }
        });
        
        // Game loop
        Timer timer = new Timer(30, e -> {
            update();
            repaint();
            updateFPS();
        });
        timer.start();
    }
    
    private void updateFPS() {
        fps++;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFpsTime >= 1000) {
            lastFpsTime = currentTime;
            setTitle("Mini Tibia - Java Edition | FPS: " + fps);
            fps = 0;
        }
    }
    
    private void update() {
        // Movimento do player
        int dx = 0, dy = 0;
        
        if (keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A]) {
            dx = -5;
        }
        if (keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D]) {
            dx = 5;
        }
        if (keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W]) {
            dy = -5;
        }
        if (keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S]) {
            dy = 5;
        }
        
        // Verificar colisão antes de mover
        int newX = player.getX() + dx;
        int newY = player.getY() + dy;
        
        // Limite da tela
        if (newX >= 0 && newX + TILE_SIZE <= WIDTH) {
            player.setX(newX);
        }
        if (newY >= 0 && newY + TILE_SIZE <= HEIGHT) {
            player.setY(newY);
        }
        
        // Colisão com NPCs
        for (NPC npc : npcs) {
            if (player.collidesWith(npc)) {
                System.out.println("[NPC " + npc.getName() + "]: " + npc.getMessage());
            }
        }
        
        // Movimento e AI dos inimigos
        for (Enemy enemy : enemies) {
            enemy.update(player);
            
            // Colisão com player
            if (player.collidesWith(enemy)) {
                player.takeDamage(1); // Dano contínuo
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Desenhar mapa
        map.draw(g2d, TILE_SIZE);
        
        // Desenhar NPCs
        for (NPC npc : npcs) {
            npc.draw(g2d);
        }
        
        // Desenhar inimigos
        for (Enemy enemy : enemies) {
            enemy.draw(g2d);
        }
        
        // Desenhar player
        player.draw(g2d);
        
        // UI
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("HP: " + player.getHp() + "/" + player.getMaxHp(), 10, 30);
        g2d.drawString("Pos: " + player.getX() + ", " + player.getY(), 10, 60);
        g2d.drawString("Inimigos: " + countAliveEnemies(), 10, 90);
        g2d.drawString("Controles: SETAS/WASD para mover, ESC para sair", 10, 120);
    }
    
    private int countAliveEnemies() {
        int count = 0;
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                count++;
            }
        }
        return count;
    }
}
