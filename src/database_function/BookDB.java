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

    public void insertAllBooks() {
        List<Map<String, String>> books = List.of(
                // College of Fine Arts and Design (CFAD)
                Map.of("book_id", "CFAD001", "title", "Art Fundamentals: Theory and Practice (12th Edition)", "author", "Otto G. Ocvirk, Robert E. Stinson, Philip R. Wigg, Robert O. Bone, David L. Cayton", "genre", "College of Fine Arts and Design (CFAD)", "copyright", "2012", "publisher", "McGraw-Hill Education", "copies", "10"),
                Map.of("book_id", "CFAD002", "title", "Design Basics (9th Edition)", "author", "David A. Lauer, Stephen Pentak", "genre", "College of Fine Arts and Design (CFAD)", "copyright", "2015", "publisher", "Cengage Learning", "copies", "10"),
                Map.of("book_id", "CFAD003", "title", "The Elements of Graphic Design (2nd Edition)", "author", "Alex W. White", "genre", "College of Fine Arts and Design (CFAD)", "copyright", "2011", "publisher", "Allworth Press", "copies", "10"),
                Map.of("book_id", "CFAD004", "title", "History of Modern Art (7th Edition)", "author", "H. H. Arnason, Elizabeth C. Mansfield", "genre", "College of Fine Arts and Design (CFAD)", "copyright", "2012", "publisher", "Pearson Education", "copies", "10"),
                Map.of("book_id", "CFAD005", "title", "Interaction of Color (50th Anniversary Edition)", "author", "Josef Albers", "genre", "College of Fine Arts and Design (CFAD)", "copyright", "2013", "publisher", "Yale University Press", "copies", "10"),

                // College of Engineering (COE)
                Map.of("book_id", "COE001", "title", "Engineering Mechanics: Statics (15th Edition)", "author", "Russell C. Hibbeler", "genre", "College of Engineering (COE)", "copyright", "2022", "publisher", "Pearson Education", "copies", "10"),
                Map.of("book_id", "COE002", "title", "Engineering Mechanics: Dynamics (7th Edition)", "author", "J. L. Meriam, L. G. Kraige", "genre", "College of Engineering (COE)", "copyright", "2013", "publisher", "John Wiley & Sons, Inc.", "copies", "10"),
                Map.of("book_id", "COE003", "title", "Thermodynamics: An Engineering Approach (9th Edition)", "author", "Yunus A. Çengel, Michael A. Boles", "genre", "College of Engineering (COE)", "copyright", "2019", "publisher", "McGraw-Hill Education", "copies", "10"),
                Map.of("book_id", "COE004", "title", "Introduction to Fluid Mechanics (8th Edition)", "author", "Robert W. Fox, Alan T. McDonald, Philip J. Pritchard", "genre", "College of Engineering (COE)", "copyright", "2016", "publisher", "Wiley", "copies", "10"),
                Map.of("book_id", "COE005", "title", "Electrical Engineering: Principles and Applications (7th Edition)", "author", "Allan R. Hambley", "genre", "College of Engineering (COE)", "copyright", "2013", "publisher", "Pearson Education", "copies", "10"),

                // College of Arts and Letters (CAL)
                Map.of("book_id", "CAL001", "title", "Why Choose the Liberal Arts?", "author", "Mark William Roche", "genre", "College of Arts and Letters (CAL)", "copyright", "2022", "publisher", "University of Notre Dame Press", "copies", "10"),
                Map.of("book_id", "CAL002", "title", "The Problems of Viewing Performance: Epistemology and Other Minds", "author", "Michael Y. Bennett", "genre", "College of Arts and Letters (CAL)", "copyright", "2021", "publisher", "Routledge", "copies", "10"),
                Map.of("book_id", "CAL003", "title", "Contemporary African Dance Theatre: Phenomenology, Whiteness, and the Gaze", "author", "Sabine Sörgel", "genre", "College of Arts and Letters (CAL)", "copyright", "2020", "publisher", "Springer Nature", "copies", "10"),
                Map.of("book_id", "CAL004", "title", "Acts of Dramaturgy: The Shakespeare Trilogy", "author", "Michael Pinchbeck", "genre", "College of Arts and Letters (CAL)", "copyright", "2020", "publisher", "Intellect Books", "copies", "10"),
                Map.of("book_id", "CAL005", "title", "Gathering: Political Writing on Art and Culture", "author", "Marian Pastor-Roces", "genre", "College of Arts and Letters (CAL)", "copyright", "2019", "publisher", "De La Salle-College of Saint Benilde, Incorporated", "copies", "10"),
                Map.of("book_id", "CAL006", "title", "Jolography", "author", "Paolo Manalo", "genre", "College of Arts and Letters (CAL)", "copyright", "2003", "publisher", "University of the Philippines Press", "copies", "10"),
                Map.of("book_id", "CAL007", "title", "Sa Sariling Bayan: Apat na Dulang May Musika", "author", "Bienvenido Lumbera", "genre", "College of Arts and Letters (CAL)", "copyright", "2004", "publisher", "De La Salle University-Manila Press", "copies", "10"),
                Map.of("book_id", "CAL008", "title", "Philippine Theater: A History and Anthology (Vol. I–V)", "author", "Nicanor Tiongson", "genre", "College of Arts and Letters (CAL)", "copyright", "1999", "publisher", "University of the Philippines Press", "copies", "10"),
                Map.of("book_id", "CAL009", "title", "Contemporary Social Philosophy", "author", "Manuel B. Dy Jr.", "genre", "College of Arts and Letters (CAL)", "copyright", "2014", "publisher", "Aklat ng Bayan", "copies", "10"),
                Map.of("book_id", "CAL010", "title", "Notes on Bakya and Other Essays", "author", "Carlos Palanca Memorial Awardee", "genre", "College of Arts and Letters (CAL)", "copyright", "2024", "publisher", "University of the Philippines Press", "copies", "10"),

                // College of Architecture Books (CAR)
                Map.of("book_id", "CAR001", "title", "Audiovisual Textbook of Philippine Architecture", "author", "Gerard Rey A. Lico, Joonee Gamboa, Susan Medina, Maria Cristina V. Turalba", "genre", "College of Architecture (CAR)", "copyright", "2007", "publisher", "National Commission for Culture and the Arts (NCCA) Committee on Architecture and Allied Arts", "copies", "10"),
                Map.of("book_id", "CAR002", "title", "Eric Owen Moss: Buildings and Projects", "author", "Eric Owen Moss", "genre", "College of Architecture (CAR)", "copyright", "1991", "publisher", "Monacelli Press", "copies", "10"),
                Map.of("book_id", "CAR003", "title", "Commercial Space: Offices", "author", "Francisco Asensio Cerver", "genre", "College of Architecture (CAR)", "copyright", "1995", "publisher", "Editions du Chêne", "copies", "10"),
                Map.of("book_id", "CAR004", "title", "Building Codes Illustrated: A Guide to Understanding the 2000 International Building Code", "author", "Frank Ching", "genre", "College of Architecture (CAR)", "copyright", "2003", "publisher", "John Wiley & Sons", "copies", "10"),
                Map.of("book_id", "CAR005", "title", "Pasyal: UP Diliman Art Trail", "author", "Tessa Maria Guazon", "genre", "College of Architecture (CAR)", "copyright", "2017", "publisher", "University of the Philippines", "copies", "10"),

                // College of Information and Computing Sciences (CICS)
                Map.of("book_id", "CICS001", "title", "Introduction to the Theory of Computation (3rd Edition)", "author", "Michael Sipser", "genre", "College of Information and Computing Sciences (CICS)", "copyright", "2012", "publisher", "Cengage Learning", "copies", "10"),
                Map.of("book_id", "CICS002", "title", "Artificial Intelligence: A Modern Approach (4th Edition)", "author", "Stuart Russell, Peter Norvig", "genre", "College of Information and Computing Sciences (CICS)", "copyright", "2020", "publisher", "Pearson Education", "copies", "10"),
                Map.of("book_id", "CICS003", "title", "Computer Networking: A Top-Down Approach (8th Edition)", "author", "James F. Kurose, Keith W. Ross", "genre", "College of Information and Computing Sciences (CICS)", "copyright", "2021", "publisher", "Pearson Education", "copies", "10"),
                Map.of("book_id", "CICS004", "title", "Clean Code: A Handbook of Agile Software Craftsmanship", "author", "Robert C. Martin", "genre", "College of Information and Computing Sciences (CICS)", "copyright", "2008", "publisher", "Prentice Hall", "copies", "10"),
                Map.of("book_id", "CICS005", "title", "Database System Concepts (7th Edition)", "author", "Abraham Silberschatz, Henry F. Korth, S. Sudarshan", "genre", "College of Information and Computing Sciences (CICS)", "copyright", "2019", "publisher", "McGraw-Hill Education", "copies", "10"),

                // College of Tourism and Hospitality Management (CTHM)
                Map.of("book_id", "CTHM001", "title", "Hospitality Today: An Introduction (8th Edition)", "author", "Rocco M. Angelo, Andrew N. Vladimir", "genre", "College of Tourism and Hospitality Management (CTHM)", "copyright", "2016", "publisher", "Educational Institute of the American Hotel & Lodging Association", "copies", "10"),
                Map.of("book_id", "CTHM002", "title", "Tourism: Principles, Practices, Philosophies (12th Edition)", "author", "Charles R. Goeldner, J.R. Brent Ritchie", "genre", "College of Tourism and Hospitality Management (CTHM)", "copyright", "2012", "publisher", "Wiley", "copies", "10"),
                Map.of("book_id", "CTHM003", "title", "Managing Hospitality Human Resources (6th Edition)", "author", "Robert H. Woods, Misty M. Johanson, Michael Sciarini", "genre", "College of Tourism and Hospitality Management (CTHM)", "copyright", "2020", "publisher", "AHLEI", "copies", "10"),
                Map.of("book_id", "CTHM004", "title", "Introduction to Hospitality (8th Edition)", "author", "John R. Walker", "genre", "College of Tourism and Hospitality Management (CTHM)", "copyright", "2019", "publisher", "Pearson Education", "copies", "10"),
                Map.of("book_id", "CTHM005", "title", "Sustainable Tourism: Principles, Contexts and Practices", "author", "David A. Fennell, Ross K. Dowling", "genre", "College of Tourism and Hospitality Management (CTHM)", "copyright", "2020", "publisher", "Channel View Publications", "copies", "10")
        );

        for (Map<String, String> book : books) {
            addBook(book);
        }
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
        String sql = "DELETE FROM BOOKS WHERE CAST(BOOK_ID AS VARCHAR(128)) = ?";
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
                 DeleteReturnBook(bookData.get("book_id"));
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
            } else {
                System.out.println("Failed to update book quantity.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AddQuantity(String bookId, int quantity) {
        String sql = "UPDATE BOOKS SET COPIES = COPIES + ? WHERE CAST(BOOK_ID AS VARCHAR(128)) = ?";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            System.out.println("AddQuantity called with bookId: " + bookId + ", quantity: " + quantity);

            preparedStatement.setInt(1, quantity);
            preparedStatement.setString(2, bookId);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Book quantity updated successfully.");
            } else {
                System.out.println("Failed to update book quantity. No rows affected.");
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


                String id = resultSet.getString("BORROW_ID");
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
            } else {
                System.out.println("Failed to delete borrowed book.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DeleteReturnBook(String bookId) {
        String sql = "DELETE FROM RETURNBOOKK WHERE CAST(BOOK_ID AS VARCHAR(128)) = ?";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, bookId);
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Returned book deleted successfully.");
            } else {
                System.out.println("Failed to delete returned book.");
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

            System.out.println("Deleting BORROW_ID: " + borrowId + ", Quantity: " + quantity);

            int borrowIdInt = Integer.parseInt(borrowId); // Convert to int if necessary
            preparedStatement.setInt(1, borrowIdInt);
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Borrowed book deleted successfully.");
                JOptionPane.showMessageDialog(null, "Borrowed book deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                AddQuantity(borrowId, Integer.parseInt(quantity)); // Assuming you want to add back 1 copy when deleting
            } else {
                System.out.println("Failed to delete borrowed book. No rows affected.");
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
