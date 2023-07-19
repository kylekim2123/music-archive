package com.programmers.musicapp.service;

import com.programmers.musicapp.dto.request.MusicCreateRequest;
import com.programmers.musicapp.dto.response.MusicResponse;
import com.programmers.musicapp.entity.Music;
import com.programmers.musicapp.repository.MusicRepository;
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
}
