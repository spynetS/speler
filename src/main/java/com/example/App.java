package com.example;

import com.example.Game;
import com.example.scripting.GameObject;

import javax.swing.*;
import java.awt.*;

public class App 
{
    public static void main( String[] args )
    {
				Game game = new Game();
				GameObject gameObject = new GameObject(game.ecs);

				
    }
}
