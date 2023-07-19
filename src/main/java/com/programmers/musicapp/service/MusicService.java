package com.programmers.musicapp.service;

import com.programmers.musicapp.dto.request.MusicCreateRequest;
import com.programmers.musicapp.dto.request.MusicUpdateRequest;
import com.programmers.musicapp.dto.response.MusicResponse;
import com.programmers.musicapp.entity.Music;
import com.programmers.musicapp.repository.MusicRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicService {

    private final MusicRepository musicRepository;

    public MusicResponse create(MusicCreateRequest request) {
        Music music = musicRepository.save(request.toEntity());

        return MusicResponse.fromEntity(music);
    }

    public List<MusicResponse> findAllMusics() {
        return musicRepository.findAllMusics().stream()
                .map(MusicResponse::fromEntity)
                .toList();
    }

    public MusicResponse findMusicById(long musicId) {
        Music music = getMusicOrThrow(musicId);

        return MusicResponse.fromEntity(music);
    }

    public MusicResponse update(long musicId, MusicUpdateRequest request) {
        Music music = getMusicOrThrow(musicId);
        Music updatedMusic = musicRepository.update(request.toEntity(musicId, music.getCreatedDatetime()));

        return MusicResponse.fromEntity(updatedMusic);
    }

    private Music getMusicOrThrow(long musicId) {
        return musicRepository.findMusicById(musicId)
                .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 음악이 없습니다."));
    }
}
