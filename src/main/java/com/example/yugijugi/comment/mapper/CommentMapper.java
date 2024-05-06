package com.example.yugijugi.comment.mapper;

import com.example.yugijugi.comment.dto.CommentDto;
import com.example.yugijugi.comment.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public static Comment toEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setUserName(commentDto.getUserName());
        comment.setContent(commentDto.getContent());

        return comment;
    }
}
