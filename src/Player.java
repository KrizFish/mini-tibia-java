import java.awt.*;

public class Player extends Entity {
    private int hp = 100;
    private int maxHp = 100;
    private long lastDamageTime = 0;
    private static final long DAMAGE_COOLDOWN = 500; // ms
    
    public Player(int x, int y, int size) {
        super(x, y, size);
    }
    
    public void draw(Graphics2D g) {
        // Corpo do player
        g.setColor(new Color(0, 100, 255));
        g.fillRect(x, y, size, size);
        
        // Borda
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(3));
        g.drawRect(x, y, size, size);
        
        // Barra de HP acima
        drawHPBar(g);
        
        // Olhos
        g.setColor(Color.WHITE);
        g.fillOval(x + 8, y + 8, 5, 5);
        g.fillOval(x + 19, y + 8, 5, 5);
    }
    
    private void drawHPBar(Graphics2D g) {
        int barWidth = size;
        int barHeight = 5;
        int barX = x;
        int barY = y - 12;
        
        // Fundo vermelho
        g.setColor(Color.RED);
        g.fillRect(barX, barY, barWidth, barHeight);
        
        // Barra de vida verde
        g.setColor(new Color(0, 200, 0));
        int healthWidth = (int)(barWidth * hp / (double)maxHp);
        g.fillRect(barX, barY, healthWidth, barHeight);
        
        // Borda
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(1));
        g.drawRect(barX, barY, barWidth, barHeight);
    }
    
    public void takeDamage(int damage) {
        long currentTime = System.currentTimeMillis();
        
        // Cooldown entre danos
        if (currentTime - lastDamageTime > DAMAGE_COOLDOWN) {
            hp = Math.max(0, hp - damage);
            lastDamageTime = currentTime;
            System.out.println("[DANO] -" + damage + " HP. HP Atual: " + hp + "/" + maxHp);
        }
    }
    
    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }
    
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public boolean isAlive() { return hp > 0; }
}
