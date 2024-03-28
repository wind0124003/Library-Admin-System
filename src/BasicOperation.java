public interface BasicOperation {
    enum Order {
        ASCENDING, DESCENDING
    }
    public static final int RETURN = 1;
    public static final int RESERVE = 2;

    public Book search(String isbn);
    public void search(String isbn, String title);
    public String getBookInfo(String isbn);

    public void borrow(String isbn);

    public boolean reserve(String isbn, String name);

    public String showWaitingQueue(String isbn);

    public String returnBook(String isbn);

    public void exit();


}
