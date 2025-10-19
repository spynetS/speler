package com.example.editor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import com.example.Game;
import com.example.Scene;
import com.example.ecs.ECS;
import com.example.ecs.ECS.*;
import com.example.input.Input;
import com.example.resources.ResourceManager.Sprite;
import com.example.scripting.GameObject;
import com.example.scripting.Script;

import java.awt.*;
import java.util.UUID;

public class Editor extends Game {


		Inspector inspector = new Inspector();
		HierarchyPanel hierarchyPanel = new HierarchyPanel(inspector);
		
    public Editor() {
				super();
        this.window.setTitle("Unity-like Editor");
        this.window.setSize(1000, 700);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setLocationRelativeTo(null);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem("New Scene"));
        fileMenu.add(new JMenuItem("Open Scene"));
        fileMenu.add(new JMenuItem("Save Scene"));
        menuBar.add(fileMenu);
        this.window.setJMenuBar(menuBar);

        // Main Panels
        JSplitPane horizontalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        horizontalSplit.setDividerLocation(200);


        
        // Right Split - Scene + Inspector
        JSplitPane rightSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        rightSplit.setDividerLocation(700);

        // Scene Panel
				GameObject g = new GameObject(this.ecs);
				g.transform.worldX = 10;
				g.addComponent(new ScriptComponent(new Script(g) {
								@Override
								public void update(float deltatime) {
										// TODO Auto-generated method stub
										super.update(deltatime);
										//g.transform.worldX = (int) Input.getMousePosition().x;
										//								g.transform.worldY = (int) Input.getMousePosition().y;
								}
						}));
				GameObject g2 = new GameObject(this.ecs);
				g2.transform.x = 100;
				g.addChild(g2);

				
				
				try{
						g.addComponent(new SpriteComponent(Sprite.getSprite("/home/spy/Pictures/davve.png")));
						g2.addComponent(new SpriteComponent(Sprite.getSprite("/home/spy/Pictures/kevva.png")));

				}
				catch (Exception e) {
						e.printStackTrace();
				}
				
				EditorScene scenePanel = new EditorScene(ecs);
				selectedScene = scenePanel;

        // Inspector Panel
				inspector.setGameObject(g);
		
        rightSplit.setLeftComponent(scenePanel);
        rightSplit.setRightComponent(inspector);

        horizontalSplit.setLeftComponent(hierarchyPanel);
        horizontalSplit.setRightComponent(rightSplit);

        this.window.add(horizontalSplit, BorderLayout.CENTER);

        // Bottom Panel - Console
        JPanel consolePanel = new JPanel();
        consolePanel.setBackground(Color.BLACK);
        consolePanel.setLayout(new BorderLayout());
        consolePanel.add(new JLabel("Console", SwingConstants.CENTER), BorderLayout.NORTH);
        consolePanel.setPreferredSize(new Dimension(1000, 100));

				this.update(0);
        this.window.add(consolePanel, BorderLayout.SOUTH);
				this.window.setVisible(true);

				Timer timer = new Timer(16, e -> {
								if (selectedScene != null) {
										ecs.update(1 / 60);
								}

								// repaint scene panel
								scenePanel.repaint();
        });
        timer.start();
				

    }

		@Override
		public void setSelectedScene(Scene scene) {
				// TODO Auto-generated method stub
				//		    super.setSelectedScene(scene);
		}

		@Override
		public void update(double delta) {
				super.update(delta);
				System.out.println("UPDATE");
				hierarchyPanel.update(this.ecs);
		}

		@Override
		protected void render() {
				// TODO Auto-generated method stub
				//		super.render();
		}
		
}
