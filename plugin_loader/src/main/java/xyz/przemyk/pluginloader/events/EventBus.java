package xyz.przemyk.pluginloader.events;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class EventBus {

    private static final Set<Method> eventHandlers = new HashSet<>();

    public static void loadEventHandlers() {

        Reflections reflections = new Reflections(new MethodAnnotationsScanner());
        Set<Method> methods = reflections.getMethodsAnnotatedWith(SubscribeEvent.class);
        for (Method method : methods) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 1 && Event.class.isAssignableFrom(parameterTypes[0])) {
                eventHandlers.add(method);
            }
        }
    }

    public static<T extends Event> void post(T event) {
        for (Method method : eventHandlers) {
            if (method.getParameterTypes()[0].isAssignableFrom(event.getClass())) {
                try {
                    method.invoke(null, event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
