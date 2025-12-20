package com.example.speler.swing;

import com.example.speler.GameWindow;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class JGameWindow extends JFrame implements GameWindow
{
	public JGameWindow() {
		super("example");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void setBlankCursor() {

			// Transparent 16 x 16 pixel cursor image.
			BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

			// Create a new blank cursor.
			Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
																																					cursorImg, new Point(0, 0), "blank cursor");
			// Set the blank cursor to the JFrame.
			this.getContentPane().setCursor(blankCursor);
	}
		
}
