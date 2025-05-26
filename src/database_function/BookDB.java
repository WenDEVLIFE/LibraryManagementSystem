package database_function;

import model.BookModel;
import model.BookModel2;
import model.BorrowBookModel;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import model.ReturnBookModel;

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

    public void borrowBook(Map<String, String> bookData) {
        String sql = "INSERT INTO BOOKBORROW (BOOK_ID, USER_ID, DATE_BORROWED, DATE_RETURNED, BORROW_QUANTITY, BORROW_ID) VALUES (?, ?, ?, ?, ?, ?)";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            String countSql = "SELECT COUNT(*) FROM BOOKBORROW";
            int borrowId = 0;
            try (var countStmt = connection.prepareStatement(countSql);
                 var countResultSet = countStmt.executeQuery()) {
                if (countResultSet.next()) {
                    borrowId = countResultSet.getInt(1) + 1; // Increment count for new ID
                }
            }

            preparedStatement.setString(1, bookData.get("book_id"));
            preparedStatement.setString(2, bookData.get("user_id"));
            preparedStatement.setString(3, bookData.get("borrow_date"));
            preparedStatement.setString(4, bookData.get("return_date"));
            preparedStatement.setInt(5, Integer.parseInt(bookData.get("quantity")));
            preparedStatement.setInt(6, borrowId);


            int result = preparedStatement.executeUpdate();


            if (result > 0) {
                System.out.println("Book borrowed successfully.");
                JOptionPane.showMessageDialog(null, "Book borrowed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                UpdateQuantity(bookData.get("book_id"), Integer.parseInt(bookData.get("quantity")));
            } else {
                System.out.println("Failed to borrow book.");
                JOptionPane.showMessageDialog(null, "Failed to borrow book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UpdateQuantity(String bookId, int quantity) {
        String sql = "UPDATE BOOKS SET COPIES = COPIES - ? WHERE CAST(BOOK_ID AS VARCHAR(128)) = ?";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, quantity);
            preparedStatement.setString(2, bookId);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Book quantity updated successfully.");
                JOptionPane.showMessageDialog(null, "Book quantity updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Failed to update book quantity.");
                JOptionPane.showMessageDialog(null, "Failed to update book quantity.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AddQuantity(String bookId, int quantity) {
        String sql = "UPDATE BOOKS SET COPIES = COPIES + ? WHERE CAST(BOOK_ID AS VARCHAR(128)) = ?";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, quantity);
            preparedStatement.setString(2, bookId);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Book quantity updated successfully.");
                JOptionPane.showMessageDialog(null, "Book quantity updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Failed to update book quantity.");
                JOptionPane.showMessageDialog(null, "Failed to update book quantity.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BorrowBookModel> getBorrowedBooks() {
        String sql = "SELECT * FROM BOOKBORROW";
        String getBookDetailsSql = "SELECT TITLE, AUTHOR, GENRE FROM BOOKS WHERE CAST(BOOK_ID AS VARCHAR(128)) = ?";
        List<BorrowBookModel> borrowedBooks = new java.util.ArrayList<>();
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql);
             var resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // Generate BORROW_ID by counting rows
                String countSql = "SELECT COUNT(*) FROM BOOKBORROW";
                int borrowId = 0;
                try (var countStmt = connection.prepareStatement(countSql);
                     var countResultSet = countStmt.executeQuery()) {
                    if (countResultSet.next()) {
                        borrowId = countResultSet.getInt(1) + 1; // Increment count for new ID
                    }
                }

                String id = String.valueOf(borrowId); // Use the count as BORROW_ID
                String userId = resultSet.getString("USER_ID");
                String bookId = resultSet.getString("BOOK_ID");
                String dateBorrowed = resultSet.getString("DATE_BORROWED");
                String dateReturned = resultSet.getString("DATE_RETURNED");
                int copiesBorrowed = resultSet.getInt("BORROW_QUANTITY");

                try (var bookDetailsStmt = connection.prepareStatement(getBookDetailsSql)) {
                    bookDetailsStmt.setString(1, bookId);
                    var bookDetailsResultSet = bookDetailsStmt.executeQuery();
                    if (bookDetailsResultSet.next()) {
                        String bookTitle = bookDetailsResultSet.getString("TITLE");
                        String bookAuthor = bookDetailsResultSet.getString("AUTHOR");
                        String category = bookDetailsResultSet.getString("GENRE");

                        borrowedBooks.add(new BorrowBookModel(id, userId, bookId, bookTitle, bookAuthor, category, dateBorrowed, dateReturned, String.valueOf(copiesBorrowed)));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return borrowedBooks;
    }

    public List<BorrowBookModel> borrowBookByUser(String userId) {
        String sql = "SELECT * FROM BOOKBORROW WHERE CAST(USER_ID AS VARCHAR(128)) = ?";
        String getBookDetailsSql = "SELECT TITLE, AUTHOR, GENRE FROM BOOKS WHERE CAST(BOOK_ID AS VARCHAR(128)) = ?";
        List<BorrowBookModel> borrowedBooks = new java.util.ArrayList<>();
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userId);
            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("BORROW_ID");
                String bookId = resultSet.getString("BOOK_ID");
                String dateBorrowed = resultSet.getString("DATE_BORROWED");
                String dateReturned = resultSet.getString("DATE_RETURNED");
                int copiesBorrowed = resultSet.getInt("BORROW_QUANTITY");

                try (var bookDetailsStmt = connection.prepareStatement(getBookDetailsSql)) {
                    bookDetailsStmt.setString(1, bookId);
                    var bookDetailsResultSet = bookDetailsStmt.executeQuery();
                    if (bookDetailsResultSet.next()) {
                        String bookTitle = bookDetailsResultSet.getString("TITLE");
                        String bookAuthor = bookDetailsResultSet.getString("AUTHOR");
                        String category = bookDetailsResultSet.getString("GENRE");

                        borrowedBooks.add(new BorrowBookModel(id, userId, bookId, bookTitle, bookAuthor, category, dateBorrowed, dateReturned, String.valueOf(copiesBorrowed)));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return borrowedBooks;
    }

    public List<BookModel2> getBorrowedBooks2(String userId) {
        String sql = "SELECT * FROM BOOKBORROW WHERE CAST(USER_ID AS VARCHAR(128)) = ?";
        String getBookDetailsSql = "SELECT TITLE, AUTHOR, GENRE FROM BOOKS WHERE CAST(BOOK_ID AS VARCHAR(128)) = ?";
        List<BookModel2> borrowedBooks = new java.util.ArrayList<>();

        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userId);
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("BORROW_ID");
                    String bookId = resultSet.getString("BOOK_ID");
                    String dateBorrowed = resultSet.getString("DATE_BORROWED");
                    String dateReturned = resultSet.getString("DATE_RETURNED");
                    int borrowedCopies = resultSet.getInt("BORROW_QUANTITY");

                    // Calculate days remaining
                    String daysRemaining = "N/A";
                    if (dateReturned != null && !dateReturned.isEmpty()) {
                        java.time.LocalDate today = java.time.LocalDate.now();
                        java.time.LocalDate returnDate = java.time.LocalDate.parse(dateReturned);
                        long days = java.time.temporal.ChronoUnit.DAYS.between(today, returnDate);
                        daysRemaining = days >= 0 ? String.valueOf(days) : "Overdue";
                    }

                    // Fetch book details
                    try (var bookDetailsStmt = connection.prepareStatement(getBookDetailsSql)) {
                        bookDetailsStmt.setString(1, bookId);
                        try (var bookDetailsResultSet = bookDetailsStmt.executeQuery()) {
                            if (bookDetailsResultSet.next()) {
                                String title = bookDetailsResultSet.getString("TITLE");
                                String author = bookDetailsResultSet.getString("AUTHOR");
                                String genre = bookDetailsResultSet.getString("GENRE");

                                borrowedBooks.add(new BookModel2(id, bookId, title, author, genre, dateBorrowed, daysRemaining, String.valueOf(borrowedCopies)));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return borrowedBooks;
    }

    public void returnBook(Map<String, String> bookData) {
        String countSql = "SELECT COUNT(*) FROM RETURNBOOKK";
        String insertSql = "INSERT INTO RETURNBOOKK (RETURN_ID, BOOK_ID, USER_ID, DATE_BORROWED, DATE_RETURNED, BORROW_QUANTITY) VALUES (?, ?, ?, ?, ?, ?)";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var countStmt = connection.prepareStatement(countSql);
             var resultSet = countStmt.executeQuery()) {

            int returnId = 1; // Default ID if table is empty
            if (resultSet.next()) {
                returnId = resultSet.getInt(1) + 1; // Increment count for new ID
            }

            try (var preparedStatement = connection.prepareStatement(insertSql)) {
                preparedStatement.setInt(1, returnId); // Set RETURN_ID
                preparedStatement.setString(2, bookData.get("book_id"));
                preparedStatement.setString(3, bookData.get("user_id"));
                preparedStatement.setString(4, bookData.get("date_borrowed"));
                preparedStatement.setString(5, bookData.get("date_returned"));
                preparedStatement.setInt(6, Integer.parseInt(bookData.get("copies_borrowed")));

                int result = preparedStatement.executeUpdate();

                if (result > 0) {
                    System.out.println("Book returned successfully.");
                    JOptionPane.showMessageDialog(null, "Book returned successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    AddQuantity(bookData.get("book_id"), Integer.parseInt(bookData.get("copies_borrowed")));
                    DeleteBorrowedBook(bookData.get("borrow_id"));
                } else {
                    System.out.println("Failed to return book.");
                    JOptionPane.showMessageDialog(null, "Failed to return book.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DeleteBorrowedBook(String bookId) {
        String sql = "DELETE FROM BOOKBORROW WHERE BORROW_ID = ?";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            int bookId11 = Integer.parseInt(bookId); // Convert to int if necessary
            preparedStatement.setInt(1, bookId11);
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Borrowed book deleted successfully.");
                JOptionPane.showMessageDialog(null, "Borrowed book deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Failed to delete borrowed book.");
                JOptionPane.showMessageDialog(null, "Failed to delete borrowed book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DeleteReturnBook(String returnId) {
        String sql = "DELETE FROM RETURNBOOKK WHERE RETURN_ID = ?";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            int returnId11 = Integer.parseInt(returnId); // Convert to int if necessary
            preparedStatement.setInt(1, returnId11);
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Returned book deleted successfully.");
                JOptionPane.showMessageDialog(null, "Returned book deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Failed to delete returned book.");
                JOptionPane.showMessageDialog(null, "Failed to delete returned book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ReturnBookModel> getReturnBook(String userId) {
        String sql = "SELECT * FROM RETURNBOOKK WHERE CAST(USER_ID AS VARCHAR(128)) = ?";
        String getBookDetailsSql = "SELECT TITLE, AUTHOR, GENRE FROM BOOKS WHERE CAST(BOOK_ID AS VARCHAR(128)) = ?";
        List<ReturnBookModel> borrowedBooks = new java.util.ArrayList<>();
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userId);
            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("RETURN_ID");
                String bookId = resultSet.getString("BOOK_ID");
                String dateBorrowed = resultSet.getString("DATE_BORROWED");
                String dateReturned = resultSet.getString("DATE_RETURNED");
                int copiesBorrowed = resultSet.getInt("BORROW_QUANTITY");

                try (var bookDetailsStmt = connection.prepareStatement(getBookDetailsSql)) {
                    bookDetailsStmt.setString(1, bookId);
                    var bookDetailsResultSet = bookDetailsStmt.executeQuery();
                    if (bookDetailsResultSet.next()) {
                        String bookTitle = bookDetailsResultSet.getString("TITLE");
                        String bookAuthor = bookDetailsResultSet.getString("AUTHOR");
                        String category = bookDetailsResultSet.getString("GENRE");

                        borrowedBooks.add(new ReturnBookModel(id, userId, bookId, bookTitle, bookAuthor, category, dateBorrowed, dateReturned, String.valueOf(copiesBorrowed)));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return borrowedBooks;

    }

    public List<ReturnBookModel> getReturnB() {
        String sql = "SELECT * FROM RETURNBOOKK";
        String getBookDetailsSql = "SELECT TITLE, AUTHOR, GENRE FROM BOOKS WHERE CAST(BOOK_ID AS VARCHAR(128)) = ?";
        List<ReturnBookModel> borrowedBooks = new java.util.ArrayList<>();

        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql);
             var resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("RETURN_ID");
                String bookId = resultSet.getString("BOOK_ID");
                String dateBorrowed = resultSet.getString("DATE_BORROWED");
                String dateReturned = resultSet.getString("DATE_RETURNED");
                int copiesBorrowed = resultSet.getInt("BORROW_QUANTITY");

                // Fetch book details
                try (var bookDetailsStmt = connection.prepareStatement(getBookDetailsSql)) {
                    bookDetailsStmt.setString(1, bookId);
                    try (var bookDetailsResultSet = bookDetailsStmt.executeQuery()) {
                        if (bookDetailsResultSet.next()) {
                            String bookTitle = bookDetailsResultSet.getString("TITLE");
                            String bookAuthor = bookDetailsResultSet.getString("AUTHOR");
                            String category = bookDetailsResultSet.getString("GENRE");

                            borrowedBooks.add(new ReturnBookModel(id, null, bookId, bookTitle, bookAuthor, category, dateBorrowed, dateReturned, String.valueOf(copiesBorrowed)));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return borrowedBooks;
    }

    public int getBorrowedBooksCount(String userId) {
        String sql = "SELECT COUNT(*) FROM BOOKBORROW WHERE CAST(USER_ID AS VARCHAR(128)) = ?";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userId);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0; // Return 0 if no borrowed books found or an error occurs
    }

    public int getReturnedBooksCount(String userId) {
        String sql = "SELECT COUNT(*) FROM RETURNBOOKK WHERE CAST(USER_ID AS VARCHAR(128)) = ?";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userId);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Return 0 if no returned books found or an error occurs
    }

    public void notifyUser(String Id) {
        String sql = "SELECT * FROM BOOKBORROW WHERE CAST(USER_ID AS VARCHAR(128)) = ?";
        String getBookDetailsSql = "SELECT TITLE, AUTHOR, GENRE FROM BOOKS WHERE CAST(BOOK_ID AS VARCHAR(128)) = ?";
        List<BorrowBookModel> borrowedBooks = new java.util.ArrayList<>();
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, Id);
            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("BORROW_ID");
                String bookId = resultSet.getString("BOOK_ID");
                String dateBorrowed = resultSet.getString("DATE_BORROWED");
                String dateReturned = resultSet.getString("DATE_RETURNED");
                int copiesBorrowed = resultSet.getInt("BORROW_QUANTITY");

                // Calculate days remaining
                if (dateReturned != null && !dateReturned.isEmpty()) {
                    java.time.LocalDate today = java.time.LocalDate.now();
                    java.time.LocalDate returnDate = java.time.LocalDate.parse(dateReturned);
                    long daysRemaining = java.time.temporal.ChronoUnit.DAYS.between(today, returnDate);

                    if (daysRemaining == 1) { // Notify if 1 day is remaining
                        try (var bookDetailsStmt = connection.prepareStatement(getBookDetailsSql)) {
                            bookDetailsStmt.setString(1, bookId);
                            var bookDetailsResultSet = bookDetailsStmt.executeQuery();
                            if (bookDetailsResultSet.next()) {
                                String bookTitle = bookDetailsResultSet.getString("TITLE");
                                String bookAuthor = bookDetailsResultSet.getString("AUTHOR");
                                String category = bookDetailsResultSet.getString("GENRE");

                                borrowedBooks.add(new BorrowBookModel(id, Id, bookId, bookTitle, bookAuthor, category, dateBorrowed, dateReturned, String.valueOf(copiesBorrowed)));
                            }
                        }
                    }
                }
            }

            // Notify user about books with 1 day remaining
            if (!borrowedBooks.isEmpty()) {
                StringBuilder message = new StringBuilder("The following books must be returned within 1 day:\n");
                for (BorrowBookModel book : borrowedBooks) {
                    message.append(book.getBookTitle()).append(" by ").append(book.getBookAuthor()).append("\n");
                }
                JOptionPane.showMessageDialog(null, message.toString(), "Return Reminder", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No books need to be returned within 1 day.", "No Pending Returns", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getBorrowedCount() {

        String sql = "SELECT COUNT(*) FROM BOOKBORROW";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql);
             var resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
       }

    public int getReturnedCount() {
        String sql = "SELECT COUNT(*) FROM RETURNBOOKK";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql);
             var resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  0;
   }

    public int getBookCount() {

        String sql = "SELECT COUNT(*) FROM BOOKS";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql);
             var resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
     }

    public void deleteBorrowedBook(String borrowId, String quantity) {
        String sql = "DELETE FROM BOOKBORROW WHERE BORROW_ID = ?";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            int borrowIdInt = Integer.parseInt(borrowId); // Convert to int if necessary
            preparedStatement.setInt(1, borrowIdInt);
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Borrowed book deleted successfully.");
                JOptionPane.showMessageDialog(null, "Borrowed book deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                AddQuantity(borrowId, Integer.parseInt(quantity)); // Assuming you want to add back 1 copy when deleting
            } else {
                System.out.println("Failed to delete borrowed book.");
                JOptionPane.showMessageDialog(null, "Failed to delete borrowed book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteReturnBook(String returnId, String quantity) {
        String sql = "DELETE FROM RETURNBOOKK WHERE RETURN_ID = ?";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            int returnIdInt = Integer.parseInt(returnId); // Convert to int if necessary
            preparedStatement.setInt(1, returnIdInt);
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Returned book deleted successfully.");
                JOptionPane.showMessageDialog(null, "Returned book deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                AddQuantity(returnId, Integer.parseInt(quantity)); // Assuming you want to add back 1 copy when deleting
            } else {
                System.out.println("Failed to delete returned book.");
                JOptionPane.showMessageDialog(null, "Failed to delete returned book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
