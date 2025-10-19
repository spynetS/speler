package com.example.editor;

import java.awt.BorderLayout;
import java.util.UUID;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;

import com.example.ecs.ECS;
import com.example.ecs.ECS.ParentComponent;
import com.example.scripting.GameObject;

public class HierarchyPanel extends JPanel {


		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Scene");

		JTree hierarchyTree = new JTree(root);
		JScrollPane hierarchyScroll = new JScrollPane(hierarchyTree);
		GameObject selectedGameObject;
		Inspector inspector;
		
		public HierarchyPanel(Inspector inspector) {
			this.inspector = inspector;
				setLayout(new BorderLayout());
				this.add(new JLabel("Hierarchy", SwingConstants.CENTER), BorderLayout.NORTH);
				this.add(hierarchyScroll, BorderLayout.CENTER);
				hierarchyTree.addTreeSelectionListener(e -> {
								// Get the newly selected node
								DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) hierarchyTree.getLastSelectedPathComponent();
    
								if (selectedNode == null) return; // nothing selected

								// Example: print the node
								System.out.println("Selected node: " + selectedNode.getUserObject());

								// If your node holds a GameObject or custom object:
								Object obj = selectedNode.getUserObject();
								if (obj instanceof GameObject) {
										GameObject selectedGameObject = (GameObject) obj;
										// Update inspector or other UI
										
										inspector.setGameObject(selectedGameObject);
								}
						});
		}

		public void update(ECS ecs) {
			for (UUID id : ecs.getEntities()) {
				GameObject gameObject = new GameObject(ecs, id);

				if (gameObject.getComponent(ParentComponent.class) != null)
					continue;
				
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(id.toString());
					node.setUserObject(gameObject);
					for (GameObject child : gameObject.getChildren()) {
						DefaultMutableTreeNode childN = new DefaultMutableTreeNode();
						childN.setUserObject(child);
						node.add(childN);
					}
					root.add(node);

			}
		}
		public GameObject getSelectedGameObject() {
		    return selectedGameObject;
		}
}
