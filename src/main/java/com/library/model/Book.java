package com.library.model;

import java.time.LocalDate;

/**
 * Represents a Book in the library.
 */
public class Book {

    private String id;
    private String title;
    private String author;
    private String genre;
    private int year;
    private boolean available;
    private LocalDate addedDate;

    public Book(String id, String title, String author, String genre, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.available = true;
        this.addedDate = LocalDate.now();
    }

    // â”€â”€ Getters â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public String getId()          { return id; }
    public String getTitle()       { return title; }
    public String getAuthor()      { return author; }
    public String getGenre()       { return genre; }
    public int    getYear()        { return year; }
    public boolean isAvailable()   { return available; }
    public LocalDate getAddedDate(){ return addedDate; }

    // â”€â”€ Setters â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return String.format("%-6s | %-35s | %-20s | %-15s | %d | %s",
                id, title, author, genre, year,
                available ? "âœ… Available" : "âŒ Borrowed");
    }
}
