package xyz.przemyk.testplugin;

import xyz.przemyk.pluginloader.events.DifferentEvent;
import xyz.przemyk.pluginloader.events.Event;
import xyz.przemyk.pluginloader.events.OtherEvent;
import xyz.przemyk.pluginloader.events.SubscribeEvent;

public class EventHandler {

    @SubscribeEvent
    public static void onEvent(Event event) {
        System.out.println("Event received with id: " + event.id);
    }

    @SubscribeEvent
    public static void onOtherEvent(OtherEvent event) {
        System.out.println("OtherEvent received with id: " + event.id);
    }

    @SubscribeEvent
    public static void onDifferentEvent(DifferentEvent event) {
        System.out.println("DifferentEvent received with id: " + event.id);
    }
}
