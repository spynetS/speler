package com.example.speler.swing;

import com.example.speler.GameWindow;
import com.example.speler.Scene;
import com.example.speler.Vector2;
import com.example.speler.input.Input;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JGameWindow extends JFrame implements GameWindow
{

		
		GamePanel gamePanel;

		public JGameWindow() {
				super("example");
				setSize(800, 600);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setVisible(true);
				gamePanel = new GamePanel(null);
				gamePanel.setBackground(new Color(40, 125, 255));
				gamePanel.initInput();
				gamePanel.setDoubleBuffered(true); // helps prevent flickering
				add(gamePanel,BorderLayout.CENTER);
		}

		@Override
		public void setSelectedScene(Scene scene) {
				gamePanel.myScene = scene;
		}

		@Override
		public void renderWindow() {
				this.validate();
				gamePanel.validate();
				gamePanel.repaint();
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

		public static class GamePanel extends JPanel {

				Scene myScene;
				
				public GamePanel(Scene myScene){
						this.myScene = myScene;
				}
				
				public void removeInput() {
						removeMouseListener(getMouseListeners()[0]);
				}				
				@Override
				protected void paintComponent(Graphics g) {
						super.paintComponent(g);

						if(myScene == null) return;
						
						Toolkit.getDefaultToolkit().sync();

						int x = (int)Input.getMousePositionOnCanvas().getX();
						int y = (int)Input.getMousePositionOnCanvas().getY();

						Input.setMousePosition(new Vector2(
																							 myScene.getCamera().screenToWorldX(x, getWidth()),
																							 myScene.getCamera().screenToWorldY(y, getHeight())
																							 ));
						Graphics2D graphics2D =  (Graphics2D) g;
						// graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						// graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

						if(myScene.getRenderer() instanceof SwingRenderer){
								((SwingRenderer)(myScene.getRenderer())).setGraphics(graphics2D);
								try {
										myScene.render(getWidth(), getHeight());
								} catch (Exception e) {
									e.printStackTrace();
								}
						}
								
				}

				
				public void initInput() {
						//setLayout(new GridLayout(0, 1));

						setFocusable(true);

						addKeyListener(new KeyAdapter() {
										@Override
										public void keyPressed(KeyEvent e) {
												super.keyPressed(e);
												Input.addKey(e);
										}

										@Override
										public void keyReleased(KeyEvent e) {
												super.keyReleased(e);
												Input.removeKey(e);
										}
								});

						MouseAdapter mouseAdapter = new MouseAdapter() {
										@Override
										public void mousePressed(MouseEvent e) {
												super.mousePressed(e);
												Input.addMouseButton(e);
												Input.setMouseEvent(e);
												Input.setMousePressed(e.getButton());
												//										requestFocus();
										}

										@Override
										public void mouseReleased(MouseEvent e) {
												super.mouseReleased(e);
												Input.removeMouseButton(e);
												Input.setMouseEvent(e);

										}

										@Override
										public void mouseDragged(MouseEvent e) {
												super.mouseDragged(e);
												Input.setMousePositionOnCanvas(
																											 new Vector2((float) e.getPoint().getX(), (float) e.getPoint().getY()));
												Input.setMouseEvent(e);

										}

										@Override
										public void mouseMoved(MouseEvent e) {
												super.mouseMoved(e);
												Input.setMousePositionOnCanvas(
																											 new Vector2((float) e.getPoint().getX(), (float) e.getPoint().getY()));
												Input.setMouseEvent(e);
												//requestFocus();
										}

										@Override
										public void mouseWheelMoved(MouseWheelEvent e) {
												Input.setScrollValue((float) e.getPreciseWheelRotation());
												Input.setMouseEvent(e);
										}

								};
						addMouseListener(mouseAdapter);
						addMouseMotionListener(mouseAdapter);
						addMouseWheelListener(mouseAdapter);
				}

		}		
}
