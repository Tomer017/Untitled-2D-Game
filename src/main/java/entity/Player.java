package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

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
        // ONLY UPDATE IMAGE WHEN NEW DIRECTION IS GIVEN
        if (moving){
            if (!collisionOn){
                switch(direction){
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
            }
        }


        spriteCounter++;
        if (spriteCounter > 10) {
            if (spriteNum == 1){
                spriteNum = 2;
            }
            else if (spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                image = up1;
                if (keyH.upPressed) {
                    if (spriteNum == 1){
                        image = up2;
                    }
                    if (spriteNum == 2){
                        image = up3;
                    }

                }
                break;
            case "down":
                image = down1;
                if (keyH.downPressed) {
                    if (spriteNum == 1){
                        image = down2;
                    }
                    if (spriteNum == 2){
                        image = down3;
                    }
                }
                break;
            case "left":
                image = left1;
                if (keyH.leftPressed) {
                    if (spriteNum == 1){
                        image = left2;
                    }
                    if (spriteNum == 2){
                        image = left3;
                    }
                }
                break;
            case "right":
                image = right1;
                if (keyH.rightPressed) {
                    if (spriteNum == 1){
                        image = right2;
                    }
                    if (spriteNum == 2){
                        image = right3;
                    }
                }
                break;
            default:
                image = down1;
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null );


    }


}
