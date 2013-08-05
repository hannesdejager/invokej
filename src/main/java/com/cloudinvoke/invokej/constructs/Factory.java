package com.cloudinvoke.invokej.constructs;

/**
 * @author Hannes de Jager
 * @since 22 May 2013 
 *
 * @param <RESULT>
 */
public interface Factory<RESULT> {
	RESULT createInstance();
}
