package com.example.speler.ecs;

import com.example.speler.SerializableComponent;
import com.example.speler.Vector2;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Camera implements SerializableComponent {
    public float x, y;     // Camera position in world coordinates
    public float zoom;     // Zoom factor (1.0 = normal size)
    
    public Camera(float x, float y, float zoom) {
        this.x = x;
        this.y = y;
        this.zoom = zoom;
    }
    
    // Convert world coordinates to screen coordinates
    public int worldToScreenX(float worldX, int screenWidth) {
        return Math.round((worldX - x) * zoom + screenWidth / 2.0f);
    }
    
    public int worldToScreenY(float worldY, int screenHeight) {
        return Math.round((worldY - y) * zoom + screenHeight / 2.0f);
    }
    
    public float screenToWorldX(int screenX, int screenWidth) {
        return x + (screenX - screenWidth / 2.0f) / zoom;
    }
    
    public float screenToWorldY(int screenY, int screenHeight) {
        return y + (screenY - screenHeight / 2.0f) / zoom;
    }
    
    // Convert world size to screen size (for scaling sprites)
    public int worldToScreenSize(int size) {
        return Math.round(size * zoom);
    }
    
    public void centerOn(Vector2 worldPos, int screenWidth, int screenHeight) {
        // Round camera position to pixel boundaries to prevent sub-pixel gaps
        float targetX = worldPos.getX() - (screenWidth / 2f) / zoom;
        float targetY = worldPos.getY() - (screenHeight / 2f) / zoom;
        
        // Round to nearest pixel in world space
        this.x = Math.round(targetX * zoom) / zoom;
        this.y = Math.round(targetY * zoom) / zoom;
    }
    
    // Alternative: Snap camera to pixel grid (use this if you still see gaps)
    public void snapToPixelGrid() {
        this.x = Math.round(this.x * zoom) / zoom;
        this.y = Math.round(this.y * zoom) / zoom;
    }
    
    // Use this method to move camera smoothly while staying pixel-perfect
    public void setPosition(float x, float y) {
        // Snap to pixel boundaries in screen space
        this.x = Math.round(x * zoom) / zoom;
        this.y = Math.round(y * zoom) / zoom;
    }
    
    @Override
    public JsonObject serialize() {
        JsonObject ob = new JsonObject();
        ob.add("x", new JsonPrimitive(x));
        ob.add("y", new JsonPrimitive(y));
        ob.add("zoom", new JsonPrimitive(zoom));
        return ob;
    }
    
    @Override
    public void deserialize(JsonObject data) {
        x = data.get("x").getAsFloat();  // Changed from getAsInt()
        y = data.get("y").getAsFloat();  // Changed from getAsInt()
        zoom = data.get("zoom").getAsFloat();
    }
}
