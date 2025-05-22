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

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used in this implementation
    }

    /**
     * Handles key press events.
     * Updates the corresponding movement flags when WASD keys are pressed.
     *
     * @param e The key event containing information about the pressed key
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
    }

    /**
     * Handles key release events.
     * Updates the corresponding movement flags when WASD keys are released.
     *
     * @param e The key event containing information about the released key
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}