This is a simple 2D engine with simular structure to unity.
It is developed in Java Swing.


# Getting started

Simplest emo here is creating a scene with an empty rectangle

    
    package com.example.testgame;
    
    import com.example.speler.Game;
    import com.example.speler.swing.JGameWindow;
    
    
    public class Main {
    
    
    		public static void main(String[] args) {
    				Game game = new Game(new JGameWindow());
    
    				Scene myScene = new JScene();
    
    				GameObject g1 = new GameObject();
    				g1.addComponent(new Renderable());
    
    				
    				game.run();
        }
    }

