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
    private static final ArrayList<Class<?>> plugins = new ArrayList<>();
//    public static void removePlugin(String className) {
//        List<Class<?>> filteredPlugin = plugins.stream()
//                .filter(plugin -> plugin.getName().equalsIgnoreCase(className))
//                .collect(Collectors.toList());
//        plugins.removeAll(filteredPlugin);
//    }
    public static List<Object> getPluginsWithClass(@NotNull Class<?> cls) {
        List<Class<?>> pluginsClass = plugins.stream().filter(cls::isAssignableFrom).collect(Collectors.toList());
        return pluginsClass.stream().map(plugin -> {
            try {
                Object instance = plugin.newInstance();
                return instance;
            } catch (InstantiationException | IllegalAccessException e) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
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
            List<String> classNames = new ArrayList<>();
            while (jarEntries.hasMoreElements()) {
                JarEntry jarEntry = jarEntries.nextElement();
                if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
                    continue;
                }
                String className = jarEntry.getName().replace('/', '.');
                className = className.substring(0, className.length() - ".class".length());
                if (!className.equalsIgnoreCase("module-info") && !className.equalsIgnoreCase("com.htn.api.Plugin")) {
                    try {
                        Class<?> clazz = classLoader.loadClass(className);
                        if (Plugin.class.isAssignableFrom(clazz) && Modifier.isPublic(clazz.getModifiers())) {
                            classNames.add(clazz.getName());
                            plugins.add(clazz);
                            Plugin plugin = (Plugin) clazz.newInstance();
                            System.out.println("loading " + className);
                            plugin.load();
                        }
                    } catch (NoClassDefFoundError | InstantiationException | IllegalAccessException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            System.out.println(classNames);
            Collections.sort(classNames);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
