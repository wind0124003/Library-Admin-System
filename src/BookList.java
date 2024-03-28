import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class BookList implements Database {
    private MyLinkedList<Book> bookList;

    public BookList() {
        bookList = new MyLinkedList<>();
    }


    @Override
    public void add(String isbn, String title) {
        bookList.addLast(new Book(isbn, title));
    }

    @Override
    public Book search(String isbn, String title) {
        Book book;
        for (int i = 0; i < bookList.size(); i++) {
            book = bookList.get(i);
            if (title.equals(book.getTitle()) && isbn.equals(book.getIsbn())){
                return book;
            }
        }
        return null;
    }

    @Override
    public Database searchPattern(SearchItem item, String pat) {
        Database tempList = new BookList();

        Pattern pattern = Pattern.compile(pat);
        Matcher matcher;

        for (Book book : bookList) {
            switch (item) {
                case ISBN:
                    matcher = pattern.matcher(book.getIsbn());
                    break;
                case TITLE:
                    matcher = pattern.matcher(book.getTitle());
                    break;
                default:
                    matcher = pattern.matcher("");
            }
            if (matcher.find()) {
                tempList.add(book.getIsbn(), book.getTitle());
            }
        }
        return tempList;
    }

    public Book search(String isbn) {
        for (Book book : bookList) {
            if (isbn.equals(book.getIsbn())) {
                return book;
            }
        }
        return null;
    }

    @Override
    public boolean isContain(String isbn) {
        Book book;
        for (int i = 0; i < bookList.size(); i++) {
            book = bookList.get(i);
            if (isbn.equals(book.getIsbn())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Book search(int index) {
        return bookList.get(index);
    }

    @Override
    public int countResult() {
        return bookList.size();
    }

    @Override
    public void printList() {
        for (int i = 0; i < bookList.size();i++){
            Book book = bookList.get(i);
            System.out.println("[ISBN: "+book.getIsbn()+" , Title: " + book.getTitle()
                    + ", " + book.isAvailable()+"]");
        }
    }

    @Override
    public void remove(String isbn) {
        Book book = null;
        try {
            book = search(isbn);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        bookList.remove(book);
    }

    @Override
    public void update(String isbn, String newIsbn, String title) {
        Book book = search(isbn);
        int index = bookList.indexOf(book);
        book.setTitle(title);
        book.setIsbn(newIsbn);
        bookList.set(index, book);
    }

    public void setBookAvailable(String isbn, boolean ifAvailable) {
        Book book = search(isbn);
        int index = bookList.indexOf(book);
        book.setAvailable(ifAvailable);
        bookList.set(index, book);
    }

    @Override
    public Database sortIsbn(BasicOperation.Order order) {
        return sort(Book::getIsbn, order);
    }

    private BookList sort(Function<Book,String> bookFunction, BasicOperation.Order order) {
        BookList result = new BookList();
        Book[] bookArray = new Book[bookList.size];
        bookList.toArray(bookArray);

        if (order == BasicOperation.Order.ASCENDING) {
            Arrays.sort(bookArray, Comparator.comparing(bookFunction));
        } else {
            Arrays.sort(bookArray, Comparator.comparing(bookFunction).reversed());
        }

        for (Book book : bookArray) {
            result.add(book.getIsbn(), book.getTitle());
        }
        return result;
    }

    public Database sortTitle(BasicOperation.Order order) {
        return sort(Book::getTitle, order);
    }

    public void addQueue(String isbn, String name) {
        Book book = search(isbn);
        int index = bookList.indexOf(book);
        book.getReservedQueue().enqueue(name);
        bookList.set(index, book);
    }

    public MyQueue<String> getQueue(String isbn) {
        Book book = search(isbn);
        return book.getReservedQueue();
    }

    public boolean checkIfWait(String isbn) {
        return getQueue(isbn).size > 0;
    }

    public String dequeue(String isbn) {
        Book book = search(isbn);
        int index = bookList.indexOf(book);
        String temp = book.getReservedQueue().dequeue();
        bookList.set(index, book);
        return temp;
    }

}
