import java.awt.*;

public class Enemy extends Entity {
    private String name;
    private int moveCounter = 0;
    private int hp = 20;
    private int maxHp = 20;
    private Color color;
    private int startX, startY;
    private long deathTime = 0;
    private static final long RESPAWN_TIME = 5000;
    
    public Enemy(int x, int y, int size, String name) {
        super(x, y, size);
        this.name = name;
        this.startX = x;
        this.startY = y;
        
        switch (name) {
            case "Goblin":
                this.color = new Color(100, 150, 100);
                break;
            case "Orc":
                this.color = new Color(200, 50, 50);
                break;
            case "Spider":
                this.color = new Color(120, 50, 150);
                break;
            default:
                this.color = Color.RED;
        }
    }
    
    public void update(Player player) {
        if (!isAlive()) return;
        
        moveCounter++;
        
        int distX = Math.abs(player.getX() - x);
        int distY = Math.abs(player.getY() - y);
        int distance = (int)Math.sqrt(distX * distX + distY * distY);
        
        if (distance < 200) {
            if (player.getX() > x) x += 1.5;
            else if (player.getX() < x) x -= 1.5;
            
            if (player.getY() > y) y += 1.5;
            else if (player.getY() < y) y -= 1.5;
        } else {
            if (moveCounter % 40 == 0) {
                int dir = (int)(Math.random() * 4);
                switch (dir) {
                    case 0: x += 3; break;
                    case 1: x -= 3; break;
                    case 2: y += 3; break;
                    case 3: y -= 3; break;
                }
            }
        }
        
        x = Math.max(0, Math.min(800 - size, x));
        y = Math.max(0, Math.min(600 - size, y));
    }
    
    @Override
    public void draw(Graphics2D g) {
        if (!isAlive()) return;
        
        // Corpo
        g.setColor(color);
        
        if (name.equals("Spider")) {
            drawSpider(g);
        } else if (name.equals("Orc")) {
            drawOrc(g);
        } else {
            drawGoblin(g);
        }
        
        // Barra de HP
        drawHPBar(g);
    }
    
    private void drawGoblin(Graphics2D g) {
        // Corpo verde
        g.fillOval(x + 8, y + 10, 16, 12);
        
        // Cabeça
        g.fillOval(x + 10, y + 2, 12, 10);
        
        // Presas amarelas
        g.setColor(Color.YELLOW);
        g.fillRect(x + 12, y + 10, 2, 3);
        g.fillRect(x + 18, y + 10, 2, 3);
        
        // Olhos vermelhos
        g.setColor(Color.RED);
        g.fillOval(x + 11, y + 5, 2, 2);
        g.fillOval(x + 19, y + 5, 2, 2);
    }
    
    private void drawOrc(Graphics2D g) {
        // Corpo musculoso
        g.fillRect(x + 6, y + 12, 20, 14);
        
        // Braços musculosos
        g.fillRect(x + 2, y + 14, 4, 8);
        g.fillRect(x + 28, y + 14, 4, 8);
        
        // Cabeça
        g.fillOval(x + 10, y + 2, 12, 10);
        
        // Chifres
        g.setColor(new Color(100, 100, 100));
        g.fillRect(x + 11, y - 3, 2, 4);
        g.fillRect(x + 21, y - 3, 2, 4);
        
        // Olhos malvados
        g.setColor(Color.YELLOW);
        g.fillOval(x + 12, y + 5, 2, 2);
        g.fillOval(x + 20, y + 5, 2, 2);
    }
    
    private void drawSpider(Graphics2D g) {
        // Corpo roxo
        g.fillOval(x + 12, y + 12, 8, 8);
        
        // Cabeça
        g.fillOval(x + 13, y + 5, 6, 6);
        
        // 8 pernas (4 de cada lado)
        g.setStroke(new BasicStroke(2));
        int legX1 = x + 12;
        int legX2 = x + 20;
        int legY = y + 14;
        
        for (int i = 0; i < 4; i++) {
            g.drawLine(legX1, legY + (i * 3), legX1 - 5, legY + (i * 3) + 3);
            g.drawLine(legX2, legY + (i * 3), legX2 + 5, legY + (i * 3) + 3);
        }
        
        // Olhos
        g.setColor(Color.RED);
        g.fillOval(x + 14, y + 7, 1, 1);
        g.fillOval(x + 17, y + 7, 1, 1);
    }
    
    private void drawHPBar(Graphics2D g) {
        int barWidth = size;
        int barHeight = 3;
        
        g.setColor(Color.RED);
        g.fillRect(x, y - 6, barWidth, barHeight);
        
        g.setColor(new Color(0, 200, 0));
        int healthWidth = (int)(barWidth * hp / (double)maxHp);
        g.fillRect(x, y - 6, healthWidth, barHeight);
    }
    
    public void takeDamage(int damage) {
        hp = Math.max(0, hp - damage);
        if (hp <= 0) {
            deathTime = System.currentTimeMillis();
        }
    }
    
    public void checkRespawn() {
        if (!isAlive() && System.currentTimeMillis() - deathTime > RESPAWN_TIME) {
            hp = maxHp;
            x = startX;
            y = startY;
        }
    }
    
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public String getName() { return name; }
    public boolean isAlive() { return hp > 0; }
}
