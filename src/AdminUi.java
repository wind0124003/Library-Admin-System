import javax.swing.table.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;
public class AdminUi extends JFrame implements UI {
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnSave;
    private JButton btnDelete;
    private JButton btnSearch;
    private JButton btnMore;
    private JButton btnLoadTestData;
    private JTextField txtIsbn;
    private JTextField txtTitle;
    private JButton btnDisplayAll;
    private JButton btnExit;
    private JButton btnDisplayAllByISBN;
    private JButton btnDisplayAllByTitle;
    private DefaultTableModel model;
    private Operation operation;

    private int clickTitle = 0;
    private int clickIsbn;
    private String oldIsbn;

    public AdminUi() {
        Date dateCreated = new Date();
        operation = new Operation(new BookList(), this);
        clickIsbn = 0;


        /* Declare GUI component */
        JTextArea txaDisplayAdmin = new JTextArea(
                "Student Name and ID: Chan Wai Chi (19060801d)\n" + dateCreated,
                6, 30);
        btnAdd = new JButton("Add");
        btnEdit = new JButton("Edit");
        btnSave = new JButton("Save");
        btnSave.setEnabled(false);
        btnDelete = new JButton("Delete");
        btnSearch = new JButton("Search");
        btnMore = new JButton("More>>");
        btnLoadTestData = new JButton("Load Test Data");
        btnDisplayAll = new JButton("Display All");
        btnDisplayAllByISBN = new JButton("Display All by ISBN");
        btnDisplayAllByTitle = new JButton("Display All by Title");
        btnExit = new JButton("Exit");
        txtIsbn = new JTextField(15);
        txtTitle = new JTextField(15);
        JLabel lblISBN = new JLabel("ISBN: ");
        JLabel lblTitle = new JLabel("Title: ");
        JPanel pnlTxtBookRecord = new JPanel();
        JPanel pnlAction = new JPanel();
        JPanel pnlButton01 = new JPanel();
        JPanel pnlButton02 = new JPanel();
        model = new DefaultTableModel();

        /* Add the GUI component into panels*/
        pnlTxtBookRecord.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        pnlTxtBookRecord.add(lblISBN);
        pnlTxtBookRecord.add(txtIsbn);
        pnlTxtBookRecord.add(lblTitle);
        pnlTxtBookRecord.add(txtTitle);
        pnlButton01.setLayout(new FlowLayout(FlowLayout.CENTER, 10,
                15));
        pnlButton01.add(btnAdd);
        pnlButton01.add(btnEdit);
        pnlButton01.add(btnSave);
        pnlButton01.add(btnDelete);
        pnlButton01.add(btnSearch);
        pnlButton01.add(btnMore);
        pnlButton02.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
        pnlButton02.add(btnLoadTestData);
        pnlButton02.add(btnDisplayAll);
        pnlButton02.add(btnDisplayAllByISBN);
        pnlButton02.add(btnDisplayAllByTitle);
        pnlButton02.add(btnExit);
        pnlAction.setLayout(new GridLayout(3, 1));
        pnlAction.add(pnlTxtBookRecord);
        pnlAction.add(pnlButton01);
        pnlAction.add(pnlButton02);

        /* Set table */
        JTable tblDisplayBook = new JTable(model);
        model.addColumn("ISBN");
        model.addColumn("Title");
        model.addColumn("Available");
        tblDisplayBook.setRowSelectionAllowed(true);
        tblDisplayBook.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        /* Add the GUI component into this UI page*/
        add(pnlAction, BorderLayout.SOUTH);
        add(new JScrollPane(tblDisplayBook), BorderLayout.CENTER);
        add(txaDisplayAdmin, BorderLayout.NORTH);

        btnAdd.addActionListener(e -> {
            if (txtIsbn.getText().isEmpty()){
                showErrorMessage(UI.TEXT_FIELD_EMPTY,"ISBN", "Message");
            } else if (txtTitle.getText().isEmpty()){
                showErrorMessage(UI.TEXT_FIELD_EMPTY, "title", "Message");
            } else {
                operation.add(txtIsbn.getText(), txtTitle.getText());
            }
        });

        btnExit.addActionListener(e -> {
            System.exit(1);
        });

        btnLoadTestData.addActionListener(e -> {
            operation.add("0131450913", "HTML How to Program");
            operation.add("0131857576", "C++ How to Program");
            operation.add("0132222205","Java How to Program");
        });

        btnDelete.addActionListener(e -> {
            if (txtIsbn.getText().isEmpty()) {
                showErrorMessage(UI.TEXT_FIELD_EMPTY, "ISBN", "Error");
            }
            else {
                operation.remove(txtIsbn.getText());
            }
        });

        btnEdit.addActionListener(e -> {
            if(txtIsbn.getText().isEmpty()) {
                showErrorMessage(UI.TEXT_FIELD_EMPTY, "ISBN", "Error");
            }
            else if (operation.isEditable(txtIsbn.getText())) {
                setBtnSaveEnabled(true);
                oldIsbn = txtIsbn.getText();
                Book book = operation.search(txtIsbn.getText());
                txtTitle.setText(book.getTitle());
            }
        });

        btnSave.addActionListener(e -> {
            if ((txtIsbn.getText().isEmpty()) || (txtTitle.getText().isEmpty())) {
                showErrorMessage(INPUT_EMPTY, "Error");
            } else {
                if (operation.edit(oldIsbn, txtIsbn.getText(), txtTitle.getText())) {
                    setBtnSaveEnabled(false);
                }
            }
        });

        btnSearch.addActionListener(e -> {
            if (txtIsbn.getText().isEmpty() && txtTitle.getText().isEmpty()) {
                showErrorMessage(UI.INPUT_EMPTY, "Message");
            }
            else {
                operation.search(txtIsbn.getText(), txtTitle.getText());
            }
        });

        btnDisplayAll.addActionListener(e -> {
            operation.displayAll();
        });

        btnDisplayAllByISBN.addActionListener(e -> {
            if (isOdd(clickIsbn)) {
                operation.displayISBN(BasicOperation.Order.ASCENDING);
            }
            else {
                operation.displayISBN(BasicOperation.Order.DESCENDING);
            }
            clickIsbn++;
        });

        btnDisplayAllByTitle.addActionListener(e -> {
            if (isOdd(clickTitle)) {
                operation.displayTitle(BasicOperation.Order.ASCENDING);
            }
            else {
                operation.displayTitle((BasicOperation.Order.DESCENDING));
            }
            clickTitle++;
        });

        btnMore.addActionListener(e -> {
            if (txtIsbn.getText().isEmpty()) {
                showErrorMessage(UI.TEXT_FIELD_EMPTY, "ISBN", "Error");
            }
            else {
                if (operation.isEditable(txtIsbn.getText())) {
                    BookControlUi bookControlUi = new BookControlUi(txtIsbn.getText() ,operation,this);
                    bookControlUi.setVisible(true);
                }
            }
        });
    }

    private boolean isOdd(int number) {
        return number % 2 == 0;
    }

    private void setBtnSaveEnabled(boolean ifEnabled) {
        btnSave.setEnabled(ifEnabled);

        // the state is opposite to btnSave
        btnAdd.setEnabled(!(ifEnabled));
        btnEdit.setEnabled(!(ifEnabled));
        btnSearch.setEnabled(!(ifEnabled));
        btnMore.setEnabled(!(ifEnabled));
        btnDelete.setEnabled(!(ifEnabled));
        btnDisplayAll.setEnabled(!(ifEnabled));
        btnDisplayAllByISBN.setEnabled(!(ifEnabled));
        btnDisplayAllByTitle.setEnabled(!(ifEnabled));
        btnLoadTestData.setEnabled(!(ifEnabled));
        btnExit.setEnabled(!(ifEnabled));
    }

    public void clearTextField() {
        txtIsbn.setText("");
        txtTitle.setText("");
    }

    public void execute(){
        this.setSize(800, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Library Admin System");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void renderTable(Database database) {
        model.setRowCount(0);
        for (int i = 0; i < database.countResult(); i++) {
            Book book = database.search(i);
            model.addRow(new Object[] {book.getIsbn(), book.getTitle(),book.isAvailable()});
        }
    }

    public void showErrorMessage(String message, String title) {
        showErrorMessage(this,message,title);
    }
}
