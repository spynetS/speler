package com.example;
import javax.swing.JFrame;

public class GameWindow extends JFrame
{
	
	public GameWindow() {
		super("example");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 	setVisible(true);
		System.out.println("Window started");
	}
}
