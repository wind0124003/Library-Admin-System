import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class BookControlUi extends JDialog{
    private final JTextArea txaBookInfo;
    private final JButton btnBorrow;
    private final JButton btnReturn;
    private final JButton btnReserve;
    private final JButton btnWaitQueue;
    private final JTextArea txaSystemMessage;

    BasicOperation operation;
    String isbn;
    BookControlUi(String isbn, BasicOperation basic, JFrame frame) {
        super(frame,basic.search(isbn).getTitle(), true);
        this.operation = basic;


        /* Declaring GUI component */
        txaBookInfo = new JTextArea(operation.getBookInfo(isbn), 8, 30);
        btnBorrow = new JButton("Borrow");
        btnReturn = new JButton("Return");
        btnReserve = new JButton("Reserve");
        btnWaitQueue = new JButton("Waiting Queue");
        txaSystemMessage = new JTextArea(3, 30);
        JPanel pnlButton = new JPanel();
        JPanel pnlAll = new JPanel();

        /* Add the GUI Component into frame */
        pnlButton.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 30));
        pnlButton.add(btnBorrow);
        pnlButton.add(btnReturn);
        pnlButton.add(btnReserve);
        pnlButton.add(btnWaitQueue);

        JPanel pnlTop = new JPanel();
        pnlTop.add(txaBookInfo);

        pnlAll.setLayout(new BorderLayout());
        pnlAll.add(txaBookInfo, BorderLayout.NORTH);
        pnlAll.add(txaSystemMessage, BorderLayout.SOUTH);
        pnlAll.add(pnlButton, BorderLayout.CENTER);

        Container container = getContentPane();
        container.add(pnlAll);
        this.setSize(600, 500);

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                if (operation.search(isbn).isAvailable()) {
                    enableBtnBorrow(true);
                }
                else {
                    enableBtnBorrow(false);
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
               operation.exit();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        btnBorrow.addActionListener(e -> {
            operation.borrow(isbn);
            updateTextArea(txaBookInfo, operation.getBookInfo(isbn));
            updateTextArea(txaSystemMessage, "The book is borrowed.");
            enableBtnBorrow(operation.search(isbn).isAvailable());
        });

        btnReserve.addActionListener(e -> {
            String name = "";
            name = JOptionPane.showInputDialog(null, "What is your name?", "title",
                    JOptionPane.QUESTION_MESSAGE);
            if (operation.reserve(isbn, name)) {
                updateTextArea(txaSystemMessage, "The book is reserved by " + name + ".");
            }
        });

        btnWaitQueue.addActionListener(e -> {
            String message = operation.showWaitingQueue(isbn);
            updateTextArea(txaSystemMessage, message);
        });

        btnReturn.addActionListener(e -> {
            String name = operation.returnBook(isbn);
            String message = "The book is returned.\n";
            if (name.isEmpty()) {
                enableBtnBorrow(true);
            }
            else {
                message = message + "The book is now borrowed by " + name + ".";
            }
            updateTextArea(txaSystemMessage, message);
            updateTextArea(txaBookInfo, operation.getBookInfo(isbn));
        });
    }

    private void enableBtnBorrow(boolean ifEnabled) {
        btnBorrow.setEnabled(ifEnabled);

        // the enabled state is opposite to borrow button state
        btnReserve.setEnabled(!ifEnabled);
        btnReturn.setEnabled(!ifEnabled);
        btnWaitQueue.setEnabled(!ifEnabled);
    }

    private void updateTextArea(JTextArea textArea, String message) {
        textArea.setText(message);
    }
}
