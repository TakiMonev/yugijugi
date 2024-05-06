package com.example.yugijugi.comment.entity;

import com.example.yugijugi.facility.entity.Facility;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
    private String userName;
    private String content;

    public void setFacility(Facility facility) {
        this.facility = facility;
        facility.getComments().add(this);
    }
}
