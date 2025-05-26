package com.MrM0ra.booksAuthors.controller;

import com.MrM0ra.booksAuthors.model.dto.AuthorDTO;
import com.MrM0ra.booksAuthors.service.AuthorService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity<String> createAuthor(@RequestBody AuthorDTO author) {
        authorService.createAuthor(author);
        return ResponseEntity.ok("Author created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAuthor(@PathVariable int id, @RequestBody AuthorDTO author) {
        author.setId(id);
        authorService.updateAuthor(author);
        return ResponseEntity.ok("Author updated");
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable int id) {
        AuthorDTO author = authorService.getAuthor(id);
        return ResponseEntity.ok(author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable int id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok("Author deleted");
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }
}
