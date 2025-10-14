package com.example.resources;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    private BufferedImage sheet;
    private int frameWidth, frameHeight;
    private int rows, cols;

    public SpriteSheet(BufferedImage sheet, int frameWidth, int frameHeight) {
        this.sheet = sheet;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.cols = sheet.getWidth() / frameWidth;
        this.rows = sheet.getHeight() / frameHeight;
    }

    public BufferedImage getFrame(int row, int col) {
        if (row >= rows || col >= cols) return null;
        return sheet.getSubimage(col * frameWidth, row * frameHeight, frameWidth, frameHeight);
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
}
