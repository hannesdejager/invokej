package com.cloudinvoke.invokej.it;

import java.util.Iterator;

/**
 * This class exists as convenience for creating Iterators that does not support the remove method.
 * 
 * @author Hannes de Jager
 * @since 16 Apr 2009
 */
public abstract class NoRemoveIterator<T> implements Iterator<T> {

    /**
     * {@inheritDoc}
     * @see java.util.Iterator#remove()
     */
    public void remove() {
        throw new UnsupportedOperationException("remove not supported on iterator");
        
    }
    
}
