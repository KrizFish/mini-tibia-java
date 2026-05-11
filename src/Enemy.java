import java.awt.*;

public class Enemy extends Entity {
    private String name;
    private int moveCounter = 0;
    private int hp = 20;
    private int maxHp = 20;
    private Color color;
    
    public Enemy(int x, int y, int size, String name) {
        super(x, y, size);
        this.name = name;
        
        // Cores diferentes para cada tipo
        switch (name) {
            case "Goblin":
                this.color = new Color(150, 75, 0);
                break;
            case "Orc":
                this.color = new Color(200, 0, 0);
                break;
            case "Spider":
                this.color = new Color(100, 0, 100);
                break;
            default:
                this.color = Color.RED;
        }
    }
    
    public void update(Player player) {
        if (!isAlive()) return;
        
        moveCounter++;
        
        // Calcular distância
        int distX = Math.abs(player.getX() - x);
        int distY = Math.abs(player.getY() - y);
        int distance = (int)Math.sqrt(distX * distX + distY * distY);
        
        if (distance < 200) {
            // PERSEGUIR player
            if (player.getX() > x) x += 1.5;
            else if (player.getX() < x) x -= 1.5;
            
            if (player.getY() > y) y += 1.5;
            else if (player.getY() < y) y -= 1.5;
        } else {
            // Patrulha aleatória
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
        
        // Limites da tela
        x = Math.max(0, Math.min(800 - size, x));
        y = Math.max(0, Math.min(600 - size, y));
    }
    
    @Override
    public void draw(Graphics2D g) {
        // Corpo
        g.setColor(color);
        g.fillRect(x, y, size, size);
        
        // Borda
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(2));
        g.drawRect(x, y, size, size);
        
        // Nome
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(name);
        g.drawString(name, x + (size - textWidth) / 2, y - 5);
        
        // Barra de HP
        drawHPBar(g);
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
    }
    
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public String getName() { return name; }
    public boolean isAlive() { return hp > 0; }
}
