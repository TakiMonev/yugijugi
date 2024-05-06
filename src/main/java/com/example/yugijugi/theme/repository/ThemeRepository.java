package com.example.yugijugi.theme.repository;

import com.example.yugijugi.theme.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
    @Query("SELECT t FROM Theme t WHERE t.name = :name")
    public Optional<Theme> findThemeByName(@Param("name") String name);
}