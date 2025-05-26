package model;

public class BorrowBookModel {

    String id;

    String name;

    String bookId;

    String bookTitle;

    String BookAuthor;

    String Category;

    String dateBorrowed;

    String dateReturned;

    String copiesBorrowed;

    public BorrowBookModel(String id, String name, String bookId, String bookTitle, String bookAuthor, String category, String dateBorrowed, String dateReturned, String copiesBorrowed) {
        this.id = id;
        this.name = name;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        BookAuthor = bookAuthor;
        Category = category;
        this.dateBorrowed = dateBorrowed;
        this.dateReturned = dateReturned;
        this.copiesBorrowed = copiesBorrowed;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return BookAuthor;
    }

    public String getCategory() {
        return Category;
    }

    public String getDateBorrowed() {
        return dateBorrowed;
    }

    public String getDateReturned() {
        return dateReturned;
    }

    public String getCopiesBorrowed() {
        return copiesBorrowed;
    }



}
