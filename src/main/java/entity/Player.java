package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public final class Player extends Entity {
    private final GamePanel gp;
    private final KeyHandler keyH;

    public final int screenX;
    public final int screenY;

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

    private void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

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

    public void update() {
        boolean moving = false;

        if (keyH.upPressed) {
            direction = "up";
            moving = true;
        } else if (keyH.downPressed) {
            direction = "down";
            moving = true;
        } else if (keyH.leftPressed) {
            direction = "left";
            moving = true;
        } else if (keyH.rightPressed) {
            direction = "right";
            moving = true;
        }

        //CHECK TILE COLLISION
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // IF COLLISION IS FALSE, PLAYER CAN MOVE
        if (moving && !collisionOn) {
            switch(direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
            
            // Only animate if moving
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

    public void draw(Graphics2D g2) {
        BufferedImage image = getDirectionalImage();
        Objects.requireNonNull(image, "Player sprite image cannot be null");
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

    private BufferedImage getDirectionalImage() {
        return switch (direction) {
            case "up" -> keyH.upPressed ? getSpriteFrame(up1, up2, up3) : up1;
            case "down" -> keyH.downPressed ? getSpriteFrame(down1, down2, down3) : down1;
            case "left" -> keyH.leftPressed ? getSpriteFrame(left1, left2, left3) : left1;
            case "right" -> keyH.rightPressed ? getSpriteFrame(right1, right2, right3) : right1;
            default -> down1;
        };
    }

    private BufferedImage getSpriteFrame(BufferedImage frame1, BufferedImage frame2, BufferedImage frame3) {
        return switch (spriteNum) {
            case 1 -> frame1;
            case 2 -> frame2;
            case 3 -> frame3;
            default -> frame1;
        };
    }
}
