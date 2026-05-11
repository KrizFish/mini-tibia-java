import java.awt.*;

public class ItemDrop extends Entity {
    private String type; // "gold" ou "crystal"
    private int amount;
    private boolean active = true;
    private float floatOffset = 0;
    
    public ItemDrop(int x, int y, String type, int amount) {
        super(x, y, 16);
        this.type = type;
        this.amount = amount;
    }
    
    @Override
    public void draw(Graphics2D g) {
        if (!active) return;
        
        // Animação de flutuação e rotação
        floatOffset += 0.08f;
        float bobY = (float)Math.sin(floatOffset) * 2;
        float rotation = floatOffset;
        
        int drawY = (int)(y + bobY);
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(x + size / 2, drawY + size / 2);
        g2d.rotate(rotation);
        
        if (type.equals("gold")) {
            // Moeda de ouro giratória
            g2d.setColor(new Color(255, 215, 0));
            g2d.fillOval(-6, -6, 12, 12);
            g2d.setColor(new Color(200, 160, 0));
            g2d.drawOval(-6, -6, 12, 12);
            
            // Símbolo $ dentro
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 8));
            g2d.drawString("$", -2, 2);
        } else if (type.equals("crystal")) {
            // Cristal azul brilhante
            g2d.setColor(new Color(0, 150, 255));
            int[] xPoints = {-4, 0, 4, 0};
            int[] yPoints = {0, -6, 0, 6};
            g2d.fillPolygon(xPoints, yPoints, 4);
            
            g2d.setColor(new Color(100, 200, 255));
            g2d.drawPolygon(xPoints, yPoints, 4);
            
            // Brilho
            g2d.setColor(new Color(200, 230, 255, 150));
            g2d.fillOval(-2, -2, 4, 4);
        }
        
        g2d.dispose();
    }
    
    public String getType() { return type; }
    public int getAmount() { return amount; }
    public boolean isActive() { return active; }
    public void deactivate() { active = false; }
}
