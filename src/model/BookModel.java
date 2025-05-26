package model;

public class BookModel {
    String id;

    String title;

    String author;

    String genre;

    String copyright;

    String publisher;

    int copies;

    public BookModel(String id, String title, String author, String genre, String copyright, String publisher, int copies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.copyright = copyright;
        this.publisher = publisher;
        this.copies = copies;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getCopies() {
        return copies;
    }

}
