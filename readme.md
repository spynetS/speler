This is a simple 2D engine with simular structure to unity.
It is developed in Java Swing.


# Getting started

Simplest demo here is creating a scene with an empty rectangle

```java
import com.example.speler.Game;
import com.example.speler.swing.JGameWindow;
import com.example.speler.swing.JScene;
import com.example.speler.scripting.GameObject;
import com.example.speler.ecs.components.Renderable;

public class Main {
    public static void main(String[] args) {
		// create a scene
		Scene scene = new Scene();
		// create a game with swing game window and our scene
		Game game = new Game(new JGameWindow(), scene);
		// set the renderer for the scene 
		scene.setRenderer(new SwingRenderer(scene.getCamera()));
		// create a gameobject
		GameObject g1 = new GameObject(game.getEcs());
		// attach a renderable component to it
		g1.addComponent(new Renderable());

	    // run the game
		game.run();
   }
}
```
