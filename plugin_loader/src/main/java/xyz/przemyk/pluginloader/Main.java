package xyz.przemyk.pluginloader;

import xyz.przemyk.pluginloader.classloader.CustomClassLoader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    // java14 -jar plugin_loader-0.1.jar -Djava.system.class.loader=xyz.przemyk.pluginloader.classloader.CustomClassLoader
    @SuppressWarnings("SpellCheckingInspection")
    public static void main(String[] args) {
        System.out.println("Main.main() start");

        try (Stream<Path> paths = Files.walk(Paths.get(System.getProperty("user.dir"), "plugins"))) {
            List<URL> classpaths = paths.filter(Files::isRegularFile).map(Path::normalize).map(path -> {
                try {
                    return path.toUri().toURL();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());

            System.out.println(classpaths);

            CustomClassLoader customClassLoader = CustomClassLoader.findAncestor(Thread.currentThread().getContextClassLoader());
            if (customClassLoader == null) {
                System.out.println("Custom class loader is null!");
                customClassLoader = new CustomClassLoader();
            }

            for (URL url : classpaths) {
                customClassLoader.add(url);
            }

            Class<?> pluginClass = Class.forName("xyz.przemyk.testplugin.TestPlugin", true, customClassLoader);
            System.out.println(pluginClass.getTypeName());

            IPlugin plugin = (IPlugin) pluginClass.getDeclaredConstructor().newInstance();
            plugin.testPrint();
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}