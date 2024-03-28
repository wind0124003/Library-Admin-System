public class Book {
    private String title; // title of book
    private String isbn; // store the ISBN of book
    private boolean available; // the status of book
    private MyQueue<String> reservedQueue;

    public Book() {
        available = true;
        reservedQueue = new MyQueue<>();
    }

    public Book(String isbn, String title) {
        this.title = title;
        this.isbn = isbn;
        this.available = true;
        reservedQueue = new MyQueue<>();
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return available;
    }

    public MyQueue<String> getReservedQueue() {
        return reservedQueue;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String  getBookInfo(){
        return "ISBN: " + getIsbn() + "\n Title: " + getTitle() +
                "\n Available: " + isAvailable();
    }

}
