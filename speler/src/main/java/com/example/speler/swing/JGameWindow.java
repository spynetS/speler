package com.example.speler.swing;

import com.example.speler.GameWindow;
import javax.swing.JFrame;

public class JGameWindow extends JFrame implements GameWindow
{
	public JGameWindow() {
		super("example");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 	setVisible(true);
	}
}
