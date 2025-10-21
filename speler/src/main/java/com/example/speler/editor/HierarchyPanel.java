package com.example.speler.editor;

import java.awt.BorderLayout;
import java.util.UUID;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;

import com.example.speler.ecs.ECS;
import com.example.speler.ecs.components.ParentComponent;
import com.example.speler.ecs.components.SpriteComponent;
import com.example.speler.resources.ResourceManager.Sprite;
import com.example.speler.scripting.GameObject;

public class HierarchyPanel extends JPanel {


		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Scene");
		private JPopupMenu nodeMenu;
		JTree hierarchyTree = new JTree(root);
		JScrollPane hierarchyScroll = new JScrollPane(hierarchyTree);
		GameObject selectedGameObject;
		Editor editor;
		
		public HierarchyPanel(Editor editor) {
			this.editor = editor;
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
										
										editor.setSelectedGameObject(selectedGameObject);
								}
						});
				initPopupMenu();
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

		
			
		private void initPopupMenu() {
				nodeMenu = new JPopupMenu();

				JMenuItem createObjectItem = new JMenuItem("Create New GameObject");
				createObjectItem.addActionListener(e -> {
								DefaultMutableTreeNode selectedNode =
										(DefaultMutableTreeNode) hierarchyTree.getLastSelectedPathComponent();
					if (selectedNode == null)
						return;

								
								Object obj = selectedNode.getUserObject();
					if (obj instanceof GameObject parentObj) {
						// Create a new GameObject
						GameObject newObj = new GameObject(parentObj.getEcs());

						newObj.addComponent(new ParentComponent(parentObj.id));
						try {
							newObj.addComponent(new SpriteComponent(Sprite.getSprite("/home/spy/Pictures/kevva.png")));
						} catch (Exception ec) {
						}
						// Update hierarchy
						update(parentObj.getEcs());
					}
					else if (selectedNode == root) {
						GameObject newObj = new GameObject(this.editor.getEcs());						
						update(this.editor.getEcs());
					}

					
						});

		    JMenuItem removeObjectItem = new JMenuItem("Remove GameObject");
				removeObjectItem.addActionListener(e -> {
								DefaultMutableTreeNode selectedNode =
										(DefaultMutableTreeNode) hierarchyTree.getLastSelectedPathComponent();
								if (selectedNode == null) return;

								Object obj = selectedNode.getUserObject();
								if (obj instanceof GameObject go) {
										// Remove from ECS
										go.getEcs().removeEntity(go.getId());

										// Refresh hierarchy
										update(go.getEcs());
								}
						});
				nodeMenu.add(removeObjectItem);

				nodeMenu.add(createObjectItem);

				// Attach mouse listener to the tree
				hierarchyTree.addMouseListener(new java.awt.event.MouseAdapter() {
								@Override
								public void mousePressed(java.awt.event.MouseEvent e) {
										showMenu(e);
								}

								@Override
								public void mouseReleased(java.awt.event.MouseEvent e) {
										showMenu(e);
								}

								private void showMenu(java.awt.event.MouseEvent e) {
										if (e.isPopupTrigger()) {
												int row = hierarchyTree.getClosestRowForLocation(e.getX(), e.getY());
												hierarchyTree.setSelectionRow(row);
												nodeMenu.show(e.getComponent(), e.getX(), e.getY());
										}
								}
						});
		}
		
		public GameObject getSelectedGameObject() {
		    return selectedGameObject;
		}
}
