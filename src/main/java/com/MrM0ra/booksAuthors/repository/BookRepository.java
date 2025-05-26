package com.MrM0ra.booksAuthors.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.MrM0ra.booksAuthors.model.dto.BookDTO;

import jakarta.annotation.PostConstruct;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall insertBookProc;
    private SimpleJdbcCall updateBookProc;
    private SimpleJdbcCall getBookProc;
    private SimpleJdbcCall deleteBookProc;
    private SimpleJdbcCall getAllBooksProc;
    private SimpleJdbcCall getBooksByAuthorProc;

    @PostConstruct
    public void init() {
        insertBookProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("insert_book");

        updateBookProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("update_book");

        getBookProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("get_book");

        deleteBookProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("delete_book");
        
        getAllBooksProc = new SimpleJdbcCall(jdbcTemplate)
            .withProcedureName("get_all_books")
            .returningResultSet("P_CURSOR", new BookRowMapper());

        getBooksByAuthorProc = new SimpleJdbcCall(jdbcTemplate)
            .withProcedureName("get_books_by_author")
            .returningResultSet("P_CURSOR", new BookRowMapper());
    }

    public void insertBook(int id, String title, int authorId) {
        insertBookProc.execute(Map.of(
                "p_id", id,
                "p_title", title,
                "p_author_id", authorId
        ));
    }

    public void updateBook(int id, String title, int authorId) {
        updateBookProc.execute(Map.of(
                "p_id", id,
                "p_title", title,
                "p_author_id", authorId
        ));
    }

    public Map<String, Object> getBook(int id) {
        return getBookProc.execute(Map.of("p_id", id));
    }

    public void deleteBook(int id) {
        deleteBookProc.execute(Map.of("p_id", id));
    }

    @SuppressWarnings("unchecked")
    public List<BookDTO> getAllBooks() {
        Map<String, Object> result = getAllBooksProc.execute();
        return (List<BookDTO>) result.get("P_CURSOR");
    }

    @SuppressWarnings("unchecked")
    public List<BookDTO> getBooksByAuthor(int authorId) {
        Map<String, Object> result = getBooksByAuthorProc.execute(Map.of("p_author_id", authorId));
        return (List<BookDTO>) result.get("P_CURSOR");
    }


    private static class BookRowMapper implements RowMapper<BookDTO> {
        @Override
        public BookDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            BookDTO book = new BookDTO();
            book.setId(rs.getInt("ID"));
            book.setTitle(rs.getString("TITLE"));
            book.setAuthorId(rs.getInt("AUTHOR_ID"));
            return book;
        }
    }
}
