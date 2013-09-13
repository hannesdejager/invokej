package com.cloudinvoke.invokej.structs;

/**
 * A container of 3 objects.
 * 
 * @author Hannes de Jager
 * @since 21 Sep 2009
 */
public class Triplet<LEFT_TYPE, MIDDLE_TYPE, RIGHT_TYPE> {

    /** The left or first object in the pair. */
    public LEFT_TYPE left;

    /** The middle or second object in the pair. */
    public MIDDLE_TYPE middle;
    
    /** The right or third object in the pair. */
    public RIGHT_TYPE right;
    

    public Triplet(LEFT_TYPE left, MIDDLE_TYPE middle, RIGHT_TYPE right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }
    
    @Override
    public boolean equals(Object obj ) {
        if (!(obj instanceof Triplet<?, ?, ?>))
            return false ;

        Triplet<?, ?, ?> other = (Triplet<?, ?, ?>)obj ;
        return other.left == left && other.middle == middle && other.right == right;
    }
    
    @Override
    public int hashCode() {
        int result = System.identityHashCode(left);
        result = 31 * result + System.identityHashCode(middle);
        result = 31 * result + System.identityHashCode(right);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append("Left: ").append(left.toString())
            .append(", Middle: ").append(middle.toString())
            .append(", Right: ").append(right.toString())
            .toString();
    }        
    
}
