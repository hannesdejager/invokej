package com.cloudinvoke.invokej.structs;

/**
 * A utility class that stores a value with an associated name
 * 
 * @author Hannes de Jager
 * @since 08 Jun 2009
 */
public class KeyValuePair<KEY_TYPE, VALUE_TYPE> {

    /** The name part of the pair. */
    public final KEY_TYPE key;
    
    /** The value part of the pair. */
    public final VALUE_TYPE value;
    

    public KeyValuePair(KEY_TYPE key, VALUE_TYPE value) {
        this.key = key;
        this.value = value;
    }
    
    @Override
    public boolean equals(Object obj ) {
        if (!(obj instanceof KeyValuePair))
            return false ;

        KeyValuePair<?, ?> other = (KeyValuePair<?, ?>)obj ;
        return other.key == key && other.value == value;
    }
    
    @Override
    public int hashCode() {
        return System.identityHashCode(key) ^ System.identityHashCode(value);
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append("Key: ").append(""+key)
            .append(", Value: ").append(""+value)
            .toString();
    }    
}
