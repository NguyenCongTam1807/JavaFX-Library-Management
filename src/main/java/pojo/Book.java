package pojo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.util.UUID;

public class Book extends RecursiveTreeObject<Book> {
    private int id,amount,publishedYear;
    private String title,author,genre,publisher,summary;

    public Book(int id,String title,int amount,String author,int publishedYear,String genre,String publisher,String summary){
        this.id=id;
        this.title=title;
        this.amount=amount;
        this.author=author;
        this.publishedYear=publishedYear;
        this.genre=genre;
        this.publisher=publisher;
        this.summary=summary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


}
