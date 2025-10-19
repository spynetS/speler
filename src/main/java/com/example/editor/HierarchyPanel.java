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
import com.example.scripting.GameObject;

public class HierarchyPanel extends JPanel {


		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Scene");

		JTree hierarchyTree = new JTree(root);
		JScrollPane hierarchyScroll = new JScrollPane(hierarchyTree);

		public HierarchyPanel() {
				setLayout(new BorderLayout());
				this.add(new JLabel("Hierarchy", SwingConstants.CENTER), BorderLayout.NORTH);
        this.add(hierarchyScroll, BorderLayout.CENTER);

		}

		public void update(ECS ecs) {
			for (UUID id : ecs.getEntities()) {
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(id.toString());
					GameObject gameObject = new GameObject(id);
					root.add(node);

			}
		}
		
}
