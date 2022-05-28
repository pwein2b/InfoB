package visitor;

/**
 * Classes that implement the interface Visitor assure that the implement the
 * method {@link #visit(Object)}. The method {@link #visit(Object)} will be
 * called by a {@link Visitable} instance for every Object it visits during one
 * call of the method {@link Visitable#accept(Visitor)}.
 *
 * In addition to the visit() method everyone expects, there are also methods that allow
 * for some customisation of the visit:
 *  - shouldRecurse(): the visitable asks the visitor if an element should be visited recursively, if supported
 *  - preResursion(), postRecursion(): in that case, some options can be performed.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 * @param <E>
 *           type of the elements which can be visited by this Visitor
 * 
 * @see Visitable
 */
public interface Visitor<E> {

   /**
    * Called by the method {@link Visitable#accept(Visitor)} for every element it
    * visits. The visiting can be stopped by returning <code>false</code>.
    * 
    * @param o
    *           the element that has just been visited by
    *           {@link Visitable#accept(Visitor)}
    * @return <code>true</code> if the visit should be continued until every
    *         element in a {@link Visitable} has been visited once, else
    *         <code>false</code>
    */
   public boolean visit(E o);

   /**
    * Called by the method Visitable.accept() to determine if an element, after it has been visited,
    * should be visited recursively.
    * If the element does not permit recursive visiting, this method will not be called; if visit() has
    * already returned false, it will likewise not be called.
    *
    * The default implementation always denies the question.
    *
    * @param o the object that can be recursively visited
    * @return true if the element given should be visited by the very same Visitor recursively.
    */
   public default boolean shouldRecurse(E o) {
     return false;
   }

   /**
    * Perform some operation prior to recursively visiting an element through the very same Visitor instance.
    * The default implementation does nothing.
    * This method will not be called in the first place if shouldRecurse() returned false.
    */
   public default void preRecursion(E o) {
   }

   /**
    * Perform some operation after recursively visiting an element through the very same Visitor instance.
    * The default implementation does nothing.
    * This method will not be called in the first place if shouldRecurse() returned false.
    */
   public default void postRecursion(E o) {
   }
}
