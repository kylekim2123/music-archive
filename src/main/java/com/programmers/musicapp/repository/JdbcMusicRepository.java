package com.programmers.musicapp.repository;

import com.programmers.musicapp.entity.Music;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMusicRepository implements MusicRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcMusicRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Music save(Music music) {
        String sql = "insert into music(title, poster_url, description, artist_name, released_date, created_datetime, updated_datetime) "
                + "values (:title, :posterUrl, :description, :artistName, :releasedDate, :createdDatetime, :updatedDatetime)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(sql, getParameterSource(music), keyHolder);
        music.setId(keyHolder.getKey().longValue());

        return music;
    }

    private SqlParameterSource getParameterSource(Music music) {
        return new MapSqlParameterSource()
                .addValue("title", music.getTitle())
                .addValue("posterUrl", music.getPosterUrl())
                .addValue("description", music.getDescription())
                .addValue("artistName", music.getArtistName())
                .addValue("releasedDate", music.getReleasedDate())
                .addValue("createdDatetime", music.getCreatedDatetime())
                .addValue("updatedDatetime", music.getUpdatedDateTime());
    }
}
