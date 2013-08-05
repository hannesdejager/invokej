package com.cloudinvoke.invokej.constructs;

/**
 * An "invokeable" that gets a value of a certain type
 *
 * @author Hannes de Jager
 * @since 2012/07/05
 */
public interface Getter<TYPE> {

    public TYPE get();

}
