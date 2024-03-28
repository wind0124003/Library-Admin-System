public interface Manageable {
    public void add(String isbn, String title);
    public void remove(String isbn);
    public boolean edit(String isbn, String newIsbn,String title);
    public boolean isEditable(String isbn);
}
