package com.example.speler.scripting;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ScriptManager {
    private static final Map<String, Class<? extends Script>> scriptRegistry = new HashMap<>();
		private static URLClassLoader classLoader;
		
    // Register a script class
    public static void registerScript(String name, Class<? extends Script> clazz) {
        scriptRegistry.put(name, clazz);
    }

    // Get a new instance of a script by name
    public static Script getScript(String name) {
        Class<? extends Script> clazz = scriptRegistry.get(name);
				if (clazz == null) {
						try{
								return (Script) classLoader.loadClass(name).getDeclaredConstructor().newInstance();
						} catch (Exception e) {
								e.printStackTrace();
								return null;
						}
				}

        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Optional: check if a script is registered
		public static boolean isRegistered(String name) {
				return scriptRegistry.containsKey(name);
		}

		public static void loadScripts(String folderPath) {
				try {
						File folder = new File(folderPath);
						URL[] urls = { folder.toURI().toURL() };
						ScriptManager.classLoader = new URLClassLoader(urls, ScriptManager.class.getClassLoader());
				} catch (Exception e) {
						e.printStackTrace();
				}
		}
		
}
