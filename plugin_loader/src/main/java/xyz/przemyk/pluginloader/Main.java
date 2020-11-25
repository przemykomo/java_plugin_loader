package xyz.przemyk.pluginloader;

import xyz.przemyk.pluginloader.classloader.PluginLoader;

public class Main {

    // java14 -jar plugin_loader-0.1.jar -Djava.system.class.loader=xyz.przemyk.pluginloader.classloader.CustomClassLoader
    @SuppressWarnings("SpellCheckingInspection")
    public static void main(String[] args) {
        PluginLoader.loadPlugins();

        for (IPlugin plugin : PluginLoader.plugins) {
            plugin.testPrint();
        }
    }
}