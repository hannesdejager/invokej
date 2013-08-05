package com.cloudinvoke.invokej.constructs.io;

import java.io.IOException;

/**
 * Represents a function that performs some action on its input.
 * 
 * @author Hannes de Jager
 * @since 29 Dec 2010
 */
public interface IoOperation<CONTEXT> {
    
    /**
     * Performs the operation.
     * @param input the input value to the operation.
     */
    public void perform(CONTEXT context) throws IOException;
}
