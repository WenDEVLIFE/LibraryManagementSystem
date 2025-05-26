package database_function;

import javax.swing.*;
import java.util.Map;

public class BookDB {

    private static volatile BookDB instance;

    public static BookDB getInstance() {
        if (instance == null) {
            synchronized (BookDB.class) {
                if (instance == null) {
                    instance = new BookDB();
                }
            }
        }
        return instance;
    }

    public void addBook(Map<String, String> bookData) {
        String sql = "INSERT INTO BOOKS (BOOK_ID,TITLE, AUTHOR, GENRE, COPYRIGHT, PUBLISHER, COPIES) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            int copies = Integer.parseInt(bookData.get("copies"));
            preparedStatement.setString(1, bookData.get("book_id"));
            preparedStatement.setString(2, bookData.get("title"));
            preparedStatement.setString(3, bookData.get("author"));
            preparedStatement.setString(4, bookData.get("genre"));
            preparedStatement.setString(5, bookData.get("copyright"));
            preparedStatement.setString(6, bookData.get("publisher"));
            preparedStatement.setInt(7, copies);

             int result = preparedStatement.executeUpdate();

             if (result > 0) {
                 System.out.println("Book added successfully.");
                 JOptionPane.showMessageDialog(null, "Book added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
             } else {
                 System.out.println("Failed to add book.");
                    JOptionPane.showMessageDialog(null, "Failed to add book.", "Error", JOptionPane.ERROR_MESSAGE);
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
