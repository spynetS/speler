package com.example;

import javax.swing.SwingUtilities;

import com.example.editor.*;

public class App 
{

    public static void main( String[] args ) throws Exception
    {
        SwingUtilities.invokeLater(Editor::new);
		}
		
}
