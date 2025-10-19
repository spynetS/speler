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
import com.example.ecs.components.ParentComponent;
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
		private void addChildrenNodes(DefaultMutableTreeNode parentNode, GameObject parentObj) {
				for (GameObject child : parentObj.getChildren()) {
						DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child.id.toString());
						childNode.setUserObject(child);
						parentNode.add(childNode);

						// Recursive call for grandchildren
						addChildrenNodes(childNode, child);
				}
		}

		public void update(ECS ecs) {
				// Remove all nodes from the root first
				root.removeAllChildren();

				for (UUID id : ecs.getEntities()) {
						GameObject gameObject = new GameObject(ecs, id);

						// Skip children, we'll attach them to parents
						if (gameObject.getComponent(ParentComponent.class) != null)
								continue;

						DefaultMutableTreeNode node = new DefaultMutableTreeNode(gameObject.id.toString());
						node.setUserObject(gameObject);

						// Recursively add children
						addChildrenNodes(node, gameObject);

						root.add(node);
				}

				// Notify the tree model that it has changed
				((javax.swing.tree.DefaultTreeModel) hierarchyTree.getModel()).reload();

				// Expand all rows (optional)
				for (int i = 0; i < hierarchyTree.getRowCount(); i++) {
						hierarchyTree.expandRow(i);
				}
		}
		public GameObject getSelectedGameObject() {
		    return selectedGameObject;
		}
}
