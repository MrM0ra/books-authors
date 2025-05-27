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

import com.MrM0ra.booksAuthors.model.dto.AuthorDTO;

import jakarta.annotation.PostConstruct;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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

    @PostConstruct
    public void init() {
        // Configuración para INSERT_AUTHOR
        insertAuthorProc = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("SYSTEM")
                .withProcedureName("INSERT_AUTHOR")
                .declareParameters(
                    new SqlParameter("p_name", Types.VARCHAR),
                    new SqlOutParameter("p_id", Types.NUMERIC)
                );

        // Configuración para UPDATE_AUTHOR
        updateAuthorProc = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("SYSTEM")
                .withProcedureName("UPDATE_AUTHOR")
                .declareParameters(
                    new SqlParameter("p_id", Types.NUMERIC),
                    new SqlParameter("p_name", Types.VARCHAR)
                );

        // Configuración para GET_AUTHOR
        getAuthorProc = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("SYSTEM")
                .withProcedureName("GET_AUTHOR")
                .declareParameters(
                    new SqlParameter("p_id", Types.NUMERIC),
                    new SqlOutParameter("p_name", Types.VARCHAR)
                );

        // Configuración para DELETE_AUTHOR
        deleteAuthorProc = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("SYSTEM")
                .withProcedureName("DELETE_AUTHOR")
                .declareParameters(
                    new SqlParameter("p_id", Types.NUMERIC)
                );

        // Configuración para GET_ALL_AUTHORS
        getAllAuthorsProc = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("SYSTEM")
                .withProcedureName("GET_ALL_AUTHORS")
                .declareParameters(
                    new SqlOutParameter("p_cursor", Types.REF_CURSOR, new AuthorRowMapper())
                );
    }

    public Long insertAuthor(String name) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_name", name);
        
        Map<String, Object> out = insertAuthorProc.execute(in);
        return ((Number) out.get("p_id")).longValue();
    }

    public void updateAuthor(Long id, String name) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id", id)
                .addValue("p_name", name);
        
        updateAuthorProc.execute(in);
    }

    public String getAuthorName(Long id) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id", id);
        
        Map<String, Object> out = getAuthorProc.execute(in);
        return (String) out.get("p_name");
    }

    public void deleteAuthor(Long id) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id", id);
        
        deleteAuthorProc.execute(in);
    }

    @SuppressWarnings("unchecked")
    public List<AuthorDTO> getAllAuthors() {
        Map<String, Object> result = getAllAuthorsProc.execute();
        return (List<AuthorDTO>) result.get("p_cursor");
    }

    private static class AuthorRowMapper implements RowMapper<AuthorDTO> {
        @Override
        public AuthorDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new AuthorDTO(
                rs.getLong("author_id"),
                rs.getString("name")
            );
        }
    }
}
