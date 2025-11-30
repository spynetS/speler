
package com.example.testgame;
import com.example.speler.input.Input;
import com.example.speler.input.Keys;

import com.example.speler.scripting.Script;

public class MyScript extends Script {
		
		@Override
		public void start() {
				// TODO Auto-generated method stub
				super.start();
				System.out.println("START SCRIPT");
				this.scriptName  = "myscript";
		}
		
		@Override
		public void update(float deltatime) {
			super.update(deltatime);

			this.gameObject.transform.position = Input.getMousePosition();
			
			// if (Input.isKeyDown(Keys.A)){
			// 		this.gameObject.transform.position.x-=1;
			// }
					
			// if (Input.isKeyDown(Keys.D)){
			// 		this.gameObject.transform.position.x+=1;
			// }

			// if (Input.isKeyDown(Keys.W)){
			// 		this.gameObject.transform.position.y-=1;
			// }
			// if (Input.isKeyDown(Keys.S)){
			// 		this.gameObject.transform.position.y+=1;
			// }
		}
			
}
