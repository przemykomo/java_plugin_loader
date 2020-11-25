package xyz.przemyk.pluginloader.classloader;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

public class CustomClassLoader extends URLClassLoader {

    static {
        registerAsParallelCapable();
    }

    public CustomClassLoader(String name, ClassLoader parent) {
        super(name, new URL[0], parent);
    }

    public CustomClassLoader(ClassLoader parent) {
        this("classpath", parent);
        System.out.println("classloading!");
    }

    public CustomClassLoader() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public void add(URL url) {
        addURL(url);
    }

    public static CustomClassLoader findAncestor(ClassLoader cl) {
        do {
            if (cl instanceof CustomClassLoader) {
                return (CustomClassLoader) cl;
            }

            cl = cl.getParent();
        } while (cl != null);

        return null;
    }

    @SuppressWarnings("unused")
    private void appendToClassPathForInstrumentation(String jarfile) throws IOException {
        add(Paths.get(jarfile).toRealPath().toUri().toURL());
    }
}
