package com.example.yugijugi.comment.dto;

import com.example.yugijugi.facility.entity.Facility;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
    private String userName;
    private String content;
}