package com.cloudinvoke.invokej.constructs.io;

import java.io.IOException;

public interface IoTransform<I,O> {
	
	public O transform(I input) throws IOException;
}
