import java.awt.*;

public class TileMap {
    private int[][] tiles;
    private int width, height;
    private static final int TILE_GRAMA = 0;
    private static final int TILE_AGUA = 1;
    private static final int TILE_ARVORE = 2;
    
    public TileMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new int[height][width];
        
        // Gerar mapa com Perlin noise simples
        generateMap();
    }
    
    private void generateMap() {
        // Preencher com grama
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double random = Math.random();
                
                if (random < 0.08) {
                    tiles[y][x] = TILE_AGUA;
                } else if (random < 0.15) {
                    tiles[y][x] = TILE_ARVORE;
                } else {
                    tiles[y][x] = TILE_GRAMA;
                }
            }
        }
        
        // Criar um caminho no meio
        for (int x = 0; x < width; x++) {
            tiles[height / 2][x] = TILE_GRAMA;
        }
        for (int y = 0; y < height; y++) {
            tiles[y][width / 2] = TILE_GRAMA;
        }
    }
    
    public void draw(Graphics2D g, int tileSize) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelX = x * tileSize;
                int pixelY = y * tileSize;
                
                switch (tiles[y][x]) {
                    case TILE_AGUA:
                        g.setColor(new Color(0, 102, 204));
                        break;
                    case TILE_ARVORE:
                        g.setColor(new Color(34, 139, 34));
                        break;
                    case TILE_GRAMA:
                    default:
                        g.setColor(new Color(144, 238, 144));
                }
                
                g.fillRect(pixelX, pixelY, tileSize, tileSize);
                
                // Grades
                g.setColor(new Color(100, 100, 100, 50));
                g.setStroke(new BasicStroke(1));
                g.drawRect(pixelX, pixelY, tileSize, tileSize);
            }
        }
    }
    
    public int getTile(int x, int y) {
        int tileX = x / 32;
        int tileY = y / 32;
        
        if (tileX < 0 || tileX >= width || tileY < 0 || tileY >= height) {
            return TILE_AGUA; // Fora do mapa = parede
        }
        
        return tiles[tileY][tileX];
    }
    
    public boolean isWalkable(int x, int y) {
        return getTile(x, y) == TILE_GRAMA;
    }
}
