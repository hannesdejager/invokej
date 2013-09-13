package com.cloudinvoke.invokej.reflect;

import java.lang.reflect.Method;

import com.cloudinvoke.invokej.ArgumentNullException;

/**
 * Wraps a getter/setter method and extracts some info from it.
 * 
 * @author Hannes de Jager
 * @since 2013-09-14
 */
public class BeanProperty {
	
	public final Method method;
	public final boolean isGetter;
	public final String propertyName;
	
	public BeanProperty(Method method) {
		if (method == null)
			throw new ArgumentNullException("method");
		String name = method.getName();
		String propertyName;
		boolean isGetter;
		if (name.startsWith("set")) {
			if (name.length() == 3)
				propertyName = "";
			else
				propertyName = name.substring(3);
			isGetter = false;
		}
		else {
			isGetter = true;
			if (name.startsWith("is")) {
				if (name.length() == 2)
					propertyName = "";
				else
					propertyName = name.substring(2);
			}
			else if (name.startsWith("get")) {
				if (name.length() == 3)
					propertyName = "";
				else
					propertyName = name.substring(3);
			}
			else
				throw new IllegalArgumentException("Not a bean method");
		}
		this.method = method;
		this.isGetter = isGetter;
		this.propertyName = propertyName;
	}

}
