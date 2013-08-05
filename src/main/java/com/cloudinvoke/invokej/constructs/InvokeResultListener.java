package com.cloudinvoke.invokej.constructs;

/**
 * @author Hannes de Jager
 * @since 23 May 2013 
 */
public interface InvokeResultListener {
	
	void notifySuccess();
	
	void notifyFailed(Throwable e);
}
