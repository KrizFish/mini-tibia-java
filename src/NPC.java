import java.awt.*;

public class NPC extends Entity {
    private String name;
    private String message;
    private long lastInteractionTime = 0;
    private static final long INTERACTION_COOLDOWN = 2000; // ms
    
    public NPC(int x, int y, int size, String name, String message) {
        super(x, y, size);
        this.name = name;
        this.message = message;
    }
    
    @Override
    public void draw(Graphics2D g) {
        // Corpo
        g.setColor(new Color(255, 200, 0));
        g.fillRect(x, y, size, size);
        
        // Borda
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawRect(x, y, size, size);
        
        // Nome
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 11));
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(name);
        g.drawString(name, x + (size - textWidth) / 2, y - 5);
        
        // Rosto simples (smilie)
        g.setColor(Color.BLACK);
        g.fillOval(x + 8, y + 8, 4, 4);
        g.fillOval(x + 20, y + 8, 4, 4);
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
