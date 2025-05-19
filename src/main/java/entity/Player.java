package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

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
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try{
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

        }catch (IOException e){
            e.printStackTrace();
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
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
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
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (keyH.upPressed) {
                    switch(spriteNum) {
                        case 1: image = up1; break;
                        case 2: image = up2; break;
                        case 3: image = up3; break;
                    }
                } else {
                    image = up1;
                }
                break;
            case "down":
                if (keyH.downPressed) {
                    switch(spriteNum) {
                        case 1: image = down1; break;
                        case 2: image = down2; break;
                        case 3: image = down3; break;
                    }
                } else {
                    image = down1;
                }
                break;
            case "left":
                if (keyH.leftPressed) {
                    switch(spriteNum) {
                        case 1: image = left1; break;
                        case 2: image = left2; break;
                        case 3: image = left3; break;
                    }
                } else {
                    image = left1;
                }
                break;
            case "right":
                if (keyH.rightPressed) {
                    switch(spriteNum) {
                        case 1: image = right1; break;
                        case 2: image = right2; break;
                        case 3: image = right3; break;
                    }
                } else {
                    image = right1;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }


}
