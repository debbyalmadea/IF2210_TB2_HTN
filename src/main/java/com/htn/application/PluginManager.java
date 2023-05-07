package com.htn.application;
import com.htn.api.Plugin;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginManager {
    @Getter private static final ArrayList<Class<?>> plugins = new ArrayList<>();
//    private static PluginManager instance = null;
//    private PluginManager() {
//        load();
//    }
//    public static PluginManager getInstance() {
//        if (instance == null) {
//            instance = new PluginManager();
//        }
//        return instance;
//    }
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
                        System.out.println(classNames);
                        if (Plugin.class.isAssignableFrom(clazz) && Modifier.isPublic(clazz.getModifiers())) {
                            classNames.add(clazz.getName());
                            plugins.add(clazz);
                            Plugin plugin = (Plugin) clazz.newInstance();
                            plugin.load();
                        }
                    } catch (NoClassDefFoundError e) {
                        System.out.println(e.getMessage());
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
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
