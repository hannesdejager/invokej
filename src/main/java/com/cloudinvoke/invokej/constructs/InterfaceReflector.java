package com.cloudinvoke.invokej.constructs;

import java.util.Iterator;

/**
 * Objects that fulfill the requirements of this interface can provide interfaces to a requester if the 
 * object implements the interface either through inheritance (statically) or containment/aggregation (dynamically).
 * <p>
 * A requester can basically ask an class via InterfaceReflector: "Hey I need an interface of type X, can you give me a workable 
 * one. I don't care if you implement it yourself or get it from somewhere else, I just need to work with one." 
 * <p>
 * This mechanism is can be used to ask plug-ins about their capabilities and to make use of them.
 * <p>
 * You can see this design as a mini-variant of the Extension Interface pattern as described in POSA II (Pattern-Oriented Software 
 * Architecture Volume 2). This is the same pattern on which COM, EJB and CORBA is largely based (The idea was inspired by a book
 * about COM titled Inside COM). I'd like to believe that this variant provides most of the benefits at a fraction of the complexity of the 
 * full-fledged pattern. The Netbeans framework's Lookup class also seem very similar to this one. 
 * <p>
 * The main benefits provided by the additional indirection introduced by this mechanism would be:
 * <ul>
 *   <li>Extensibility - Extending the functionality of a component like a plug-in don't require the main interface to change
 *   <li>Prevent class bloating - The main plug-in class don't have to implement every single supported interface directly via inheritance which 
 *                                either causes very long classes or unnecessary delegation
 *   <li>Loose coupling - Separate interfaces can be implemented separately and need no be tied to the main interface.
 *   <li>Less code for the implementor - You don't have to provide a get method for every sub-interface you need to make available to clients.
 *   <li>Polymorphism is accomplished through composition instead of inheritance 
 * </ul>
 * 
 * @author Hannes de Jager
 * @since 2011-04-14
 */
public interface InterfaceReflector {
	
	/**
	 * Returns an implementation of the requested interface if it is available, otherwise null. 
	 */
	public <T> T queryInterface(Class<T> query);
	
	/**
	 * @return The classes of all interfaces that are available.
	 */
	public Iterator<Class<?>> availableInterfaces();
}
