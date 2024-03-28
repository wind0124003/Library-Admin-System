import javax.swing.*;
import java.awt.*;

public interface UI {
    public static final int INPUT_EMPTY = 0;
    public static final int DATABASE_EMPTY = 1;
    public static final int ALREADY_EXIST = 2;
    public static final int NOT_EXIST = 3;
    public static final int TEXT_FIELD_EMPTY = 4;

    public default void showErrorMessage(Component component, String message, String title) {
        JOptionPane.showMessageDialog(component,message,title,JOptionPane.ERROR_MESSAGE);
    }

    default String generateErrorMessage(int operation) {
        return generateErrorMessage(operation, "");
    }
    default String generateErrorMessage(int errorType, String string) {
        switch(errorType) {
            case INPUT_EMPTY:
                return "The input could not be empty";
            case DATABASE_EMPTY:
                return "The database is empty";
            case ALREADY_EXIST:
                return "The book of ISBN (" + string + ") already exists.";
            case NOT_EXIST:
                return "The book of ISBN (" + string + ") is not exist";
            case TEXT_FIELD_EMPTY:
                return "The " + string + " text field could not be empty";
            default:
                return "";
        }
    }

    void showErrorMessage(String message, String title);

    default void showErrorMessage(int type, String title) {
        showErrorMessage(generateErrorMessage(type), title);
    }

    default void showErrorMessage(int type, String message, String title) {
        showErrorMessage(generateErrorMessage(type, message),title);
    }

    public void renderTable(Database database);

    public void clearTextField();

    public default void updateTextArea(JTextArea textArea, String message){
        textArea.setText(message);
    }
}
