package com.cloudinvoke.invokej.structs;

/**
 * A wrapper for objects to simulate out parameters in Java i.e. where you want to 
 * return a value from a method if you can't or don't want to use the method result.
 * <p>
 * The java hack way to do this is to pass in an array of 1 item. This class tries 
 * to avoid this and tries to be more explicit about the intention. 
 * 
 * @author Hannes de Jager
 * @since 18 Jun 2009
 */
public class OutParam<TYPE> {

    /** Stored the parameter. */
    public TYPE value;
    
    public OutParam() { }
    
    public OutParam(TYPE initialValue) {
    	this.value = initialValue;
    }
    
}
