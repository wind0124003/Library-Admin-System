public class Operation implements Manageable, BasicOperation{
    private Database database;
    UI ui;

    public Operation(Database database, UI ui){
        this.database = database;
        this.ui = ui;
    }

    public void add(String isbn, String title) {
        if (database.isContain(isbn)) {
            ui.showErrorMessage(UI.ALREADY_EXIST, isbn, "Message");
        }
        else {
            database.add(isbn, title);
            ui.renderTable(database);
            ui.clearTextField();
        }
    }

    @Override
    public void remove(String isbn) {
        if (database.countResult() == 0) {
            ui.showErrorMessage(UI.DATABASE_EMPTY,"Error");
        }
        else if (database.isContain(isbn)) {
            database.remove(isbn);
        }
        else {
            ui.showErrorMessage(UI.NOT_EXIST,isbn,"Error");
        }
        ui.renderTable(database);
        ui.clearTextField();
    }

    @Override
    public boolean edit(String isbn, String newIsbn,String title){
        if(!(isbn.equals(newIsbn)) && database.isContain(newIsbn)) {
            ui.showErrorMessage(UI.ALREADY_EXIST,newIsbn,"Error");
            return false;
        }
        else {
            database.update(isbn, newIsbn, title);
            ui.renderTable(database);
            ui.clearTextField();
            return true;
        }
    }

    @Override
    public boolean isEditable(String isbn) {
        if (database.countResult() == 0) {
            ui.showErrorMessage(UI.DATABASE_EMPTY, "Error");
            return false;
        }
        else if (!database.isContain(isbn)) {
            ui.showErrorMessage(UI.NOT_EXIST,isbn, "Error");
            return false;
        }
        return true;
    }

    @Override
    public Book search(String isbn) {
        return database.search(isbn);
    }

    @Override
    public void search(String isbn, String title) {
        Database result;
        if (title.isEmpty()) {
            result = database.searchPattern(Database.SearchItem.ISBN, isbn);
        } else {
            result = database.searchPattern(Database.SearchItem.TITLE, title);
        }
        ui.renderTable(result);
    }

    public void displayAll() {
        ui.renderTable(database);
    }

    public void displayISBN(Order order) {
        Database result = database.sortIsbn(order);
        ui.renderTable(result);
    }

    public void displayTitle(Order order) {
        Database result = database.sortTitle(order);
        ui.renderTable(result);
    }

    @Override
    public String getBookInfo(String isbn) {
        Book book = search(isbn);
        return book.getBookInfo();
    }

    @Override
    public void borrow(String isbn) {
        database.setBookAvailable(isbn, false);
    }

    @Override
    public boolean reserve(String isbn, String name) {
        if (name.isEmpty()) {
            ui.showErrorMessage(UI.INPUT_EMPTY, "Error");
            return false;
        }
        else {
            database.addQueue(isbn, name);
            return true;
        }
    }

    @Override
    public String showWaitingQueue(String isbn) {
        MyLinkedList<String> list = database.getQueue(isbn).getList();

        if (list.isEmpty()){
            return "No people waiting for this book.";
        }

        StringBuilder result = new StringBuilder("The waiting queue:\n");
        for (int i = 0; i < list.size; i++) {
            result.append(list.get(i));
            result.append("\n");
        }
        return result.toString();
    }

    @Override
    public String returnBook(String isbn) {
        String name = "";
        // check if others waits for this book
        if (database.checkIfWait(isbn))
        {
            name = database.dequeue(isbn);
        } else {
            database.setBookAvailable(isbn,true);
        }
        return name;
    }

    public void exit() {
        ui.renderTable(database);
    }
}
