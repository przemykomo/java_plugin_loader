package xyz.przemyk.pluginloader;

import org.reflections.Reflections;
import xyz.przemyk.pluginloader.classloader.PluginLoader;
import xyz.przemyk.pluginloader.events.DifferentEvent;
import xyz.przemyk.pluginloader.events.Event;
import xyz.przemyk.pluginloader.events.EventBus;
import xyz.przemyk.pluginloader.events.OtherEvent;

import java.util.Set;

public class Main {

    // java14 -jar plugin_loader-0.1.jar -Djava.system.class.loader=xyz.przemyk.pluginloader.classloader.CustomClassLoader
    @SuppressWarnings("SpellCheckingInspection")
    public static void main(String[] args) {

        Set<IPlugin> plugins = PluginLoader.loadPlugins();
        EventBus.loadEventHandlers();

        for (IPlugin plugin : plugins) {
            plugin.testPrint();
        }

        EventBus.post(new Event("testID"));
        EventBus.post(new DifferentEvent("differentID"));
        EventBus.post(new OtherEvent("otherID"));
    }
}