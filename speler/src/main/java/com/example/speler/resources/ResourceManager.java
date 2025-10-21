package com.example.speler.resources;

import java.util.Map;

import javax.imageio.ImageIO;

import com.example.speler.Game;

import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;

public class ResourceManager {

	private static Map<String, BufferedImage> images = new HashMap<>();
		
    public static BufferedImage loadImage(String path) {
        if (!images.containsKey(path)) {
            try {
                BufferedImage img = ImageIO.read(new File(path));
                images.put(path, img);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return images.get(path);
    }

		public static String registerImage(BufferedImage image) {
				try {
						// Convert image to byte array (e.g., PNG format)
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(image, "png", baos);
						byte[] imageBytes = baos.toByteArray();

						// Compute SHA-256 hash of image bytes
						MessageDigest digest = MessageDigest.getInstance("SHA-256");
						byte[] hash = digest.digest(imageBytes);

						// Encode hash to Base64 or hex for readable key
						String key = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);

						// Optional: shorten key for convenience
						key = key.substring(0, 16); // first 16 chars is usually enough

						images.put(key, image);
						return key;
				} catch (Exception e) {
						throw new RuntimeException("Error generating image key", e);
				}
		}

		public static BufferedImage getImage(String path) {
			return images.get(path);
		}

			public static class Sprite {
				public static Game game;
				public static String getSprite(String path) throws Exception {
						game.getResourceManager().loadImage(path);
						return path;
				}
				public static String getSprite(SpriteSheet sheet, int row, int col) throws Exception {
						return game.getResourceManager().registerImage(sheet.getFrame(row,col));
				}
			 
		}
		

}
