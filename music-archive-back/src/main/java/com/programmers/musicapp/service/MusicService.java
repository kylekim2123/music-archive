package com.programmers.musicapp.service;

import static com.programmers.musicapp.exception.ExceptionRule.NOT_FOUND_404;

import com.programmers.musicapp.dto.request.CommentCreateRequest;
import com.programmers.musicapp.dto.request.MusicCreateRequest;
import com.programmers.musicapp.dto.request.MusicUpdateRequest;
import com.programmers.musicapp.dto.response.CommentResponse;
import com.programmers.musicapp.dto.response.MusicResponse;
import com.programmers.musicapp.entity.Comment;
import com.programmers.musicapp.entity.Music;
import com.programmers.musicapp.exception.CommentException;
import com.programmers.musicapp.exception.MusicException;
import com.programmers.musicapp.repository.CommentRepository;
import com.programmers.musicapp.repository.MusicRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicService {

    private final MusicRepository musicRepository;
    private final CommentRepository commentRepository;

    public MusicResponse createMusic(MusicCreateRequest request) {
        Music music = musicRepository.save(request.toEntity());

        return MusicResponse.fromEntity(music);
    }

    public List<MusicResponse> findAllMusics() {
        return musicRepository.findAllMusics().stream()
                .map(MusicResponse::fromEntity)
                .toList();
    }

    public List<MusicResponse> findCustomMusics() {
        return musicRepository.findCustomMusics().stream()
                .map(MusicResponse::fromEntity)
                .toList();
    }

    public List<MusicResponse> findTopMusics() {
        return musicRepository.findTopMusics().stream()
                .map(MusicResponse::fromEntity)
                .toList();
    }

    public MusicResponse findMusicById(long musicId) {
        Music music = getMusicOrThrow(musicId);

        return MusicResponse.fromEntity(music);
    }

    public MusicResponse updateMusic(long musicId, MusicUpdateRequest request) {
        Music music = getMusicOrThrow(musicId);
        Music updatedMusic = musicRepository.update(request.toEntity(musicId, music.getCreatedDatetime()));

        return MusicResponse.fromEntity(updatedMusic);
    }

    public void deleteMusicById(long musicId) {
        getMusicOrThrow(musicId);
        musicRepository.deleteById(musicId);
    }

    public CommentResponse createComment(long musicId, CommentCreateRequest request) {
        Comment comment = commentRepository.save(request.toEntity(musicId));

        return CommentResponse.fromEntity(comment);
    }

    public List<CommentResponse> findCommentsByMusicId(long musicId) {
        getMusicOrThrow(musicId);

        return commentRepository.findCommentsByMusicId(musicId).stream()
                .map(CommentResponse::fromEntity)
                .toList();
    }

    public void deleteCommentById(long musicId, long commentId) {
        getMusicOrThrow(musicId);

        commentRepository.findCommentById(commentId)
                .orElseThrow(() -> new CommentException(NOT_FOUND_404, String.valueOf(commentId)));

        commentRepository.deleteById(commentId);
    }

    private Music getMusicOrThrow(long musicId) {
        return musicRepository.findMusicById(musicId)
                .orElseThrow(() -> new MusicException(NOT_FOUND_404, String.valueOf(musicId)));
    }
}
