package com.cloudinvoke.invokej.constructs.io;


/**
 * Helper methods for this package.
 * 
 * @author Hannes de Jager
 * @since 22 May 2013 
 */
public class IoConstructs {

	private static IoTransform<?, ?> IDENTITY_TRANSFORM = new IoTransform<Object, Object>() {
		public Object transform(Object input) {
			return input;
		}
	};

	@SuppressWarnings("unchecked")
	public <T> IoTransform<T, T> identityTransform(Class<T> type) {
		return (IoTransform<T, T>)IDENTITY_TRANSFORM;
	}
	
}
