import java.awt.*;

public class Player extends Entity {
    private int hp = 100;
    private int maxHp = 100;
    private int level = 1;
    private int xp = 0;
    private int gold = 0;
    private int crystals = 0;
    private long lastDamageTime = 0;
    private static final long DAMAGE_COOLDOWN = 500;
    private String direction = "down";
    private int inventoryCount = 0;
    
    public Player(int x, int y, int size) {
        super(x, y, size);
    }
    
    public void draw(Graphics2D g) {
        // Corpo - armadura azul
        g.setColor(new Color(0, 100, 255));
        g.fillRect(x + 8, y + 8, size - 16, size - 8);
        
        // Capacete com pluma vermelha
        g.setColor(new Color(0, 80, 200));
        g.fillRect(x + 10, y + 2, size - 20, 6);
        g.setColor(Color.RED);
        g.fillRect(x + (size/2) - 2, y - 2, 4, 5);
        
        // Escudo (esquerda)
        g.setColor(new Color(200, 150, 0));
        g.fillOval(x - 5, y + 12, 8, 12);
        
        // Espada (direita)
        g.setColor(Color.GRAY);
        g.fillRect(x + size - 3, y + 12, 2, 10);
        g.setColor(new Color(200, 150, 0));
        g.fillRect(x + size - 4, y + 10, 4, 3);
        
        // Olhos
        g.setColor(Color.WHITE);
        g.fillOval(x + 12, y + 16, 4, 4);
        g.fillOval(x + 24, y + 16, 4, 4);
        g.setColor(Color.BLACK);
        g.fillOval(x + 13, y + 17, 2, 2);
        g.fillOval(x + 25, y + 17, 2, 2);
        
        // Barra de HP
        drawHPBar(g);
    }
    
    private void drawHPBar(Graphics2D g) {
        int barWidth = size;
        int barHeight = 5;
        int barX = x;
        int barY = y - 12;
        
        g.setColor(Color.RED);
        g.fillRect(barX, barY, barWidth, barHeight);
        
        g.setColor(new Color(0, 200, 0));
        int healthWidth = (int)(barWidth * hp / (double)maxHp);
        g.fillRect(barX, barY, healthWidth, barHeight);
        
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(1));
        g.drawRect(barX, barY, barWidth, barHeight);
    }
    
    public void takeDamage(int damage) {
        long currentTime = System.currentTimeMillis();
        
        if (currentTime - lastDamageTime > DAMAGE_COOLDOWN) {
            hp = Math.max(0, hp - damage);
            lastDamageTime = currentTime;
            System.out.println("[DANO] -" + damage + " HP. HP Atual: " + hp + "/" + maxHp);
        }
    }
    
    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }
    
    public void addXp(int amount) {
        xp += amount;
        if (xp >= 100) {
            levelUp();
        }
    }
    
    private void levelUp() {
        level++;
        xp = 0;
        maxHp += 10;
        hp = maxHp;
        System.out.println("[LEVEL UP!] Você atingiu level " + level + "!");
    }
    
    public void addGold(int amount) {
        gold += amount;
        System.out.println("[+OURO] +" + amount + " moedas. Total: " + gold);
    }
    
    public void addCrystal(int amount) {
        crystals += amount;
        addXp(20 * amount);
        System.out.println("[+CRISTAL] +" + amount + " cristais. Total: " + crystals);
    }
    
    public void collectItem(ItemDrop item) {
        if (item.getType().equals("gold")) {
            addGold(item.getAmount());
        } else if (item.getType().equals("crystal")) {
            addCrystal(item.getAmount());
        }
        inventoryCount++;
    }
    
    public void setDirection(String dir) { this.direction = dir; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getLevel() { return level; }
    public int getXp() { return xp; }
    public int getGold() { return gold; }
    public int getCrystals() { return crystals; }
    public int getInventoryCount() { return inventoryCount; }
    public boolean isAlive() { return hp > 0; }
    public boolean isInCombat() { return System.currentTimeMillis() - lastDamageTime < 3000; }
}
