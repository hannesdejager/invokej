package com.cloudinvoke.invokej.constructs.io;

import java.io.IOException;

public interface IoProducer<T> {

	public T produce() throws IOException;
	
}
