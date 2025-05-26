package com.MrM0ra.booksAuthors.service;

import com.MrM0ra.booksAuthors.model.dto.BookDTO;
import com.MrM0ra.booksAuthors.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public void createBook(BookDTO book) {
        bookRepository.insertBook(book.getId(), book.getTitle(), book.getAuthorId());
    }

    public void updateBook(BookDTO book) {
        bookRepository.updateBook(book.getId(), book.getTitle(), book.getAuthorId());
    }

    public BookDTO getBook(int id) {
        Map<String, Object> result = bookRepository.getBook(id);
        BookDTO book = new BookDTO();
        book.setId(id);
        book.setTitle((String) result.get("P_TITLE"));
        book.setAuthorId((Integer) result.get("P_AUTHOR_ID"));
        return book;
    }

    public void deleteBook(int id) {
        bookRepository.deleteBook(id);
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public List<BookDTO> getBooksByAuthor(int authorId) {
        return bookRepository.getBooksByAuthor(authorId);
    }
    
}
