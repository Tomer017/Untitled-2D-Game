package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

/**
 * Represents the player character in the game.
 * Handles player movement, animation, and rendering based on user input.
 * Extends the base Entity class with player-specific functionality.
 */
public final class Player extends Entity {
    /** Reference to the main game panel */
    private final GamePanel gp;
    /** Reference to the keyboard input handler */
    private final KeyHandler keyH;

    /** Fixed screen position X coordinate */
    public final int screenX;
    /** Fixed screen position Y coordinate */
    public final int screenY;

    /**
     * Constructs a new Player with the specified game panel and key handler.
     *
     * @param gp The game panel this player belongs to
     * @param keyH The key handler for processing player input
     */
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        loadPlayerImages();
    }

    /**
     * Sets the default values for player position, speed, and direction.
     */
    private void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    /**
     * Loads all player sprite images for different directions and animation frames.
     * 
     * @throws RuntimeException if there's an error loading any sprite image
     */
    private void loadPlayerImages() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/IMG_2395.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/IMG_2396.png")));
            up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/IMG_2397.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/IMG_2386.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/IMG_2387.png")));
            down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/IMG_2388.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/IMG_2389.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/IMG_2390.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/IMG_2391.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/IMG_2392.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/IMG_2393.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/IMG_2394.png")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load player images", e);
        }
    }

    /**
     * Updates the player's state including position and animation.
     * Handles movement based on keyboard input and collision detection.
     */
    public void update() {
        boolean moving = false;
        int horizontalMovement = keyH.getHorizontalMovement();
        int verticalMovement = keyH.getVerticalMovement();

        // Set direction based on movement priority
        // Horizontal movement takes precedence over vertical
        if (horizontalMovement != 0) {
            moving = true;
            direction = horizontalMovement > 0 ? "right" : "left";
        } else if (verticalMovement != 0) {
            moving = true;
            direction = verticalMovement > 0 ? "down" : "up";
        }

        // CHECK TILE COLLISION
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // IF COLLISION IS FALSE, PLAYER CAN MOVE
        if (moving && !collisionOn) {
            // Apply movement (no diagonal movement)
            if (horizontalMovement != 0) {
                worldX += horizontalMovement * speed;
            } else if (verticalMovement != 0) {
                worldY += verticalMovement * speed;
            }
            
            // Animate only when moving
            spriteCounter++;
            if (spriteCounter > 8) {
                spriteNum = (spriteNum % 3) + 1;  // Cycles through 1, 2, 3
                spriteCounter = 0;
            }
        } else {
            // Reset to standing position when not moving
            spriteNum = 1;
            spriteCounter = 0;
        }
    }

    /**
     * Renders the player on the screen.
     *
     * @param g2 The Graphics2D object used for rendering
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = getDirectionalImage();
        Objects.requireNonNull(image, "Player sprite image cannot be null");
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

    /**
     * Gets the appropriate sprite image based on the player's direction and movement state.
     *
     * @return The BufferedImage to use for the current frame
     */
    private BufferedImage getDirectionalImage() {
        return switch (direction) {
            case "up" -> keyH.upPressed ? getSpriteFrame(up1, up2, up3) : up1;
            case "down" -> keyH.downPressed ? getSpriteFrame(down1, down2, down3) : down1;
            case "left" -> keyH.leftPressed ? getSpriteFrame(left1, left2, left3) : left1;
            case "right" -> keyH.rightPressed ? getSpriteFrame(right1, right2, right3) : right1;
            default -> down1;
        };
    }

    /**
     * Selects the appropriate sprite frame from the animation sequence.
     *
     * @param frame1 First frame of the animation
     * @param frame2 Second frame of the animation
     * @param frame3 Third frame of the animation
     * @return The selected frame based on current animation state
     */
    private BufferedImage getSpriteFrame(BufferedImage frame1, BufferedImage frame2, BufferedImage frame3) {
        return switch (spriteNum) {
            case 1 -> frame1;
            case 2 -> frame2;
            case 3 -> frame3;
            default -> frame1;
        };
    }
}
