package com.cloudinvoke.invokej.constructs;

/**
 * An "invokeable" that sets a value of a certain type
 *
 * @author Hannes de Jager
 * @since 2012/07/05
 */
public interface Setter<TYPE> {

    public void set(TYPE value);

}
