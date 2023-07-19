package com.programmers.musicapp.repository;

import com.programmers.musicapp.entity.Music;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
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

    @Override
    public List<Music> findAllMusics() {
        String sql = "select * from music";

        return template.query(sql, getMusicRowMapper());
    }

    @Override
    public Optional<Music> findMusicById(int musicId) {
        String sql = "select * from music where id = :musicId";
        Map<String, Object> parameterSource = Map.of("musicId", musicId);

        try {
            Music music = template.queryForObject(sql, parameterSource, getMusicRowMapper());

            return Optional.of(music);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
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

    private RowMapper<Music> getMusicRowMapper() {
        return ((resultSet, rowNum) -> Music.builder()
                .id(resultSet.getLong("id"))
                .title(resultSet.getString("title"))
                .posterUrl(resultSet.getString("poster_url"))
                .description(resultSet.getString("description"))
                .artistName(resultSet.getString("artist_name"))
                .releasedDate(resultSet.getDate("released_date").toLocalDate())
                .createdDatetime(resultSet.getTimestamp("created_datetime").toLocalDateTime())
                .updatedDateTime(resultSet.getTimestamp("updated_datetime").toLocalDateTime())
                .build());
    }
}
