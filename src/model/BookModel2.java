package model;

public class BookModel2 {

    String id;

    String bookId;

    String title;

    String author;

    String category;

    String dateBorrowed;

    String DaysRemaining;

    String borrowedCopies;

    public BookModel2(String id, String bookId, String title, String author, String category, String dateBorrowed, String daysRemaining, String borrowedCopies) {
        this.id = id;
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.dateBorrowed = dateBorrowed;
        DaysRemaining = daysRemaining;
        this.borrowedCopies = borrowedCopies;
    }

    public String getId() {
        return id;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getDateBorrowed() {
        return dateBorrowed;
    }

    public String getDaysRemaining() {
        return DaysRemaining;
    }

    public String getBorrowedCopies() {
        return borrowedCopies;
    }

}
