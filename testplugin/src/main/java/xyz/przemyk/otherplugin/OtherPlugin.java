package xyz.przemyk.otherplugin;

import xyz.przemyk.pluginloader.IPlugin;
import xyz.przemyk.pluginloader.Plugin;

@Plugin
public class OtherPlugin implements IPlugin {
    @Override
    public void testPrint() {
        System.out.println("Other test print 5!!!");
    }
}
