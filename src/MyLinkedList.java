// Chan Wai Chi, 19060801d
public class MyLinkedList<E> implements MyList<E> {
    protected Node<E> head, tail;
    protected int size = 0;

    /** Create an empty list */
    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /** Create a linked list from an array of object */
    public MyLinkedList(E[] objects) {
        for(E element : objects) {
            addLast(element);
        }
    }

    /** Add an element to the beginning of the list */
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
        newNode.next = head;
        head = newNode;
        size++;
        if(tail == null) {
            tail = head;
        }
    }

    /** Add an element to the end of the list */
    public void addLast(E e) {
        if(tail == null) {
            head = tail = new Node<>(e);
        }
        else {
            tail.next = new Node<>(e);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(int index, E e) {
        if (index == 0) {
            addFirst(e);
        }
        else if (index >= size) {
            addLast(e);
        }
        else {
            Node <E> current = head;
            // Find location
            for (int i = 0; i < index - 1; i++){
                current = current.next;
            }
            // Add the node
            Node<E> temp = new Node<>(e);
            temp.next = current.next;
            current.next = temp;
            size++;
        }
    }

    /** Return the head element in the list */
    public E getFirst() {
        return head.element;
    }

    /** Return the last element in the list. */
    public E getLast() {
        return tail.element;
    }

    /**
     * Remove the last node
     *  and return the object that is contained the removed node.
     *  */
    public E removeLast(){
        if(size == 0) {
            return null;
        }
        else if (size == 1) {
            Node<E> temp = head;
            head = tail = null;
            size = 0;
            return temp.element;
        }
        else {
            Node<E> current = head;
            for(int i = 0; i < size - 2; i++) {
                current = current.next;
            }
            Node<E> temp = tail;
            tail = current;
            tail.next = null;
            size--;
            return temp.element;
        }
    }

    /**
     * Remove the head node and
     * return the object that is contained in the removed node.
     */
    public E removeFirst() {
        if(size == 0) {
            return null;
        }
        else {
            Node<E> temp = head;
            head = head.next;
            size--;
            if (head == null){
                tail = null;
            }
            return temp.element;
        }
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        }
        else {
            Node<E> previous = head;
            for (int i = 0; i < index - 1; i++) {
                previous = previous.next;
            }
            Node<E> temp = previous.next;
            previous.next = temp.next;
            size--;
            return temp.element;
        }
    }

    @Override
    public E get(int index) {
        if (index == 0) {
            return getFirst();
        }
        else if (index >= size) {
            return getLast();
        }
        else {
            Node<E> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.element;
        }
    }

    /**
     * Return the index of the first matching element in this list.
     * Return -1 if no match.
     */
    @Override
    public int indexOf (Object e) {
        Node<E> current = head;
        int foundIndex = -1;
        int i = 0;
        while (current != null) {
            if (e.equals(current.element)) {
                foundIndex = i;
                return foundIndex;
            }
            current = current.next;
            i++;
        }
        return foundIndex;
    }

    /**
     *  Return the index of the last matching element in this list
     *  Return -1 if no match
     */
    @Override
    public int lastIndexOf(E e) {
        int lastIndex = -1;
        int i = 0;
        Node<E> current = head;

        if (tail.element == e) {
            lastIndex = size - 1;
        }
        else {
            while (current != null) {
                if (e.equals(current.element)) {
                    lastIndex = i;
                }
                current = current.next;
                i++;
            }
        }
        return lastIndex;
    }

    /** Replace the element at the specified position in this list with the specified element */
    @Override
    public E set(int index, E e) {
        Node<E> current = head;
        E replacedElement;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        replacedElement = current.element;
        current.element = e;
        return replacedElement;
    }

    /** Return true if this list contains the element */
    @Override
    public boolean contains(Object e) {
        boolean ifFind = false;
        Node<E> current = head;

        while (current != null) {
            if (e.equals(current.element)) {
                ifFind = true;
                break;
            }
            current = current.next;
        }
        return ifFind;
    }

    /** clear the list */
    @Override
    public void clear() {
        Node<E> current = head;
        while (current != null) {
            current = current.next;
            removeFirst();
        }
    }

    /** Override iterator() defined in Iterable */
    @Override
    public java.util.Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements java.util.Iterator<E> {
        private Node<E> current = head;

        private int index = -1;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            E e = current.element;
            index++;
            current = current.next;
            return e;
        }

        @Override
        // remove the last element returned by the iterator
        public void remove() {
            MyLinkedList.this.remove(index);
        }
    }

    /** Return the number of elements in this list */
    @Override
    public int size() {
        return size;
    }

    /** Override toString() to return elements in the list */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");

        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            result.append(current.element);
            current = current.next;
            if (current != null) {
                result.append(", "); // Separate two elements with a comma
            } else {
                result.append("]"); // Insert the closing ] in the string
            }
        }
        return result.toString();
    }


    protected static class Node<E> {
        E element;
        Node<E> next;

        public Node() {
            element = null;
            next = null;
        }

        public Node(E e) {
            element = e;
            next = null;
        }
    }
}
