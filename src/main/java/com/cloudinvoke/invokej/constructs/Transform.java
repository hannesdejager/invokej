package com.cloudinvoke.invokej.constructs;

/**
 * Allows defining the transformation or mapping of one object to another.
 * 
 * @author Hannes de Jager
 * @since 26 Aug 2008 
 *
 * @param <INPUT> The object to transform
 * @param <OUTPUT> The transformed result
 */
public interface Transform<INPUT, OUTPUT> {

	OUTPUT transform(INPUT input);
	
}
