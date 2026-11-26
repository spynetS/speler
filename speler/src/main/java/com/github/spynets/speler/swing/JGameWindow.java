package com.github.spynets.speler.swing;

import com.github.spynets.speler.GameWindow;
import com.github.spynets.speler.Scene;
import com.github.spynets.speler.Vector2;
import com.github.spynets.speler.input.Input;
import com.github.spynets.speler.debug.DebugInfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.FontUIResource;

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

    public void renderDebug(DebugInfo info) {
        gamePanel.info = info;
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
        public DebugInfo info;


				public GamePanel(Scene myScene){
						this.myScene = myScene;
				}
				
				public void removeInput() {
						removeMouseListener(getMouseListeners()[0]);
				}


        private void renderDebug(Graphics2D g) {
            if (info == null) return;

            int x = 10;
            int y = 10;
            int width = 190;
            int height = 145;

            // Background
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRoundRect(x, y, width, height, 10, 10);

            // Text
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 12));

            int lineHeight = 18;
            int textY = y + 22;

            g.drawString("DEBUG", x + 10, textY);
            textY += lineHeight;

            g.drawString("FPS: " + info.fps(), x + 10, textY);
            textY += lineHeight;

            g.drawString("Frame time: " + String.format("%.2f ms", info.frameTime()), x + 10, textY);
            textY += lineHeight;

            g.drawString("Entities: " + info.entityCount(), x + 10, textY);
            textY += lineHeight;

            g.drawString("Components: " + info.componentCount(), x + 10, textY);
            textY += lineHeight;

            g.drawString("Systems: " + info.systemCount(), x + 10, textY);
            textY += lineHeight;

            g.drawString("Memory: " + String.format("%.1f MB",
                                                    info.memoryUsed() / (1024f * 1024f)), x + 10, textY);
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
																							 myScene.getCamera().screenToWorldY(y, getHeight())));
						Graphics2D graphics2D =  (Graphics2D) g;

            // settings
            // graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						// graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

						if(myScene.getRenderer() instanceof SwingRenderer){
								((SwingRenderer)(myScene.getRenderer())).setGraphics(graphics2D);
								try {
										myScene.render(getWidth(), getHeight());
								} catch (Exception e) {
                    e.printStackTrace();
								}
						} else{
								System.out.println("No SwingRenderer found in Scene");
						}
            renderDebug(graphics2D);

						Input.resetScrollValue();
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
