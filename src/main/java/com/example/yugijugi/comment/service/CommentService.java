package com.example.yugijugi.comment.service;

import com.example.yugijugi.comment.dto.CommentDto;
import com.example.yugijugi.comment.entity.Comment;
import com.example.yugijugi.comment.mapper.CommentMapper;
import com.example.yugijugi.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private CommentMapper commentMapper;

    @Autowired
    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public void createComment(CommentDto commentDto) {
        Comment comment = commentMapper.toEntity(commentDto);
        commentRepository.save(comment);
    }

    // 이거보다 좋은 방법 없나요?
    public void createCommentByEntity(Comment comment) {
        commentRepository.save(comment);
    }

    public Optional<Comment> getCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment;
    }

    public List<Comment> getCommentsByFacilityId(Long facility_id) {
        List<Comment> commentsFoundByFacilityId = commentRepository.findCommentsByFacilityId(facility_id);
        return commentsFoundByFacilityId;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public void updateCommentContent(Long id, String content) {
        Optional<Comment> commentToUpdate = commentRepository.findById(id);
        if (commentToUpdate.isPresent()) {
            Comment updatedComment = commentToUpdate.get();
            updatedComment.setContent(content);
            commentRepository.save(updatedComment);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 댓글을 찾을 수 없습니다.");
        }
    }


    public void deleteComment(Long id) {
        Optional<Comment> commentToDelete = commentRepository.findById(id);
        commentRepository.delete(commentToDelete.get());
    }
}
