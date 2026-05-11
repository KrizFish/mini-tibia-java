import java.awt.*;

public class NPC extends Entity {
    private String name;
    private String message;
    private long lastInteractionTime = 0;
    private static final long INTERACTION_COOLDOWN = 2000;
    private float floatOffset = 0;
    
    public NPC(int x, int y, int size, String name, String message) {
        super(x, y, size);
        this.name = name;
        this.message = message;
    }
    
    @Override
    public void draw(Graphics2D g) {
        // Animação de flutuação
        floatOffset += 0.05f;
        float bobY = (float)Math.sin(floatOffset) * 3;
        
        int drawY = (int)(y + bobY);
        
        // Corpo dourado
        g.setColor(new Color(255, 200, 0));
        g.fillRect(x + 6, drawY + 8, 20, 16);
        
        // Cabeça
        g.fillOval(x + 8, drawY, 16, 12);
        
        // Aura mágica (círculo brilhante)
        g.setColor(new Color(255, 255, 100, 100));
        g.drawOval(x - 5, drawY - 5, 40, 40);
        g.drawOval(x - 3, drawY - 3, 36, 36);
        
        // Olhos
        g.setColor(Color.BLACK);
        g.fillOval(x + 10, drawY + 3, 2, 2);
        g.fillOval(x + 20, drawY + 3, 2, 2);
        
        // Nome
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 9));
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(name);
        g.drawString(name, x + (size - textWidth) / 2, drawY + 28);
    }
    
    public void interact() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastInteractionTime > INTERACTION_COOLDOWN) {
            System.out.println("\n[" + name + "]: \"" + message + "\"");
            lastInteractionTime = currentTime;
        }
    }
    
    public String getName() { return name; }
    public String getMessage() { return message; }
}
