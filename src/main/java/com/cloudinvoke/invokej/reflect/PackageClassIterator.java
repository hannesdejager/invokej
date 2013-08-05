package com.cloudinvoke.invokej.reflect;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * Iterates over the classes in a package if the package resides on the local filesystem.
 * 
 * @author Hannes de Jager
 * @since 05 Feb 2009
 */
public class PackageClassIterator implements Iterator<Class<?>> {

    /** Delegated iterator. */
    private final Iterator<Class<?>> delegate;

    /**
     * Constructor. 
     */
    public PackageClassIterator(Package pkg) {
        try {
            Set<Class<?>> classes = getClasses(pkg.getName());
            delegate = classes.iterator();
        } catch (Exception e) {
            // TODO Throw custom exception...
            throw new RuntimeException("Error creating iterator for classes in package " + pkg.getName());
        }
    }
    
    /**
     * {@inheritDoc}
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
        return delegate.hasNext();
    }
    
    /**
     * {@inheritDoc}
     * @see java.util.Iterator#next()
     */
    public Class<?> next() {
        return delegate.next();
    }
    
    /**
     * {@inheritDoc}
     * @see java.util.Iterator#remove()
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Gets the classes in the specified package.
     * 
     * @param packageName The name of the package.
     * @return A set of {@link Class} instances
     */
    private static Set<Class<?>> getClasses(String packageName) throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return getClasses(loader, packageName);
    }
    
    /**
     * Gets the classes in the specified package using the specified class loader
     * @param loader The class loader
     * @param packageName The ma of the package
     * @return A set of {@link Class} instances
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static Set<Class<?>> getClasses(ClassLoader loader, String packageName) throws IOException, ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = loader.getResources(path);
        if (resources != null) {
           while (resources.hasMoreElements()) {
              String filePath = resources.nextElement().getFile();
              // WINDOWS HACK
              if(filePath.indexOf("%20") > 0)
                 filePath = filePath.replaceAll("%20", " ");
              if (filePath != null) {
                 if ((filePath.indexOf("!") > 0) && (filePath.indexOf(".jar") > 0)) {
                    String jarPath = filePath.substring(0, filePath.indexOf("!"))
                       .substring(filePath.indexOf(":") + 1);
                    // WINDOWS HACK
                    if (jarPath.indexOf(":") >= 0) jarPath = jarPath.substring(1);
                    classes.addAll(getFromJARFile(jarPath, path));
                 } else {
                    classes.addAll(
                       getFromDirectory(new File(filePath), packageName));
                 }
              }
           }
        }
        return classes;
     }    
    
    
    private static Set<Class<?>> getFromDirectory(File directory, String packageName) throws ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        if (directory.exists()) {
           for (String file : directory.list()) {
              if (file.endsWith(".class")) {
                 String name = packageName + '.' + stripFilenameExtension(file);
                 Class<?> clazz = Class.forName(name);
                 classes.add(clazz);
              }
           }
        }
        return classes;
     }    
    
    private static Set<Class<?>> getFromJARFile(String jar, String packageName) throws IOException, ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        JarInputStream jarFile = new JarInputStream(new FileInputStream(jar));
        JarEntry jarEntry;
        do {
           jarEntry = jarFile.getNextJarEntry();
           if (jarEntry != null) {
              String className = jarEntry.getName();
              if (className.endsWith(".class")) {
                 className = stripFilenameExtension(className);
                 if (className.startsWith(packageName))             
                     classes.add(Class.forName(className.replace('/', '.')));
              }
           }
        } while (jarEntry != null);
        return classes;
     }    
    
    private static String stripFilenameExtension(String path) {
        if (path == null) {
            return null;
        }
        int sepIndex = path.lastIndexOf('.');
        return (sepIndex != -1 ? path.substring(0, sepIndex) : path);
    }
    
}
