package com.MrM0ra.booksAuthors.model.dto;

public class AuthorDTO {
    private Long id;
    private String name;

    public AuthorDTO(){}

    public AuthorDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

