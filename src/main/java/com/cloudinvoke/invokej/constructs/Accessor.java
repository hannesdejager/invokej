package com.cloudinvoke.invokej.constructs;

/**
 * An "invokeable" that can get a value and that can set a value, both of the same type
 *
 * @author Hannes de Jager
 * @since 2012/07/05
 */
public interface Accessor<TYPE> extends Getter<TYPE>, Setter<TYPE> {
}
