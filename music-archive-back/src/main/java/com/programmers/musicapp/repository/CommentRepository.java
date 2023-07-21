package com.programmers.musicapp.repository;

import com.programmers.musicapp.entity.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    List<Comment> findCommentsByMusicId(long musicId);

    Optional<Comment> findCommentById(long commentId);

    void deleteById(long commentId);
}
