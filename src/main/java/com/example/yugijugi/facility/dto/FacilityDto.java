package com.example.yugijugi.facility.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacilityDto {
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
}
