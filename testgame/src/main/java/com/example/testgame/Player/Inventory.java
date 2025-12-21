package com.example.testgame.Player;

import java.util.LinkedList;

import com.example.speler.input.Input;
import com.example.speler.input.Input.Keys;
import com.example.speler.scripting.Script;
import com.example.testgame.Resources.ItemScript;

public class Inventory extends Script  {

		LinkedList<ItemScript> items = new LinkedList<>();
		private int index = 0;
		
		public void useItem(int index){
				items.get(index).use();
		}

		@Override
		public void update(float deltatime) {
				if(Input.isKeyPressed(com.example.speler.input.Keys.E)){
						useItem(index);
				}

				
				
		}
		
}


