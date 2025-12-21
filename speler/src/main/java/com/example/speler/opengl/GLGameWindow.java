package com.example.speler.opengl;

import com.example.speler.Game;
import com.example.speler.GameWindow;
import com.example.speler.Scene;
import com.example.speler.Vector2;
import com.example.speler.input.Input;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class GLGameWindow implements GameWindow {

    private long window;
    private Scene myScene;
    private int width = 800;
    private int height = 600;
    private GLRenderer renderer;
    
    // FPS tracking
    private int frameCount = 0;
    private double lastFpsTime = 0;
    private int fps = 0;

    public GLGameWindow() {
        init();
    }

    private void init() {
        // Setup error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        // Create the window
        window = glfwCreateWindow(width, height, "example", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup input callbacks
        setupInputCallbacks();

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                window,
                (vidmode.width() - pWidth.get(0)) / 2,
                (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(40f / 255f, 125f / 255f, 255f / 255f, 1.0f);

        // Enable blending for transparency
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Create renderer
        renderer = new GLRenderer(null);
    }

    private void setupInputCallbacks() {
        // Keyboard input
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (action == GLFW_PRESS) {
                Input.addKey(key);
            } else if (action == GLFW_RELEASE) {
                Input.removeKey(key);
            }
        });

        // Mouse button input
        glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
            if (action == GLFW_PRESS) {
                Input.addMouseButton(button);
								Input.setMousePressed(button);
								System.out.println(button);
            } else if (action == GLFW_RELEASE) {
                Input.removeMouseButton(button);
            }
        });

        // Mouse position input
        glfwSetCursorPosCallback(window, (window, xpos, ypos) -> {
            // Note: ypos is already correct for our flipped coordinate system
            // GLFW gives us coordinates with origin at top-left
            Input.setMousePositionOnCanvas(new Vector2((float) xpos, (float) ypos));
            
            if (myScene != null) {
                float worldX = myScene.getCamera().screenToWorldX((int) xpos, width);
                float worldY = myScene.getCamera().screenToWorldY((int) ypos, height);
                Input.setMousePosition(new Vector2(worldX, worldY));
            }
        });

        // Mouse scroll input
        glfwSetScrollCallback(window, (window, xoffset, yoffset) -> {
            Input.setScrollValue((float) yoffset);
        });

        // Window resize callback
        glfwSetFramebufferSizeCallback(window, (window, w, h) -> {
            width = w;
            height = h;
            glViewport(0, 0, w, h);
        });
    }

    @Override
    public void setSelectedScene(Scene scene) {
        this.myScene = scene;
        if (renderer != null && scene != null) {
            renderer.setCamera(scene.getCamera());
        }
    }

    @Override
    public void renderWindow() {
        if (myScene == null) return;

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        if (myScene.getRenderer() instanceof GLRenderer) {
            try {
                myScene.render(width, height);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        glfwSwapBuffers(window);
        glfwPollEvents();
        
        // Update FPS counter
        updateFPS();
    }
    
    private void updateFPS() {
        double currentTime = glfwGetTime();
        frameCount++;
        
        if (currentTime - lastFpsTime >= 1.0) {
            fps = frameCount;
            glfwSetWindowTitle(window, "example - FPS: " + fps);
            frameCount = 0;
            lastFpsTime = currentTime;
        }
    }
    
    public int getFPS() {
        return fps;
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public void setBlankCursor() {
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
    }

    public void cleanup() {
        if (renderer != null) {
            renderer.cleanup();
        }
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GLRenderer getRenderer() {
        return renderer;
    }
}
