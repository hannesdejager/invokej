package com.cloudinvoke.invokej.constructs;

/**
 * Encapsulates a piece of code that can be executed. An object oriented version
 * of a function pointer if you like. Also acts as an abstraction that can be used
 * to implement the command pattern.
 * 
 * @author Hannes de Jager
 * @since 26 Aug 2008 
 */
public interface Command {

	/** Performs the command */
	void execute();
}
