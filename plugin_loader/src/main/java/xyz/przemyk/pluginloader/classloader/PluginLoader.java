package xyz.przemyk.pluginloader.classloader;

import org.reflections.Reflections;
import xyz.przemyk.pluginloader.IPlugin;
import xyz.przemyk.pluginloader.Plugin;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PluginLoader {
    public static Set<IPlugin> plugins = new HashSet<>();

    public static void loadPlugins() {
        loadClasses();

        Reflections reflections = new Reflections();
        Set<Class<?>> pluginClasses = reflections.getTypesAnnotatedWith(Plugin.class);
        for (Class<?> pluginClass : pluginClasses) {
            try {
                IPlugin plugin = (IPlugin) pluginClass.getDeclaredConstructor().newInstance();
                plugins.add(plugin);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadClasses() {
        try (Stream<Path> paths = Files.walk(Paths.get(System.getProperty("user.dir"), "plugins"))) {
            List<URL> classpaths = paths.filter(Files::isRegularFile).map(Path::normalize).map(path -> {
                try {
                    return path.toUri().toURL();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());

            CustomClassLoader customClassLoader = CustomClassLoader.findAncestor(Thread.currentThread().getContextClassLoader());
            if (customClassLoader == null) {
                System.err.println("Custom class loader is null!");
                customClassLoader = new CustomClassLoader();
            }

            for (URL url : classpaths) {
                customClassLoader.add(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
