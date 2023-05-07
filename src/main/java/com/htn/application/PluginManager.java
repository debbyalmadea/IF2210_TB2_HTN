package com.htn.application;
import com.htn.api.Plugin;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class PluginManager {
    private static final HashMap<String, ArrayList<Class<?>>> plugins = new HashMap<>();
    public static List<Object> getPluginsWithClass(@NotNull Class<?> cls) {
        List<Class<?>> pluginsClass = new ArrayList<>();
        plugins.keySet().forEach(key -> {
                    pluginsClass.addAll(plugins.get(key)
                    .stream().filter(cls::isAssignableFrom).collect(Collectors.toList()));
        });
        return pluginsClass.stream().map(plugin -> {
            try {
                Plugin instance = (Plugin) plugin.newInstance();
                instance.load();
                return instance;
            } catch (InstantiationException | IllegalAccessException e) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
    public static void removePluginsWithJar(String jarName) {
        plugins.remove(jarName);
    }
    public static void load(String filepath) {
        File file = new File(filepath);
        try {
            URL url = file.toURI().toURL();
            URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
            Enumeration<JarEntry> jarEntries;
            if (url.getProtocol().equals("file")) {
                JarFile jarFile = new JarFile(file);
                jarEntries = jarFile.entries();
            } else {
                JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                jarEntries = jarURLConnection.getJarFile().entries();
            }
            ArrayList<Class<?>> classes = new ArrayList<>();
            while (jarEntries.hasMoreElements()) {
                JarEntry jarEntry = jarEntries.nextElement();
                if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
                    continue;
                }
                String className = jarEntry.getName().replace('/', '.');
                className = className.substring(0, className.length() - ".class".length());
                if (!className.equalsIgnoreCase("module-info") && !className.equalsIgnoreCase("com.htn.api.Plugin")) {
                    try {
                        Class<?> pluginClass = classLoader.loadClass(className);
                        if (Plugin.class.isAssignableFrom(pluginClass) && Modifier.isPublic(pluginClass.getModifiers())) {
                            classes.add(pluginClass);
                        }
                    } catch (NoClassDefFoundError e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            plugins.put(file.getName(), classes);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
