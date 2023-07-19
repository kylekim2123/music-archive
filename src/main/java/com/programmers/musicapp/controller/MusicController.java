package com.programmers.musicapp.controller;

import com.programmers.musicapp.dto.request.MusicCreateRequest;
import com.programmers.musicapp.dto.response.MusicResponse;
import com.programmers.musicapp.dto.response.ResponseWrapper;
import com.programmers.musicapp.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/musics")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseWrapper<MusicResponse> create(@RequestBody MusicCreateRequest request) {
        MusicResponse response = musicService.create(request);

        return ResponseWrapper.of(HttpStatus.CREATED.value(), "success", response);
    }
}
