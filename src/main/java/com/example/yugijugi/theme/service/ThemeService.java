package com.example.yugijugi.theme.service;

import com.example.yugijugi.theme.dto.ThemeDto;
import com.example.yugijugi.theme.entity.Theme;
import com.example.yugijugi.theme.mapper.ThemeMapper;
import com.example.yugijugi.theme.repository.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {
    private ThemeRepository themeRepository;
    private ThemeMapper themeMapper;

    public ThemeService(ThemeRepository themeRepository, ThemeMapper themeMapper) {
        this.themeRepository = themeRepository;
        this.themeMapper = themeMapper;
    }

    public List<Theme> getAllThemes() { return themeRepository.findAll(); }

    public Optional<Theme> getTheme(Long theme_id) { return themeRepository.findById(theme_id); }

    public Optional<Theme> getThemeByName(String name) { return themeRepository.findThemeByName(name); }

    public void createTheme(ThemeDto themeDto) {
        Theme theme = themeMapper.toEntity(themeDto);
        themeRepository.save(theme);
    }

    public void deleteTheme(Long id) {
        Optional<Theme> themeToDelete = themeRepository.findById(id);
        themeRepository.delete(themeToDelete.get());
    }
}
