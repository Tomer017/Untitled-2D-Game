package Tile;

import java.awt.image.BufferedImage;

/**
 * Represents a single tile in the game world.
 * Each tile has an image and collision properties, used to build the game's environment.
 */
public class Tile {
    /** The visual representation of the tile */
    public BufferedImage image;
    /** Whether entities can pass through this tile */
    public boolean collision = false;
}
