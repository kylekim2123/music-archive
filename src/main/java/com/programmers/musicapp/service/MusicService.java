package com.programmers.musicapp.service;

import com.programmers.musicapp.dto.request.MusicCreateRequest;
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

    public MusicResponse findMusicById(int musicId) {
        Music music = musicRepository.findMusicById(musicId)
                .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 음악이 없습니다."));

        return MusicResponse.fromEntity(music);
    }
}
