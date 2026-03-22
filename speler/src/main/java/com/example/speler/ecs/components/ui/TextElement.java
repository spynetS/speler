package com.example.speler.ecs.components.ui;

import com.example.speler.ecs.ECS.Component;

public class TextElement implements Component {

		public String text;
		public int fontSize = 10;

		public TextElement(String text, int fontSize) {
				this.text = text;
				this.fontSize = fontSize;
		}
		
}
