This is a simple 2D engine with simular structure to unity.
It is developed in Java Swing.


# Getting started

Simplest demo here is creating a scene with an empty rectangle

```java
import com.example.speler.Game;
import com.example.speler.Scene;
import com.example.speler.input.Input;
import com.example.speler.input.Keys;
import com.example.speler.swing.JGameWindow;
import com.example.speler.swing.SwingRenderer;
import com.example.speler.opengl.GLRenderer;
import com.example.speler.opengl.GLGameWindow;
import com.example.speler.scripting.GameObject;
import com.example.speler.ecs.components.*;
import com.example.speler.resources.ResourceManager.Sprite;
import com.example.speler.ecs.components.ui.TextElement;
import com.example.speler.Vector2;
import com.example.speler.scripting.*;

public class Main {
    public static void main(String[] args) {
				
				Scene scene = new Scene();
				Game game = new Game(new JGameWindow(), scene);
				scene.setRenderer(new SwingRenderer(scene.getCamera()));

				GameObject g1 = new GameObject(game.getEcs());
				g1.addComponent(new Renderable());

				game.run();
		}
}

```

Gameobject with a movement script

```java
public class Main {
    public static void main(String[] args) {

				var scene = new Scene();
				Game game = new Game(new JGameWindow(), scene);
				scene.setRenderer(new SwingRenderer(scene.getCamera()));

				GameObject g1 = new GameObject(game.getEcs());
				g1.addComponent(new Renderable());
				g1.addComponent(new ScriptComponent(new TestScript()));


				game.run();
		}

		public static class TestScript extends Script {

				public void update(float deltatime){
					if(Input.isKeyDown(Keys.A))
						this.gameObject.transform.position = this.gameObject.transform.position.add(new Vector2(-100,0).multiply(deltatime));
					if(Input.isKeyDown(Keys.D))
						this.gameObject.transform.position = this.gameObject.transform.position.add(new Vector2(100,0).multiply(deltatime));
					if(Input.isKeyDown(Keys.W))
						this.gameObject.transform.position = this.gameObject.transform.position.add(new Vector2(0,-100).multiply(deltatime));
					if(Input.isKeyDown(Keys.S))
						this.gameObject.transform.position = this.gameObject.transform.position.add(new Vector2(0,100).multiply(deltatime));
				}
		}

}
```


Gameobject with movement using rigidbody

```java
import com.example.speler.Game;
import com.example.speler.Scene;
import com.example.speler.input.Input;
import com.example.speler.input.Keys;
import com.example.speler.swing.JGameWindow;
import com.example.speler.swing.SwingRenderer;
import com.example.speler.opengl.GLRenderer;
import com.example.speler.opengl.GLGameWindow;
import com.example.speler.scripting.GameObject;
import com.example.speler.ecs.components.*;
import com.example.speler.resources.ResourceManager.Sprite;
import com.example.speler.ecs.components.ui.TextElement;
import com.example.speler.Vector2;
import com.example.speler.scripting.*;

public class Main {
    public static void main(String[] args) {

				var scene = new Scene();
				Game game = new Game(new JGameWindow(), scene);
				scene.setRenderer(new SwingRenderer(scene.getCamera()));

				GameObject g1 = new GameObject(game.getEcs());
				g1.addComponent(new Renderable());
				g1.addComponent(new ScriptComponent(new TestScript()));

		g1.addComponent(new Rigidbody());
		g1.getComponent(Rigidbody.class).useGravity = false;
				game.run();
		}

		public static class TestScript extends Script {

			Rigidbody rigidbody;

			@Override
			public void start() {
				this.rigidbody = this.gameObject.getComponent(Rigidbody.class);
			}

			public void update(float deltatime){
					if(Input.isKeyDown(Keys.A))
						this.rigidbody.acceleration = this.rigidbody.acceleration.add(new Vector2(-1,0));
					if(Input.isKeyDown(Keys.D))
						this.rigidbody.acceleration = this.rigidbody.acceleration.add(new Vector2(1,0));
					if(Input.isKeyDown(Keys.W))
						this.rigidbody.acceleration = this.rigidbody.acceleration.add(new Vector2(0,-1));
					if(Input.isKeyDown(Keys.S))
						this.rigidbody.acceleration = this.rigidbody.acceleration.add(new Vector2(0,1));
				if(Input.isKeyDown(Keys.SPACE))
					this.rigidbody.useGravity = !this.rigidbody.useGravity;
			}
		}

}
```


Compile project to jar with static dependencies
```bash
mvn clean package
```

jar is in
`speler/target/speler-1.0-SNAPSHOT.jar`


