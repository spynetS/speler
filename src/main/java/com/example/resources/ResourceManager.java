package com.example.resources;

import java.util.Map;

import javax.imageio.ImageIO;

import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

    public static BufferedImage getImage(String path) {
        return images.get(path);
    }
		
}
