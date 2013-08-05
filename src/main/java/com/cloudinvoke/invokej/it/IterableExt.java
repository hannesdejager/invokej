package com.cloudinvoke.invokej.it;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;

import javax.swing.tree.DefaultMutableTreeNode;

import com.cloudinvoke.invokej.constructs.Transform;

/**
 * This interface extends the basic functionality of the {@link Iterable} interface. Use the from 
 * methods in the {@link Itq} class to get an instance that implements this interface.
 * <p>
 * Using {@link Itq}'s from methods, we can convert {@link Iterable}s, {@link Iterator}s, 
 * arrays, Enums, {@link Enumeration}s, separate variables and even File contents to a consistent object 
 * that is iterable and is compatible with the {@link Iterable} interface which can be used with 
 * Java foreach loops.
 * 
 * Example use - Iterating a over the string properties inside members of an ENUM: 
 * <pre>
 *    List<String> files = IterableE
 *      .from(AppImage.values())
 *      .select(new Transform<AppImage, String>() {
 *          public String transform(AppImage input) {
 *              return input.getFilename();
 *          }})
 *      .toList();
 *      
 *    for (String filename : files)
 *        System.out.println(filename);
 *      
 * </pre>
 * 
 * Another example - Iterating the user objects of the children of a 
 * {@link DefaultMutableTreeNode} as their actual type, which in this case is String:
 * <pre>
 *    IterableI<String> textIter = 
 *      .from((Enumeration<?>)node.children())
 *      .cast(DefaultMutableTreeNode.class)
 *      .select(new Transform<DefaultMutableTreeNode, String>() {
 *          public String transform(DefaultMutableTreeNode input) {
 *             return (String)input.getUserObject();
 *         }
 *      });
 *      
 *   for (String text : textIter) {
 *     System.out.println("Node Text: " + textIter);   
 *   }   
 * </pre>
 * 
 * 
 * @see Itq
 * @see Transform
 * 
 * @author Hannes de Jager
 * @since 02 Sep 2008
 */
public interface IterableExt<T> extends Iterable<T> {

    /**
     * Returns the number of elements in the iterable.
     * @return number of elements
     */
    public int size();

    /**
     * Returns an {@link Iterable} object that will, when iterated, yield a subset of the original 
     * {@link Iterable}'s elements using the result of the supplied condition as criteria on every 
     * element. 
     * 
     * @param condition The condition that should be true for elements that should be in the result set.
     * @return a new non-null {@link IterableExt} instance.
     */
    public IterableExt<T> where(final Transform<T, Boolean> condition);
    
    /**
     * Returns an {@link Iterable} object that will, when iterated, yield only those items in the original set
     * that is not null
     * 
     * @return a new non-null {@link IterableExt} instance.
     */
    public IterableExt<T> whereNotNull();

    /**
     * Converts an source iterator to an new iterator that yields an adapted version of the source
     * elements on each iteration according to the transformation function specified. 
     * 
     * @param <O> The resultant iterator's element type
     * @param transform The transformation to apply
     * @return The adaption iterator as a new non-null {@link IterableExt} instance.
     */
    public <O> IterableExt<O> select(final Transform<T, O> transform);
    
    /** 
     * Casts this class to an {@link IterableExt} over O
     * 
     * @param <O> The type of element in the casted {@link IterableExt}
     * @param target The target type.
     * @return A new {@link IterableExt} instance with casted elements.
     */
    public <O> IterableExt<O> cast(Class<O> target);    
    
    /**
     * Converts an iterator to a list by iterating through it and collecting the items.
     * 
     * @return The resultant list. Will never be null.
     */
    public List<T> toList();
    
    /**
     * Converts an iterator to a stack by iterating through it and pushing the items on the stack.
     * 
     * @return The resultant stack. Will never be null.
     */
    public Stack<T> toStack();

    /**
     * Converts an iterator to a map by iterating through it and collecting the items, using the 
     * specified key producer to get the map key for each item.
     * 
     * @param keyProducer an object that creates the map key to use from an iteration variable.
     * @return The resultant map. Will never be null.
     */
    public <K> Map<K, T> toMap(Transform<T, K> keyProducer);
    
    /**
     * Joins 2 or more iterators into a new iterator that will, when iterated yield all the items
     * of the first iterator in sequence, then the items of the second iterator in sequence until all
     * iterators have been iterated.
     * 
     * @param itr2 The iterator to join onto the end of this iterator
     * @param rest Some additional iterators to add in sequence.
     * @return A new {@link IterableExt} instance over {@link Object}'s representing the joint iterators.
     */
    public IterableExt<T> union(final Iterable<T> itr2, final Iterable<T>... rest);
    
    /**
     * Iterate through the items and return the number of items found
     * @return - Number of items iterated
     */
    public int iterate();
    
    /**
     * Returns an object that allows iteration over the original iterable object in pages of the 
     * specified size.
     * 
     * @param count The page size i.e. the amount of items in each group.
     * @return An iterable over lists of the element type.
     */
    public Iterable<List<T>> inGroupsOf(final int count);

    /**
     * Gets the iterator, performs the first loop and yields the first iteration variable. Same as
     * calling #iterator() and then calling its next() method.
     * @return The first iteration variable.
     */
    public T single() throws NoSuchElementException;
    
    public StringBuilder concat(String seperator);
  
}