package com.cloudinvoke.invokej.constructs.ex;


/**
 * Helper methods for this package.
 * 
 * @author Hannes de Jager
 * @since 22 May 2013 
 */
public class ExConstructs {

	private static ExTransform<?, ?> IDENTITY_TRANSFORM = new ExTransform<Object, Object>() {
		public Object transform(Object input) {
			return input;
		}
	};

	@SuppressWarnings("unchecked")
	public <T> ExTransform<T, T> identityTransform(Class<T> type) {
		return (ExTransform<T, T>)IDENTITY_TRANSFORM;
	}
	
}
