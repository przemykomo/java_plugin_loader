package xyz.przemyk.testplugin;

import xyz.przemyk.pluginloader.IPlugin;
import xyz.przemyk.pluginloader.Plugin;

@Plugin
public class TestPlugin implements IPlugin {
    static {
        System.out.println("TestPlugin static");
    }

    public TestPlugin() {
        System.out.println("TestPlugin constructor");
    }

    @Override
    public void testPrint() {
        System.out.println("Test print1!");
    }
}
