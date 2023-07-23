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
        String sql = "insert into music(title, poster_url, description, artist_name, released_date, is_spotify, created_datetime, updated_datetime) "
                + "values (:title, :posterUrl, :description, :artistName, :releasedDate, :isSpotify, :createdDatetime, :updatedDatetime)";
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
    public List<Music> findCustomMusics() {
        String sql = "select * from music where is_spotify is FALSE";

        return template.query(sql, getMusicRowMapper());
    }

    @Override
    public List<Music> findTopMusics() {
        String sql = "select * from music where is_spotify is TRUE";

        return template.query(sql, getMusicRowMapper());
    }

    @Override
    public Optional<Music> findMusicById(long musicId) {
        String sql = "select * from music where id = :musicId";

        try {
            Music music = template.queryForObject(sql, getMusicIdMap(musicId), getMusicRowMapper());

            return Optional.of(music);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Music update(Music music) {
        String sql = "update music set title = :title, poster_url = :posterUrl, description = :description, artist_name = :artistName, "
                + "released_date = :releasedDate, created_datetime = :createdDatetime, updated_datetime = :updatedDatetime where id = :musicId";
        MapSqlParameterSource parameterSource = getParameterSource(music).addValue("musicId", music.getId());

        template.update(sql, parameterSource);

        return music;
    }

    @Override
    public void deleteById(long musicId) {
        String sql = "delete from music where id = :musicId";

        template.update(sql, getMusicIdMap(musicId));
    }

    private Map<String, Object> getMusicIdMap(long musicId) {
        return Map.of("musicId", musicId);
    }

    private MapSqlParameterSource getParameterSource(Music music) {
        return new MapSqlParameterSource()
                .addValue("title", music.getTitle())
                .addValue("posterUrl", music.getPosterUrl())
                .addValue("description", music.getDescription())
                .addValue("artistName", music.getArtistName())
                .addValue("releasedDate", music.getReleasedDate())
                .addValue("isSpotify", music.getIsSpotify())
                .addValue("createdDatetime", music.getCreatedDatetime())
                .addValue("updatedDatetime", music.getUpdatedDatetime());
    }

    private RowMapper<Music> getMusicRowMapper() {
        return ((resultSet, rowNum) -> Music.builder()
                .id(resultSet.getLong("id"))
                .title(resultSet.getString("title"))
                .posterUrl(resultSet.getString("poster_url"))
                .description(resultSet.getString("description"))
                .artistName(resultSet.getString("artist_name"))
                .releasedDate(resultSet.getDate("released_date").toLocalDate())
                .isSpotify(resultSet.getBoolean("is_spotify"))
                .createdDatetime(resultSet.getTimestamp("created_datetime").toLocalDateTime())
                .updatedDatetime(resultSet.getTimestamp("updated_datetime").toLocalDateTime())
                .build());
    }
}
