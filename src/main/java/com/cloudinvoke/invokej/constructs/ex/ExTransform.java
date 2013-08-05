package com.cloudinvoke.invokej.constructs.ex;

public interface ExTransform<I,O> {
	
	public O transform(I input) throws Exception;
}
