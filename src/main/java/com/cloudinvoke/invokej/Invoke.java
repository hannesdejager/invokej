package com.cloudinvoke.invokej;

import com.cloudinvoke.invokej.constructs.Transform;

/**
 * @author Hannes de Jager
 * @since 22 May 2013 
 */
public class Invoke {

	private static Transform<?, ?> IDENTITY_TRANSFORM = new Transform<Object, Object>() {
		public Object transform(Object input) {
			return input;
		}
	};

	@SuppressWarnings("unchecked")
	public <T> Transform<T, T> identityTransform(Class<T> type) {
		return (Transform<T, T>)IDENTITY_TRANSFORM;
	}
	
}
