package com.example.yugijugi.comment.controller;

import com.example.yugijugi.comment.dto.CommentDto;
import com.example.yugijugi.comment.entity.Comment;
import com.example.yugijugi.comment.service.CommentService;
import com.example.yugijugi.facility.entity.Facility;
import com.example.yugijugi.facility.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/comment")
public class CommentController {
    private CommentService commentService;
    private FacilityService facilityService;

    @Autowired
    public CommentController(CommentService commentService, FacilityService facilityService) {
        this.commentService = commentService;
        this.facilityService = facilityService;
    }

    @PostMapping("/{facility_id}")
    public String addComment(@PathVariable("facility_id") Long facility_id,
                             @RequestParam("content") String content) {
        Optional<Facility> facility = facilityService.getFacility(facility_id);

        Comment comment = new Comment();
        comment.setUserName("댓글");
        comment.setContent(content);
        comment.setFacility(facility.get());

        commentService.createCommentByEntity(comment);

        return "redirect:/facility/" + facility.get().getId();
    }

    @PostMapping("/update/{facility_id}/{comment_id}")
    public String updateComment(@PathVariable("facility_id") Long facility_id,
                                @PathVariable("comment_id") Long comment_id,
                                @RequestBody CommentDto commentDto) {
        Optional<Facility> facility = facilityService.getFacility(facility_id);
        Optional<Comment> commentToUpdate = commentService.getCommentById(comment_id);

        if (commentToUpdate.isPresent()) {
            commentService.updateCommentContent(comment_id, commentDto.getContent());
            return "redirect:/facility/" + facility.get().getId();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 댓글을 찾을 수 없습니다.");
        }
    }

    @PostMapping("/delete/{facility_id}/{comment_id}")
    public String deleteComment(@PathVariable("facility_id") Long facility_id,
                                @PathVariable("comment_id") Long comment_id) {
        Optional<Facility> facility = facilityService.getFacility(facility_id);
        Optional<Comment> commentToDelete = commentService.getCommentById(comment_id);

        if (commentToDelete.isPresent()) {
            commentService.deleteComment(comment_id);
            return "redirect:/facility/" + facility.get().getId();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 댓글을 찾을 수 없습니다.");
        }
    }
}
