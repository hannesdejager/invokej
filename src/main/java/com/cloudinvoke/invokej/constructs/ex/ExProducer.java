package com.cloudinvoke.invokej.constructs.ex;

public interface ExProducer<T> {

	public T produce() throws Exception;
	
}
