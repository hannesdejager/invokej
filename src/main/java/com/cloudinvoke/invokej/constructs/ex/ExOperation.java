package com.cloudinvoke.invokej.constructs.ex;

/**
 * Represents a function that performs some action on its input. Differs from {@link Operation} in that
 * its perform method allows all checked exceptions to be thrown.
 * 
 * @author Hannes de Jager
 * @since 07 Dec 2011
 */
public interface ExOperation<I> {
    
    /**
     * Performs the operation.
     * @param context the input value to the operation.
     */
    public void perform(I context) throws Exception;
}
