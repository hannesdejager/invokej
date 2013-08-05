package com.cloudinvoke.invokej;

/**
 * Exception that can be thrown when a method is invoked with a null argument
 * that should not have been null. Created to save some typing
 * 
 * @author Hannes de Jager
 * @since 05 Oct 2007
 */
public class ArgumentNullException extends IllegalArgumentException {

	/**
	 * Constructor.
	 * 
	 * @param argumentName the name of the argument that cannot be null.
	 */
	public ArgumentNullException(String argumentName) {
		super(new StringBuilder(40).append("Argument \"").append(argumentName)
				.append("\" cannot be null.").toString());
	}

}
