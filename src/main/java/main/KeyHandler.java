package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles keyboard input for the game.
 * Implements KeyListener to process key events and maintain the state of movement keys.
 */
public class KeyHandler implements KeyListener {
    /** Flag indicating if the up movement key is currently pressed */
    public boolean upPressed;
    /** Flag indicating if the down movement key is currently pressed */
    public boolean downPressed;
    /** Flag indicating if the left movement key is currently pressed */
    public boolean leftPressed;
    /** Flag indicating if the right movement key is currently pressed */
    public boolean rightPressed;

    /** Stores the last movement key pressed */
    private int lastKeyPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used in this implementation
    }

    /**
     * Handles key press events.
     * Updates the corresponding movement flags when WASD or arrow keys are pressed.
     * Also tracks the last pressed key for movement priority.
     *
     * @param e The key event containing information about the pressed key
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = true;
            lastKeyPressed = code;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = true;
            lastKeyPressed = code;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = true;
            lastKeyPressed = code;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
            lastKeyPressed = code;
        }
    }

    /**
     * Handles key release events.
     * Updates the corresponding movement flags when WASD or arrow keys are released.
     * Updates the last pressed key when necessary.
     *
     * @param e The key event containing information about the released key
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = false;
            if (lastKeyPressed == code) {
                updateLastKeyPressed();
            }
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = false;
            if (lastKeyPressed == code) {
                updateLastKeyPressed();
            }
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false;
            if (lastKeyPressed == code) {
                updateLastKeyPressed();
            }
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
            if (lastKeyPressed == code) {
                updateLastKeyPressed();
            }
        }
    }

    /**
     * Updates the last pressed key based on currently pressed keys.
     * Called when the current last pressed key is released.
     */
    private void updateLastKeyPressed() {
        if (rightPressed) lastKeyPressed = KeyEvent.VK_RIGHT;
        else if (leftPressed) lastKeyPressed = KeyEvent.VK_LEFT;
        else if (downPressed) lastKeyPressed = KeyEvent.VK_DOWN;
        else if (upPressed) lastKeyPressed = KeyEvent.VK_UP;
        else lastKeyPressed = 0;
    }

    /**
     * Gets the effective vertical movement based on the last pressed key.
     * 
     * @return 1 for downward movement, -1 for upward movement, 0 for no vertical movement
     */
    public int getVerticalMovement() {
        if (!upPressed && !downPressed) return 0;
        if (lastKeyPressed == KeyEvent.VK_UP || lastKeyPressed == KeyEvent.VK_W) return -1;
        if (lastKeyPressed == KeyEvent.VK_DOWN || lastKeyPressed == KeyEvent.VK_S) return 1;
        return 0;
    }

    /**
     * Gets the effective horizontal movement based on the last pressed key.
     * 
     * @return 1 for rightward movement, -1 for leftward movement, 0 for no horizontal movement
     */
    public int getHorizontalMovement() {
        if (!leftPressed && !rightPressed) return 0;
        if (lastKeyPressed == KeyEvent.VK_LEFT || lastKeyPressed == KeyEvent.VK_A) return -1;
        if (lastKeyPressed == KeyEvent.VK_RIGHT || lastKeyPressed == KeyEvent.VK_D) return 1;
        return 0;
    }
}