package com.example.yugijugi.facility.repository;

import com.example.yugijugi.facility.entity.Facility;
import com.example.yugijugi.theme.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    @Query("SELECT f FROM Facility f WHERE f.location = :location")
    public List<Facility> findFacilityByLocation(@Param("location") String location);

    @Query("SELECT f FROM Facility f WHERE f.theme = :theme")
    public List<Facility> findFacilityByTheme(@Param("theme") String theme);

    @Query("SELECT f FROM Facility f WHERE f.password = :password AND f.id = :id")
    public Optional<Facility> findFacilityByPasswordAndId(@Param("password") String password,
                                               @Param("id") Long id);
}
