package com.example.yugijugi.facility.service;

import com.example.yugijugi.facility.dto.FacilityDto;
import com.example.yugijugi.facility.entity.Facility;
import com.example.yugijugi.facility.mapper.FacilityMapper;
import com.example.yugijugi.facility.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class FacilityService {
    private FacilityRepository facilityRepository;
    private FacilityMapper facilityMapper;

    @Autowired
    public FacilityService(FacilityRepository facilityRepository, FacilityMapper facilityMapper) {
        this.facilityRepository = facilityRepository;
        this.facilityMapper = facilityMapper;
    }

    public List<Facility> getAllFacilities() { return facilityRepository.findAll(); }

    public List<Facility> getFacilityByLocation(String location) {
        return facilityRepository.findFacilityByLocation(location);
    }

    public List<Facility> getFacilityByTheme(String theme) {
        return facilityRepository.findFacilityByTheme(theme);
    }

    public Optional<Facility> getFacility(Long facility_id) { return facilityRepository.findById(facility_id); }

    public void createFacility(FacilityDto facilityDto) {
        Facility facility = facilityMapper.toEntity(facilityDto);
        facilityRepository.save(facility);
    }

    public Optional<Facility> updateFacility(Optional<Facility> facility, Long id) {
        if (facility.get().getId() == id) {
            facilityRepository.save(facility.orElse(null));
            return facility;
        } else {
            return null;
        }
    }

    public Optional<Facility> deleteFacility(String password, Long id) {
        Optional<Facility> facility = facilityRepository.findFacilityByPasswordAndId(password, id);

        if (facility.isPresent()) {
            facilityRepository.delete(facility.orElse(null));
            return facility;
        } else {
            return null;
        }
    }
}
