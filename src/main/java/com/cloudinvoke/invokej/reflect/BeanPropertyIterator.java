package com.cloudinvoke.invokej.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;

import com.cloudinvoke.invokej.constructs.Transform;
import com.cloudinvoke.invokej.it.Itq;

/**
 * Allows iteration over Bean getter and setter methods in an object.
 * 
 * @author Hannes de Jager
 * @since 28 Jan 2009
 */
public class BeanPropertyIterator implements Iterable<BeanProperty> {
    
    private static final int MODIFIER_FILTER = (Modifier.PUBLIC | Modifier.STATIC);
    private static final int MODIFIER_EXPECTED = Modifier.PUBLIC;
    
    /**
     * Indicator to filter getter or setter methods. 
     */
    public enum Filter {
        
        /** Only getter methods. */
        GETTERS(new Transform<Method, Boolean>(){
            public Boolean transform(Method input) {
                return (input.getName().startsWith("get") || 
                        input.getName().startsWith("is")) 
                    &&  input.getParameterTypes().length == 0;
            };
        }),
        
        /** Only setter methods. */
        SETTTERS(new Transform<Method, Boolean>(){
            public Boolean transform(Method input) {
                return input.getName().startsWith("set") && 
                    input.getParameterTypes().length == 1;
            };
        }),
        
        /** Getter and setter methods. */
        BOTH(new Transform<Method, Boolean>(){
            public Boolean transform(Method input) {
                return Filter.SETTTERS.condition.transform(input) || 
                    Filter.GETTERS.condition.transform(input);
            };
        });
        
        private Transform<Method, Boolean> condition;
        
        private Filter(Transform<Method, Boolean> condition) {
            this.condition = condition;
        }
                
    };
    
    /**
     * Iterate parent methods also?
     */
    public enum Scope {
        PARENTS_ALSO() {
            @Override
            protected Method[] getMethods(Class<?> c) {
                return c.getMethods();
            };
        },
        THIS_CLASS_ONLY() {
            @Override
            protected Method[] getMethods(Class<?> c) {
                return c.getDeclaredMethods();
            }
        };
        
        protected abstract Method[] getMethods(Class<?> c);
    }
    
    private final Filter filter;
    private final Scope scope;
    private final Class<?> theClass;

    public BeanPropertyIterator(Class<?> theClass, Filter what, Scope scope) {
        this.filter = what;
        this.theClass = theClass;
        this.scope = scope;
    }
    
    public BeanPropertyIterator(Class<?> theClass) {
        this(theClass, Filter.BOTH, Scope.PARENTS_ALSO);
    }
    
    private static boolean isPublic(Method method) {
        return (method.getModifiers() & MODIFIER_FILTER) == MODIFIER_EXPECTED; 
    }
    
    public Iterator<BeanProperty> iterator() {
        return Itq
            .from(this.scope.getMethods(this.theClass))
            .where(new Transform<Method, Boolean>() {
                public Boolean transform(Method input) {                    
                    return isPublic(input) && filter.condition.transform(input);
                }
            })
            .select(new Transform<Method, BeanProperty>() {
            	public BeanProperty transform(Method input) {
            		return new BeanProperty(input);
            	}
			})
            .iterator();
    }
    
}
