package com.MrM0ra.booksAuthors.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.MrM0ra.booksAuthors.model.dto.BookDTO;

import jakarta.annotation.PostConstruct;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
        // Configuración para INSERT_BOOK
        insertBookProc = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("SYSTEM")
                .withProcedureName("INSERT_BOOK")
                .declareParameters(
                    new SqlParameter("p_title", Types.VARCHAR),
                    new SqlParameter("p_author_id", Types.NUMERIC),
                    new SqlOutParameter("p_book_id", Types.NUMERIC)
                );

        // Configuración para UPDATE_BOOK
        updateBookProc = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("SYSTEM")
                .withProcedureName("UPDATE_BOOK")
                .declareParameters(
                    new SqlParameter("p_id", Types.NUMERIC),
                    new SqlParameter("p_title", Types.VARCHAR),
                    new SqlParameter("p_author_id", Types.NUMERIC)
                );

        // Configuración para GET_BOOK
        getBookProc = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("SYSTEM")
                .withProcedureName("GET_BOOK")
                .declareParameters(
                    new SqlParameter("p_id", Types.NUMERIC),
                    new SqlOutParameter("p_title", Types.VARCHAR),
                    new SqlOutParameter("p_author_id", Types.NUMERIC)
                );

        // Configuración para DELETE_BOOK
        deleteBookProc = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("SYSTEM")
                .withProcedureName("DELETE_BOOK")
                .declareParameters(
                    new SqlParameter("p_id", Types.NUMERIC)
                );

        // Configuración para GET_ALL_BOOKS
        getAllBooksProc = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("SYSTEM")
                .withProcedureName("GET_ALL_BOOKS")
                .declareParameters(
                    new SqlOutParameter("p_cursor", Types.REF_CURSOR, new BookRowMapper())
                );

        // Configuración para GET_BOOKS_BY_AUTHOR
        getBooksByAuthorProc = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("SYSTEM")
                .withProcedureName("GET_BOOKS_BY_AUTHOR")
                .declareParameters(
                    new SqlParameter("p_author_id", Types.NUMERIC),
                    new SqlOutParameter("p_cursor", Types.REF_CURSOR, new BookRowMapper())
                );
    }

    public Long insertBook(String title, Long authorId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_title", title)
                .addValue("p_author_id", authorId);
        
        Map<String, Object> out = insertBookProc.execute(in);
        return ((Number) out.get("p_book_id")).longValue();
    }

    public void updateBook(Long id, String title, Long authorId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id", id)
                .addValue("p_title", title)
                .addValue("p_author_id", authorId);
        
        updateBookProc.execute(in);
    }

    public BookDTO getBook(Long id) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id", id);
        
        Map<String, Object> out = getBookProc.execute(in);
        return new BookDTO(
            id,
            (String) out.get("p_title"),
            ((Number) out.get("p_author_id")).longValue()
        );
    }

    public void deleteBook(Long id) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id", id);
        
        deleteBookProc.execute(in);
    }

    @SuppressWarnings("unchecked")
    public List<BookDTO> getAllBooks() {
        Map<String, Object> result = getAllBooksProc.execute();
        return (List<BookDTO>) result.get("p_cursor");
    }

    @SuppressWarnings("unchecked")
    public List<BookDTO> getBooksByAuthor(Long authorId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_author_id", authorId);
        
        Map<String, Object> result = getBooksByAuthorProc.execute(in);
        return (List<BookDTO>) result.get("p_cursor");
    }

    private static class BookRowMapper implements RowMapper<BookDTO> {
        @Override
        public BookDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new BookDTO(
                rs.getLong("book_id"),
                rs.getString("title"),
                rs.getLong("author_id")
            );
        }
    }
}
