package com.example.speler.input;

import com.example.speler.Vector2;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Input class adapter to work with both Swing and GLFW/OpenGL
 * You'll need to add these methods to your existing Input class
 */
public class Input {
    
    private static Set<Integer> pressedKeys = new HashSet<>();
    private static Set<Integer> pressedMouseButtons = new HashSet<>();
    private static Vector2 mousePosition = new Vector2(0, 0);
    private static Vector2 mousePositionOnCanvas = new Vector2(0, 0);
    private static float scrollValue = 0;
    private static int mousePressed = -1;

    // GLFW key codes - add these methods to handle GLFW input
    public static void addKey(int glfwKeyCode) {
        pressedKeys.add(glfwKeyCode);
    }

    public static void removeKey(int glfwKeyCode) {
        pressedKeys.remove(glfwKeyCode);
    }

    public static void addMouseButton(int glfwButton) {
        pressedMouseButtons.add(glfwButton);
				mousePressed = glfwButton;
    }

    public static void removeMouseButton(int glfwButton) {
        pressedMouseButtons.remove(glfwButton);
				if(mousePressed == glfwButton)  mousePressed = -1;
    }

    public static void setMousePressed(int button) {
        mousePressed = button;
    }

    // Existing Swing methods - keep these for backward compatibility
    public static void addKey(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    public static void removeKey(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    public static void addMouseButton(MouseEvent e) {
        pressedMouseButtons.add(e.getButton());
    }

    public static void removeMouseButton(MouseEvent e) {
        pressedMouseButtons.remove(e.getButton());

    }

    public static void setMouseEvent(MouseEvent e) {
        // Compatibility method for Swing
    }

    // Common methods
    public static void setMousePosition(Vector2 pos) {
        mousePosition = pos;
    }

    public static void setMousePositionOnCanvas(Vector2 pos) {
        mousePositionOnCanvas = pos;
    }

    public static Vector2 getMousePosition() {
        return mousePosition;
    }

    public static Vector2 getMousePositionOnCanvas() {
        return mousePositionOnCanvas;
    }

    public static void setScrollValue(float value) {
        scrollValue = value;
    }

    public static float getScrollValue() {
        return scrollValue;
    }

    public static void resetScrollValue() {
        scrollValue = 0;
    }

    public static boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }
    
    public static boolean isKeyDown(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    public static boolean isMouseButtonPressed(int button) {
        return mousePressed == button;
    }
    
    public static boolean isMouseButtonDown(int button) {
        return pressedMouseButtons.contains(button);
    }

    public static int getMousePressed() {
        return mousePressed;
    }

    public static void resetMousePressed() {
        mousePressed = -1;
    }

    // GLFW key code constants - map common keys
    public static class Keys {
        // Add GLFW key codes here
        public static final int W = 87;
        public static final int A = 65;
        public static final int S = 83;
        public static final int D = 68;
        public static final int SPACE = 32;
        public static final int ESCAPE = 256;
        public static final int LEFT = 263;
        public static final int RIGHT = 262;
        public static final int UP = 265;
        public static final int DOWN = 264;
        // Add more as needed
    }

    public static class Mouse {
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
        public static final int MIDDLE = 2;
    }
}
