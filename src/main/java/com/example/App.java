package com.example;

import javax.swing.SwingUtilities;

import com.example.ecs.components.ScriptComponent;
import com.example.editor.*;
import com.example.editor.Editor.MyScript;
import com.example.scripting.GameObject;
import com.example.scripting.ScriptManager;
import com.formdev.flatlaf.FlatDarculaLaf;

public class App 
{

    public static void main( String[] args ) throws Exception
		{
				try {
						FlatDarculaLaf.setup();
				} catch (Exception ex) {
						System.err.println("Failed to initialize LaF");
				}

				SwingUtilities.invokeLater(Editor::new);


		}

		
}
