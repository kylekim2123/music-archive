package com.programmers.musicapp.repository;

import com.programmers.musicapp.entity.Comment;
import java.util.List;

public interface CommentRepository {

    Comment save(Comment comment);

    List<Comment> findCommentsByMusicId(long musicId);
}
