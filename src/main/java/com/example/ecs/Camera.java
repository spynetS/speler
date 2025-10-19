package com.example.ecs;

import com.example.Vector2;

public class Camera {
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
				this.x = (int)worldPos.x - (screenWidth / 2f) / zoom;
				this.y = (int)worldPos.y - (screenHeight / 2f) / zoom;
		}

}
