package com.cloudinvoke.invokej.constructs;


/**
 * Tracks the support a component have for interfaces according to the model as imposed 
 * by {@link InterfaceReflector}. This is an interface for convenience that can be passed
 * around between objects in a component like a plug-in to add and remove interface support
 * from different points. 
 * 
 * @see InterfaceReflector
 * 
 * @author Hannes de Jager
 * @since 22 Jul 2011
 */
public interface InterfaceSupportRegistry extends InterfaceReflector {
	
	/**
	 * Adds support for an interface. After calling this method, calls 
	 * to {@link #queryInterface(Class)} should return the specified
	 * implementation when queried for the specified interface.
	 * 
	 * @param intf The interface to support.
	 * @param instance The implementation of the interface
	 * @return The old implementation of the interface if any otherwise null
	 */
	public <T> Object addInterfaceSupport(Class<T> intf, T instance);
	
	
	/**
	 * Removes support for the specified interface. After calling this method, calls 
	 * to {@link #queryInterface(Class)} should return null when queried for the 
	 * specified interface.
	 * 
	 * @param intf The interface to remove support for
	 * @return The previously registered implementation of the interface or null if none
	 *         was previously registerd.
	 */
	public Object removeInterfaceSupport(Class<?> intf);
}
