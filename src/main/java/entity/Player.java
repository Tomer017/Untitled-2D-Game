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

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
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
        if (keyH.upPressed) {
            y -= speed;
            direction = "up";
        } else if (keyH.downPressed) {
            y += speed;
            direction = "down";
        } else if (keyH.leftPressed) {
            x -= speed;
            direction = "left";
        } else if (keyH.rightPressed) {
            x += speed;
            direction = "right";
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

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null );


    }


}
