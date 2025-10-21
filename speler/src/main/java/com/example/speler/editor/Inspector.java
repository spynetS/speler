package com.example.speler.editor;

import java.awt.FlowLayout;
import java.awt.Font;
import java.lang.reflect.Field;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.example.speler.ecs.ECS.Component;
import com.example.speler.scripting.GameObject;

public class Inspector extends JPanel {
		

	Editor editor;

	public Inspector(Editor editor) {
			this.editor = editor;
				this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
		

	public void updateInspector() {
		this.removeAll();

		if (this.editor.getSelectedGameObject() == null) {
			this.revalidate();
			this.repaint();
			return;
		}

		for (Object component : this.editor.getSelectedGameObject().getComponents()) {
			Class<?> compClass = component.getClass();
			JLabel compLabel = new JLabel(compClass.getSimpleName());
			compLabel.setFont(compLabel.getFont().deriveFont(Font.BOLD));
			this.add(compLabel);

			for (Field field : compClass.getDeclaredFields()) {
				field.setAccessible(true);
				try {
					Object value = field.get(component);
					JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
					fieldPanel.add(new JLabel(field.getName() + ":"));

					if (field.getType() == String.class || field.getType() == int.class || field.getType() == float.class
							|| field.getType() == double.class) {
						JTextField fieldInput = new JTextField(value.toString(), 8);
						// Live update using DocumentListener
						fieldInput.getDocument().addDocumentListener(new DocumentListener() {
							private void update() {
								try {
									String text = fieldInput.getText();
									if (text.isEmpty())
										return;
									if (field.getType() == int.class)
										field.setInt(component, Integer.parseInt(text));
									if (field.getType() == float.class)
										field.setFloat(component, Float.parseFloat(text));
									if (field.getType() == double.class)
										field.setDouble(component, Double.parseDouble(text));
									if (field.getType() == String.class)
										field.set(component, text);
									
								} catch (IllegalAccessException | NumberFormatException ex) {
									ex.printStackTrace();
								}
							}
									
							@Override
							public void insertUpdate(DocumentEvent e) {
								update();
							}

							@Override
							public void removeUpdate(DocumentEvent e) {
								update();
							}

							@Override
							public void changedUpdate(DocumentEvent e) {
								update();
							}
										
						});
						fieldPanel.add(fieldInput);

					} else if (field.getType() == boolean.class) {
						JCheckBox checkBox = new JCheckBox();
						checkBox.setSelected((boolean) value);
						checkBox.addItemListener(e -> {
							try {
								field.setBoolean(component, checkBox.isSelected());
							} catch (IllegalAccessException ex) {
								ex.printStackTrace();
							}
						});
						fieldPanel.add(checkBox);

					} else {
						fieldPanel.add(new JLabel(value != null ? value.toString() : "null"));
					}
					this.add(fieldPanel);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			JButton delete = new JButton("DELETE");
			delete.addActionListener(e -> {
				this.editor.getSelectedGameObject().removeComponent(((Component) component).getClass());
				updateInspector();
			});
			this.add(delete);

			this.add(new JSeparator(SwingConstants.HORIZONTAL));
		}

		JButton addComponentButton = new JButton("+ Add Component");
		addComponentButton.addActionListener(e -> showAddComponentDialog(editor.selectedGameObject));
		this.add(addComponentButton);

		this.revalidate();
		this.repaint();
	}

		    private void showAddComponentDialog(GameObject gameObject) {
        // Example: You can customize this list to fit your engine’s components
        String[] availableComponents = {
            "TransformComponent",
            "SpriteRenderer",
            "RigidBody",
            "BoxCollider",
            "CameraComponent"
        };

        String choice = (String) JOptionPane.showInputDialog(
            this,
            "Select a component to add:",
            "Add Component",
            JOptionPane.PLAIN_MESSAGE,
            null,
            availableComponents,
            availableComponents[0]
        );

        if (choice != null) {
            try {
                // Assuming components live under "com.example.ecs.components"
                String className = "com.example.ecs.components." + choice;
                Class<?> clazz = Class.forName(className);
                Component newComponent = (Component) clazz.getDeclaredConstructor().newInstance();
                gameObject.addComponent(newComponent);
                updateInspector();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Failed to add component: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
		
}
