package com.programmers.musicapp.repository;

import com.programmers.musicapp.entity.Comment;
import javax.sql.DataSource;
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

    private MapSqlParameterSource getParameterSource(Comment comment) {
        return new MapSqlParameterSource()
                .addValue("musicId", comment.getMusicId())
                .addValue("description", comment.getDescription())
                .addValue("createdDatetime", comment.getCreatedDatetime())
                .addValue("updatedDatetime", comment.getUpdatedDatetime());
    }
}
