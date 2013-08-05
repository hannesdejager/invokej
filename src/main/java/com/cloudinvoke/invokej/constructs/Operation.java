package com.cloudinvoke.invokej.constructs;

/**
 * Can be used to encapsulate code to be performed on some input.
 * 
 * @author Hannes de Jager
 * @since 26 Aug 2008
 *
 * @param <CONTEXT>
 */
public interface Operation<CONTEXT> {

	void perform(CONTEXT context);
	
}
