package com.cloudinvoke.invokej.structs;


/**
 * A pair of objects
 * 
 * @author Hannes de Jager
 * @since 07 May 2008
 */
public class Pair<LEFT_TYPE, RIGHT_TYPE> {

    /** The left or first object in the pair. */
    public LEFT_TYPE left;
    
    /** The right or second object in the pair. */
    public RIGHT_TYPE right;
    
    
    /**
     * Constructor. 
     */
    public Pair(LEFT_TYPE left, RIGHT_TYPE right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public boolean equals(Object obj ) {
        if (!(obj instanceof Pair))
            return false ;

        Pair<?, ?> other = (Pair<?, ?>)obj ;
        return other.left == left && other.right == right;
    }
    
    @Override
    public int hashCode() {
        int result = System.identityHashCode(left);
        result = 31 * result + System.identityHashCode(right);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append("Left: ").append(left.toString())
            .append(", Right: ").append(right.toString())
            .toString();
    }    
}
