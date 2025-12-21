package com.example.speler.opengl;

import com.example.speler.Vector2;
import com.example.speler.ecs.Camera;
import com.example.speler.render.Renderer;
import com.example.speler.resources.ResourceManager;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class GLRenderer implements Renderer {

    private Camera camera;
    private int screenW, screenH;
    
    // Shader program
    private int shaderProgram;
    private int vao, vbo, ebo;
    
    // Texture cache
    private Map<String, Integer> textureCache = new HashMap<>();
    
    // Uniform locations
    private int uProjection, uModel, uTexture, uUseTexture, uColor;

    private static final String VERTEX_SHADER = 
        "#version 330 core\n" +
        "layout (location = 0) in vec2 aPos;\n" +
        "layout (location = 1) in vec2 aTexCoord;\n" +
        "out vec2 TexCoord;\n" +
        "uniform mat4 projection;\n" +
        "uniform mat4 model;\n" +
        "void main() {\n" +
        "    gl_Position = projection * model * vec4(aPos, 0.0, 1.0);\n" +
        "    TexCoord = aTexCoord;\n" +
        "}\n";

    private static final String FRAGMENT_SHADER = 
        "#version 330 core\n" +
        "in vec2 TexCoord;\n" +
        "out vec4 FragColor;\n" +
        "uniform sampler2D texture1;\n" +
        "uniform bool useTexture;\n" +
        "uniform vec4 color;\n" +
        "void main() {\n" +
        "    if (useTexture) {\n" +
        "        FragColor = texture(texture1, TexCoord);\n" +
        "    } else {\n" +
        "        FragColor = color;\n" +
        "    }\n" +
        "}\n";

    public GLRenderer(Camera camera) {
        this.camera = camera;
        initShaders();
        initBuffers();
    }

    private void initShaders() {
        // Compile vertex shader
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, VERTEX_SHADER);
        glCompileShader(vertexShader);
        checkShaderCompilation(vertexShader, "VERTEX");

        // Compile fragment shader
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, FRAGMENT_SHADER);
        glCompileShader(fragmentShader);
        checkShaderCompilation(fragmentShader, "FRAGMENT");

        // Link shaders into program
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);
        checkProgramLinking(shaderProgram);

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);

        // Get uniform locations
        uProjection = glGetUniformLocation(shaderProgram, "projection");
        uModel = glGetUniformLocation(shaderProgram, "model");
        uTexture = glGetUniformLocation(shaderProgram, "texture1");
        uUseTexture = glGetUniformLocation(shaderProgram, "useTexture");
        uColor = glGetUniformLocation(shaderProgram, "color");
    }

    private void initBuffers() {
        // Quad vertices with texture coordinates
        // Flipped vertically to account for OpenGL's coordinate system
        float[] vertices = {
            // positions   // texture coords
            -0.5f,  0.5f,  0.0f, 1.0f,  // top left
             0.5f,  0.5f,  1.0f, 1.0f,  // top right
             0.5f, -0.5f,  1.0f, 0.0f,  // bottom right
            -0.5f, -0.5f,  0.0f, 0.0f   // bottom left
        };

        int[] indices = {
            0, 1, 2,
            2, 3, 0
        };

        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        ebo = glGenBuffers();

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        // Position attribute
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        // Texture coord attribute
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    @Override
    public void begin(int w, int h) {
        this.screenW = w;
        this.screenH = h;
        
        glUseProgram(shaderProgram);
        
        // Create orthographic projection matrix
        float left = 0;
        float right = w;
        float bottom = h;
        float top = 0;
        
        FloatBuffer projection = BufferUtils.createFloatBuffer(16);
        createOrthographicMatrix(left, right, bottom, top, -1, 1, projection);
        
        glUniformMatrix4fv(uProjection, false, projection);
        glUniform1i(uTexture, 0);
    }

    @Override
    public void drawSprite(String imageId, Vector2 pos, float rot, Vector2 scale, boolean flipX) {
        int textureId = getOrLoadTexture(imageId);
        
        glBindTexture(GL_TEXTURE_2D, textureId);
        glUniform1i(uUseTexture, 1);
        
        // Create model matrix
        int x = camera.worldToScreenX(pos.getX(), screenW);
        int y = camera.worldToScreenY(pos.getY(), screenH);
        int w = camera.worldToScreenSize((int) scale.getX());
        int h = camera.worldToScreenSize((int) scale.getY());
        
        if (flipX) w = -w;
        
        FloatBuffer model = BufferUtils.createFloatBuffer(16);
        createModelMatrix(x, y, w, h, rot, model);
        glUniformMatrix4fv(uModel, false, model);
        
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

    @Override
    public void drawRect(Vector2 pos, Vector2 size, float rot) {
        glUniform1i(uUseTexture, 0);
        glUniform4f(uColor, 1.0f, 1.0f, 1.0f, 1.0f);
        
        int x = camera.worldToScreenX(pos.getX(), screenW);
        int y = camera.worldToScreenY(pos.getY(), screenH);
        int w = camera.worldToScreenSize((int) size.getX());
        int h = camera.worldToScreenSize((int) size.getY());
        
        FloatBuffer model = BufferUtils.createFloatBuffer(16);
        createModelMatrix(x, y, w, h, rot, model);
        glUniformMatrix4fv(uModel, false, model);
        
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

    @Override
    public void end() {
        glUseProgram(0);
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Camera getCamera() {
        return camera;
    }

    private int getOrLoadTexture(String imageId) {
        if (textureCache.containsKey(imageId)) {
            return textureCache.get(imageId);
        }
        
        BufferedImage img = ResourceManager.getImage(imageId);
        int textureId = loadTexture(img);
        textureCache.put(imageId, textureId);
        return textureId;
    }

    private int loadTexture(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
        
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = pixels[y * width + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));  // Red
                buffer.put((byte) ((pixel >> 8) & 0xFF));   // Green
                buffer.put((byte) (pixel & 0xFF));          // Blue
                buffer.put((byte) ((pixel >> 24) & 0xFF));  // Alpha
            }
        }
        
        buffer.flip();
        
        int textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureId);
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        
        return textureId;
    }

    private void createOrthographicMatrix(float left, float right, float bottom, float top, float near, float far, FloatBuffer dest) {
        dest.put(0, 2.0f / (right - left));
        dest.put(1, 0);
        dest.put(2, 0);
        dest.put(3, 0);
        
        dest.put(4, 0);
        dest.put(5, 2.0f / (top - bottom));
        dest.put(6, 0);
        dest.put(7, 0);
        
        dest.put(8, 0);
        dest.put(9, 0);
        dest.put(10, -2.0f / (far - near));
        dest.put(11, 0);
        
        dest.put(12, -(right + left) / (right - left));
        dest.put(13, -(top + bottom) / (top - bottom));
        dest.put(14, -(far + near) / (far - near));
        dest.put(15, 1);
    }

    private void createModelMatrix(float x, float y, float w, float h, float rot, FloatBuffer dest) {
        float radians = (float) Math.toRadians(rot);
        float cos = (float) Math.cos(radians);
        float sin = (float) Math.sin(radians);
        
        // Scale * Rotation * Translation
        dest.put(0, w * cos);
        dest.put(1, w * sin);
        dest.put(2, 0);
        dest.put(3, 0);
        
        dest.put(4, -h * sin);
        dest.put(5, h * cos);
        dest.put(6, 0);
        dest.put(7, 0);
        
        dest.put(8, 0);
        dest.put(9, 0);
        dest.put(10, 1);
        dest.put(11, 0);
        
        dest.put(12, x);
        dest.put(13, y);
        dest.put(14, 0);
        dest.put(15, 1);
    }

    private void checkShaderCompilation(int shader, String type) {
        int success = glGetShaderi(shader, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            String log = glGetShaderInfoLog(shader);
            System.err.println("ERROR::SHADER::" + type + "::COMPILATION_FAILED\n" + log);
        }
    }

    private void checkProgramLinking(int program) {
        int success = glGetProgrami(program, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            String log = glGetProgramInfoLog(program);
            System.err.println("ERROR::PROGRAM::LINKING_FAILED\n" + log);
        }
    }

    public void cleanup() {
        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
        glDeleteBuffers(ebo);
        glDeleteProgram(shaderProgram);
        
        for (int textureId : textureCache.values()) {
            glDeleteTextures(textureId);
        }
    }
}
