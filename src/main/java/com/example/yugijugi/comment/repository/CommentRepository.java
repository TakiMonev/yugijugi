package com.example.yugijugi.comment.repository;

import com.example.yugijugi.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.facility.id = (SELECT f.id FROM Facility f WHERE f.id = :id)")
    public List<Comment> findCommentsByFacilityId(@Param("id") Long id);
}
