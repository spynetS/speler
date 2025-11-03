package com.example.speler.editor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import com.example.speler.Game;
import com.example.speler.Scene;
import com.example.speler.ecs.ECS;
import com.example.speler.ecs.components.*;
import com.example.speler.input.Input;
import com.example.speler.input.Keys;
import com.example.speler.resources.ResourceManager.Sprite;
import com.example.speler.scripting.GameObject;
import com.example.speler.scripting.Script;
import com.example.speler.scripting.ScriptManager;
import com.example.speler.ecs.*;

import java.awt.*;
import java.io.File;
import java.util.UUID;

public class Editor extends Game {


		Inspector inspector = new Inspector(this);
		HierarchyPanel hierarchyPanel = new HierarchyPanel(this);
		JSplitPane rightSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		GameObject selectedGameObject;

		
    public Editor() {
				super();
				ecs.updateSystems.clear();
				ecs.addSystem(new ParentSystem());

        this.window.setTitle("speler");
        this.window.setSize(1920-200,1080-200);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setLocationRelativeTo(null);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

				JMenuItem newScene = new JMenuItem("New Scene");
				newScene.addActionListener(e->{
								this.ecs = new ECS();
								setSelectedScene(new EditorScene(this,ecs));
								hierarchyPanel.update(ecs);
								

						});
				fileMenu.add(newScene);
				JMenuItem open = new JMenuItem("Open Scene");
				open.addActionListener(e -> {
								try{
										selectedScene.loadScene("/home/spy/dev/playengine/scene.json");
								}catch(Exception exception){exception.printStackTrace();}
								hierarchyPanel.update(this.ecs);
						});
				fileMenu.add(open);

				JMenuItem item = new JMenuItem("Save Scene");
				item.addActionListener(e -> {
								try{
										selectedScene.saveScene("/home/spy/dev/playengine/scene.json");
								}catch(Exception ex){ex.printStackTrace();}

						});
        fileMenu.add(item);


		JMenuItem loadResources = new JMenuItem("Load resources");
		loadResources.addActionListener(e->{
						JFileChooser j = new JFileChooser();
						j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

						int r = j.showSaveDialog(null);
						if (r == JFileChooser.APPROVE_OPTION) {
								File folder = new File(j.getSelectedFile().getAbsolutePath());
								System.out.println("Load resources in, " + j.getSelectedFile().getAbsolutePath());
								this.resourceManager.loadFolder(folder);
						}
				});
        

		fileMenu.add(loadResources);

		menuBar.add(fileMenu);		

				JMenu runMenu = new JMenu("Run");
				JMenuItem run = new JMenuItem("Run scene");
				run.addActionListener(e->{
								
								Game g = new Game();
								g.getWindow().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
								
								try{
										selectedScene.saveScene("/home/spy/dev/playengine/scene.json");
										g.getSelectedScene().loadScene("/home/spy/dev/playengine/scene.json");
										//g.getSelectedScene().requestFocusInWindow();
										
										
										
								}catch(Exception er) {er.printStackTrace();}

								new Thread(() -> g.run()).start();
						});
				runMenu.add(run);
				
				menuBar.add(runMenu);

				
        this.window.setJMenuBar(menuBar);

        // Main Panels
        JSplitPane horizontalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        horizontalSplit.setDividerLocation(200);


        
        // Right Split - Scene + Inspector
       
        rightSplit.setDividerLocation(1000);

     		
				EditorScene scenePanel = new EditorScene(this,ecs);
				selectedScene = scenePanel;

        // Inspector Panel
		
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

				hierarchyPanel.update(ecs);
        this.window.add(consolePanel, BorderLayout.SOUTH);
				this.window.setVisible(true);

				Timer timer = new Timer(8, e -> {
								if (selectedScene != null) {
										ecs.update(1 / 60);
								}

								// repaint scene panel
								selectedScene.repaint();
        });
				timer.start();
				

    }

		@Override
		public void setSelectedScene(Scene scene) {
				selectedScene = scene;
				if(rightSplit != null)
						rightSplit.setLeftComponent(scene);
		}

		@Override
		protected void render() {
				// TODO Auto-generated method stub
				//		super.render();
		}

		public void setSelectedGameObject(GameObject selectedGameObject) {
		    this.selectedGameObject = selectedGameObject;
				this.inspector.updateInspector();
				((EditorScene) selectedScene).system.setSelectedEntity(selectedGameObject.id);
		}

		public GameObject getSelectedGameObject() {
		    return selectedGameObject;
		}

		public static class MyScript extends Script {
				public MyScript() {
						this.scriptName = "MyScript";
						ScriptManager.registerScript(scriptName, this.getClass());
				}
				@Override
				public void update(float deltatime) {
						if(Input.isKeyDown(Keys.D))
								gameObject.transform.worldX++;
						if(Input.isKeyDown(Keys.A))
								gameObject.transform.worldX--;

						if(Input.isKeyDown(Keys.S))
								gameObject.transform.worldY++;
						if(Input.isKeyDown(Keys.W))
								gameObject.transform.worldY--;
				}
		}
		
		
}
