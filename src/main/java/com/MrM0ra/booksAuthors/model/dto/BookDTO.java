package com.MrM0ra.booksAuthors.model.dto;

public class BookDTO {
    private int id;
    private String title;
    private int authorId;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }
}

