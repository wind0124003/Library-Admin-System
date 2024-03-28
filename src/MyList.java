import java.util.Collection;
public interface MyList<E> extends Collection<E> {

    /** Insert a new element at the specified index in this list. */
    void add (int index, E e);

    /** Return the element from this list at the specified index */
    E get(int index);

    /** Return the index of first matching element in this list */
    int indexOf(Object e);

    /** Return the index of the last matching element in this list. */
    int lastIndexOf(E e);

    /** Remove the element at the specified index and returns the removed element. */
    E remove(int index);

    /** Set the element at the specified index and returns the element being replaced. */
    E set(int index, E e);

    /** Add a new element at the end of this list. */
    @Override
    public default boolean add(E e) {
        add(size(), e);
        return true;
    }

    /** Return true if this list contains no elements. */
    @Override
    public default boolean isEmpty() {
        return size() == 0;
    }

    /**
     *  Remove the first occurrence of the element e from this list. Shift any subsequent elements to the left.
     *  Return true if the element is removed.
     */
    @Override
    public default boolean remove(Object e) {
        if (indexOf(e) >= 0) {
            remove(indexOf(e));
            return true;
        }
        else
            return false;
    }

    @Override
    public default boolean containsAll(Collection<?> c) {
        for (Object e: c)
            if (!this.contains(e)) {
                return false;
            }
        return true;
    }

    /**
     *  Add the elements in otherList to this list.
     *  Return true if this list changed as a result of the call
     */
    @Override
    public default boolean addAll(Collection<? extends E> c) {
        for (E e: c) {
            this.add(e);
        }
        return c.size() > 0;
    }

    /**
     *  Remove all the elements in otherList from this list.
     *  Return true if this list changed as a result of the call.
     */
    @Override
    public default boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for (Object e: c) {
            if (remove(e))
                changed = true;
        }

        return changed;
    }

    /**
     * Retain the elements in this list that are also in otherList.
     * Return true if this list changed as a result of the call.
     */
    @Override
    public default boolean retainAll(Collection<?> c) {
        boolean changed = false;
        for (int i = 0; i < this.size(); ) {
            if (!c.contains(this.get(i))) {
                this.remove(get(i));
                changed = true;
            }
            else
                i++;
        }

        return changed;
    }

    @Override
    public default Object[] toArray() {
        Object[] result = new Object[size()];
        for (int i = 0; i < size(); i++) {
            result[i] = this.get(i);
        }
        return result;
    }

    @Override
    public default <T> T[] toArray(T[] a) {
        for (int i = 0; i < size(); i++) {
            a[i] = (T)this.get(i);
        }
        return a;
    }
}
