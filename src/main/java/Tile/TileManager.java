package Tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import javax.imageio.ImageIO;

import main.GamePanel;

/**
 * Manages the game's tile system, including loading, storing, and rendering map tiles.
 * This class handles the game world's visual representation through a tile-based system,
 * supporting different tile types (grass, water, buildings, etc.) and their properties.
 */
public final class TileManager {
    /** Reference to the main game panel */
    private final GamePanel gp;
    /** Array storing all tile types used in the game */
    public Tile[] tile;
    /** 2D array representing the map layout using tile numbers */
    public int[][] mapTileNum;

    /**
     * Constructs a new TileManager with the specified game panel.
     * Initializes tile arrays and loads necessary resources.
     *
     * @param gp The game panel that this tile manager will work with
     */
    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[20];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    /**
     * Loads all tile images and initializes their properties.
     * Sets up different types of tiles including grass, buildings, water, and trees,
     * along with their collision properties.
     * 
     * @throws RuntimeException if there's an error loading any tile image
     */
    public void getTileImage() {
        try{
            // GRASS
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass/grass1.PNG")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass/grass2.PNG")));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass/grass3.PNG")));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass/grass4.PNG")));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass/grass5.PNG")));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass/grass6.PNG")));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Buildings/brick.PNG")));
            tile[6].collision = true;

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Water/water4.PNG")));
            tile[7].collision = true;

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/dirt1.PNG")));

            tile[9] = new Tile();
            tile[9].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Trees/treecomplete.PNG")));
            tile[9].collision = true;

        } catch (Exception e) {
            throw new RuntimeException("Failed to load tile images", e);
        }
    }

    /**
     * Loads a map from a text file and converts it into a tile-based world.
     * The map file should contain space-separated numbers representing tile types.
     *
     * @param mapFile The path to the map file in the resources directory
     * @throws RuntimeException if there's an error reading or parsing the map file
     */
    public void loadMap(String mapFile) {
        try {
            InputStream is = Objects.requireNonNull(getClass().getResourceAsStream(mapFile));
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                int col = 0;
                int row = 0;
                
                while (row < gp.maxWorldRow && col < gp.maxWorldCol) {
                    String line = br.readLine();
                    
                    while (col < gp.maxWorldCol){
                        String[] numbers = line.split(" ");
                        int num = Integer.parseInt(numbers[col]);
                        mapTileNum[col][row] = num;
                        col++;
                    }
                    if (col == gp.maxWorldCol) {
                        col = 0;
                        row++;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load map file: " + mapFile, e);
        }
    }

    /**
     * Renders the visible tiles on the screen based on the player's position.
     * Only draws tiles that are within the player's view range to optimize performance.
     *
     * @param g2 The Graphics2D object used for rendering
     */
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Only draw tiles visible on screen
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
