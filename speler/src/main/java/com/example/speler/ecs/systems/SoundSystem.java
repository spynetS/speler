package com.example.speler.ecs.systems;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;

import com.example.speler.Vector2;
import com.example.speler.ecs.ECS;
import com.example.speler.ecs.ECS.Component;
import com.example.speler.ecs.components.*;
import com.example.speler.ecs.listeners.*;

// TODO fix so sounds are preloaded
public class SoundSystem implements UpdateSystem, EntityListener {

		Map<SoundComponent, Transform> playingClips = new HashMap<>();

    // (we are actually starting the clips later when a listener is present in updateSpatialAudio)
		public void playSound(Transform transform, SoundComponent sc) throws Exception {
						
				new Thread(){
						@Override
						public void run() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
								try{

										AudioInputStream audioStream = AudioSystem.getAudioInputStream(sc.audioFile);
										Clip clip = AudioSystem.getClip();

										clip.open(audioStream);
										playingClips.put(sc,transform);

										sc.clip = clip;
										// Wait for the clip to finish
										clip.addLineListener(event -> {
														if (event.getType() == LineEvent.Type.STOP) {
																clip.close();
																sc.isPlaying = false; // allows replay
																playingClips.remove(sc);
														}
												});
								} catch (Exception e) {
										e.printStackTrace();
								}
						}
				}.start();

    }

private int spatialUpdateCounter = 0;


		@Override
		public void update(ECS ecs, float deltaTime) {
				for (UUID entity : ecs.getEntities()) {
						SoundComponent sc = ecs.getComponent(entity, SoundComponent.class);
						Transform transform = ecs.getComponent(entity, Transform.class);
						
						if (sc != null) {
								if(sc.shouldPlay) {
										try{
												playSound(transform, sc);
										}catch (Exception e) {
												e.printStackTrace();
										}
										sc.isPlaying=true;
										sc.shouldPlay=false;
								}
						}

						// if we have a soundlistener entity change the volume from it
						SoundListenerComponent lc = ecs.getComponent(entity, SoundListenerComponent.class);
						if (lc == null) continue;
						
            if (++spatialUpdateCounter % 5 == 0) {
                updateSpatialAudio(transform);
            }				
				}
		}

    private void updateSpatialAudio(Transform listener){
        for (Map.Entry<SoundComponent, Transform> entry : playingClips.entrySet()) {
            SoundComponent psc = entry.getKey();
            Transform pst = entry.getValue();
            if (!psc.clip.isRunning()) psc.clip.start();
            
            float panValue = pst.position.subtract(listener.position).getNormalized().getX();
            panValue = Math.max(-1.0f, Math.min(1.0f, panValue)); // clamp

            float distance = pst.position.getDistance(listener.position);
            float maxDistance = 500f;
            float volume = 1.0f - Math.min(distance / maxDistance, 1.0f); // 0.0 to 1.0
								
            if (psc.clip.isControlSupported(FloatControl.Type.PAN)) {

                FloatControl pan = (FloatControl) psc.clip.getControl(FloatControl.Type.PAN);
                pan.setValue(panValue);

                FloatControl gainControl = (FloatControl) psc.clip.getControl(FloatControl.Type.MASTER_GAIN);
                volume *= psc.volume;
                float dB = (volume > 0) ? (float)(20 * Math.log10(volume)) : -80f; // to dB
                gainControl.setValue(dB); // typically -80.0 to 6.0
            } else {
                System.out.println("Pan control not supported");
            }
        }

    }
    
		@Override
		public void start(ECS ecs) {}
		@Override
		public void onComponentAdded(UUID id, Component component) {}
		@Override
		public void onComponentRemoved(UUID id, Component component) {
				if (playingClips.containsKey(component)) {
						System.out.println("COMPONENT-REMOVED: "+component);
						((SoundComponent)component).clip.stop();
						((SoundComponent)component).isPlaying = false;
						playingClips.remove(component);
				}
		}
		@Override
		public void onSystemAdded(UpdateSystem system) {}
		
}
