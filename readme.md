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
		JScene myScene = new JScene();
		Game game = new Game(new JGameWindow(), myScene);
			
		GameObject g1 = new GameObject(game.getEcs());
		g1.addComponent(new Renderable());
		game.run();
   }
}
```
