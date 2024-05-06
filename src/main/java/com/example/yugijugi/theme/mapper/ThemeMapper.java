package com.example.yugijugi.theme.mapper;

import com.example.yugijugi.theme.dto.ThemeDto;
import com.example.yugijugi.theme.entity.Theme;
import org.springframework.stereotype.Component;

@Component
public class ThemeMapper {
    public static Theme toEntity(ThemeDto themeDto) {
        Theme theme = new Theme();
        theme.setId(themeDto.getId());
        theme.setName(themeDto.getName());
        theme.setPassword(themeDto.getPassword());
        theme.setDescription(themeDto.getDescription());
        return theme;
    }
}
