package com.MrM0ra.booksAuthors.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.MrM0ra.booksAuthors.model.dto.AuthorDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall insertAuthorProc;
    private SimpleJdbcCall updateAuthorProc;
    private SimpleJdbcCall getAuthorProc;
    private SimpleJdbcCall deleteAuthorProc;
    private SimpleJdbcCall getAllAuthorsProc;

    public AuthorRepository() {
        insertAuthorProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("insert_author");

        updateAuthorProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("update_author");

        getAuthorProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("get_author");

        deleteAuthorProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("delete_author");

        getAllAuthorsProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("get_all_authors")
                .returningResultSet("P_CURSOR", new AuthorRowMapper());
    }

    public void insertAuthor(int id, String name) {
        insertAuthorProc.execute(Map.of(
                "p_id", id,
                "p_name", name
        ));
    }

    public void updateAuthor(int id, String name) {
        updateAuthorProc.execute(Map.of(
                "p_id", id,
                "p_name", name
        ));
    }

    public Map<String, Object> getAuthor(int id) {
        return getAuthorProc.execute(Map.of("p_id", id));
    }

    public void deleteAuthor(int id) {
        deleteAuthorProc.execute(Map.of("p_id", id));
    }

    @SuppressWarnings("unchecked")
    public List<AuthorDTO> getAllAuthors() {
        Map<String, Object> result = getAllAuthorsProc.execute();
        return (List<AuthorDTO>) result.get("P_CURSOR");
    }

    private static class AuthorRowMapper implements RowMapper<AuthorDTO> {
    @Override
    public AuthorDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        AuthorDTO author = new AuthorDTO();
        author.setId(rs.getInt("ID"));
        author.setName(rs.getString("NAME"));
        return author;
    }
}
}

