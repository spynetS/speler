package com.example.speler.swing;

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
import java.util.List;
import java.util.LinkedList;

import com.example.speler.input.*;

import javax.swing.JPanel;
import com.example.speler.ecs.*;
import com.example.speler.Vector2;
import com.example.speler.ecs.systems.*;
import com.example.speler.Game;
import com.example.speler.Scene;



public class JScene extends Scene {

		GamePanel panel = new GamePanel(this);
		
		
		public JScene(ECS ecs) {
				super(ecs);
				panel.setBackground(new Color(40, 125, 255));
				panel.initInput();
				panel.setDoubleBuffered(true); // helps prevent flickering
		}
		public JScene() {
				super();
				panel.setBackground(new Color(40, 125, 255));
				panel.initInput();
				panel.setDoubleBuffered(true); // helps prevent flickering
		}

		@Override
		public void start(Game game) throws Exception {
				if(game.getWindow() instanceof JGameWindow)
						((JGameWindow) game.getWindow()).add(panel, BorderLayout.CENTER);
				panel.requestFocus();
		}
		
		@Override
		public void render() throws Exception {
				panel.validate();
				panel.repaint();
		}

		public GamePanel getPanel(){
				return panel;
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

						Toolkit.getDefaultToolkit().sync();

						int x = (int)Input.getMousePositionOnCanvas().x;
						int y = (int)Input.getMousePositionOnCanvas().y;

						Input.setMousePosition(new Vector2(
																							 myScene.getCamera().screenToWorldX(x, getWidth()),
																							 myScene.getCamera().screenToWorldY(y, getHeight())
																							 ));
						Graphics2D graphics2D =  (Graphics2D) g;
						// graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						// graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				
						RenderSystem renderer = new JSpriteRenderSystem();
						renderer.render(myScene.getEcs(), graphics2D, myScene.getCamera(), this.getWidth(), this.getHeight());
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
