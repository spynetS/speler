package com.example.speler.ecs.systems;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;

import com.example.speler.Vector2;
import com.example.speler.ecs.ECS;
import com.example.speler.ecs.components.SoundComponent;
import com.example.speler.ecs.components.Transform;

public class SoundSystem implements UpdateSystem {

		CopyOnWriteArrayList<Clip> playingClips = new CopyOnWriteArrayList<>();
		
		public void playSound(SoundComponent sc, float panValue) throws Exception {

				new Thread(){
						@Override
						public void run() {
								try{

								AudioInputStream audioStream = AudioSystem.getAudioInputStream(sc.audioFile);
								Clip clip = AudioSystem.getClip();

								clip.open(audioStream);
								clip.start();
								sc.clip = clip;
								// Wait for the clip to finish
								clip.addLineListener(event -> {
												if (event.getType() == LineEvent.Type.STOP) {
														clip.close();
														//sc.isPlaying = false; // allows replay
												}
										});
								} catch (Exception e) {
										e.printStackTrace();
								}
						}
				}.start();

    }

		@Override
		public void update(ECS ecs, float deltaTime) {
				for (UUID entity : ecs.getEntities()) {
						SoundComponent sc = ecs.getComponent(entity, SoundComponent.class);
						Transform transform = ecs.getComponent(entity, Transform.class);

						float panValue = new Vector2(-200,-200).subtract(transform.position).getNormalized().getX();
						panValue = Math.max(-1.0f, Math.min(1.0f, panValue)); // clamp

						float distance = new Vector2(-200,-200).getDistance(transform.position);
						float maxDistance = 500f;
						float volume = 1.0f - Math.min(distance / maxDistance, 1.0f); // 0.0 to 1.0
						float dB = (volume > 0) ? (float)(20 * Math.log10(volume)) : -80f; // to dB

						if(!sc.isPlaying) {
								try{
										playSound(sc,panValue);
								}catch (Exception e) {
										e.printStackTrace();
								}
								sc.isPlaying=true;
						}else {
								if (sc.clip.isControlSupported(FloatControl.Type.PAN)) {
										System.out.println("update");
										FloatControl pan = (FloatControl) sc.clip.getControl(FloatControl.Type.PAN);
										pan.setValue(panValue);

										FloatControl gainControl = (FloatControl) sc.clip.getControl(FloatControl.Type.MASTER_GAIN);
										gainControl.setValue(dB); // typically -80.0 to 6.0
								} else {
										System.out.println("Pan control not supported");
								}
						}
				}
		}

		@Override
		public void start(ECS ecs) {

		}
		
}
