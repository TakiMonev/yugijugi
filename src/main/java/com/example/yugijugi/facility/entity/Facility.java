package com.example.yugijugi.facility.entity;

import com.example.yugijugi.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_id")
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String content;
    private String location;
    private String theme;
    private String likes;
    private Date date;
    @OneToMany(mappedBy = "facility")
    List<Comment> comments = new ArrayList<>();
}
