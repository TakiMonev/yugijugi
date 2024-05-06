package com.example.yugijugi.facility.controller;

import com.example.yugijugi.comment.entity.Comment;
import com.example.yugijugi.comment.service.CommentService;
import com.example.yugijugi.facility.dto.FacilityDto;
import com.example.yugijugi.facility.entity.Facility;
import com.example.yugijugi.facility.service.FacilityService;
import com.example.yugijugi.theme.entity.Theme;
import com.example.yugijugi.theme.service.ThemeService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class FacilityController {
    private FacilityService facilityService;
    private ThemeService themeService;
    private CommentService commentService;

    @Autowired
    public FacilityController(FacilityService facilityService,
                              ThemeService themeService,
                              CommentService commentService) {
        this.facilityService = facilityService;
        this.themeService = themeService;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String getAllBoards(Model model) {
        List<Facility> facilities = facilityService.getAllFacilities();
        model.addAttribute("facilities", facilities);

        List<Theme> themes = themeService.getAllThemes();
        model.addAttribute("themes", themes);

        return "home";
    }

    @GetMapping("/location/{location}")
    public String getFacilityByLocation(@PathVariable("location") String location,
                                                    Model model) {
        List<Facility> foundByLocation = facilityService.getFacilityByLocation(location);
        model.addAttribute("facilities", foundByLocation);
        return "location";
    }

    @GetMapping("/post")
    public String makePost(Model model) {
        model.addAttribute("themes", themeService.getAllThemes());
        model.addAttribute("facility_id", null);
        model.addAttribute("title_value", null);
        model.addAttribute("content_value", null);
        model.addAttribute("location_value", null);
        model.addAttribute("theme_value", null);

        return "post";
    }

    @PostMapping("/post")
    public String submitPost(@RequestBody FacilityDto facilityDto) {

        FacilityDto newFacility = new FacilityDto();
        newFacility.setTitle(facilityDto.getTitle());
        newFacility.setPassword(facilityDto.getPassword());
        newFacility.setContent(facilityDto.getContent());
        newFacility.setLocation(facilityDto.getLocation());
        newFacility.setTheme(facilityDto.getTheme());
        newFacility.setLikes("0");
        newFacility.setDate(new Date());

        facilityService.createFacility(newFacility);

        return "home";
    }

    @GetMapping("/facility/{facility_id}")
    public String getFacility(@PathVariable("facility_id") Long facility_id,
                              Model model) {
        try {
                Optional<Facility> facility = facilityService.getFacility(facility_id);
                Optional<Theme> themeFoundByThemeName = themeService.getThemeByName(facility.get().getTheme());
                model.addAttribute("theme", themeFoundByThemeName.get());

                if (facility.isPresent()) {
                    model.addAttribute("facility", facility.get());
                } else {
                    return "시설이 존재하지 않습니다.";
            }

            List<Comment> comments = commentService.getCommentsByFacilityId(facility_id);
            model.addAttribute("comments", comments);

            return "facility";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/facility/update/{facility_id}")
    public String getFacilityToUpdate(@PathVariable("facility_id") Long facility_id,
                              Model model) {
        try {
            Optional<Facility> facility = facilityService.getFacility(facility_id);
            model.addAttribute("themes", themeService.getAllThemes());

            if (facility.isPresent()) {
                model.addAttribute("facility_id", facility.get().getId());
                model.addAttribute("title_value", facility.get().getTitle());
                model.addAttribute("content_value", facility.get().getContent());
                model.addAttribute("location_value", facility.get().getLocation());
                model.addAttribute("theme_value", facility.get().getTheme());

                return "post";
            } else {
                return "시설이 존재하지 않습니다.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/facility/update/{facility_id}")
    public ResponseEntity updateFacility(@PathVariable("facility_id") Long facility_id,
                                         @RequestBody FacilityDto facilityDto,
                                         Model model) {
        try {
            Optional<Facility> facilityUpdated = facilityService.getFacility(facility_id);
            if (facilityUpdated.isPresent()) {
                Facility facility = facilityUpdated.get();
                model.addAttribute("facility", facilityService.getFacility(facility_id).get());
                model.addAttribute("theme", themeService.getThemeByName(facility.getTheme()));
                System.out.println(facilityDto.getPassword());

                if (!facility.getPassword().equals(facilityDto.getPassword())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호를 잘못 입력하셨습니다.");
                }
                facility.setTitle(facilityDto.getTitle());
                facility.setLocation(facilityDto.getLocation());
                facility.setTheme(facilityDto.getTheme());
                facility.setContent(facilityDto.getContent());
                facilityService.updateFacility(Optional.ofNullable(facility), facility_id);

                model.addAttribute("facility", facility);
                Optional<Theme> themeOptional = themeService.getThemeByName(facility.getTheme());
                if (themeOptional.isPresent()) {
                    model.addAttribute("theme", themeOptional.get());
                }
                return ResponseEntity.ok("수정 성공");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("시설이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 오류가 발생했습니다.");
        }
    }

    @PostMapping("/facility/delete/{facility_id}")
    public ResponseEntity deleteFacility(@RequestBody FacilityDto facilityDto,
                                         @PathVariable("facility_id") Long facility_id,
                                         Model model) {

        String password = facilityDto.getPassword();
        Optional<Facility> toDelete = facilityService.getFacility(facility_id);
        model.addAttribute("facility_id", facility_id);

        System.out.println(password);

        try {
            if (toDelete.isPresent()) {
                if (toDelete.get().getPassword().equals(password)) {
                    Optional<Theme> themeToGetThemeId = themeService.getThemeByName(toDelete.get().getTheme());
                    model.addAttribute("theme_id", themeToGetThemeId.get().getId());
                    facilityService.deleteFacility(password, facility_id);

                    return ResponseEntity.ok("삭제 성공");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호를 잘못 입력하셨습니다.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("시설이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 오류가 발생했습니다.");
        }
    }
}
