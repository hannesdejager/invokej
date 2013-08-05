package com.cloudinvoke.invokej.it;

import java.util.NoSuchElementException;

/**
 * An iterator of one single item. 
 * 
 * @author Hannes de Jager
 * @since 2012-02-09
 * @param <T>
 */
public final class SingleItemIterator<T> extends NoRemoveIterator<T> {

	private T item;
	private boolean iterated;
	
	/**
	 * @param item The item to yield on the first iteration request.
	 */
	public SingleItemIterator(T item) {
		this.item = item;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean hasNext() {
		return !iterated;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public T next() {
		if (iterated)
			throw new NoSuchElementException();
		iterated = true;
		return item;
	}

}
