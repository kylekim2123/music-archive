package com.programmers.musicapp.repository;

import com.programmers.musicapp.entity.Comment;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCommentRepository implements CommentRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcCommentRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Comment save(Comment comment) {
        String sql = "insert into comment(music_id, description, created_datetime, updated_datetime) "
                + "values (:musicId, :description, :createdDatetime, :updatedDatetime)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(sql, getParameterSource(comment), keyHolder);
        comment.setId(keyHolder.getKey().longValue());

        return comment;
    }

    @Override
    public List<Comment> findCommentsByMusicId(long musicId) {
        String sql = "select * from comment where music_id = :musicId";
        Map<String, Object> parameterSource = Map.of("musicId", musicId);

        return template.query(sql, parameterSource, getCommentRowMapper());
    }

    @Override
    public Optional<Comment> findCommentById(long commentId) {
        String sql = "select * from comment where id = :commentId";
        Map<String, Object> parameterSource = Map.of("commentId", commentId);

        try {
            Comment comment = template.queryForObject(sql, parameterSource, getCommentRowMapper());

            return Optional.of(comment);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(long commentId) {
        String sql = "delete from comment where id = :commentId";
        Map<String, Object> parameterSource = Map.of("commentId", commentId);

        template.update(sql, parameterSource);
    }

    private MapSqlParameterSource getParameterSource(Comment comment) {
        return new MapSqlParameterSource()
                .addValue("musicId", comment.getMusicId())
                .addValue("description", comment.getDescription())
                .addValue("createdDatetime", comment.getCreatedDatetime())
                .addValue("updatedDatetime", comment.getUpdatedDatetime());
    }

    private RowMapper<Comment> getCommentRowMapper() {
        return ((resultSet, rowNum) -> Comment.builder()
                .id(resultSet.getLong("id"))
                .musicId(resultSet.getLong("music_id"))
                .description(resultSet.getString("description"))
                .createdDatetime(resultSet.getTimestamp("created_datetime").toLocalDateTime())
                .updatedDatetime(resultSet.getTimestamp("updated_datetime").toLocalDateTime())
                .build());
    }
}
