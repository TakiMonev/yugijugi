package com.example.yugijugi.theme.controller;

import com.example.yugijugi.facility.entity.Facility;
import com.example.yugijugi.facility.service.FacilityService;
import com.example.yugijugi.theme.dto.ThemeDto;
import com.example.yugijugi.theme.entity.Theme;
import com.example.yugijugi.theme.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/theme")
public class ThemeController {
    private ThemeService themeService;
    private FacilityService facilityService;

    @Autowired
    public ThemeController(ThemeService themeService, FacilityService facilityService) {
        this.themeService = themeService;
        this.facilityService = facilityService;
    }

    @GetMapping("/{id}")
    public String getFacilityByTheme(@PathVariable("id") Long id,
                                     Model model) {
        Optional<Theme> themeFoundById = themeService.getTheme(id);
        model.addAttribute("name", themeFoundById.get().getName());
        if (themeFoundById.isPresent()) {
            List<Facility> facilitiesFoundByThemes = facilityService.getFacilityByTheme(themeFoundById.get().getName());
            model.addAttribute("facilities", facilitiesFoundByThemes);
            model.addAttribute("theme", themeFoundById);
            return "theme";
        } else {
            return "theme";
        }
    }

    @PostMapping
    public String createTheme(@ModelAttribute("themeDto") ThemeDto themeDto,
                              RedirectAttributes redirectAttributes) {
        if (themeService.getThemeByName(themeDto.getName()).isPresent()) {
            redirectAttributes.addAttribute("message", "테마가 이미 존재합니다");
            return "redirect:/";
        }
        themeService.createTheme(themeDto);
        redirectAttributes.addAttribute("message", "테마 저장 성공");
        return "redirect:/";
    }

    @PostMapping("/delete/{theme_id}")
    public ResponseEntity deleteTheme(@PathVariable("theme_id") Long theme_id,
                                      @RequestBody ThemeDto themeDto) {

        String password = themeDto.getPassword();

        try {
            Optional<Theme> themeToDelete = themeService.getTheme(theme_id);

            if (themeToDelete.isPresent()) {
                Theme theme = themeToDelete.get();

                if (password.equals(theme.getPassword())) {
                    themeService.deleteTheme(theme_id);
                    //return "redirect:/";
                    return ResponseEntity.status(200).body("삭제 성공");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 틀렸습니다.");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 테마를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 오류가 발생했습니다.");
        }
    }
}
