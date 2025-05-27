package com.MrM0ra.booksAuthors.service;

import com.MrM0ra.booksAuthors.model.dto.AuthorDTO;
import com.MrM0ra.booksAuthors.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public void createAuthor(AuthorDTO author) {
        authorRepository.insertAuthor(author.getName());
    }

    public void updateAuthor(AuthorDTO author) {
        authorRepository.updateAuthor(author.getId(), author.getName());
    }

    public AuthorDTO getAuthor(Long id) {
        String result = authorRepository.getAuthorName(id);
        AuthorDTO author = new AuthorDTO();
        author.setId(id);
        author.setName(result); // cuidado con el nombre en mayúsculas
        return author;
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteAuthor(id);
    }

    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.getAllAuthors();
    }
}
