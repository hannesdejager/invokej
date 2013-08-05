package com.cloudinvoke.invokej.constructs.io;

import java.io.IOException;

/**
 * Encapsulates a piece of code that may throw an IO Exception
 * 
 * @author Hannes de Jager
 * @since 14 Aug 2009
 */
public interface IoCommand {
    
    /**
     * Executes the command.
     */
    void execute() throws IOException;
}
