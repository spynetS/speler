package com.example.scripting;

import java.util.HashMap;
import java.util.Map;

public class ScriptManager {
    private static final Map<String, Class<? extends Script>> scriptRegistry = new HashMap<>();

    // Register a script class
    public static void registerScript(String name, Class<? extends Script> clazz) {
        scriptRegistry.put(name, clazz);
    }

    // Get a new instance of a script by name
    public static Script getScript(String name) {
        Class<? extends Script> clazz = scriptRegistry.get(name);
        if (clazz == null) return null;

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
}
