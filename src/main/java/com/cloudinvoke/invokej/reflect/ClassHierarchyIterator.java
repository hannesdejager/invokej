package com.cloudinvoke.invokej.reflect;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterates over the class Hierarchy of a given class, including all implemented and super 
 * implemented interfaces.
 * <p>
 * The current implementation yields the following in the following sequence during iteration:
 * <ol>
 *   <li>The class itself
 *   <li>The implemented interfaces of the class in declaration order
 *   <li>Does 1 and 2 and 3 for the superclass until no more superclass
 * </ol>
 * <p>
 * Limitations: The iterator does not traverse over parent interfaces of implemented interfaces and 
 *              may yield a certain interface more than once if it is implemented in more than one
 *              level of the hierarchy. 
 * 
 * @author Hannes de Jager
 * @since 10 Jul 2008
 */
public class ClassHierarchyIterator implements Iterator<Class<?>>, Iterable<Class<?>>{

    /** The interfaces of {@link #currentClass} */
    private Class<?>[] clazzInterfaces;
    
    /** The current class that is being interrogated. */
    private Class<?> currentClass;
    
    /** The index into {@link #clazzInterfaces} or -1 to denote the use of {@link #currentClass} */
    private int index;
    
    /** The value that will be yielded next if {@link #next()} is called. */
    private Class<?> nextYieldValue;
    
    
    /**
     * Constructor. 
     *
     * @param clazz The class lowest in the hierarchy. May be null.
     * @throws IllegalArgumentException If the supplier object represents an interface rather than an actual class.
     */
    public ClassHierarchyIterator(Class<?> clazz) {
        if (clazz != null && clazz.isInterface())
            throw new IllegalArgumentException("Supplied class type must be an actual class and not an interface.");
        
        this.nextYieldValue = clazz;
        this.currentClass = clazz;
        this.index = -1;
    }
    
    /**
     * {@inheritDoc}
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
        return nextYieldValue != null;
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<Class<?>> iterator() {
        return this;
    }

    /**
     * {@inheritDoc}
     * @see java.util.Iterator#next()
     */
    public Class<?> next() {
        if (nextYieldValue == null)
            throw new NoSuchElementException();
        
        Class<?> result = nextYieldValue;
        
        // If currently referring to a class (not an interface)
        if (index == -1) {
            clazzInterfaces = currentClass.getInterfaces();
            index = 0;
        }
        
        // If not done with interfaces of current class.
        if (index < clazzInterfaces.length) {
            nextYieldValue = clazzInterfaces[index];
            index++;
        }
        else { // Move to superclass
            currentClass = currentClass.getSuperclass();
            nextYieldValue = currentClass;
            index = -1;
            clazzInterfaces = null;
        }
        
        return result;
    }
    
    /**
     * {@inheritDoc}
     * @see java.util.Iterator#remove()
     * 
     * @throws UnsupportedOperationException Always 
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
