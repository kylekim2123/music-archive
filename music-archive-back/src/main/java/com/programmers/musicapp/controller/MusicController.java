package com.programmers.musicapp.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.programmers.musicapp.dto.request.CommentCreateRequest;
import com.programmers.musicapp.dto.request.MusicCreateRequest;
import com.programmers.musicapp.dto.request.MusicUpdateRequest;
import com.programmers.musicapp.dto.response.CommentResponse;
import com.programmers.musicapp.dto.response.MusicResponse;
import com.programmers.musicapp.dto.response.ResponseWrapper;
import com.programmers.musicapp.service.MusicService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @ResponseStatus(CREATED)
    public ResponseWrapper<MusicResponse> createMusic(@RequestBody MusicCreateRequest request) {
        MusicResponse response = musicService.createMusic(request);

        return ResponseWrapper.of(CREATED.value(), "success", response);
    }

    @GetMapping
    public ResponseWrapper<List<MusicResponse>> findAllMusics() {
        List<MusicResponse> response = musicService.findAllMusics();

        return ResponseWrapper.of(OK.value(), "success", response);
    }

    @GetMapping("/{musicId}")
    public ResponseWrapper<MusicResponse> findMusicById(@PathVariable long musicId) {
        MusicResponse response = musicService.findMusicById(musicId);

        return ResponseWrapper.of(OK.value(), "success", response);
    }

    @PutMapping("/{musicId}")
    public ResponseWrapper<MusicResponse> updateMusic(@PathVariable long musicId, @RequestBody MusicUpdateRequest request) {
        MusicResponse response = musicService.updateMusic(musicId, request);

        return ResponseWrapper.of(OK.value(), "success", response);
    }

    @DeleteMapping("/{musicId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteMusicById(@PathVariable long musicId) {
        musicService.deleteMusicById(musicId);
    }

    @PostMapping("/{musicId}/comments")
    @ResponseStatus(CREATED)
    public ResponseWrapper<CommentResponse> createComment(@PathVariable long musicId, @RequestBody CommentCreateRequest request) {
        CommentResponse response = musicService.createComment(musicId, request);

        return ResponseWrapper.of(CREATED.value(), "success", response);
    }

    @GetMapping("/{musicId}/comments")
    public ResponseWrapper<List<CommentResponse>> findCommentsByMusicId(@PathVariable long musicId) {
        List<CommentResponse> response = musicService.findCommentsByMusicId(musicId);

        return ResponseWrapper.of(OK.value(), "success", response);
    }

    @DeleteMapping("/{musicId}/comments/{commentId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCommentById(@PathVariable long commentId) {
        musicService.deleteCommentById(commentId);
    }
}
