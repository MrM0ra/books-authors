package com.MrM0ra.booksAuthors.service;

import com.MrM0ra.booksAuthors.model.dto.BookDTO;
import com.MrM0ra.booksAuthors.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public void createBook(BookDTO book) {
        bookRepository.insertBook(book.getTitle(), book.getAuthorId());
    }

    public void updateBook(BookDTO book) {
        bookRepository.updateBook(book.getId(), book.getTitle(), book.getAuthorId());
    }

    public BookDTO getBook(Long id) {
        BookDTO result = bookRepository.getBook(id);
        return result;
    }

    public void deleteBook(Long id) {
        bookRepository.deleteBook(id);
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public List<BookDTO> getBooksByAuthor(Long authorId) {
        return bookRepository.getBooksByAuthor(authorId);
    }
    
}
