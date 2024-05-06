package com.example.yugijugi.facility.mapper;

import com.example.yugijugi.facility.dto.FacilityDto;
import com.example.yugijugi.facility.entity.Facility;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class FacilityMapper {
    public static Facility toEntity(FacilityDto facilityDto) {
        Facility facility = new Facility();
        facility.setTitle(facilityDto.getTitle());
        facility.setPassword(facilityDto.getPassword());
        facility.setContent(facilityDto.getContent());
        facility.setTheme(facilityDto.getTheme());
        facility.setLocation(facilityDto.getLocation());
        facility.setLikes(facilityDto.getLikes());
        facility.setDate(facilityDto.getDate()); // 확신이 안섬

        return facility;
    }
}
