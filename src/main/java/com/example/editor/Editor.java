package com.example.editor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import com.example.Game;
import com.example.Scene;
import com.example.ecs.ECS;
import com.example.ecs.ECS.*;
import com.example.resources.ResourceManager.Sprite;
import com.example.scripting.GameObject;

import java.awt.*;
import java.util.UUID;

public class Editor extends Game {

		HierarchyPanel hierarchyPanel = new HierarchyPanel();	
		JPanel inspectorPanel = new JPanel();
		
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
				GameObject g2 = new GameObject(this.ecs);
				g.addChild(g2);
				try{
						g.addComponent(new SpriteComponent(Sprite.getSprite("/home/spy/Pictures/davve.png")));
				}
				catch(Exception e)
						{e.printStackTrace();
				}
				
        JPanel scenePanel = new Scene(ecs);
				//        scenePanel.setBackground(Color.DARK_GRAY);
				//        scenePanel.setLayout(new BorderLayout());
				//        scenePanel.add(new JLabel("Scene View", SwingConstants.CENTER), BorderLayout.CENTER);

        // Inspector Panel

        inspectorPanel.setBackground(Color.GRAY);
        inspectorPanel.setLayout(new BorderLayout());
        inspectorPanel.add(new JLabel("Inspector", SwingConstants.CENTER), BorderLayout.NORTH);

        rightSplit.setLeftComponent(scenePanel);
        rightSplit.setRightComponent(inspectorPanel);

        horizontalSplit.setLeftComponent(hierarchyPanel);
        horizontalSplit.setRightComponent(rightSplit);

        this.window.add(horizontalSplit, BorderLayout.CENTER);

        // Bottom Panel - Console
        JPanel consolePanel = new JPanel();
        consolePanel.setBackground(Color.BLACK);
        consolePanel.setLayout(new BorderLayout());
        consolePanel.add(new JLabel("Console", SwingConstants.CENTER), BorderLayout.NORTH);
        consolePanel.setPreferredSize(new Dimension(1000, 100));

				this.update();
        this.window.add(consolePanel, BorderLayout.SOUTH);
        this.window.setVisible(true);
				
    }

		@Override
		public void setSelectedScene(Scene scene) {
		    // TODO Auto-generated method stub
				//		    super.setSelectedScene(scene);
		}

		public void update(){
				hierarchyPanel.update(this.ecs);
		}
		
}
