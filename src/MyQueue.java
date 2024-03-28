public class MyQueue<E> {
    MyLinkedList<E> list;
    int size;

    public MyQueue () {
        list = new MyLinkedList<>();
        size = 0;
    }

    /** Add an element to this queue */
    public void enqueue(E e) {
        size++;
        list.addLast(e);
    }

    /** Remove an element from this queue */
    public E dequeue() {
        size--;
        return list.removeFirst();
    }

    /** Return the number of elements from this queue */
    public int getSize() {
        return size;
    }

    /** Return the list */
    public MyLinkedList<E> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "Queue: " + list.toString();
    }

}
