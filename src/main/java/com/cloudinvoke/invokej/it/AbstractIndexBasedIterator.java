
package com.cloudinvoke.invokej.it;

import java.util.NoSuchElementException;


/**
 * A convenience base class for iterators that are index based 
 * (e.g. the <code>for (int i = 0; i &lt; count; i++)</code> type). The iterator start at zero and
 * stop before count is reached.
 * <p>
 * Simply implement the next method.
 * <p>
 * The iterator does not support the remove operation.
 * 
 * 
 * @author Hannes de Jager
 * @since 17 Apr 2009
 */
public abstract class AbstractIndexBasedIterator<E> extends NoRemoveIterator<E> {

    private int count;
    private int index;
    
    /**
     * Constructor. 
     *
     * @param count The total amount of items to iterate over.
     */
    public AbstractIndexBasedIterator(int count) {
        this.count = count;
        this.index = 0;
    }
    
    /**
     * {@inheritDoc}
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
        return index < count;
    }
    
    /**
     * @return The iterator index or position
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * {@inheritDoc}
     * @see java.util.Iterator#next()
     */
    public E next() {
        if (index >= count)
            throw new NoSuchElementException("Invalid index: " + index);
        return next(index++);
    }
    
    protected abstract E next(int index);
    
}

