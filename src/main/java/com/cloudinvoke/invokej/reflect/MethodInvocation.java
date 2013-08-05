package com.cloudinvoke.invokej.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Encapsulates all the needed info to invoke a method like the object instance, the arguments and the 
 * {@link Method} reference.
 * 
 * @author Hannes de Jager
 * @since 02 Mar 2011
 */
public class MethodInvocation {

	private final Method method;
	private final Object[] args;
	private final Object instance;
	
	/**
	 * Constructor. 
	 * 
	 * @param instance The instance to invoke the method on.
	 * @param method The method to invoke
	 * @param args The arguments to pass to the method.
	 */
	public MethodInvocation(Object instance, Method method, Object... args) {
		this.method = method;
		this.args = args;
		this.instance = instance;
	}
	
	/** Returns the {@link Method} that will be called. */
	public Method getMethod() {
		return method;
	}
	
	/** Returns the arguments for the method in sequence */
	public Object[] getArgs() {
		return args;
	}
	
	/** Gets the instance on which the method will be called. */
	public Object getInstance() {
		return instance;
	}
	
	/**
	 * Performs the invocation by calling {@link Method#invoke(Object, Object...)}
	 * @see Method#invoke(Object, Object...)
	 */
	public Object perform() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		return method.invoke(instance, args);
	}
	
}
