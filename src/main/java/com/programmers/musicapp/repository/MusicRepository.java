package com.programmers.musicapp.repository;

import com.programmers.musicapp.entity.Music;
import java.util.List;
import java.util.Optional;

public interface MusicRepository {

    Music save(Music music);

    List<Music> findAllMusics();

    Optional<Music> findMusicById(long musicId);

    Music update(Music music);
}
