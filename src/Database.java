public interface Database {
    enum SearchItem{
        ISBN, TITLE, BOTH
    }

    public void add(String isbn, String title);
    public void remove(String isbn);

    public void update(String isbn, String newIsbn, String title);
    public void printList();

    public Book search(String isbn, String title);
    public Book search(String isbn);
    public Database searchPattern(SearchItem searchItem, String pattern);
    public Database sortIsbn(BasicOperation.Order order);
    public Database sortTitle(BasicOperation.Order order);
    public Book search(int index);
    public boolean isContain(String isbn);
    public int countResult();

    public void setBookAvailable(String isbn, boolean ifAvailable);
    public void addQueue(String isbn, String name);
    public MyQueue<String> getQueue(String isbn);

    public boolean checkIfWait(String isbn);
    public String dequeue(String isbn);
}
