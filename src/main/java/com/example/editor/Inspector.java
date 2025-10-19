package com.example.editor;

import java.awt.FlowLayout;
import java.awt.Font;
import java.lang.reflect.Field;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.example.scripting.GameObject;

public class Inspector extends JPanel {
		
	GameObject currentGameObject;

		public Inspector() {
				this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
		
		public void setGameObject(GameObject gameObject) {
				this.currentGameObject = gameObject;
				updateInspector();
		}

		private void updateInspector() {
			this.removeAll();

			if (currentGameObject == null) {
				this.revalidate();
				this.repaint();
				return;
			}

			for (Object component : currentGameObject.getComponents()) {
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

						if (field.getType() == int.class || field.getType() == float.class
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

				this.add(new JSeparator(SwingConstants.HORIZONTAL));
			}

			this.revalidate();
			this.repaint();
		}
		
}
