package database_function;

import model.BookModel;

import javax.swing.*;
import java.util.List;
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

    public boolean isBookIdExists(String bookId) {
        String sql = "SELECT COUNT(*) FROM BOOKS WHERE CAST(BOOK_ID AS VARCHAR(128)) = ?";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, bookId);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public boolean isTitleExists(String title) {
        String sql = "SELECT COUNT(*) FROM BOOKS WHERE CAST(TITLE AS VARCHAR(128)) = ?";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, title);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

    public List<BookModel> getBooks() {
        String sql = "SELECT * FROM BOOKS";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql);
             var resultSet = preparedStatement.executeQuery()) {

            List<BookModel> books = new java.util.ArrayList<>();
            while (resultSet.next()) {
                String id = resultSet.getString("BOOK_ID");
                String title = resultSet.getString("TITLE");
                String author = resultSet.getString("AUTHOR");
                String genre = resultSet.getString("GENRE");
                String copyright = resultSet.getString("COPYRIGHT");
                String publisher = resultSet.getString("PUBLISHER");
                int copies = resultSet.getInt("COPIES");

                books.add(new BookModel(id, title, author, genre, copyright, publisher, copies));
            }
            return books;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateBook(Map<String, String> updatedBook) {
        String sql = "UPDATE BOOKS SET TITLE = ?, AUTHOR = ?, GENRE = ?, COPYRIGHT = ?, PUBLISHER = ?, COPIES = ? WHERE CAST(BOOK_ID AS VARCHAR(128)) = ?";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, updatedBook.get("title"));
            preparedStatement.setString(2, updatedBook.get("author"));
            preparedStatement.setString(3, updatedBook.get("genre"));
            preparedStatement.setString(4, updatedBook.get("copyright"));
            preparedStatement.setString(5, updatedBook.get("publisher"));
            preparedStatement.setInt(6, Integer.parseInt(updatedBook.get("copies")));
            preparedStatement.setString(7, updatedBook.get("book_id"));

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Book updated successfully.");
                JOptionPane.showMessageDialog(null, "Book updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Failed to update book.");
                JOptionPane.showMessageDialog(null, "Failed to update book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(String bookID) {
        String sql = "DELETE FROM BOOKS WHERE WHERE CAST(BOOK_ID AS VARCHAR(128)) = ?";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, bookID);
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Book deleted successfully.");
                JOptionPane.showMessageDialog(null, "Book deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Failed to delete book.");
                JOptionPane.showMessageDialog(null, "Failed to delete book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
