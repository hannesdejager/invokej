package com.cloudinvoke.invokej.it;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cloudinvoke.invokej.ArgumentNullException;
import com.cloudinvoke.invokej.constructs.Transform;

/**
 * A DSL flavoured utility class to do magic with {@link Iterable}s and {@link Iterators}. Itq stands for
 * Iterable/Iterator Query. The name is kept short to avoid typing.
 * <p>
 * Note that none of the iterators produced by the class supports the remove operation.
 * 
 * See {@link IterableExt} for example use.
 * 
 * @see IterableExt
 * 
 * @author Hannes de Jager
 * @since 26 Aug 2008
 */
public class Itq {

    /**
     * An iterator that iterates over nothing and therefore has no elements.
     * Use {@link #emptyIterable()} method to get to this iterator
     */
    @SuppressWarnings("rawtypes")
	private static final Iterator EMPTY_ITERATOR = new Iterator() {
        public boolean hasNext() {
            return false;
        }
        
        public Object next() {
            throw new NoSuchElementException();
        }
        
        public void remove() {
        }
    }; 
    
    /**
     * An {@link Iterable} instance that iterates over nothing and therefore has no elements.
     * Use {@link #emptyIterable()} method to obtain this instance.
     */
    @SuppressWarnings("rawtypes")
    private static final Iterable EMPTY_ITERABLE = new Iterable() {
        public Iterator iterator() {
            return EMPTY_ITERATOR;
        }
    }; 
    
    /**
     * Returns an empty {@link Iterable} for the specified element type.
     * @param <T> The element type
     * @return A non-null instance of {@link Iterable} that iterates over nothing.
     */
    @SuppressWarnings("unchecked")
    public static <T> Iterable<T> emptyIterable() {
        return EMPTY_ITERABLE;
    }
    
    /**
     * Returns an empty iterator for the specified element type.
     * 
     * @param <T> The element type
     * @return A non-null instance of {@link Iterator} that iterates over nothing.
     */
    @SuppressWarnings("unchecked")
    public static <T> Iterator<T> emptyIterator() {
        return EMPTY_ITERATOR;
    }

    /**
     * Ensures a non-null iterable is used that will produce a non-null iterator.
     * 
     * @param <T> The iterator element type
     * @param iter The iterator to check and validate.
     * @return iter if not null, otherwise returns {@link #emptyIterable()}
     */
    public static <T> Iterable<T> valid(Iterable<T> iter) {
        if (iter != null) {
            Iterator<T> it = iter.iterator();
            if (it != null)
                return iter;
        }
        return emptyIterable();
    }

    /**
     * Count the number of entries returned by the iterable object. 
     * WARNING: This will perform a full iteration so the operation
     * is expensive.
     * 
     * @param iterable which entries will be counted.
     * @return the number of entries returned by the iterator.
     */
    public static <T> int count(Iterable<T> iterable) {
        if (iterable instanceof IterableExt<?>)
            return ((IterableExt<T>)iterable).size();

        // count the number of entries
        int size = 0;

        // loop over the iterable
        for (@SuppressWarnings("unused") T o : iterable) {
            size++;
        }

        return size;
    }

    /**
     * Check if the iterable contains the specified entry.
     * 
     * @param iterable which entries will be counted.
     * @param needle to look for among the iterable entries.
     * @return the number of entries returned by the iterator.
     */
    public static <T> boolean contains(Iterable<T> iterable, T needle) {
        for (T entry : iterable) {
            if (entry == needle) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Converts an iterator to a list by iterating through it and collecting the items.
     * @param <T> The type of item returned by each iteration.
     * @param iter The iterator.
     * @return The resultant list. Will never be null although it may be empty.
     */
    public static <T> List<T> toList(Iterable<T> itr) {
        if (itr instanceof IterableExt<?>)
            return ((IterableExt<T>)itr).toList();
        
        itr = valid(itr);
        List<T> result = new ArrayList<T>(); 
        for (T t :itr)
            result.add(t);
        return result;
    }

    /**
     * Converts an iterator to a {@link Stack} by iterating through it and collecting the items.
     * @param <T> The type of item returned by each iteration.
     * @param iter The iterator.
     * @return The resultant stack. Will never be null although it may be empty.
     */
    public static <T> Stack<T> toStack(Iterable<T> itr) {
        if (itr instanceof IterableExt<?>)
            return ((IterableExt<T>)itr).toStack();
        
        itr = valid(itr);
        Stack<T> result = new Stack<T>(); 
        for (T t :itr)
            result.push(t);
        return result;
    }

    /**
     * Converts an iterator to a map by iterating through it and collecting the items, using the 
     * specified key producer to get the map key for each item.

     * @param <K> The map key type
     * @param <T> The map value type
     * @param itr The object to iterate
     * @param keyProducer an object that creates the map key to use from an iteration variable.
     * @return The resultant map. Will never be null.
     */
    public static <K,T> Map<K, T> toMap(Iterable<T> itr, Transform<T, K> keyProducer) {
        itr = valid(itr);
        Map<K,T> map = new HashMap<K, T>();
        for (T t : itr)
            map.put(keyProducer.transform(t), t);
        return map;
    }
    
    /**
     * Finds the maximum element in a iterable object.
     * <p>
     * Performance Note: This implementation actually performs the loop which means it has exact 
     * linear O(n)complexity. 
     * 
     * @param <T> The element type of the iterator
     * @param itr the iterable object
     * @return the maximum element or null if the iterator is empty.
     */
    public static <T extends Comparable<T>> T max(Iterable<T> itr) {
        itr = valid(itr);
        Iterator<T> it = itr.iterator();
        T result = it.hasNext()?it.next():null;
        while (it.hasNext()) {
            T n = it.next();
            if (result.compareTo(n) < 0)
                result = n;
        }
        return result;
    }
    
    /**
     * Finds the minimum element in a iterable object.
     * <p>
     * Performance Note: This implementation actually performs the loop which means it has exact 
     * linear O(n)complexity. 
     * 
     * @param <T> The element type of the iterator
     * @param itr the iterable object
     * @return the minimum element or null if the iterator is empty.
     */
    public static <T extends Comparable<T>> T min(Iterable<T> itr) {
        itr = valid(itr);
        Iterator<T> it = itr.iterator();
        T result = it.hasNext()?it.next():null;
        while (it.hasNext()) {
            T n = it.next();
            if (n.compareTo(result) < 0)
                result = n;
        }
        return result;
    }    

    /**
     * Returns an iterable object that will, when iterated, yield a subset of the original {@link Iterable}'s
     * elements using the result of the supplied condition as filter criteria on every element. 
     * 
     * @param <T> The element type we are iterating over.
     * @param itr The original iterable containing the whole set of elements. 
     * @param condition The condition that should be true for elements that should be in the result set.
     * @return a new non-null iterable instance.
     */
    public static <T> IterableExt<T> where(final Iterable<T> itr, final Transform<T, Boolean> condition) {
        if (itr instanceof IterableExt<?>)
            return ((IterableExt<T>)itr).where(condition);
        
        if (condition == null)
            throw new ArgumentNullException("condition");
        return new IntfImpl<T>(new Iterable<T>() {
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    
                    private Iterator<T> it = valid(itr).iterator();
                    private boolean cachedCond;
                    private T cachedResult;
                    private boolean fetchedNext = false;

                    public boolean doHasNext() {
                        if (!fetchedNext) {
                            cachedResult = it.next();
                            Boolean b = condition.transform(cachedResult);
                            cachedCond = b != null && b.booleanValue();
                            fetchedNext = true;
                        }
                        return cachedCond;
                    }
                    
                    public boolean hasNext() {
                        boolean res = false;
                        if (fetchedNext)
                            return true;
                        
                        while (it.hasNext()) {
                            res = doHasNext();
                            if (res) {
                                fetchedNext = true;
                                break;
                            }
                            else
                                fetchedNext = false;
                        }
                        return res;
                    }
                    
                    public T next() {
                        if (!hasNext())
                            throw new IllegalStateException();
                        
                        T result = cachedResult;
                        fetchedNext = false;
                        return result;
                    }
                    
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        });
    }
    
    /**
     * Converts an source iterator to an new iterator that yields an adapted version of the source
     * elements on each iteration according to the adaptation specified. 
     * 
     * @param <I> The input iterator's element type
     * @param <O> The output iterator's element type
     * @param itr The input iterator
     * @param transformation The transformation to apply to the the source iterator's values to get to 
     *          the target iterator values.
     * @return The adaption iterator.
     */
    public static <I, O> IterableExt<O> select(final Iterable<I> itr, final Transform<I, O> transformation) {
        if (itr instanceof IterableExt<?>)
            return ((IterableExt<I>)itr).select(transformation);
        
        if (transformation == null)
            throw new ArgumentNullException("adaptation");

        return from(
                new Iterator<O>() {
                    private Iterator<I> it = null;
                    
                    private Iterator<I> getIt() {
                        return it == null?(it = valid(itr).iterator()):it;
                    }
                    
                    public boolean hasNext() {
                        return getIt().hasNext();
                    }
                    
                    public O next() {
                        return transformation.transform(getIt().next());
                    }
                    
                    public void remove() {
                        getIt().remove();
                    }
                });
    }
    
    /** 
     * Casts an {@link Iterable} over I to an {@link IterableExt} over O
     * 
     * @param <I>
     * @param <O>
     * @param itr The {@link Iterable} instance to be casted.
     * @param target The target type.
     * @return A new {@link IterableExt} instance with casted elements.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <I, O> IterableExt<O> cast(final Iterable<I> itr, Class<O> target) {
        return (IterableExt<O>)(IterableExt)from(itr);
    }
    /**
     * Joins 2 or more iterators into a new iterator that will, when iterated yield all the items
     * of the first iterator in sequence, then the items of the second iterator in sequence until all
     * iterators have been iterated.
     * 
     * @param itr1 The first iterator
     * @param itr2 The iterator to join onto the end of itr1
     * @param rest Some additional iterators to add in sequence.
     * @return A new {@link IterableExt} instance over {@link Object}'s representing the joint iterators.
     */
    public static <T> IterableExt<T> union(final Iterable<T> itr1, final Iterable<T> itr2, final Iterable<T>... rest) {
        final Queue<Iterable<T>> queue = new LinkedList<Iterable<T>>();
        queue.add(itr1);
        queue.add(itr2);
        for (Iterable<T> o : rest)
            queue.add(o);
        
        return new IntfImpl<T>(new Iterable<T>() {
            public Iterator<T> iterator() {
                return new Iterator<T>() {

                    Iterator<T> next = null;
                    
                    public boolean hasNext() {
                        Iterable<T> nextIta = null;
                        return next != null && next.hasNext()  
                            || !queue.isEmpty() 
                                && (
                                    (nextIta = queue.remove()) != null && 
                                    (next = nextIta.iterator()) != null && next.hasNext() || hasNext()
                                );
                    }
                    
                    /**
                     * {@inheritDoc}
                     * @see java.util.Iterator#next()
                     */
                    public T next() {
                        return next.next();
                    }
                    
                    /**
                     * {@inheritDoc}
                     * @see java.util.Iterator#remove()
                     */
                    public void remove() {
                        next.remove();
                    }
                };
            }
        });
    }
    
    /**
     * Flattens a 2 dimensional {@link Iterable} over an {@link Iterable} over type T by converting 
     * it to a 1 dimensional {@link IterableExt} over type T.
     *  
     * @param <T> The element type of the inner iterable.
     * @param iter The 2 dimensional {@link Iterable}
     * @return a 1 dimensional {@link IterableExt} over T
     */
    public static <T> IterableExt<T> flatten(final Iterable<Iterable<T>> iter) {
        return from(new Iterator<T>() {

            Iterator<T> next = null;
            Iterator<Iterable<T>> parent;
            
            public boolean hasNext() {                
                return next != null && next.hasNext()
                  || parent != null && parent.hasNext() && (nextIta() || hasNext())
                  || parent == null && (parent = iter.iterator()) != null && hasNext();
            }
            
            private boolean nextIta() {
                Iterable<T> nextIta = null;
                nextIta = parent.next();
                return nextIta != null && (next = nextIta.iterator()) != null && next.hasNext();
            }
            
            public T next() {
                return next.next();
            }
            
            public void remove() {
                next.remove();
            }
        });
    }
    
    /**
     * Returns an object that allows iteration over the sprecified iterable object in pages of the 
     * specified size.
     * 
     * @param count The page size i.e. the amount of items in each group.
     * @return An iterable over lists of the element type.
     */    
    public static <T> Iterable<List<T>> inGroupsOf(final Iterable<T> iter, final int count) {
        return from(new Iterator<List<T>> () {
            
            private Iterator<T> mainIter = Itq.from(iter).iterator();
            private List<T> buf;
            
            /**
             * {@inheritDoc}
             * @see java.util.Iterator#hasNext()
             */
            public boolean hasNext() {
                return mainIter.hasNext();
            }
            
            /**
             * {@inheritDoc}
             * @see java.util.Iterator#next()
             */
            public List<T> next() {
                int i = 0;
                buf = new ArrayList<T>(count);
                while (mainIter.hasNext() && i < count) {
                    i++;
                    buf.add(mainIter.next());
                }
                return buf;
            }
            
            
            /**
             * {@inheritDoc}
             * @see java.util.Iterator#remove()
             */
            public void remove() {
                throw new UnsupportedOperationException();
            }
        });
    }
    
    /**
     * Concatenates all items in the iterator returned by the {@link Itq} into a string using
     * the specified seperator.
     * 
     * @param <E> The element type of the 
     * @param itr The iterable object
     * @param seperator The seperator to use when joining into a string. May be null
     * @return A {@link StringBuilder} object that can be used to build the final string.
     */
    public static <E> StringBuilder concat(final Iterable<E> itr, final String seperator) {
        StringBuilder b = new StringBuilder();
        Iterator<E> it = from(itr).iterator();
        if (it.hasNext())
            b.append(it.next());
        while (it.hasNext()) {
            if (seperator != null)
                b.append(seperator);
            b.append(it.next());
        }
        return b;
    }

    
    /**
     * Converts a {@link Iterable} to a {@link IterableExt}
     * @param <T> The iterator's element type
     * @param itr The input iterator
     * @return The {@link IterableExt} instance
     */
    public static <T> IterableExt<T> from(final Iterable<T> itr) {
        if (itr instanceof IterableExt<?>)
            return (IterableExt<T>)itr;
        else
            return new IntfImpl<T>(itr);
    }
    
    /**
     * Normalises an {@link IterableExt}, converting null values to a valid empty {@link IterableExt}
     * @param <T> The iterator's element type
     * @param itr The input iterator
     * @return itr if not null, otherwise an empty {@link IterableExt} instance.
     */
    public static <T> IterableExt<T> from(final IterableExt<T> itr) {
        if (itr != null)
            return itr;
        else {
            Iterable<T> emptyIterable = emptyIterable();
            return Itq.from(emptyIterable);
        }
    }
    
    /**
     * Converts an array to a {@link IterableExt}
     * @param <T> The iterator's element type
     * @param itr The input iterator
     * @return The {@link IterableExt} instance
     */
    public static <T> IterableExt<T> from(T[] arr) {
    	if (arr == null) {
    		Iterable<T> emptyIterable = emptyIterable();
    		return Itq.from(emptyIterable);
    	}
        return Itq.from(Arrays.asList(arr));
    }
    
    /**
     * Creates an {@link Iterable} object from individual items.
     * 
     * @param <T> The type of the items
     * @param t The first item in the list
     * @param args The rest of the items.
     * @return An {@link IterableExt} implementation that will, when iterated, yield the specified 
     *         parameters in sequence.
     */
    public static <T> IterableExt<T> from(T t, T... args) {
        List<T> l = new ArrayList<T>(args.length + 1);
        l.add(t);
        for (T o : args)
            l.add(o);
        return Itq.from(l);
    }
    
    
    /**
     * Converts an {@link Iterator} to a {@link IterableExt}
     * @param <T> The iterator's element type
     * @param itr The input iterator
     * @return The {@link IterableExt} instance
     */
    public static <T> IterableExt<T> from(final Iterator<T> iterator) {
        return Itq.from(new Iterable<T>() {
            public Iterator<T> iterator() {
                return iterator;
            }
        });
    }
    
    /**
     * Converts a {@link Enumeration} over T to an {@link Iterable} over T.
     * @param <T> The enumeration/iterator element type.
     * @param enumeration The enumeration.
     * @return A new {@link IterableExt} instance that is never null.
     */
    public static <T> IterableExt<T> from(final Enumeration<T> enumeration) {
        if (enumeration == null)
            return new IntfImpl<T>(null);
            
        return Itq.from(
            new Iterator<T>() {
              public boolean hasNext() {
                  return enumeration.hasMoreElements();
              };  
              
              public T next() {
                  return enumeration.nextElement();
              };
              
              public void remove() {
                  throw new UnsupportedOperationException();
              };
            }
        );
    }
    
    /**
     * Same as {@link #from(Enumeration)} but with support for enumerations that are not 
     * parameterised for generics.
     * 
     * @param <T> The element type of the enumeration
     * @param enumeration The untyped enumeration instance. 
     * @param castClass The element type to cast
     * @return An iterable object over the Items in the enumeration which is casted to the specified type.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> IterableExt<T> from(final Enumeration enumeration, Class<T> castClass) {
        return (IterableExt<T>)(IterableExt)from(enumeration);
    }

    /**
     * Converts a DOM node list to an {@link IterableExt} that will yield the XML DOM nodes in 
     * sequence.
     * 
     * @param xmlNodeList The XML DOM Node list. May be null.
     * @return A new valid {@link IterableExt} object. Will never be null.
     */
    public static IterableExt<Node> from(final NodeList xmlNodeList) {
        Iterator<Node> iter;
        if (xmlNodeList == null)
            iter = emptyIterator();
        else {
            iter = new Iterator<Node>() {
                private int count = xmlNodeList.getLength();
                private int at = 0;
                
                public boolean hasNext() {
                    return at < count;
                }
                
                public Node next() {
                    return xmlNodeList.item(at++);
                }
                
                public void remove() {
                    throw new UnsupportedOperationException();
                }
                
            };
        }
        return from(iter);
    }
    
    /**
     * Creates an iterable object from a stack that will unstack the items from the stack. The stack
     * will be empty after this operation.
     * 
     * @param <T> The stack element.
     * @param stack The stack.
     * @return In iterator over the stack items from the top to bottom. Will not be null even if 
     *         supplied stack is null.
     */
    public static <T> IterableExt<T> from(final Stack<T> stack) {
        return from(new NoRemoveIterator<T>() {
            public boolean hasNext() {
                return stack != null && !stack.empty();
            }
            
            public T next() {
                return stack.pop();
            }
        });
    }
    
    /**
     * An implementation of {@link IterableExt} that is yielded as result by methods in this class.
     * 
     * @author Hannes de Jager
     * @since 02 Sep 2008
     */
    private static class IntfImpl<T> implements IterableExt<T> {
        
        /** The original iterator */
        private final Iterable<T> delegate;
        
        /**
         * Constructor. 
         */
        public IntfImpl(Iterable<T> delegate) {
            this.delegate = Itq.valid(delegate);
        }

        /**
         * {@inheritDoc}
         * @see com.attix5.util.invoke.itql.IterableI#size()
         */
        public int size() {
            int result = 0;
            Iterator<T> iterator = delegate.iterator();
            while (iterator.hasNext()) {
                iterator.next();
                result++;
            }
            return result;
        }

        /**
         * {@inheritDoc}
         * @see java.lang.Iterable#iterator()
         */
        public Iterator<T> iterator() {
            return delegate.iterator();
        }
        
        /**
         * {@inheritDoc}
         * @see com.attix5.util.invoke.itql.IterableI#select(com.attix5.util.invoke.constructs.Transform)
         */
        public <O> IterableExt<O> select(Transform<T, O> adaptation) {
            return Itq.select(delegate, adaptation);
        }
        
        /**
         * {@inheritDoc}
         * @see com.attix5.util.invoke.itql.IterableI#cast(java.lang.Class)
         */
        public <O> IterableExt<O> cast(Class<O> target) {
            return Itq.cast(delegate, target);
        }
        
        /**
         * {@inheritDoc}
         * @see com.attix5.util.invoke.itql.IterableI#where(com.attix5.util.invoke.constructs.Transform)
         */
        public IterableExt<T> where(Transform<T, Boolean> condition) {
            return Itq.where(delegate, condition);
        }

        /**
         * {@inheritDoc}
         */
        public IterableExt<T> whereNotNull() {
        	return Itq.where(delegate, new Transform<T, Boolean>() {
        		public Boolean transform(T input) {
        			return input != null;
        		};
			} );
        }
        
        /**
         * {@inheritDoc}
         * @see com.attix5.util.invoke.itql.IterableI#toList()
         */
        public List<T> toList() {
            return Itq.toList(delegate);
        }
        
        /**
         * {@inheritDoc}
         * @see com.attix5.util.invoke.itql.IterableI#toStack()
         */
        public Stack<T> toStack() {
            return Itq.toStack(delegate);
        }
        
        /**
         * {@inheritDoc}
         * @see com.attix5.util.invoke.itql.IterableI#toMap(com.attix5.util.invoke.constructs.Transform)
         */
        public <K> Map<K, T> toMap(Transform<T, K> keyProducer) {
            return Itq.toMap(delegate, keyProducer);
        }
        
        /**
         * {@inheritDoc}
         * @see com.attix5.util.invoke.itql.IterableI#union(Iterable, Iterable...)
         */
        public IterableExt<T> union(Iterable<T> itr2, Iterable<T>... rest) {
            return Itq.union(delegate, itr2, rest);
        }
        
        /**
         * {@inheritDoc}
         * @see com.attix5.util.invoke.itql.IterableI#concat(java.lang.String)
         */
        public StringBuilder concat(String seperator) {
            return Itq.concat(delegate, seperator);
        }
        
        /**
         * {@inheritDoc}
         * @see com.attix5.util.invoke.itql.IterableI#iterate()
         */
        public int iterate() {
            int result = 0;
            Iterator<T> iterator = delegate.iterator();
            while (iterator.hasNext()) {
                iterator.next();
                result++;
            }
            return result;
        }
        
        /**
         * {@inheritDoc}
         * @see com.attix5.util.invoke.itql.IterableI#inGroupsOf(int)
         */
        public Iterable<List<T>> inGroupsOf(int count) {
            return Itq.inGroupsOf(delegate, count);
        }
        
        
        /**
         * {@inheritDoc}
         * @see com.attix5.util.invoke.itql.IterableI#single()
         */
        public T single() throws NoSuchElementException {
            return iterator().next();
        }
        
    };

}
