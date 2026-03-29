package com.example.speler.ecs.components;

import java.io.File;

import javax.sound.sampled.Clip;

import com.example.speler.ecs.ECS.Component;
import com.google.gson.JsonObject;

import com.example.speler.SerializableComponent;

public class SoundComponent implements Component, SerializableComponent {


		public float volume = 1;
		public File audioFile;
		public boolean isPlaying = false;
		public boolean shouldPlay = false;
		public Clip clip;

		public SoundComponent(File audioFile, float volume){
				this.volume = volume;
				this.audioFile = audioFile;
		}
		public SoundComponent(File audioFile, float volume, boolean isPlaying){
				this.volume = volume;
				this.audioFile = audioFile;
				this.isPlaying = isPlaying;
		}

		public JsonObject serialize() {
				throw new UnsupportedOperationException("Unimplemented method 'serialize'");
		}
		
    public void deserialize(JsonObject data) {
				throw new UnsupportedOperationException("Unimplemented method 'deserilize'");
		}
}
