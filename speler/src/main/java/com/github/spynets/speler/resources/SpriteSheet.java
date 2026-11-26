package com.github.spynets.speler.resources;

import java.awt.image.BufferedImage;


/***

		SpriteSheet is a class to handle
		sprites easier.
		It loads an image of sprites (spritesheet) and takes the size of each sprite
		then you can can extract each frame with getFrame
 **/
public class SpriteSheet {
    private BufferedImage sheet;
    private int frameWidth, frameHeight;
    private int spacing, margin;
    private int rows, cols;

    public SpriteSheet(BufferedImage sheet, int frameWidth, int frameHeight) {
        this(sheet, frameWidth, frameHeight, 0, 0);
    }

    /***
        Sheets exported by tile editors often inset the whole grid by a margin
        and separate the frames by a spacing gutter. Pass those in so the
        frames are cut on the right boundaries.
     **/
    public SpriteSheet(BufferedImage sheet, int frameWidth, int frameHeight, int spacing, int margin) {
        this.sheet = sheet;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.spacing = spacing;
        this.margin = margin;
        // width == 2*margin + cols*frameWidth + (cols-1)*spacing, solved for cols.
        this.cols = (sheet.getWidth() - 2 * margin + spacing) / (frameWidth + spacing);
        this.rows = (sheet.getHeight() - 2 * margin + spacing) / (frameHeight + spacing);
    }

    public BufferedImage getFrame(int row, int col) {
        if (row < 0 || col < 0 || row >= rows || col >= cols) return null;
        return sheet.getSubimage(
                margin + col * (frameWidth + spacing),
                margin + row * (frameHeight + spacing),
                frameWidth,
                frameHeight);
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
}
