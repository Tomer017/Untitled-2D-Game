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

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = true;
        }

        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }

        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }

        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
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

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = false;
        }

        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }

        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }

        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }
}