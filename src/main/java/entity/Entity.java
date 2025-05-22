package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Base class for all game entities (players, NPCs, etc.).
 * Provides common properties and functionality for any object that exists in the game world.
 */
public class Entity {
    /** X coordinate in the world space */
    public int worldX;
    /** Y coordinate in the world space */
    public int worldY;
    /** Movement speed of the entity */
    public int speed;

    /** Sprite images for different movement directions and animation frames */
    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
    /** Current facing direction of the entity */
    public String direction;

    /** Counter for sprite animation timing */
    public int spriteCounter = 0;
    /** Current sprite number in the animation sequence */
    public int spriteNum = 1;

    /** Collision area for the entity */
    public Rectangle solidArea;
    /** Flag indicating if the entity is currently colliding */
    public boolean collisionOn = false;
}
